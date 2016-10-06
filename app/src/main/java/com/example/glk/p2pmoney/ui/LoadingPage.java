package com.example.glk.p2pmoney.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.util.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by zgqdg on 2016/9/26.
 */

//通过loadpage把一个Fragment要展示的界面的各个情况封装抽取到这个中，展示界面的时候统一使用loadPage
public abstract class LoadingPage extends FrameLayout {

    private Context mContext;
    //已经实现了异步请求的功能，在这里进行对网络请求的抽象，这样实现对page_current_state状态修稿
    AsyncHttpClient client = new AsyncHttpClient();

    //四个不同的加载状态
    private  static final int PAGE_LOADING_STATE = 1;
    private  static final int PAGE_ERROR_STATE = 2;
    private  static final int PAGE_EMPTY_STATE = 3;
    private  static final int PAGE_SUCCESS_STATE = 4;

    //这个是网络请求来觉得网络状态
    private  int page_current_state = 1;

    //四个不同状态的界面
    private View loadingView;
    private View errorView;
    private View emptyView;
    //这个不能直接addView，因为每个页面加载成功都不一样，而且必须请求成功之后，服务器必须返回请求之后有值才知道加载页面的样子
    private View successView;

    private LayoutParams lp;

    private ResultState resultState = null;


    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //这边的context是Fragment所在的activity的context对象
        this.mContext = context;
        init();
    }

    private void init() {
        //LayoutParams,无论哪个页面都需要MatchParent
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //因为无论哪个页面，这三个加载情况的页面都是一样的，所以直接addView
        if(loadingView == null){
            loadingView = UIUtils.getXmlView(R.layout.page_loading);
            addView(loadingView,lp);
        }
        if (errorView == null){
            errorView = UIUtils.getXmlView(R.layout.page_error);
            addView(errorView,lp);
        }

        if(emptyView == null){
            emptyView = UIUtils.getXmlView(R.layout.page_empty);
            addView(emptyView,lp);
        }
        //Safe因为是操作界面，必须在主线程
        showSafePage();
    }

    private void showSafePage() {
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {

        loadingView.setVisibility(page_current_state == PAGE_LOADING_STATE ? View.VISIBLE : View.GONE);
        errorView.setVisibility(page_current_state == PAGE_ERROR_STATE ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(page_current_state == PAGE_EMPTY_STATE ? View.VISIBLE : View.GONE);

        if (successView == null){
            //这边不用UIUtils，是因UIUtils的context对象不携带activity配置的样式
            successView = View.inflate(mContext,LayoutId(),null);
           //successView =  UIUtils.getXmlView(LayoutId());
            addView(successView,lp);
        }

        successView.setVisibility(page_current_state == PAGE_SUCCESS_STATE ? View.VISIBLE : View.GONE);
    }

    public abstract int LayoutId();

    //当Fragment调用show()方法，就使用网络请求。
    public void show(){
        //每次网络请求都会调用这个方法，所以必须对page_current_state进行重置
        if(page_current_state != PAGE_LOADING_STATE){
            page_current_state = PAGE_LOADING_STATE;
        }
        String url = url();
        //这里因为可能有不发送请求的Fragment，所以在这里要处理一下，这里以判断URL是否为空来判断
        if(TextUtils.isEmpty(url)){
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
        }else {

            //因为请求地址不一样，所以对地址进行抽象
            //因为每个请求的参数不一样，所以对请求进行抽象
            //回调方法
            client.get(url,params(),new AsyncHttpResponseHandler(){

                @Override
                public void onSuccess(String content) {
                    //对返回数据进行判断
                    if(TextUtils.isEmpty(content)){
                        resultState = ResultState.EMPTY;
                        resultState.setContent("");
                    }else {
                        resultState = ResultState.SUCCESS;
                        resultState.setContent(content);
                    }
                    loadPage();
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    //请求失败，指定请求状态为ERROR
                    resultState = resultState.ERROR;
                    //设置content为空
                    resultState.setContent("");

                    loadPage();
                }
            });
        }
    }

    private void loadPage() {
        switch (resultState){
            case ERROR:
                //当前状态设置为2，显示错误界面
                page_current_state = 2;
                break;
            case EMPTY:
                page_current_state = 3;
                //当前状态设置为3，显示空界面
                break;
            case SUCCESS:
                page_current_state = 4;
                //当前状态设置为4，显示为成功界面
                break;
        }
        showSafePage();
        //这个是因为如果状态是4，必须传递数据给HomeFragment
        if (page_current_state == 4){
            OnSuccess(resultState,successView);
        }
    }

    protected abstract void OnSuccess(ResultState resultState,View successView);

    protected abstract RequestParams params();


    protected abstract String url();


    public enum ResultState{
        ERROR(2),EMPTY(3),SUCCESS(4);

        private int state;
        private String content;

        ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
