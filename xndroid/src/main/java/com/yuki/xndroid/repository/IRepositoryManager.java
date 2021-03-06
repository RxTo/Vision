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

import android.content.Context;


/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 必要的 Api 做数据处理
 * ================================================
 */
public interface IRepositoryManager {
	
	/**
	 * 根据传入的 Class 获取对应的 Retrofit service
	 *
	 * @return
	 */
	<T> T http(Class<T> service);
	
	/**
	 * 根据传入的 Class 获取对应的 RxCache service
	 *
	 * @param cache
	 * @param <T>
	 * @return
	 */
	<T> T cache(Class<T> cache);
	
	/**
	 * 清理所有缓存
	 */
	void clearAllCache();
	
	Context getContext();
	
}
