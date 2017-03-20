package com.cicada.startup.common.config;

import android.content.Context;

import com.cicada.startup.common.AppContext;

/**
 * UserPreferences
 * <p>
 * Create time: 2017/2/17 15:28
 *
 * @author liuyun.
 */
public class UserPreferences extends BasePreferences {
    private static UserPreferences instance;

    private UserPreferences() {
        app = AppContext.getContext().getSharedPreferences("user_"
                        + AppPreferences.getInstance().getUserId(),
                Context.MODE_PRIVATE);

    }


    public static UserPreferences getInstance() {
        if (null == instance) {
            synchronized (UserPreferences.class) {
                if (null == instance) {
                    instance = new UserPreferences();
                }
            }
        }

        return instance;
    }
}
