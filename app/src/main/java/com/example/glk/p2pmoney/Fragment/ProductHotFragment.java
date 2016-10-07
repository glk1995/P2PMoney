package com.example.glk.p2pmoney.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.ui.FlowLayout;
import com.example.glk.p2pmoney.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/9/29.
 */

public class ProductHotFragment extends Fragment {
    @BindView(R.id.flowlayout)
    FlowLayout flowlayout;

    private String[] datas = new String[]{"新手计划", "乐享活系列90天计划", "钱包", "30天理财计划(加息2%)",
            "林业局投资商业经营与大捞一笔", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍",
            "Java培训老师自己周转", "HelloWorld", "C++-C-ObjectC-java", "Android vs ios",
            "算法与数据结构", "JNI与NDK", "team working"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_product_hot);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        for (String data : datas) {
            TextView tv = new TextView(getActivity());
            //因为默认的LayoutParams是ViewGounp的LayoutParams，而我们在FlowLayout中使用的是MarginLayout
            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mp.leftMargin = UIUtils.dp2px(10);
            mp.rightMargin = UIUtils.dp2px(10);
            mp.topMargin = UIUtils.dp2px(10);
            mp.bottomMargin = UIUtils.dp2px(10);
            tv.setLayoutParams(mp);
            tv.setText(data);
            flowlayout.addView(tv);
        }
    }
}
