package com.fund.myhotfix;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry括号里面应填写原来的的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(Application.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {

//你的后台版本，和控制台版本一样
        String appVersion = "1.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("28019030", "e6bd920e01acfa402b46cc66710a90d0", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCEiR/JFBZIUyiMJ3ChWGbmotR5ZzQxBfmZ31XViFKIy82AcArZ8R67dE4YIjlRwZxsVHWRn9xFas6bplu5eCx7kRHGV6+42dogM3wwICFl6voFc+jSEiqCqiUs1lsPo+X/Z8N1zqsPi+InuHfe7my30KOzoj+exvTiqvj8qyt8yuiW9f8T/9VYSTCongTRO402czyWyvNF9vAP40z3GJTJFdIASwi3yV+L0/F+/g7qmQL137SPi1px7665bJyvjgH255jnKq23qt+vH5+AuZkWN6RWNXOWsQBTP5voi8dYhccfHXZPBhBf4c343HuruKuY+aZTEDTMHMuxcysQChJjAgMBAAECggEASdDpoB+M7YlLWwwYYfSPk0L7XgaJ/gfa5cWK09wSQ9hmw3MjyBDQUg1L9YsSK6PO0FMWDB5iG1OKcQxtp9XzyHqa0MJDv4uuPiMOtuseTtpZmDHfaSju/klvXRKiMwrQ7bhIF9QcWBNLyV2nfcWDTblpg4IJNxCOkbQx6uAW33B5BsYVxHyWfuSaMmH+gJiqvDo7QLG9EWIbSMhH99PpcuMKtksolu8hEJWTEJd0Y8YxDZlWrRTlhUwMG8NNO+JJYrI8zZYHDmj5/0ZhRDIqu8yGJ7LbXAHGXN6SDAdrxRk01z/8uBbyuAhtt4BqZ762yRc3fiIQqUngW/vEmo6pUQKBgQD5STlnSMuRtEIM1WgMnA7Pmaeu9671Dazrqxfwt5IyOTp7WgGmRvvd0RAvetis874YjNpGAOR1rDQkxPjqv+EGbQqpT1ZOsUoFvggJNRS/tkwQkzN8f4LxofDaIZofHMmADtW4SPgufmJ9zl9RsDJGrT27sKXsp9ZLyfZPZjH86QKBgQCIGu4Y9R8TmEeSiIljll3jlZetjfYdYUnzCTxw3C3xWCUjLNIV6mXcdVHIqfERrdcoWuEWAHL2H1jghiBo4dr/DWsDE+2AyrihKJ2MXD3rXVSXO1v/Rrk5S45ghg0+IR6O67hUj56WG2gkbFhGCtzYky52zAIjwpn9qhcxc0xVawKBgQDUXuVgcEu83WxHsqbp1ylnAugvzyJl8jiWrTPAByOtVcPUwmEVXvPDjQ/U4zskXKJeF+D8MlZvST1C9et5Ue5KoyqqAsTsVW1SZwpBjc8XZB55IG6Eab0MTYH60xWR29xuFDVOwhtswI73zpC17v1l6MDm3R799h5CgtQr4ExFwQKBgGBP7jzq+Fq9X6hpxg8a0MXDjdnao1aXCeVZm9Ohe0FVxrHlfaTUgdBHlOASoeyIv8BmzvDq0G8qL5JyV2zgpj/mFtvOtjp1OcGRJCu2+nobSUjbUHaMbXYF6XKUabqVZTJty7rLBsXiDwf3Q2ANP8piHHckVj9H7gB65H6xwEqRAoGBANx/cgl8ccXqs+52pKxtrGCvZ5BRh3Rq4Jh7oRNLzm59xE01Tb4y0CvXyduwDVcw36FpQS9WEXC3d+N0G0Jrs6cllKBYTdGrVvZ7V+44JNaC7HGz8kEXTKd6qrm+Hnu2k5fqITXFNn3Pnn7T7M49YtxSMn3TOdizzFyyImn2pzQf")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            //必须使用原生的SharePreference

                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}


