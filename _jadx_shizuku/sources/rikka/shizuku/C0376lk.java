package rikka.shizuku;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.lk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0376lk extends Yk {
    public final PreferenceGroup d;
    public ArrayList e;
    public ArrayList f;
    public final ArrayList g;
    public final RunnableC0607t4 i = new RunnableC0607t4(11, this);
    public final Handler h = new Handler(Looper.getMainLooper());

    public C0376lk(PreferenceGroup preferenceGroup) {
        this.d = preferenceGroup;
        preferenceGroup.G = this;
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.g = new ArrayList();
        if (preferenceGroup instanceof PreferenceScreen) {
            j(((PreferenceScreen) preferenceGroup).T);
        } else {
            j(true);
        }
        n();
    }

    @Override // rikka.shizuku.Yk
    public final int a() {
        return this.f.size();
    }

    @Override // rikka.shizuku.Yk
    public final long b(int i) {
        if (this.b) {
            return m(i).d();
        }
        return -1L;
    }

    @Override // rikka.shizuku.Yk
    public final int c(int i) {
        C0345kk c0345kk = new C0345kk(m(i));
        ArrayList arrayList = this.g;
        int iIndexOf = arrayList.indexOf(c0345kk);
        if (iIndexOf != -1) {
            return iIndexOf;
        }
        int size = arrayList.size();
        arrayList.add(c0345kk);
        return size;
    }

    @Override // rikka.shizuku.Yk
    public final void d(AbstractC0470ol abstractC0470ol, int i) {
        ColorStateList colorStateList;
        C0500pk c0500pk = (C0500pk) abstractC0470ol;
        Preference preferenceM = m(i);
        View view = c0500pk.a;
        Drawable background = view.getBackground();
        Drawable drawable = c0500pk.u;
        if (background != drawable) {
            WeakHashMap weakHashMap = Hs.a;
            view.setBackground(drawable);
        }
        TextView textView = (TextView) c0500pk.q(R.id.title);
        if (textView != null && (colorStateList = c0500pk.v) != null && !textView.getTextColors().equals(colorStateList)) {
            textView.setTextColor(colorStateList);
        }
        preferenceM.m(c0500pk);
    }

    @Override // rikka.shizuku.Yk
    public final AbstractC0470ol f(ViewGroup viewGroup, int i) {
        C0345kk c0345kk = (C0345kk) this.g.get(i);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        TypedArray typedArrayObtainStyledAttributes = viewGroup.getContext().obtainStyledAttributes((AttributeSet) null, Jk.a);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        if (drawable == null) {
            drawable = AbstractC0744xh.v(viewGroup.getContext(), R.drawable.list_selector_background);
        }
        typedArrayObtainStyledAttributes.recycle();
        View viewInflate = layoutInflaterFrom.inflate(c0345kk.a, viewGroup, false);
        if (viewInflate.getBackground() == null) {
            WeakHashMap weakHashMap = Hs.a;
            viewInflate.setBackground(drawable);
        }
        ViewGroup viewGroup2 = (ViewGroup) viewInflate.findViewById(R.id.widget_frame);
        if (viewGroup2 != null) {
            int i2 = c0345kk.b;
            if (i2 != 0) {
                layoutInflaterFrom.inflate(i2, viewGroup2);
            } else {
                viewGroup2.setVisibility(8);
            }
        }
        return new C0500pk(viewInflate);
    }

    public final ArrayList k(PreferenceGroup preferenceGroup) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int size = preferenceGroup.O.size();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            Preference preferenceC = preferenceGroup.C(i3);
            if (preferenceC.w) {
                int i4 = preferenceGroup.S;
                if (i4 == Integer.MAX_VALUE || i2 < i4) {
                    arrayList.add(preferenceC);
                } else {
                    arrayList2.add(preferenceC);
                }
                if (preferenceC instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) preferenceC;
                    if (preferenceGroup2 instanceof PreferenceScreen) {
                        continue;
                    } else {
                        if (preferenceGroup.S != Integer.MAX_VALUE && preferenceGroup2.S != Integer.MAX_VALUE) {
                            throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
                        }
                        ArrayList arrayListK = k(preferenceGroup2);
                        int size2 = arrayListK.size();
                        int i5 = 0;
                        while (i5 < size2) {
                            Object obj = arrayListK.get(i5);
                            i5++;
                            Preference preference = (Preference) obj;
                            int i6 = preferenceGroup.S;
                            if (i6 == Integer.MAX_VALUE || i2 < i6) {
                                arrayList.add(preference);
                            } else {
                                arrayList2.add(preference);
                            }
                            i2++;
                        }
                    }
                } else {
                    i2++;
                }
            }
        }
        int i7 = preferenceGroup.S;
        if (i7 != Integer.MAX_VALUE && i2 > i7) {
            long j = preferenceGroup.c;
            CharSequence string = null;
            Bb bb = new Bb(preferenceGroup.a, null);
            bb.E = moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c003a;
            Context context = bb.a;
            Drawable drawableV = AbstractC0744xh.v(context, moe.shizuku.privileged.api.R.drawable._0_resource_name_obfuscated_res_0x7f08009b);
            if (bb.k != drawableV) {
                bb.k = drawableV;
                bb.j = 0;
                bb.i();
            }
            bb.j = moe.shizuku.privileged.api.R.drawable._0_resource_name_obfuscated_res_0x7f08009b;
            String string2 = context.getString(moe.shizuku.privileged.api.R.string._0_resource_name_obfuscated_res_0x7f110062);
            if (!TextUtils.equals(string2, bb.h)) {
                bb.h = string2;
                bb.i();
            }
            if (999 != bb.g) {
                bb.g = 999;
                C0376lk c0376lk = bb.G;
                if (c0376lk != null) {
                    Handler handler = c0376lk.h;
                    RunnableC0607t4 runnableC0607t4 = c0376lk.i;
                    handler.removeCallbacks(runnableC0607t4);
                    handler.post(runnableC0607t4);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            int size3 = arrayList2.size();
            while (i < size3) {
                Object obj2 = arrayList2.get(i);
                i++;
                Preference preference2 = (Preference) obj2;
                CharSequence charSequence = preference2.h;
                boolean z = preference2 instanceof PreferenceGroup;
                if (z && !TextUtils.isEmpty(charSequence)) {
                    arrayList3.add((PreferenceGroup) preference2);
                }
                if (arrayList3.contains(preference2.I)) {
                    if (z) {
                        arrayList3.add((PreferenceGroup) preference2);
                    }
                } else if (!TextUtils.isEmpty(charSequence)) {
                    string = string == null ? charSequence : context.getString(moe.shizuku.privileged.api.R.string._0_resource_name_obfuscated_res_0x7f110125, string, charSequence);
                }
            }
            bb.y(string);
            bb.N = j + 1000000;
            bb.f = new R2(this, 19, preferenceGroup);
            arrayList.add(bb);
        }
        return arrayList;
    }

    public final void l(ArrayList arrayList, PreferenceGroup preferenceGroup) {
        synchronized (preferenceGroup) {
            Collections.sort(preferenceGroup.O);
        }
        int size = preferenceGroup.O.size();
        for (int i = 0; i < size; i++) {
            Preference preferenceC = preferenceGroup.C(i);
            arrayList.add(preferenceC);
            C0345kk c0345kk = new C0345kk(preferenceC);
            if (!this.g.contains(c0345kk)) {
                this.g.add(c0345kk);
            }
            if (preferenceC instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup2 = (PreferenceGroup) preferenceC;
                if (!(preferenceGroup2 instanceof PreferenceScreen)) {
                    l(arrayList, preferenceGroup2);
                }
            }
            preferenceC.G = this;
        }
    }

    public final Preference m(int i) {
        if (i < 0 || i >= this.f.size()) {
            return null;
        }
        return (Preference) this.f.get(i);
    }

    public final void n() {
        ArrayList arrayList = this.e;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Preference) obj).G = null;
        }
        ArrayList arrayList2 = new ArrayList(this.e.size());
        this.e = arrayList2;
        PreferenceGroup preferenceGroup = this.d;
        l(arrayList2, preferenceGroup);
        this.f = k(preferenceGroup);
        this.a.b();
        ArrayList arrayList3 = this.e;
        int size2 = arrayList3.size();
        while (i < size2) {
            Object obj2 = arrayList3.get(i);
            i++;
            ((Preference) obj2).getClass();
        }
    }
}
