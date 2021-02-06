package com.crownstack.dotpesample.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crownstack.dotpesample.R;
import com.crownstack.dotpesample.adapters.AvailableItemAdapter;
import com.crownstack.dotpesample.adapters.BottomItemAdapter;
import com.crownstack.dotpesample.constants.ToolBarManager;
import com.crownstack.dotpesample.constants.Utility;
import com.crownstack.dotpesample.interfaces.IItemClickListener;
import com.crownstack.dotpesample.model.response.ItemResponse;
import com.crownstack.dotpesample.retrofit.RetrofitApi;
import com.google.android.material.snackbar.Snackbar;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements IItemClickListener {

    private final AvailableItemAdapter mAvailableItemAdapter = new AvailableItemAdapter(this);
    private final BottomItemAdapter mBottomItemAdapter = new BottomItemAdapter(this);
    private List<ItemResponse.ItemCategory> mItemCategoryList = new ArrayList<>();
    private List<ItemResponse.CategoryItemObject> mAvailableItemList = new ArrayList<>();
    private List<ItemResponse.CategoryItemObject> mAllItemList = new ArrayList<>();
    private View cartContainer;
    private TextView cartCount;
    private int mCount;
    private boolean mIsDoubleBackPressClicked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.frament_home, container, false);
        setupToolbar();
        setupUI();
        initiateServerCall();
        return mContentView;
    }

    @Override
    public boolean onBackPressed() {
        if (mIsDoubleBackPressClicked) {
            super.onBackPressedToExit();
            return true;
        }
        Snackbar.make(mContentView, getString(R.string.back_press_msg), Snackbar.LENGTH_SHORT).show();
        mIsDoubleBackPressClicked = true;
        new Handler(Looper.getMainLooper()).postDelayed(() -> mIsDoubleBackPressClicked = false, 1500);
        return true;
    }

    private void initiateServerCall() {
        showProgress();
        new Thread(() -> {
            try {
                Call<ItemResponse> call = RetrofitApi.getAppServicesObject().getItemsServerCall();
                final Response<ItemResponse> response = call.execute();
                updateOnUiThread(() -> handleResponse(response));
            } catch (Exception e) {
                stopProgress();
                showToast(e.getMessage());
                Log.e("TAG", "initiateServerCall: ", e);
            }
        }).start();
    }

    private void handleResponse(Response<ItemResponse> response) {
        if (response.isSuccessful()) {
            ItemResponse itemResponse = response.body();
            if (itemResponse != null) {
                if (itemResponse.isStatus()) {
                    if (Utility.isNotEmpty(mItemCategoryList)) {
                        mItemCategoryList.clear();
                    }
                    mItemCategoryList = itemResponse.getItemCategoryList();
                    separateAvailableItemFromResponse();
                    separateAllItemFromResponse();
                } else {
                    showToast(itemResponse.getMessage());
                }
            }
        }
        stopProgress();
    }

    private void separateAvailableItemFromResponse() {
        if (Utility.isNotEmpty(mAvailableItemList)) {
            mAvailableItemList.clear();
        }
        for (ItemResponse.ItemCategory itemCategory : mItemCategoryList) {
            if (Utility.isEmpty(itemCategory.getCategory().getName())) {
                mAvailableItemList.addAll(itemCategory.getCategoryItemList());
            }
        }
        mAvailableItemAdapter.setAvailableItemList(mAvailableItemList);
    }

    private void separateAllItemFromResponse() {
        if (Utility.isNotEmpty(mAllItemList)) {
            mAllItemList.clear();
        }
        for (ItemResponse.ItemCategory itemCategory : mItemCategoryList) {
            if (Utility.isNotEmpty(itemCategory.getCategory().getName())) {
                mAllItemList.addAll(itemCategory.getCategoryItemList());
            }
        }
        mBottomItemAdapter.setAllItemList(mAllItemList);
    }

    private void setupToolbar() {
        ToolBarManager toolBar = ToolBarManager.getInstance();
        toolBar.hideToolBar(mActivity, false);
        toolBar.hideBackPressFromToolBar(mActivity, true);
        toolBar.setHeaderTitle(getString(R.string.app_name));
        toolBar.onBackPressed(this);
    }

    private void setupUI() {
        cartContainer = mContentView.findViewById(R.id.cartContainer);
        cartCount = mContentView.findViewById(R.id.cartCount);
        RecyclerView availableItemRecyclerView = mContentView.findViewById(R.id.availableItemRecyclerView);
        availableItemRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        availableItemRecyclerView.setAdapter(mAvailableItemAdapter);
        RecyclerView bottomItemRecyclerView = mContentView.findViewById(R.id.bottomItemRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        bottomItemRecyclerView.setLayoutManager(layoutManager);
        bottomItemRecyclerView.setAdapter(mBottomItemAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bottomItemRecyclerView.getContext(), layoutManager.getOrientation());
        bottomItemRecyclerView.addItemDecoration(dividerItemDecoration);
        mContentView.findViewById(R.id.viewCart).setOnClickListener(view -> launchFragment(new CartFragment(), true));
    }

    @Override
    public void onItemClickListener(int position, String mode) {
        cartContainer.setVisibility(View.VISIBLE);
        ++mCount;
        cartCount.setText(String.format("%d %s", mCount, (mCount == 1 ? " Item" : "Items")));
    }
}
