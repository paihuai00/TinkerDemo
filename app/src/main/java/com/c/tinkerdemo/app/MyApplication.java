package com.c.tinkerdemo.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

import java.util.Locale;


/**
 * Create by cuishuxiang
 *
 * @date: on 2018/11/15
 * @description:
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        //安装 tinker
        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //以下为热更新
//        setStrictMode();
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = false;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
        //设置渠道
//        Beta.appChannel = channelId;

        /**
         *  全量升级状态回调
         */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean b) {

            }


            @Override
            public void onUpgradeSuccess(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                Log.i("bugly", "已是最新版本");
            }

            @Override
            public void onUpgrading(boolean b) {
                Log.i("bugly", "正在更新中。。。");
            }

            @Override
            public void onDownloadCompleted(boolean b) {

            }
        };

        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFileUrl) {
                Log.i("bugly", patchFileUrl);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Log.i("bugly", "检测到有热修复包：" + String.format(Locale.getDefault(),
                        "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
                Log.i("bugly", "热修复加载完毕");
                Log.i("bugly", "patchFilePath");
//                Beta.applyDownloadedPatch();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Log.i("bugly", msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                Log.i("bugly", msg);
            }

            @Override
            public void onApplyFailure(String msg) {
                Log.i("bugly", msg);
            }

            @Override
            public void onPatchRollback() {
                Log.i("bugly", "回滚中。。。");
            }
        };
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
        //测试52ed3d8309
        Bugly.init(this, "0b5cd33f13", true);
    }

}
