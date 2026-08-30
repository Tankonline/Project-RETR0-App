package rikka.shizuku;

import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

/* JADX INFO: renamed from: rikka.shizuku.nf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0433nf extends InputConnectionWrapper {
    public final /* synthetic */ C0610t7 a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0433nf(InputConnection inputConnection, C0610t7 c0610t7) {
        super(inputConnection, false);
        this.a = c0610t7;
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        Ys ys = null;
        if (inputContentInfo != null && Build.VERSION.SDK_INT >= 25) {
            ys = new Ys(20, new C0495pf(inputContentInfo));
        }
        if (this.a.e(ys, i, bundle)) {
            return true;
        }
        return super.commitContent(inputContentInfo, i, bundle);
    }
}
