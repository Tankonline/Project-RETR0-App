package rikka.shizuku;

import android.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.preference.DialogPreference;

/* JADX INFO: renamed from: rikka.shizuku.gk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractDialogInterfaceOnClickListenerC0223gk extends U9 implements DialogInterface.OnClickListener {
    public DialogPreference m0;
    public CharSequence n0;
    public CharSequence o0;
    public CharSequence p0;
    public CharSequence q0;
    public int r0;
    public BitmapDrawable s0;
    public int t0;

    @Override // rikka.shizuku.U9, rikka.shizuku.Ac
    public void A(Bundle bundle) {
        super.A(bundle);
        bundle.putCharSequence("PreferenceDialogFragment.title", this.n0);
        bundle.putCharSequence("PreferenceDialogFragment.positiveText", this.o0);
        bundle.putCharSequence("PreferenceDialogFragment.negativeText", this.p0);
        bundle.putCharSequence("PreferenceDialogFragment.message", this.q0);
        bundle.putInt("PreferenceDialogFragment.layout", this.r0);
        BitmapDrawable bitmapDrawable = this.s0;
        if (bitmapDrawable != null) {
            bundle.putParcelable("PreferenceDialogFragment.icon", bitmapDrawable.getBitmap());
        }
    }

    @Override // rikka.shizuku.U9
    public final Dialog N() {
        this.t0 = -2;
        I1 i1 = new I1(H());
        CharSequence charSequence = this.n0;
        E1 e1 = (E1) i1.b;
        e1.d = charSequence;
        e1.c = this.s0;
        i1.e(this.o0, this);
        e1.i = this.p0;
        e1.j = this;
        H();
        int i = this.r0;
        View viewInflate = i != 0 ? j().inflate(i, (ViewGroup) null) : null;
        if (viewInflate != null) {
            R(viewInflate);
            e1.s = viewInflate;
        } else {
            e1.f = this.q0;
        }
        T(i1);
        J1 j1B = i1.b();
        if (this instanceof Ca) {
            Window window = j1B.getWindow();
            if (Build.VERSION.SDK_INT >= 30) {
                AbstractC0192fk.a(window);
                return j1B;
            }
            Ca ca = (Ca) this;
            ca.x0 = SystemClock.currentThreadTimeMillis();
            ca.U();
        }
        return j1B;
    }

    public final DialogPreference Q() {
        if (this.m0 == null) {
            Bundle bundle = this.f;
            if (bundle == null) {
                throw new IllegalStateException("Fragment " + this + " does not have any arguments.");
            }
            this.m0 = (DialogPreference) ((AbstractC0284ik) n(true)).M(bundle.getString("key"));
        }
        return this.m0;
    }

    public void R(View view) {
        int i;
        View viewFindViewById = view.findViewById(R.id.message);
        if (viewFindViewById != null) {
            CharSequence charSequence = this.q0;
            if (TextUtils.isEmpty(charSequence)) {
                i = 8;
            } else {
                if (viewFindViewById instanceof TextView) {
                    ((TextView) viewFindViewById).setText(charSequence);
                }
                i = 0;
            }
            if (viewFindViewById.getVisibility() != i) {
                viewFindViewById.setVisibility(i);
            }
        }
    }

    public abstract void S(boolean z);

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.t0 = i;
    }

    @Override // rikka.shizuku.U9, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        S(this.t0 == -1);
    }

    @Override // rikka.shizuku.U9, rikka.shizuku.Ac
    public void v(Bundle bundle) {
        super.v(bundle);
        Ac acN = n(true);
        if (!(acN instanceof AbstractC0284ik)) {
            throw new IllegalStateException("Target fragment must implement TargetFragment interface");
        }
        AbstractC0284ik abstractC0284ik = (AbstractC0284ik) acN;
        Bundle bundle2 = this.f;
        if (bundle2 == null) {
            throw new IllegalStateException("Fragment " + this + " does not have any arguments.");
        }
        String string = bundle2.getString("key");
        if (bundle != null) {
            this.n0 = bundle.getCharSequence("PreferenceDialogFragment.title");
            this.o0 = bundle.getCharSequence("PreferenceDialogFragment.positiveText");
            this.p0 = bundle.getCharSequence("PreferenceDialogFragment.negativeText");
            this.q0 = bundle.getCharSequence("PreferenceDialogFragment.message");
            this.r0 = bundle.getInt("PreferenceDialogFragment.layout", 0);
            Bitmap bitmap = (Bitmap) bundle.getParcelable("PreferenceDialogFragment.icon");
            if (bitmap != null) {
                this.s0 = new BitmapDrawable(m(), bitmap);
                return;
            }
            return;
        }
        DialogPreference dialogPreference = (DialogPreference) abstractC0284ik.M(string);
        this.m0 = dialogPreference;
        this.n0 = dialogPreference.N;
        this.o0 = dialogPreference.Q;
        this.p0 = dialogPreference.R;
        this.q0 = dialogPreference.O;
        this.r0 = dialogPreference.S;
        Drawable drawable = dialogPreference.P;
        if (drawable == null || (drawable instanceof BitmapDrawable)) {
            this.s0 = (BitmapDrawable) drawable;
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        this.s0 = new BitmapDrawable(m(), bitmapCreateBitmap);
    }

    public void T(I1 i1) {
    }
}
