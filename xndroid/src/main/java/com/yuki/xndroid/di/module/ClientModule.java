package com.yuki.xndroid.di.module;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuki.xndroid.config.GsonConfiguration;
import com.yuki.xndroid.config.OkHttpConfiguration;
import com.yuki.xndroid.config.RetrofitConfiguration;
import com.yuki.xndroid.config.RxCacheConfiguration;
import com.yuki.xndroid.repository.http.GlobalHttpHandler;
import com.yuki.xndroid.repository.http.RequestInterceptor;
import com.yuki.xndroid.utils.DataHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.JolyglotGenerics;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ================================================
 * 提供一些三方库客户端实例的 {@link Module}
 * ================================================
 */
@Module
public class ClientModule {
	private static final int TIME_OUT = 5;
	
	/**
	 * 提供 {@link Retrofit}
	 */
	@Singleton
	@Provides
	Retrofit provideRetrofit(Application application, @Nullable RetrofitConfiguration configuration, Retrofit.Builder builder, OkHttpClient client
			, HttpUrl httpUrl, Gson gson) {
		builder
				.baseUrl(httpUrl)//域名
				.client(client);//设置okHttp
		
		if (configuration != null)
			configuration.configRetrofit(application, builder);
		
		builder
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 Rxjava
				.addConverterFactory(GsonConverterFactory.create(gson));//使用 Gson
		return builder.build();
	}
	
	@Singleton
	@Provides
	public Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
		GsonBuilder builder = new GsonBuilder();
		if (configuration != null)
			configuration.configGson(application, builder);
		return builder.create();
	}
	/**
	 * 提供 {@link OkHttpClient}
	 */
	@Singleton
	@Provides
	OkHttpClient provideClient(Application application, @Nullable OkHttpConfiguration configuration, OkHttpClient.Builder builder, Interceptor
			intercept
			, @Nullable List<Interceptor> interceptors, @Nullable final GlobalHttpHandler handler) {
		builder
				.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
				.readTimeout(TIME_OUT, TimeUnit.SECONDS)
				.addNetworkInterceptor(intercept);
		
		if (handler != null)
			builder.addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
				}
			});
		
		if (interceptors != null) {//如果外部提供了interceptor的集合则遍历添加
			for (Interceptor interceptor : interceptors) {
				builder.addInterceptor(interceptor);
			}
		}
		
		if (configuration != null)
			configuration.configOkHttp(application, builder);
		return builder.build();
	}
	
	
	@Singleton
	@Provides
	Retrofit.Builder provideRetrofitBuilder() {
		return new Retrofit.Builder();
	}
	
	
	@Singleton
	@Provides
	OkHttpClient.Builder provideClientBuilder() {
		return new OkHttpClient.Builder();
	}
	
	
	@Singleton
	@Provides
	Interceptor provideInterceptor(RequestInterceptor intercept) {
		return intercept;//打印请求信息的拦截器
	}
	
	
	/**
	 * 提供 {@link RxCache}
	 *
	 * @param cacheDirectory RxCache缓存路径
	 * @return
	 */
	@Singleton
	@Provides
	RxCache provideRxCache(Application application, @Nullable RxCacheConfiguration configuration, @Named("RxCacheDirectory") File cacheDirectory,
	                       JolyglotGenerics jolyglotGenerics) {
		RxCache.Builder builder = new RxCache.Builder();
		if (configuration != null) {
			configuration.configRxCache(application, builder);
		}
		return builder.persistence(cacheDirectory, jolyglotGenerics);
	}
	
	/**
	 * 需要单独给 {@link RxCache} 提供缓存路径
	 */
	@Named("RxCacheDirectory")
	@Singleton
	@Provides
	File provideRxCacheDirectory(File cacheDir) {
		File cacheDirectory = new File(cacheDir, "RxCache");
		return DataHelper.makeDirs(cacheDirectory);
	}
	
	
}
