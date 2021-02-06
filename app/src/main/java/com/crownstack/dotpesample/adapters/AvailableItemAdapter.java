package com.crownstack.dotpesample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.crownstack.dotpesample.R;
import com.crownstack.dotpesample.constants.Constant;
import com.crownstack.dotpesample.constants.Utility;
import com.crownstack.dotpesample.interfaces.IItemClickListener;
import com.crownstack.dotpesample.model.response.ItemResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AvailableItemAdapter extends RecyclerView.Adapter<AvailableItemAdapter.AvailableItemViewHolder> {

    private List<ItemResponse.CategoryItemObject> mAvailableItemList = new ArrayList<>();
    private final IItemClickListener mListener;

    public AvailableItemAdapter(IItemClickListener listener) {
        mListener = listener;
    }

    public void setAvailableItemList(List<ItemResponse.CategoryItemObject> availableItemList) {
        mAvailableItemList = availableItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AvailableItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.available_item, parent, false);
        return new AvailableItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableItemViewHolder holder, int position) {
        ItemResponse.CategoryItemObject item = mAvailableItemList.get(position);
        if (Utility.isNotEmpty(item.getImageUrl())) {
            Picasso.get().load(item.getImageUrl()).into(holder.availableItemImageView);
        }
        holder.availableItemDescription.setText(item.getName());
        holder.availableItemPrice.setText(String.format("Rs %s", item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return Math.min(mAvailableItemList.size(), 10);
    }

    class AvailableItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView availableItemImageView;
        private final TextView availableItemDescription;
        private final TextView availableItemPrice;

        public AvailableItemViewHolder(@NonNull View itemView) {
            super(itemView);
            availableItemImageView = itemView.findViewById(R.id.availableItemImageView);
            availableItemDescription = itemView.findViewById(R.id.availableItemDescription);
            availableItemPrice = itemView.findViewById(R.id.availableItemPrice);
            itemView.findViewById(R.id.addTextView).setOnClickListener(view -> {
                if (null != mListener) {
                    mListener.onItemClickListener(getAdapterPosition(), Constant.MODE_ALL_CATEGORY);
                }
            });
        }
    }
}
