package com.crownstack.dotpesample.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.crownstack.dotpesample.MainActivity;
import com.crownstack.dotpesample.R;

import static android.content.Context.MODE_PRIVATE;
import static com.crownstack.dotpesample.constants.Constant.SHARED_PREF_NAME;

public class BaseFragment extends Fragment implements View.OnClickListener {

    protected MainActivity mActivity;
    private ProgressDialog mProgressDialog;
    protected View mContentView;
    private static final String TAG = "BaseFragment";

    public void showProgress() {
        mActivity.runOnUiThread(() -> {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage(getString(R.string.please_wait));
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
        });
    }

    public void stopProgress() {
        try {
            mActivity.runOnUiThread(() -> {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    void updateOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public void storeStringDataInSharedPref(String keyName, String value) {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = mActivity.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
            editor.putString(keyName, value);
            editor.apply();
        }
    }

    public String getStringDataFromSharedPref(String keyName) {
        SharedPreferences prefs = mActivity.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return prefs.getString(keyName, "");
    }

    void showToast(String msg) {
        mActivity.runOnUiThread(() -> mActivity.showToast(msg));
    }

    void showSnackBar(String msg) {
        mActivity.runOnUiThread(() -> mActivity.showSnackBar(msg, mContentView));
    }

    @Override
    public void onClick(View view) {
        /*
         * Just a override method to invoke the back pressed of the fragments
         * */
    }

    protected void onBackPressedToExit() {
        if (mActivity != null) {
            mActivity.finish();
        }
    }

    protected void showMessage(String msg) {
        new AlertDialog.Builder(mActivity)
            .setMessage(msg)
            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
            .setIcon(android.R.drawable.ic_dialog_info)
            .show();
    }

    void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public boolean onBackPressed() {
        return false;
    }

    void launchFragment(Fragment fragment, boolean addBackStack) {
        mActivity.launchFragment(fragment, addBackStack);
    }

    public void clearFragmentBackStack() {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
}
