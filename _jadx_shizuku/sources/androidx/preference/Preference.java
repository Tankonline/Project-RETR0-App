package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0284ik;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.Ac;
import rikka.shizuku.C0376lk;
import rikka.shizuku.C0438nk;
import rikka.shizuku.C0500pk;
import rikka.shizuku.Hn;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0069bk;
import rikka.shizuku.InterfaceC0100ck;
import rikka.shizuku.InterfaceC0161ek;
import rikka.shizuku.Jk;
import rikka.shizuku.Kc;
import rikka.shizuku.L4;
import rikka.shizuku.Qc;
import rikka.shizuku.ViewOnClickListenerC0572s0;
import rikka.shizuku.ViewOnCreateContextMenuListenerC0131dk;

/* JADX INFO: loaded from: classes.dex */
public class Preference implements Comparable<Preference> {
    public final boolean A;
    public final boolean B;
    public final boolean C;
    public final boolean D;
    public int E;
    public final int F;
    public C0376lk G;
    public ArrayList H;
    public PreferenceGroup I;
    public boolean J;
    public ViewOnCreateContextMenuListenerC0131dk K;
    public InterfaceC0161ek L;
    public final ViewOnClickListenerC0572s0 M;
    public final Context a;
    public C0438nk b;
    public long c;
    public boolean d;
    public InterfaceC0069bk e;
    public InterfaceC0100ck f;
    public int g;
    public CharSequence h;
    public CharSequence i;
    public int j;
    public Drawable k;
    public final String l;
    public Intent m;
    public final String n;
    public Bundle o;
    public final boolean p;
    public final boolean q;
    public final boolean r;
    public final String s;
    public final Object t;
    public boolean u;
    public boolean v;
    public boolean w;
    public final boolean x;
    public final boolean y;
    public final boolean z;

