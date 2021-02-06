package com.crownstack.dotpesample.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.crownstack.dotpesample.R;
import com.crownstack.dotpesample.constants.ToolBarManager;

public class CartFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.frament_cart, container, false);
        setupToolbar();
        return mContentView;
    }

    private void setupToolbar() {
        ToolBarManager toolBar = ToolBarManager.getInstance();
        toolBar.hideToolBar(mActivity, false);
        toolBar.hideBackPressFromToolBar(mActivity, false);
        toolBar.setHeaderTitle(getString(R.string.items_in_cart));
    }
}
