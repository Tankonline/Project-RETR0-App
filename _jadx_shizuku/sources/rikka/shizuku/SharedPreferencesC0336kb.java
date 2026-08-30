package rikka.shizuku;

import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: rikka.shizuku.kb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class SharedPreferencesC0336kb implements SharedPreferences {
    @Override // android.content.SharedPreferences
    public final boolean contains(String str) {
        return false;
    }

    @Override // android.content.SharedPreferences
    public final SharedPreferences.Editor edit() {
        return new SharedPreferencesEditorC0305jb();
    }

    @Override // android.content.SharedPreferences
    public final Map getAll() {
        return new HashMap();
    }

    @Override // android.content.SharedPreferences
    public final void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override // android.content.SharedPreferences
    public final void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override // android.content.SharedPreferences
    public final boolean getBoolean(String str, boolean z) {
        return z;
    }

    @Override // android.content.SharedPreferences
    public final float getFloat(String str, float f) {
        return f;
    }

    @Override // android.content.SharedPreferences
    public final int getInt(String str, int i) {
        return i;
    }

    @Override // android.content.SharedPreferences
    public final long getLong(String str, long j) {
        return j;
    }

    @Override // android.content.SharedPreferences
    public final String getString(String str, String str2) {
        return str2;
    }

    @Override // android.content.SharedPreferences
    public final Set getStringSet(String str, Set set) {
        return set;
    }
}
