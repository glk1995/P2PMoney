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
import com.example.glk.p2pmoney.ui.randomLayout.StellarMap;
import com.example.glk.p2pmoney.util.UIUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/9/29.
 */

public class ProductRecommendFragment extends Fragment {


    @BindView(R.id.stellarMap)
    StellarMap stellarMap;

    private String[] datas = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"
    };
    private Random random;

    private String[] one = new String[8];
    private String[] two = new String[8];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_product_recommand);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        int meddle = 8;
        for (int i = 0; i < 8; i++) {
            one[i] = datas[i];
        }

        for (int i = 0; i < 8; i++) {
            two[i] = datas[i + 8];
        }


        random = new Random();
        int padding = UIUtils.dp2px(5);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        stellarMap.setAdapter(new MyAdapter());
        //每组数据数据的搭配规则
        stellarMap.setRegularity(7, 9);
        //设置从哪一组开始做动画操作
        stellarMap.setGroup(0, true);
    }


    private class MyAdapter implements StellarMap.Adapter {

        //组的个数，有几组数据变化
        @Override
        public int getGroupCount() {
            return 2;
        }

        //每组对应多少个数据，这边两边都是8条数据
        @Override
        public int getCount(int group) {
            return 8;
        }

        //每组对应的8个View对象
        @Override
        public View getView(int group, int position, View convertView) {
            TextView tv = new TextView(getActivity());
            int r = random.nextInt(210);
            int g = random.nextInt(210);
            int b = random.nextInt(210);
            tv.setTextColor(Color.rgb(r, g, b));
            tv.setTextSize(UIUtils.dp2px(8) + random.nextInt(8));
            if (group == 0) {
                tv.setText(one[position]);
            } else {
                tv.setText(two[position]);
            }

            return tv;
        }

        //下一组将要出现的动画组的索引，这边只要小于2
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 1;
        }
    }
}
