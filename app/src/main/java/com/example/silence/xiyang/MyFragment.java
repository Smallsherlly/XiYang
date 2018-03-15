package com.example.silence.xiyang;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Silence on 2018/3/9.
 */

public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment1 = inflater.inflate(R.layout.fragment1, null);
        return fragment1;
    }
}
