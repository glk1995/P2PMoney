package com.example.glk.p2pmoney.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.bean.Product;
import com.example.glk.p2pmoney.common.AppNetConfig;
import com.example.glk.p2pmoney.common.BaseHolder;
import com.example.glk.p2pmoney.common.MyBaseAdapter2;
import com.example.glk.p2pmoney.common.MySimpleBaseAdapter;
import com.example.glk.p2pmoney.ui.RoundProgress;
import com.example.glk.p2pmoney.util.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/9/29.
 */

public class ProductListFragment extends Fragment {


    @BindView(R.id.lv)
    ListView lv;

    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_product_list);

        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        client.post(AppNetConfig.PRODUCT, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                JSONObject jsonObject = JSON.parseObject(content);
                //如果得到数据为true，说明得到数据了
                if (jsonObject.getBoolean("success")) {
                    String data = jsonObject.getString("data");
                    List<Product> products = JSON.parseArray(data, Product.class);
                    lv.setAdapter(new MyAdapter(products));
                }

            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
            }
        });

    }

    private class MyAdapter3 extends MyBaseAdapter2<Product> {


        public MyAdapter3(List<Product> list) {
            super(list);
        }

        @Override
        protected BaseHolder getHolder() {
            return new MyViewHolder();
        }
    }

     class MyViewHolder extends BaseHolder<Product> {

        @BindView(R.id.p_name)
        TextView pName;
        @BindView(R.id.p_money)
        TextView pMoney;
        @BindView(R.id.p_yearlv)
        TextView pYearlv;
        @BindView(R.id.p_suodingdays)
        TextView pSuodingdays;
        @BindView(R.id.p_minzouzi)
        TextView pMinzouzi;
        @BindView(R.id.p_progresss)
        RoundProgress pProgresss;

        @Override
        public View initView() {
            View view = View.inflate(getActivity(), R.layout.item_product_list, null);

            return view;
        }

        @Override
        protected void refreshView() {
            Product data = getData();

            //设置数据
            pMinzouzi.setText(data.minTouMoney);
            pMoney.setText(data.money);
            pName.setText(data.name);
            pSuodingdays.setText(data.suodingDays);
            pYearlv.setText(data.yearLv);
            pProgresss.setProgress(Integer.parseInt(data.progress));

        }
    }

    private class MyAdapter extends MySimpleBaseAdapter<Product> {


        public MyAdapter(List<Product> list) {
            super(list);
        }

        @Override
        public View getYourView(int position, View convertView, ViewGroup parent) {
            Product product = list.get(position);
            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //设置数据
            viewHolder.pMinzouzi.setText(product.minTouMoney);
            viewHolder.pMoney.setText(product.money);
            viewHolder.pName.setText(product.name);
            viewHolder.pSuodingdays.setText(product.suodingDays);
            viewHolder.pYearlv.setText(product.yearLv);
            viewHolder.pProgresss.setProgress(Integer.parseInt(product.progress));
            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.p_name)
        TextView pName;
        @BindView(R.id.p_money)
        TextView pMoney;
        @BindView(R.id.p_yearlv)
        TextView pYearlv;
        @BindView(R.id.p_suodingdays)
        TextView pSuodingdays;
        @BindView(R.id.p_minzouzi)
        TextView pMinzouzi;
        @BindView(R.id.p_progresss)
        RoundProgress pProgresss;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
