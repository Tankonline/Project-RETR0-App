package rikka.shizuku;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceScreen;

/* JADX INFO: renamed from: rikka.shizuku.nk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0438nk {
    public final Context a;
    public SharedPreferences.Editor d;
    public boolean e;
    public String f;
    public PreferenceScreen h;
    public AbstractC0284ik i;
    public AbstractC0284ik j;
    public AbstractC0284ik k;
    public long b = 0;
    public int g = 0;
    public SharedPreferences c = null;

    public C0438nk(Context context) {
        this.a = context;
        this.f = context.getPackageName() + "_preferences";
    }

    public final SharedPreferences.Editor a() {
        if (!this.e) {
            return b().edit();
        }
        if (this.d == null) {
            this.d = b().edit();
        }
        return this.d;
    }

    public final SharedPreferences b() {
        if (this.c == null) {
            int i = this.g;
            Context contextCreateDeviceProtectedStorageContext = this.a;
            if (i == 1) {
                contextCreateDeviceProtectedStorageContext = contextCreateDeviceProtectedStorageContext.createDeviceProtectedStorageContext();
            }
            this.c = contextCreateDeviceProtectedStorageContext.getSharedPreferences(this.f, 0);
        }
        return this.c;
    }
}
