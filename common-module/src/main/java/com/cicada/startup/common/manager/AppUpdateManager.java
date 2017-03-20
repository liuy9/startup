package com.cicada.startup.common.manager;

import android.app.Dialog;
import android.content.Context;

import com.cicada.startup.common.domain.VersionUpdate;
import com.cicada.startup.common.download.DownLoadAppService;
import com.cicada.startup.common.ui.view.dialog.AdDialog;

public class AppUpdateManager {
    private Context context;

    public AppUpdateManager(Context context) {
        this.context = context;
    }

    public Dialog openUpdateDialog(final VersionUpdate appUpgradeInfo) {


        AdDialog.DialogType type = AdDialog.DialogType.UPDATE_NOR;
        int updateType = appUpgradeInfo.getUpdateType();
        if (updateType == 2) {
            type = AdDialog.DialogType.UPDATE_FORCE;
        }
        String updateIntro = appUpgradeInfo.getVersionIntro();

        AdDialog dialog = new AdDialog.Builder(context)
                .withType(type)
                .withUpdateIntro(updateIntro)
                .withOnActionListener(new AdDialog.OnActionListener() {
                    @Override
                    public void action() {
                        DownLoadAppService.startDownLoadAppService(context, appUpgradeInfo.getVersion(), appUpgradeInfo.getDownLoadUrl());
                    }
                })
                .build();

        return dialog;
    }

}
