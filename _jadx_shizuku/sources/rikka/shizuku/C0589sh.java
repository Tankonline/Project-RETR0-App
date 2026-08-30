package rikka.shizuku;

import android.util.Log;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.sh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0589sh {
    public final /* synthetic */ int a;
    public final String b;

    public /* synthetic */ C0589sh(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public void a(Throwable th, String str, Object... objArr) {
        b(6, String.format(Locale.ENGLISH, str, objArr) + '\n' + Log.getStackTraceString(th));
    }

    public void b(int i, String str) {
        Log.println(i, this.b, str);
    }

    public void c(Throwable th, String str, Object... objArr) {
        b(5, String.format(Locale.ENGLISH, str, objArr) + '\n' + Log.getStackTraceString(th));
    }

    public String toString() {
        switch (this.a) {
            case 1:
                return "<" + this.b + '>';
            default:
                return super.toString();
        }
    }
}
