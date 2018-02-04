/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuki.xndroid.repository;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * ================================================
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
	private Lazy<Retrofit> mRetrofit;
	private Lazy<RxCache> mRxCache;
	private Application mApplication;
	private Map<String, Object> mServiceCache;
	
	@Inject
	public RepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache, Application application) {
		mRetrofit = retrofit;
		mApplication = application;
		mRxCache = rxCache;
		mServiceCache = new HashMap<>();
	}
	
	
	@Override
	public <T> T http(Class<T> service) {
		T httpServer = (T) mServiceCache.get(service.getCanonicalName());
		synchronized (this) {
			if (httpServer == null) {
				httpServer = mRetrofit.get().create(service);
				mServiceCache.put(service.getCanonicalName(), httpServer);
			}
		}
		return httpServer;
	}
	
	@Override
	public <T> T cache(Class<T> cache) {
		T httpServer = (T) mServiceCache.get(cache.getCanonicalName());
		synchronized (this) {
			if (httpServer == null) {
				httpServer = mRxCache.get().using(cache);
				mServiceCache.put(cache.getCanonicalName(), httpServer);
			}
		}
		return httpServer;
	}
	
	@Override
	public void clearAllCache() {
	
	}
	
	@Override
	public Context getContext() {
		return null;
	}
}
