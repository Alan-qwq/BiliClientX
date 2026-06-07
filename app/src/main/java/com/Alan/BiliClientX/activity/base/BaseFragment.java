package com.Alan.BiliClientX.activity.base;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.Alan.BiliClientX.BiliTerminal;

public class BaseFragment extends Fragment {
    public void runOnUiThread(Runnable runnable) {
        if (isAdded()) requireActivity().runOnUiThread(runnable);
    }

    public Context getAppContext() {
        return BiliTerminal.context;
    }
}
