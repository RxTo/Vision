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
package com.yuki.xndroid.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 用于解析 AndroidManifest 中的 Meta 属性
 * 配合 {@link AppConfiguration} 使用
 * ================================================
 */
public final class ManifestParser {
	private static final String MODULE_VALUE = "AppConfig";
	
	private final Context context;
	
	public ManifestParser(Context context) {
		this.context = context;
	}
	
	public List<AppConfiguration> parse() {
		List<AppConfiguration> modules = new ArrayList<>();
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			if (appInfo.metaData != null) {
				for (String key : appInfo.metaData.keySet()) {
					if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
						modules.add(parseModule(key));
					}
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException("Unable to find metadata to parse AppConfiguration", e);
		}
		
		return modules;
	}
	
	private static AppConfiguration parseModule(String className) {
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Unable to find AppConfiguration implementation", e);
		}
		
		Object module;
		try {
			module = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Unable to instantiate AppConfiguration implementation for " + clazz, e);
		}
		
		if (!(module instanceof AppConfiguration)) {
			throw new RuntimeException("Expected instanceof AppConfiguration, but found: " + module);
		}
		return (AppConfiguration) module;
	}
}
