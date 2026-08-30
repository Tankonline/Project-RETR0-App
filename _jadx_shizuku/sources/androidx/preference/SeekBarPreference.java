package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.widget.SeekBar;
import android.widget.TextView;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0500pk;
import rikka.shizuku.Jk;
import rikka.shizuku.Km;
import rikka.shizuku.Pm;
import rikka.shizuku.Qm;

/* JADX INFO: loaded from: classes.dex */
public class SeekBarPreference extends Preference {
    public int N;
    public int O;
    public int P;
    public int Q;
    public boolean R;
    public SeekBar S;
    public TextView T;
    public final boolean U;
    public final boolean V;
    public final boolean W;
    public final Pm X;
    public final Km Y;

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04041a, 0);
        this.X = new Pm(this);
        this.Y = new Km(1, this);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.k, R.attr._0_resource_name_obfuscated_res_0x7f04041a, 0);
        this.O = typedArrayObtainStyledAttributes.getInt(3, 0);
        int i = typedArrayObtainStyledAttributes.getInt(1, 100);
        int i2 = this.O;
        i = i < i2 ? i2 : i;
        if (i != this.P) {
            this.P = i;
            i();
        }
        int i3 = typedArrayObtainStyledAttributes.getInt(4, 0);
        if (i3 != this.Q) {
            this.Q = Math.min(this.P - this.O, Math.abs(i3));
            i();
        }
        this.U = typedArrayObtainStyledAttributes.getBoolean(2, true);
        this.V = typedArrayObtainStyledAttributes.getBoolean(5, false);
        this.W = typedArrayObtainStyledAttributes.getBoolean(6, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void B(int i, boolean z) {
        int i2 = this.O;
        if (i < i2) {
            i = i2;
        }
        int i3 = this.P;
        if (i > i3) {
            i = i3;
        }
        if (i != this.N) {
            this.N = i;
            TextView textView = this.T;
            if (textView != null) {
                textView.setText(String.valueOf(i));
            }
            v(i);
            if (z) {
                i();
            }
        }
    }

    public final void C(SeekBar seekBar) {
        int progress = seekBar.getProgress() + this.O;
        if (progress != this.N) {
            if (a(Integer.valueOf(progress))) {
                B(progress, false);
                return;
            }
            seekBar.setProgress(this.N - this.O);
            int i = this.N;
            TextView textView = this.T;
            if (textView != null) {
                textView.setText(String.valueOf(i));
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        super.m(c0500pk);
        c0500pk.a.setOnKeyListener(this.Y);
        this.S = (SeekBar) c0500pk.q(R.id._0_resource_name_obfuscated_res_0x7f090184);
        TextView textView = (TextView) c0500pk.q(R.id._0_resource_name_obfuscated_res_0x7f090185);
        this.T = textView;
        if (this.V) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
            this.T = null;
        }
        SeekBar seekBar = this.S;
        if (seekBar == null) {
            Log.e("SeekBarPreference", "SeekBar view is null in onBindViewHolder.");
            return;
        }
        seekBar.setOnSeekBarChangeListener(this.X);
        this.S.setMax(this.P - this.O);
        int i = this.Q;
        if (i != 0) {
            this.S.setKeyProgressIncrement(i);
        } else {
            this.Q = this.S.getKeyProgressIncrement();
        }
        this.S.setProgress(this.N - this.O);
        int i2 = this.N;
        TextView textView2 = this.T;
        if (textView2 != null) {
            textView2.setText(String.valueOf(i2));
        }
        this.S.setEnabled(h());
    }

    @Override // androidx.preference.Preference
    public final Object q(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // androidx.preference.Preference
    public final void r(Parcelable parcelable) {
        if (!parcelable.getClass().equals(Qm.class)) {
            super.r(parcelable);
            return;
        }
        Qm qm = (Qm) parcelable;
        super.r(qm.getSuperState());
        this.N = qm.a;
        this.O = qm.b;
        this.P = qm.c;
        i();
    }

    @Override // androidx.preference.Preference
    public final Parcelable s() {
        super.s();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.r) {
            return absSavedState;
        }
        Qm qm = new Qm();
        qm.a = this.N;
        qm.b = this.O;
        qm.c = this.P;
        return qm;
    }

    @Override // androidx.preference.Preference
    public final void t(Object obj) {
        if (obj == null) {
            obj = 0;
        }
        B(e(((Integer) obj).intValue()), true);
    }
}
