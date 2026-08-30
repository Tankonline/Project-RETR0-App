package rikka.shizuku;

import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.al, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0039al {
    public static void a(View view, float f) {
        try {
            view.setFrameContentVelocity(f);
        } catch (LinkageError unused) {
        }
    }
}
