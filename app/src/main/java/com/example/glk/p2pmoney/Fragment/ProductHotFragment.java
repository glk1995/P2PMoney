package com.example.glk.p2pmoney.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.util.UIUtils;

/**
 * Created by zgqdg on 2016/9/29.
 */

public class ProductHotFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_product_hot);

        return view;
    }
}
