package com.cicada.startup.common.config;

import android.content.Context;

import com.cicada.startup.common.AppContext;

/**
 * AppPreferences
 * <p>
 * Create time: 2017/2/17 15:27
 *
 * @author liuyun.
 */
public class AppPreferences extends BasePreferences {

    private static AppPreferences instance;

    private AppPreferences() {
        app = AppContext.getContext().getSharedPreferences("app",
                Context.MODE_PRIVATE);

    }

    public static AppPreferences getInstance() {
        if (null == instance) {
            synchronized (AppPreferences.class) {
                if (null == instance) {
                    instance = new AppPreferences();
                }
            }
        }

        return instance;
    }

    public String getUserId() {
        return getString(USER_ID, "");
    }

    public void setUserId(String userId) {
        setString(USER_ID, userId);
    }

    public String getLoginToken() {
        return getString(LOGIN_TOKEN, "");
    }

    public void setLoginToken(String loginToken) {
        setString(LOGIN_TOKEN, loginToken);
    }

    /**
     * 获取服务器当前时间
     */
    public long getServerTimeStampNow() {
        return getLong(SERVER_TIME, System.currentTimeMillis());
    }

    /**
     * 设置服务器当前时间
     */
    public void setServerTimeStampNow(long lngTimeStamp) {
        setLong(SERVER_TIME, lngTimeStamp);
    }
}
