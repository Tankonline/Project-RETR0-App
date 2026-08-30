package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import java.util.ArrayList;
import rikka.shizuku.C0104co;
import rikka.shizuku.C0314jk;
import rikka.shizuku.Jk;

/* JADX INFO: loaded from: classes.dex */
public abstract class PreferenceGroup extends Preference {
    public final C0104co N;
    public final ArrayList O;
    public boolean P;
    public int Q;
    public boolean R;
    public int S;

    public PreferenceGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.N = new C0104co(0);
        new Handler(Looper.getMainLooper());
        this.P = true;
        this.Q = 0;
        this.R = false;
        this.S = Integer.MAX_VALUE;
        this.O = new ArrayList();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.i, i, 0);
        this.P = typedArrayObtainStyledAttributes.getBoolean(2, typedArrayObtainStyledAttributes.getBoolean(2, true));
        if (typedArrayObtainStyledAttributes.hasValue(1)) {
            int i2 = typedArrayObtainStyledAttributes.getInt(1, typedArrayObtainStyledAttributes.getInt(1, Integer.MAX_VALUE));
            if (i2 != Integer.MAX_VALUE && TextUtils.isEmpty(this.l)) {
                Log.e("PreferenceGroup", getClass().getSimpleName().concat(" should have a key defined if it contains an expandable preference"));
            }
            this.S = i2;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final Preference B(String str) {
        Preference preferenceB;
        if (str == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (TextUtils.equals(this.l, str)) {
            return this;
        }
        int size = this.O.size();
        for (int i = 0; i < size; i++) {
            Preference preferenceC = C(i);
            if (TextUtils.equals(preferenceC.l, str)) {
                return preferenceC;
            }
            if ((preferenceC instanceof PreferenceGroup) && (preferenceB = ((PreferenceGroup) preferenceC).B(str)) != null) {
                return preferenceB;
            }
        }
        return null;
    }

    public final Preference C(int i) {
        return (Preference) this.O.get(i);
    }

    @Override // androidx.preference.Preference
    public final void b(Bundle bundle) {
        super.b(bundle);
        int size = this.O.size();
        for (int i = 0; i < size; i++) {
            C(i).b(bundle);
        }
    }

    @Override // androidx.preference.Preference
    public final void c(Bundle bundle) {
        super.c(bundle);
        int size = this.O.size();
        for (int i = 0; i < size; i++) {
            C(i).c(bundle);
        }
    }

    @Override // androidx.preference.Preference
    public final void j(boolean z) {
        super.j(z);
        int size = this.O.size();
        for (int i = 0; i < size; i++) {
            Preference preferenceC = C(i);
            if (preferenceC.v == z) {
                preferenceC.v = !z;
                preferenceC.j(preferenceC.z());
                preferenceC.i();
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void k() {
        super.k();
        this.R = true;
        int size = this.O.size();
        for (int i = 0; i < size; i++) {
            C(i).k();
        }
    }

    @Override // androidx.preference.Preference
    public final void p() {
        super.p();
        this.R = false;
        int size = this.O.size();
        for (int i = 0; i < size; i++) {
            C(i).p();
        }
    }

    @Override // androidx.preference.Preference
    public final void r(Parcelable parcelable) {
        if (!parcelable.getClass().equals(C0314jk.class)) {
            super.r(parcelable);
            return;
        }
        C0314jk c0314jk = (C0314jk) parcelable;
        this.S = c0314jk.a;
        super.r(c0314jk.getSuperState());
    }

    @Override // androidx.preference.Preference
    public final Parcelable s() {
        super.s();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        return new C0314jk(this.S);
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
}
