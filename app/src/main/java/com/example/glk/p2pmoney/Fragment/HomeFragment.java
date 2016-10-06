package com.example.glk.p2pmoney.Fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.bean.Image;
import com.example.glk.p2pmoney.bean.Index;
import com.example.glk.p2pmoney.bean.Product;
import com.example.glk.p2pmoney.common.AppNetConfig;
import com.example.glk.p2pmoney.common.BaseFragment;
import com.example.glk.p2pmoney.ui.MyScrollView;
import com.example.glk.p2pmoney.ui.RoundProgress;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;


/**
 * Created by zgqdg on 2016/9/21.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.vp_barner)
    ViewPager vpBarner;
    @BindView(R.id.circle_barner)
    CirclePageIndicator circleBarner;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.p_progress)
    RoundProgress pProgress;
    @BindView(R.id.p_yearlv)
    TextView pYearlv;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.myscrollview)
    MyScrollView myscrollview;

    private Index index;
    //首页进度条进度值
    private int totalProgress;

    @Override
    protected RequestParams getParams() {
        //不需要参数，这里返回一个空的对象
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected void initTitle() {
        titleLeft.setVisibility(View.INVISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData(String content) {
        if(!TextUtils.isEmpty(content)){
            index = new Index();
            JSONObject jsonObject = JSON.parseObject(content);
            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);

            String imageArr = jsonObject.getString("imageArr");
            List<Image> imageList = JSON.parseArray(imageArr, Image.class);
            index.product = product;
            index.imageList = imageList;

            //解析完数据后，现在要适配数据
            vpBarner.setAdapter(new MyAdapter());

            //viewpager交给指示器，关联起来
            circleBarner.setViewPager(vpBarner);
            totalProgress = Integer.parseInt(index.product.progress);
            new Thread(runnable).start();
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int tempProgress = 0;
            try {
                while(tempProgress <= totalProgress){
                    //调用RoundProgress对象的setProgress方法，重绘进度
                    pProgress.setProgress(tempProgress);
                    tempProgress++;
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return index.imageList == null ? 0 : index.imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //是否由对象生成界面
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String imageUrl = index.imageList.get(position).IMAURL;
            //通过对象生成页面
            ImageView imageView = new ImageView(getActivity());
            //设置缩放类型
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getActivity()).load(imageUrl).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
