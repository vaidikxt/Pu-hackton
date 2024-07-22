package com.example.yourheathhero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;

import org.tensorflow.lite.support.image.ops.ResizeOp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import android.content.res.AssetFileDescriptor;

public class SearchFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int NUM_CLASSES = 5;  // Replace with actual number of classes
    private ImageView imageView;
    private TextView resultTextView;
    private Interpreter tflite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.image_view);
        resultTextView = view.findViewById(R.id.result_text_view);

        Button uploadImageButton = view.findViewById(R.id.upload_image_button);
        uploadImageButton.setOnClickListener(v -> openImageChooser());

        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                detectImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void detectImage(Bitmap bitmap) {
        // Preprocess the image to match the input size of the model
        int modelInputSize = 224;  // Replace with actual model input size
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, modelInputSize, modelInputSize, true);

        TensorImage tensorImage = new TensorImage();
        tensorImage.load(resizedBitmap);

        // Apply additional preprocessing steps if necessary
        ImageProcessor processor = new ImageProcessor.Builder()
                .add(new ResizeOp(modelInputSize, modelInputSize, ResizeOp.ResizeMethod.BILINEAR))
                .add(new NormalizeOp(0, 255))  // Example normalization, adjust as needed
                .build();
        TensorImage preprocessedImage = processor.process(tensorImage);

        // Assuming output size of 1xNUM_CLASSES for classification
        float[][] output = new float[1][NUM_CLASSES];

        tflite.run(preprocessedImage.getBuffer(), output);

        // Process the result
        String result = processResult(output);
        resultTextView.setText(result);
    }

    private String processResult(float[][] output) {
        // Implement your result processing here
        // Example assumes a single class prediction
        float maxProbability = 0;
        int predictedClass = -1;
        for (int i = 0; i < output[0].length; i++) {
            if (output[0][i] > maxProbability) {
                maxProbability = output[0][i];
                predictedClass = i;
            }
        }
        return "Predicted Class: " + predictedClass + " with probability: " + maxProbability;
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getActivity().getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
