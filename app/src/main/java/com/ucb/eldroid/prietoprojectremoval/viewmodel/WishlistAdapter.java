package com.ucb.eldroid.prietoprojectremoval.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ucb.eldroid.prietoprojectremoval.R;
import com.ucb.eldroid.prietoprojectremoval.data.model.WishlistItem;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private final List<WishlistItem> items;

    public WishlistAdapter(List<WishlistItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_item, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        WishlistItem item = items.get(position);
        holder.itemName.setText(item.getName());
        holder.itemDetails.setText(item.getDetails());
        holder.itemPrice.setText(item.getPrice());

        // Attach Delete Button Listener
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClicked(item);
            }
        });

        // Attach Edit Button Listener
        holder.btnEdit.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEditClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class WishlistViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemDetails, itemPrice;
        Button btnDelete, btnEdit;

        WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDetails = itemView.findViewById(R.id.item_details);
            itemPrice = itemView.findViewById(R.id.item_price);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked(WishlistItem item);
    }

    private OnDeleteClickListener deleteListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    public interface OnEditClickListener {
        void onEditClicked(WishlistItem item);
    }

    private OnEditClickListener editListener;

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editListener = listener;
    }
}
