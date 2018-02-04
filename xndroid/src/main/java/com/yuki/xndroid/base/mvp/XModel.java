package com.yuki.xndroid.base.mvp;


import com.yuki.xndroid.repository.IRepositoryManager;


public abstract class XModel implements IModel {
	
	
	protected IRepositoryManager mRepositoryManager;//用于管理网络请求层,以及数据缓存层
	
	public XModel(IRepositoryManager repositoryManager) {
		mRepositoryManager = repositoryManager;
	}
	
	@Override
	public void onDestroy() {
		mRepositoryManager = null;
	}
}
