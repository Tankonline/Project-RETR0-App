package rikka.shizuku;

import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class T implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final C0610t7 a;

    public T(C0610t7 c0610t7) {
        this.a = c0610t7;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof T) {
            return this.a.equals(((T) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z) {
        C0644ua c0644ua = (C0644ua) this.a.b;
        AutoCompleteTextView autoCompleteTextView = c0644ua.h;
        if (autoCompleteTextView == null || X8.H(autoCompleteTextView)) {
            return;
        }
        int i = z ? 2 : 1;
        WeakHashMap weakHashMap = Hs.a;
        c0644ua.d.setImportantForAccessibility(i);
    }
}
