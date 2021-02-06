package com.crownstack.dotpesample.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ItemResponse {

    @SerializedName("status")
    private boolean mIsStatus;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("next_page")
    private int mNextPage;
    @SerializedName("item_categories")
    private List<ItemCategory> mItemCategoryList = new ArrayList<>();

    public boolean isStatus() {
        return mIsStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getNextPage() {
        return mNextPage;
    }

    public List<ItemCategory> getItemCategoryList() {
        return mItemCategoryList;
    }

    public static class Category {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class ItemCategory {
        @SerializedName("category")
        private Category mCategory;
        @SerializedName("items")
        private List<CategoryItemObject> mCategoryItemList = new ArrayList<>();

        public Category getCategory() {
            return mCategory;
        }

        public List<CategoryItemObject> getCategoryItemList() {
            return mCategoryItemList;
        }
    }

    public static class CategoryItemObject {
        @SerializedName("id")
        private int mId;
        @SerializedName("store_id")
        private int mStoreId;
        @SerializedName("name")
        private String name;
        @SerializedName("price")
        private double price;
        @SerializedName("image_url")
        private String mImageUrl;
        @SerializedName("discounted_price")
        private double discountedPrice;
        @SerializedName("available")
        private int available;
        @SerializedName("description")
        private String description;
        @SerializedName("category")
        private Category category;

        public String getImageUrl() {
            return mImageUrl;
        }

        public double getDiscountedPrice() {
            return discountedPrice;
        }

        public int getAvailable() {
            return available;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return mId;
        }

        public int getStoreId() {
            return mStoreId;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public Category getCategory() {
            return category;
        }
    }

}
