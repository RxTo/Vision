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

import com.yuki.xndroid.config.AppConfiguration;
import com.yuki.xndroid.repository.IRepositoryManager;
import com.yuki.xndroid.repository.RepositoryManager;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 提供一些框架必须的实例的 {@link Module}
 * <p>
 * ================================================
 */
@Module
public class AppModule {
	private Application mApplication;
	private List<AppConfiguration> mModules;
	
	public AppModule(Application application, List<AppConfiguration> modules) {
		this.mApplication = application;
		this.mModules = modules;
	}
	
	@Singleton
	@Provides
	public Application provideApplication() {
		return mApplication;
	}
	
	
	@Singleton
	@Provides
	public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
		return repositoryManager;
	}
	
	@Singleton
	@Provides
	public List<AppConfiguration> provideConfigModule() {
		return mModules;
	}
}
