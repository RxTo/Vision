package com.yuki.xndroid.base.mvp;

public class XPresenter<M extends IModel, V> implements IPresenter {

    protected M mModel;
    protected V mView;


    public XPresenter(M model, V view) {
        this.mModel = model;
        this.mView = view;
        onStart();
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
    }
    
  

}
