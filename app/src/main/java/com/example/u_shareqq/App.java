package com.example.u_shareqq;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this,"5ad59645f29d9809b3000024"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "kdd7U0RNSNgTkeNG");
    }
}
