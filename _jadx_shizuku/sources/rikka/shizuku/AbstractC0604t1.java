package rikka.shizuku;

import android.app.ForegroundServiceStartNotAllowedException;
import android.content.AttributionSource;
import android.content.ClipData;
import android.view.ContentInfo;

/* JADX INFO: renamed from: rikka.shizuku.t1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0604t1 {
    public static /* synthetic */ AttributionSource.Builder d(int i) {
        return new AttributionSource.Builder(i);
    }

    public static /* synthetic */ ContentInfo.Builder h(ClipData clipData, int i) {
        return new ContentInfo.Builder(clipData, i);
    }

    public static /* bridge */ /* synthetic */ ContentInfo j(Object obj) {
        return (ContentInfo) obj;
    }

    public static /* synthetic */ void l() {
    }

    public static /* bridge */ /* synthetic */ boolean p(Throwable th) {
        return th instanceof ForegroundServiceStartNotAllowedException;
    }
}
