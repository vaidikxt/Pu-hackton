package com.example.yourheathhero;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager; // Import GridLayoutManager

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewProducts;
    private RecyclerView recyclerViewHotDeals;
    private ProductAdapter productAdapter;
    private HotDealAdapter hotDealAdapter;
    private List<Product> productList;
    private List<HotDeal> hotDealList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView for products with GridLayoutManager
        recyclerViewProducts = view.findViewById(R.id.frame4);
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        // Sample data for products
        productList = new ArrayList<>();
        productList.add(new Product("https://example.com/product1.jpg", "Paracitamol", "INR 200"));
        productList.add(new Product("https://example.com/product1.jpg", "Paracitamol", "INR 200"));
        productList.add(new Product("https://example.com/product1.jpg", "Paracitamol", "INR 200"));
        productList.add(new Product("https://example.com/product1.jpg", "Paracitamol", "INR 200"));

        // Set adapter for products
        productAdapter = new ProductAdapter(getContext(), productList);
        recyclerViewProducts.setAdapter(productAdapter);

        // Initialize RecyclerView for hot deals
        recyclerViewHotDeals = view.findViewById(R.id.frame3);
        recyclerViewHotDeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Sample data for hot deals
        hotDealList = new ArrayList<>();
        hotDealList.add(new HotDeal("https://example.com/hotdeal1.jpg"));
        // Add more hot deals

        // Set adapter for hot deals
        hotDealAdapter = new HotDealAdapter(getContext(), hotDealList);
        recyclerViewHotDeals.setAdapter(hotDealAdapter);

        return view;
    }
}
