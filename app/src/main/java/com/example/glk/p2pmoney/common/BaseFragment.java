package com.example.glk.p2pmoney.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glk.p2pmoney.ui.LoadingPage;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/9/26.
 */


public abstract class BaseFragment extends Fragment{


    private LoadingPage loadingPage;

    //因为进行了抽取，所以子类中不需要再写，父类已经实现了子类需要做的相同的事情，子类只需要实现他们的不同部分就行了
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这边传递的是Fragment所在activity的context对象
        loadingPage =  new LoadingPage(container.getContext()) {
            @Override
            public int LayoutId() {
                return getLayoutId();
            }

            @Override
            protected void OnSuccess(LoadingPage.ResultState resultState,View successView) {
                //ButterKnife.bind(this, view);
                //View view = UIUtils.getXmlView(getLayoutId());
                //原来是this,view参数，现在因为this指的是loadingPage，所以需要BaseFragment.this获取
                // 然后因为OnSuccess传递了成功的view，所以绑定
                ButterKnife.bind(BaseFragment.this,successView);
                initTitle();
                //初始化数据需要content参数，所以这边传入resultState的content，下面要在方法里加一个参数
                initData(resultState.getContent());
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };
        return loadingPage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
//         这里是因为测试的时候用本地服务器加载太快使用延时2秒后加载
//        UIUtils.gethandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                show();
//            }
//        },1000);
    }

    private void show() {
        loadingPage.show();
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //主要做头部显示的内容
    protected abstract void initTitle();

    //主要做数据的初始化，比如home_frgament要向服务器请求数据进行处理
    protected abstract void initData(String content);

    //传layout的ID给onCreateView，显示不同的页面
    public abstract int getLayoutId();

}
