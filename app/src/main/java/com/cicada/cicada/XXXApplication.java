package com.cicada.cicada;

import android.app.Application;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.config.AppEnvConfig;

/**
 * TODO
 * <p>
 * Create time: 2017/2/21 17:52
 *
 * @author liuyun.
 */
public class XXXApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(this, AppEnvConfig.RELEASE);
    }
}
