package rikka.shizuku;

import android.content.ContextWrapper;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class Un extends ContextWrapper {
    @Override // android.content.ContextWrapper, android.content.Context
    public final SharedPreferences getSharedPreferences(String str, int i) {
        try {
            return super.getSharedPreferences(str, i);
        } catch (IllegalStateException unused) {
            return new SharedPreferencesC0336kb();
        }
    }
}
