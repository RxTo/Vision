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

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.yuki.xndroid.base.delegate.ActivityLifecycleCallbacks;
import com.yuki.xndroid.base.delegate.AppLifecycleCallbacks;

import java.util.List;

/**
 * ================================================
 * {@link AppConfiguration} 可以给框架配置一些参数,需要实现 {@link AppConfiguration} 后,在 AndroidManifest 中声明该实现类
 * ================================================
 */
public interface AppConfiguration {
    /**
     * 使用{@link com.yuki.xndroid.di.module.ConfigModule.Builder}给框架配置一些配置参数
     *
     */
    void applyOptions(Context context, com.yuki.xndroid.di.module.ConfigModule.Builder configBuilder);

    /**
     * 使用{@link com.yuki.xndroid.base.delegate.AppLifecycleCallbacks}在Application的生命周期中注入一些操作
     *
     */
    void injectAppLifecycle(Context context, List<AppLifecycleCallbacks> AppLifecycles);

    /**
     * 使用{@link Application.ActivityLifecycleCallbacks}在Activity的生命周期中注入一些操作
     *
     */
    void injectActivityLifecycle(Context context, List<ActivityLifecycleCallbacks> activityLifecycles);


    /**
     * 使用{@link FragmentManager.FragmentLifecycleCallbacks}在Fragment的生命周期中注入一些操作
     *
     */
    void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> fragmentLifecycles);
}