    public Preference(Context context, AttributeSet attributeSet, int i, int i2) {
        this.g = Integer.MAX_VALUE;
        this.p = true;
        this.q = true;
        this.r = true;
        this.u = true;
        this.v = true;
        this.w = true;
        this.x = true;
        this.y = true;
        this.A = true;
        this.D = true;
        this.E = R.layout._0_resource_name_obfuscated_res_0x7f0c0087;
        this.M = new ViewOnClickListenerC0572s0(3, this);
        this.a = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.g, i, i2);
        this.j = typedArrayObtainStyledAttributes.getResourceId(23, typedArrayObtainStyledAttributes.getResourceId(0, 0));
        String string = typedArrayObtainStyledAttributes.getString(26);
        this.l = string == null ? typedArrayObtainStyledAttributes.getString(6) : string;
        CharSequence text = typedArrayObtainStyledAttributes.getText(34);
        this.h = text == null ? typedArrayObtainStyledAttributes.getText(4) : text;
        CharSequence text2 = typedArrayObtainStyledAttributes.getText(33);
        this.i = text2 == null ? typedArrayObtainStyledAttributes.getText(7) : text2;
        this.g = typedArrayObtainStyledAttributes.getInt(28, typedArrayObtainStyledAttributes.getInt(8, Integer.MAX_VALUE));
        String string2 = typedArrayObtainStyledAttributes.getString(22);
        this.n = string2 == null ? typedArrayObtainStyledAttributes.getString(13) : string2;
        this.E = typedArrayObtainStyledAttributes.getResourceId(27, typedArrayObtainStyledAttributes.getResourceId(3, R.layout._0_resource_name_obfuscated_res_0x7f0c0087));
        this.F = typedArrayObtainStyledAttributes.getResourceId(35, typedArrayObtainStyledAttributes.getResourceId(9, 0));
        this.p = typedArrayObtainStyledAttributes.getBoolean(21, typedArrayObtainStyledAttributes.getBoolean(2, true));
        boolean z = typedArrayObtainStyledAttributes.getBoolean(30, typedArrayObtainStyledAttributes.getBoolean(5, true));
        this.q = z;
        this.r = typedArrayObtainStyledAttributes.getBoolean(29, typedArrayObtainStyledAttributes.getBoolean(1, true));
        String string3 = typedArrayObtainStyledAttributes.getString(19);
        this.s = string3 == null ? typedArrayObtainStyledAttributes.getString(10) : string3;
        this.x = typedArrayObtainStyledAttributes.getBoolean(16, typedArrayObtainStyledAttributes.getBoolean(16, z));
        this.y = typedArrayObtainStyledAttributes.getBoolean(17, typedArrayObtainStyledAttributes.getBoolean(17, z));
        if (typedArrayObtainStyledAttributes.hasValue(18)) {
            this.t = q(typedArrayObtainStyledAttributes, 18);
        } else if (typedArrayObtainStyledAttributes.hasValue(11)) {
            this.t = q(typedArrayObtainStyledAttributes, 11);
        }
        this.D = typedArrayObtainStyledAttributes.getBoolean(31, typedArrayObtainStyledAttributes.getBoolean(12, true));
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(32);
        this.z = zHasValue;
        if (zHasValue) {
            this.A = typedArrayObtainStyledAttributes.getBoolean(32, typedArrayObtainStyledAttributes.getBoolean(14, true));
        }
        this.B = typedArrayObtainStyledAttributes.getBoolean(24, typedArrayObtainStyledAttributes.getBoolean(15, false));
        this.w = typedArrayObtainStyledAttributes.getBoolean(25, typedArrayObtainStyledAttributes.getBoolean(25, true));
        this.C = typedArrayObtainStyledAttributes.getBoolean(20, typedArrayObtainStyledAttributes.getBoolean(20, false));
        typedArrayObtainStyledAttributes.recycle();
    }

    public static void x(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                x(viewGroup.getChildAt(childCount), z);
            }
        }
    }

    public final boolean A() {
        return (this.b == null || !this.r || TextUtils.isEmpty(this.l)) ? false : true;
    }

    public final boolean a(Serializable serializable) {
        InterfaceC0069bk interfaceC0069bk = this.e;
        return interfaceC0069bk == null || interfaceC0069bk.a(serializable);
    }

    public void b(Bundle bundle) {
        Parcelable parcelable;
        if (TextUtils.isEmpty(this.l) || (parcelable = bundle.getParcelable(this.l)) == null) {
            return;
        }
        this.J = false;
        r(parcelable);
        if (!this.J) {
            throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
        }
    }

    public void c(Bundle bundle) {
        if (TextUtils.isEmpty(this.l)) {
            return;
        }
        this.J = false;
        Parcelable parcelableS = s();
        if (!this.J) {
            throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
        }
        if (parcelableS != null) {
            bundle.putParcelable(this.l, parcelableS);
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(Preference preference) {
        Preference preference2 = preference;
        int i = this.g;
        int i2 = preference2.g;
        if (i != i2) {
            return i - i2;
        }
        CharSequence charSequence = this.h;
        CharSequence charSequence2 = preference2.h;
        if (charSequence == charSequence2) {
            return 0;
        }
        if (charSequence == null) {
            return 1;
        }
        if (charSequence2 == null) {
            return -1;
        }
        return charSequence.toString().compareToIgnoreCase(preference2.h.toString());
    }

    public long d() {
        return this.c;
    }

    public final int e(int i) {
        return !A() ? i : this.b.b().getInt(this.l, i);
    }

    public final String f(String str) {
        return !A() ? str : this.b.b().getString(this.l, str);
    }

    public CharSequence g() {
        InterfaceC0161ek interfaceC0161ek = this.L;
        return interfaceC0161ek != null ? interfaceC0161ek.a(this) : this.i;
    }

    public boolean h() {
        return this.p && this.u && this.v;
    }

    public void i() {
        int iIndexOf;
        C0376lk c0376lk = this.G;
        if (c0376lk == null || (iIndexOf = c0376lk.f.indexOf(this)) == -1) {
            return;
        }
        c0376lk.a.c(iIndexOf, 1, this);
    }

    public void j(boolean z) {
        ArrayList arrayList = this.H;
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Preference) arrayList.get(i)).o(z);
        }
    }

    public void k() {
        PreferenceScreen preferenceScreen;
        String str = this.s;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        C0438nk c0438nk = this.b;
        Preference preferenceB = null;
        if (c0438nk != null && (preferenceScreen = c0438nk.h) != null) {
            preferenceB = preferenceScreen.B(str);
        }
        if (preferenceB != null) {
            if (preferenceB.H == null) {
                preferenceB.H = new ArrayList();
            }
            preferenceB.H.add(this);
            o(preferenceB.z());
            return;
        }
        StringBuilder sbE = AbstractC0535qo.e("Dependency \"", str, "\" not found for preference \"");
        sbE.append(this.l);
        sbE.append("\" (title: \"");
        sbE.append((Object) this.h);
        sbE.append("\"");
        throw new IllegalStateException(sbE.toString());
    }

    public final void l(C0438nk c0438nk) {
        long j;
        this.b = c0438nk;
        if (!this.d) {
            synchronized (c0438nk) {
                j = c0438nk.b;
                c0438nk.b = 1 + j;
            }
            this.c = j;
        }
        if (A()) {
            C0438nk c0438nk2 = this.b;
            if ((c0438nk2 != null ? c0438nk2.b() : null).contains(this.l)) {
                t(null);
                return;
            }
        }
        Object obj = this.t;
        if (obj != null) {
            t(obj);
        }
    }

    public void m(C0500pk c0500pk) {
        Integer numValueOf;
        View view = c0500pk.a;
        view.setOnClickListener(this.M);
        view.setId(0);
        TextView textView = (TextView) c0500pk.q(android.R.id.summary);
        if (textView != null) {
            CharSequence charSequenceG = g();
            if (TextUtils.isEmpty(charSequenceG)) {
                textView.setVisibility(8);
                numValueOf = null;
            } else {
                textView.setText(charSequenceG);
                textView.setVisibility(0);
                numValueOf = Integer.valueOf(textView.getCurrentTextColor());
            }
        } else {
            numValueOf = null;
        }
        TextView textView2 = (TextView) c0500pk.q(android.R.id.title);
        boolean z = this.q;
        if (textView2 != null) {
            CharSequence charSequence = this.h;
            if (TextUtils.isEmpty(charSequence)) {
                textView2.setVisibility(8);
            } else {
                textView2.setText(charSequence);
                textView2.setVisibility(0);
                if (this.z) {
                    textView2.setSingleLine(this.A);
                }
                if (!z && h() && numValueOf != null) {
                    textView2.setTextColor(numValueOf.intValue());
                }
            }
        }
        ImageView imageView = (ImageView) c0500pk.q(android.R.id.icon);
        boolean z2 = this.B;
        if (imageView != null) {
            int i = this.j;
            if (i != 0 || this.k != null) {
                if (this.k == null) {
                    this.k = AbstractC0744xh.v(this.a, i);
                }
                Drawable drawable = this.k;
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                }
            }
            if (this.k != null) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(z2 ? 4 : 8);
            }
        }
        View viewQ = c0500pk.q(R.id._0_resource_name_obfuscated_res_0x7f0900cd);
        if (viewQ == null) {
            viewQ = c0500pk.q(android.R.id.icon_frame);
        }
        if (viewQ != null) {
            if (this.k != null) {
                viewQ.setVisibility(0);
            } else {
                viewQ.setVisibility(z2 ? 4 : 8);
            }
        }
        if (this.D) {
            x(view, h());
        } else {
            x(view, true);
        }
        view.setFocusable(z);
        view.setClickable(z);
        c0500pk.x = this.x;
        c0500pk.y = this.y;
        boolean z3 = this.C;
        if (z3 && this.K == null) {
            this.K = new ViewOnCreateContextMenuListenerC0131dk(this);
        }
        view.setOnCreateContextMenuListener(z3 ? this.K : null);
        view.setLongClickable(z3);
        if (!z3 || z) {
            return;
        }
        WeakHashMap weakHashMap = Hs.a;
        view.setBackground(null);
    }

    public void n() {
    }

    public final void o(boolean z) {
        if (this.u == z) {
            this.u = !z;
            j(z());
            i();
        }
    }

    public void p() {
        ArrayList arrayList;
        PreferenceScreen preferenceScreen;
        String str = this.s;
        if (str != null) {
            C0438nk c0438nk = this.b;
            Preference preferenceB = null;
            if (c0438nk != null && (preferenceScreen = c0438nk.h) != null) {
                preferenceB = preferenceScreen.B(str);
            }
            if (preferenceB == null || (arrayList = preferenceB.H) == null) {
                return;
            }
            arrayList.remove(this);
        }
    }

    public Object q(TypedArray typedArray, int i) {
        return null;
    }

    public void r(Parcelable parcelable) {
        this.J = true;
        if (parcelable != AbsSavedState.EMPTY_STATE && parcelable != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    public Parcelable s() {
        this.J = true;
        return AbsSavedState.EMPTY_STATE;
    }

    public void t(Object obj) {
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        CharSequence charSequence = this.h;
        if (!TextUtils.isEmpty(charSequence)) {
            sb.append(charSequence);
            sb.append(' ');
        }
        CharSequence charSequenceG = g();
        if (!TextUtils.isEmpty(charSequenceG)) {
            sb.append(charSequenceG);
            sb.append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public void u(View view) {
        AbstractC0284ik abstractC0284ik;
        String str;
        if (h() && this.q) {
            n();
            InterfaceC0100ck interfaceC0100ck = this.f;
            if (interfaceC0100ck != null) {
                interfaceC0100ck.c(this);
                return;
            }
            C0438nk c0438nk = this.b;
            if (c0438nk == null || (abstractC0284ik = c0438nk.i) == null || (str = this.n) == null) {
                Intent intent = this.m;
                if (intent != null) {
                    this.a.startActivity(intent);
                    return;
                }
                return;
            }
            for (Ac ac = abstractC0284ik; ac != null; ac = ac.w) {
            }
            Log.w("PreferenceFragment", "onPreferenceStartFragment is not implemented in the parent activity - attempting to use a fallback implementation. You should implement this method so that you can configure the new fragment that will be displayed, and set a transition between the fragments.");
            Qc qcL = abstractC0284ik.l();
            if (this.o == null) {
                this.o = new Bundle();
            }
            Bundle bundle = this.o;
            Kc kcG = qcL.G();
            abstractC0284ik.G().getClassLoader();
            Ac acA = kcG.a(str);
            acA.K(bundle);
            acA.L(abstractC0284ik);
            L4 l4 = new L4(qcL);
            int id = ((View) abstractC0284ik.I().getParent()).getId();
            if (id == 0) {
                throw new IllegalArgumentException("Must use non-zero containerViewId");
            }
            l4.f(id, acA, null, 2);
            if (!l4.h) {
                throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
            }
            l4.g = true;
            l4.i = null;
            l4.e(false, true);
        }
    }

    public final void v(int i) {
        if (A() && i != e(~i)) {
            SharedPreferences.Editor editorA = this.b.a();
            editorA.putInt(this.l, i);
            if (this.b.e) {
                return;
            }
            editorA.apply();
        }
    }

    public final void w(String str) {
        if (A() && !TextUtils.equals(str, f(null))) {
            SharedPreferences.Editor editorA = this.b.a();
            editorA.putString(this.l, str);
            if (this.b.e) {
                return;
            }
            editorA.apply();
        }
    }

    public void y(CharSequence charSequence) {
        if (this.L != null) {
            throw new IllegalStateException("Preference already has a SummaryProvider set.");
        }
        if (TextUtils.equals(this.i, charSequence)) {
            return;
        }
        this.i = charSequence;
        i();
    }

    public boolean z() {
        return !h();
    }

    public Preference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0403f3, android.R.attr.preferenceStyle), 0);
    }
}
