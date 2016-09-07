package com.bzilaji.tmdbclient.utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

public class StartWebPageCommand {

    private final String url;
    private final Context context;

    public void excecute() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (isAvailable(context, intent)) {
            context.startActivity(intent);
        }
    }

    private boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public StartWebPageCommand(String url, Context context) {
        this.url = url;
        this.context = context;
    }
}
