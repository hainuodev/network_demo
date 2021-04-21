package com.example.network_demo;

import com.ynby.network.base.INetworkRequiredInfo;

public class NetworkInfo implements INetworkRequiredInfo {
    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
