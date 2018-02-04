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
package com.yuki.xndroid.di.module;

import android.app.Application;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.yuki.xndroid.config.GsonConfiguration;
import com.yuki.xndroid.config.OkHttpConfiguration;
import com.yuki.xndroid.config.RetrofitConfiguration;
import com.yuki.xndroid.config.RxCacheConfiguration;
import com.yuki.xndroid.kit.RxErrorListener;
import com.yuki.xndroid.repository.http.GlobalHttpHandler;
import com.yuki.xndroid.repository.http.RequestInterceptor;
import com.yuki.xndroid.utils.DataHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.victoralbertos.jolyglot.GsonSpeaker;
import io.victoralbertos.jolyglot.JolyglotGenerics;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * ================================================
 * 框架独创的建造者模式 {@link Module},可向框架中注入外部配置的自定义参数
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.1">AppConfiguration Wiki 官方文档</a>
 * Created by JessYan on 2016/3/14.
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Module
public class ConfigModule {
	private HttpUrl mBaseUrl;
	private GlobalHttpHandler mHandler;
	private List<Interceptor> mInterceptors;
	private File mCacheFile;
	private RetrofitConfiguration mRetrofitConfiguration;
	private OkHttpConfiguration mOkHttpConfiguration;
	private RxCacheConfiguration mRxCacheConfiguration;
	private GsonConfiguration mGsonConfiguration;
	private RequestInterceptor.Level mPrintHttpLogLevel;
	private RxErrorListener mRxErrorListener;
	private JolyglotGenerics mJolyglotGenerics;
	
	private ConfigModule(Builder builder) {
		this.mBaseUrl = builder.baseUrl;
		this.mHandler = builder.handler;
		this.mInterceptors = builder.interceptors;
		this.mCacheFile = builder.cacheFile;
		this.mRetrofitConfiguration = builder.retrofitConfiguration;
		this.mOkHttpConfiguration = builder.okhttpConfiguration;
		this.mGsonConfiguration = builder.gsonConfiguration;
		this.mRxCacheConfiguration = builder.rxCacheConfiguration;
		this.mPrintHttpLogLevel = builder.printHttpLogLevel;
		this.mRxErrorListener = builder.rxErrorListener;
		this.mJolyglotGenerics = builder.jolyglotGenerics;
		
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	@Singleton
	@Provides
	@Nullable
	List<Interceptor> provideInterceptors() {
		return mInterceptors;
	}
	
	
	/**
	 * 提供 BaseUrl,默认使用 <"https://api.github.com/">
	 */
	@Singleton
	@Provides
	HttpUrl provideBaseUrl() {
		return mBaseUrl == null ? HttpUrl.parse("https://api.github.com/") : mBaseUrl;
	}
	
	
	/**
	 * 提供处理 Http 请求和响应结果的处理类
	 */
	@Singleton
	@Provides
	@Nullable
	GlobalHttpHandler provideGlobalHttpHandler() {
		return mHandler;
	}
	
	
	/**
	 * 提供缓存文件
	 */
	@Singleton
	@Provides
	File provideCacheFile(Application application) {
		return mCacheFile == null ? DataHelper.getCacheFile(application) : mCacheFile;
	}
	
	
	@Singleton
	@Provides
	@Nullable
	RetrofitConfiguration provideRetrofitConfiguration() {
		return mRetrofitConfiguration;
	}
	
	@Singleton
	@Provides
	@Nullable
	OkHttpConfiguration provideOkHttpConfiguration() {
		return mOkHttpConfiguration;
	}
	
	
	@Singleton
	@Provides
	@Nullable
	GsonConfiguration provideGsonConfiguration() {
		return mGsonConfiguration;
	}
	
	@Singleton
	@Provides
	@Nullable
	RxCacheConfiguration provideRxCacheConfiguration() {
		return mRxCacheConfiguration;
	}
	
	@Singleton
	@Provides
	@Nullable
	RequestInterceptor.Level providePrintHttpLogLevel() {
		return mPrintHttpLogLevel;
	}
	
	
	@Singleton
	@Provides
	RxErrorListener provideRxErrorListener() {
		return mRxErrorListener;
	}
	
	@Singleton
	@Provides
	JolyglotGenerics provideJolyglotGenerics() {
		if (mJolyglotGenerics == null)
			return new GsonSpeaker();
		else
			return mJolyglotGenerics;
	}
	
	public static final class Builder {
		private HttpUrl baseUrl;
		private GlobalHttpHandler handler;
		private List<Interceptor> interceptors;
		private File cacheFile;
		private RetrofitConfiguration retrofitConfiguration;
		private OkHttpConfiguration okhttpConfiguration;
		private GsonConfiguration gsonConfiguration;
		private RxCacheConfiguration rxCacheConfiguration;
		private RequestInterceptor.Level printHttpLogLevel;
		private RxErrorListener rxErrorListener;
		private JolyglotGenerics jolyglotGenerics;
		
		private Builder() {
		}
		
		public Builder baseUrl(String baseUrl) {//基础url
			if (TextUtils.isEmpty(baseUrl)) {
				throw new NullPointerException("BaseUrl can not be empty");
			}
			this.baseUrl = HttpUrl.parse(baseUrl);
			return this;
		}
		
		
		public Builder globalHttpHandler(GlobalHttpHandler handler) {//用来处理http响应结果
			this.handler = handler;
			return this;
		}
		
		public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
			if (interceptors == null)
				interceptors = new ArrayList<>();
			this.interceptors.add(interceptor);
			return this;
		}
		
		public Builder handleRxError(RxErrorListener listener) {//处理所有RxJava的onError逻辑
			this.rxErrorListener = listener;
			return this;
		}
		
		public Builder cacheFile(File cacheFile) {
			this.cacheFile = cacheFile;
			return this;
		}
		
		public Builder retrofitConfiguration(RetrofitConfiguration retrofitConfiguration) {
			this.retrofitConfiguration = retrofitConfiguration;
			return this;
		}
		
		public Builder okHttpConfiguration(OkHttpConfiguration okhttpConfiguration) {
			this.okhttpConfiguration = okhttpConfiguration;
			return this;
		}
		
		public Builder rxCacheConfiguration(RxCacheConfiguration rxCacheConfiguration) {
			this.rxCacheConfiguration = rxCacheConfiguration;
			return this;
		}
		
		public Builder gsonConfiguration(GsonConfiguration gsonConfiguration) {
			this.gsonConfiguration = gsonConfiguration;
			return this;
		}
		
		public Builder jolyglotGenerics(JolyglotGenerics jolyglotGenerics) {
			this.jolyglotGenerics = jolyglotGenerics;
			return this;
		}
		
		public Builder printHttpLogLevel(RequestInterceptor.Level printHttpLogLevel) { //是否让框架打印 Http 的请求和响应信息
			if (printHttpLogLevel == null)
				throw new NullPointerException("printHttpLogLevel == null. Use RequestInterceptor.Level.NONE instead.");
			this.printHttpLogLevel = printHttpLogLevel;
			return this;
		}
		
		
		public ConfigModule build() {
			return new ConfigModule(this);
		}
		
		
	}
	
	
}
