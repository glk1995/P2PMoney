package com.example.glk.p2pmoney.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.BaseFragment;
import com.example.glk.p2pmoney.util.UIUtils;
import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zgqdg on 2016/9/21.
 */
public class TouziFragment extends BaseFragment {

    @BindView(R.id.title_left)
    ImageView titleLeft;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right)
    ImageView titleRight;
    @BindView(R.id.tab_indictor)
    TabPageIndicator tabIndictor;
    @BindView(R.id.pager)
    ViewPager pager;

    private List<Fragment>  fragmentList = new ArrayList<>();

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return "";
    }

    @Override
    protected void initTitle() {
        titleTv.setText("我要投资");
        titleLeft.setVisibility(View.INVISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData(String content) {
        initFragment();
        pager.setAdapter(new MyAdapter(getFragmentManager()));
        //给pager设置tabPager指示器
        tabIndictor.setViewPager(pager);
    }

    private void initFragment() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommendFragment productRecommendFragment = new ProductRecommendFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommendFragment);
        fragmentList.add(productHotFragment);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_touzi;
    }


    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        //重写这个方法来显示标题
        @Override
        public CharSequence getPageTitle(int position) {
            return UIUtils.getStringArr(R.array.touzi_tab)[position];
        }
    }


}
