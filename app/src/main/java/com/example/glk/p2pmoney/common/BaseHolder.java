package com.example.glk.p2pmoney.common;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by zgqdg on 2016/10/5.
 *
 * getView的实现全部交给BaseHolder去做
 */

public abstract class BaseHolder<T> {
    private View rootview;
    private T mData;

    public BaseHolder() {
        this.rootview = initView();
        this.rootview.setTag(this);
        ButterKnife.bind(BaseHolder.this,rootview);
    }



    public View getRootview(){
        return rootview;
    }

    public T getData() {
        return mData;
    }

    public void setData(T t){
        this.mData = t;
        refreshView();
    }



    public abstract View initView();
    protected abstract void refreshView();
}
