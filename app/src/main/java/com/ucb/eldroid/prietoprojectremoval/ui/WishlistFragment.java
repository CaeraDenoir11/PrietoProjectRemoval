package com.ucb.eldroid.prietoprojectremoval.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ucb.eldroid.prietoprojectremoval.utils.ApiClient;
import com.ucb.eldroid.prietoprojectremoval.R;
import com.ucb.eldroid.prietoprojectremoval.viewmodel.WishlistAdapter;
import com.ucb.eldroid.prietoprojectremoval.data.WishlistApi;
import com.ucb.eldroid.prietoprojectremoval.data.model.WishlistItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment {

    private RecyclerView recyclerView;
    private WishlistAdapter adapter;
    private List<WishlistItem> itemList = new ArrayList<>();
    private WishlistApi wishlistApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new WishlistAdapter(itemList);
        recyclerView.setAdapter(adapter);
        adapter.setOnDeleteClickListener(this::showDeleteConfirmationDialog);
        adapter.setOnEditClickListener(this::showEditItemDialog);


        // Initialize Retrofit API
        wishlistApi = ApiClient.getClient().create(WishlistApi.class);

        // Load items from backend
        loadItems();

        // Set up FAB click listener
        View fab = view.findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(v -> showAddItemDialog());
        } else {
            throw new IllegalStateException("Floating Action Button not found in fragment_wishlist.xml");
        }

        return view;
    }

    private void loadItems() {
        wishlistApi.getItems().enqueue(new Callback<List<WishlistItem>>() {
            @Override
            public void onResponse(Call<List<WishlistItem>> call, Response<List<WishlistItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemList.clear();
                    itemList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(requireContext(), "Items loaded successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to load items. Check backend!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WishlistItem>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        EditText nameInput = dialogView.findViewById(R.id.item_name_input);
        EditText detailsInput = dialogView.findViewById(R.id.item_details_input);
        EditText priceInput = dialogView.findViewById(R.id.item_price_input);
        Button saveButton = dialogView.findViewById(R.id.save_button);

        AlertDialog dialog = builder.create();
        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String details = detailsInput.getText().toString().trim();
            String price = priceInput.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
                Toast.makeText(requireContext(), "Name and Price are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            WishlistItem newItem = new WishlistItem(name, details, price);
            addItem(newItem);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void addItem(WishlistItem item) {
        wishlistApi.addItem(item).enqueue(new Callback<WishlistItem>() {
            @Override
            public void onResponse(Call<WishlistItem> call, Response<WishlistItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Reload the entire list from the backend
                    loadItems();

                    Toast.makeText(requireContext(), "Item added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to add item. Check backend!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WishlistItem> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteItem(WishlistItem item) {
        wishlistApi.deleteItem(item.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    itemList.remove(item);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(requireContext(), "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to delete item!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showDeleteConfirmationDialog(WishlistItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, which) -> deleteItem(item))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
    private void showEditItemDialog(WishlistItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        EditText nameInput = dialogView.findViewById(R.id.item_name_input);
        EditText detailsInput = dialogView.findViewById(R.id.item_details_input);
        EditText priceInput = dialogView.findViewById(R.id.item_price_input);
        Button saveButton = dialogView.findViewById(R.id.save_button);

        // Pre-fill the dialog fields with the current item details
        nameInput.setText(item.getName());
        detailsInput.setText(item.getDetails());
        priceInput.setText(item.getPrice());

        AlertDialog dialog = builder.create();
        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String details = detailsInput.getText().toString().trim();
            String price = priceInput.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
                Toast.makeText(requireContext(), "Name and Price are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Log the data to confirm it's being sent correctly
            Log.d("WishlistFragment", "Editing item: name=" + name + ", details=" + details + ", price=" + price);

            // Update the item with new details
            item.setName(name);
            item.setDetails(details);
            item.setPrice(price);

            editItem(item); // Call edit API
            dialog.dismiss();
        });

        dialog.show();
    }
    private void editItem(WishlistItem item) {
        wishlistApi.editItem(item.getId(), item).enqueue(new Callback<WishlistItem>() {
            @Override
            public void onResponse(Call<WishlistItem> call, Response<WishlistItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loadItems(); // Reload the updated list
                    Toast.makeText(requireContext(), "Item updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to update item!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WishlistItem> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
