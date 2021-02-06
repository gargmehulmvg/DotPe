package com.crownstack.dotpesample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.crownstack.dotpesample.R;
import com.crownstack.dotpesample.constants.Constant;
import com.crownstack.dotpesample.constants.Utility;
import com.crownstack.dotpesample.interfaces.IItemClickListener;
import com.crownstack.dotpesample.model.response.ItemResponse;

import java.util.ArrayList;
import java.util.List;

public class BottomItemAdapter extends RecyclerView.Adapter<BottomItemAdapter.AvailableItemViewHolder> {

    private List<ItemResponse.CategoryItemObject> mAllItemList = new ArrayList<>();
    private final IItemClickListener mListener;

    public BottomItemAdapter(IItemClickListener listener) {
        mListener = listener;
    }

    public void setAllItemList(List<ItemResponse.CategoryItemObject> allItemList) {
        mAllItemList = allItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AvailableItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bottom_item, parent, false);
        return new AvailableItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableItemViewHolder holder, int position) {
        ItemResponse.CategoryItemObject item = mAllItemList.get(position);
        holder.percentageTextView.setText(String.format("%s%%", (item.getPrice() - item.getDiscountedPrice()) / 100));
        holder.nameTextView.setText(item.getName());
        holder.originalPriceTextView.setText(String.format("Rs %s", item.getPrice()));
        holder.discountPriceTextView.setText(String.format("Rs %s", item.getDiscountedPrice()));
        Utility.writeStrikeOffText(holder.originalPriceTextView);
    }

    @Override
    public int getItemCount() {
        return Math.min(mAllItemList.size(), 100);
    }

    class AvailableItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView discountPriceTextView;
        private final TextView originalPriceTextView;
        private final TextView percentageTextView;

        public AvailableItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            discountPriceTextView = itemView.findViewById(R.id.discountPriceTextView);
            originalPriceTextView = itemView.findViewById(R.id.originalPriceTextView);
            percentageTextView = itemView.findViewById(R.id.percentageTextView);
            itemView.findViewById(R.id.addTextView).setOnClickListener(view -> {
                if (null != mListener) {
                    mListener.onItemClickListener(getAdapterPosition(), Constant.MODE_ALL_CATEGORY);
                }
            });
        }
    }
}
