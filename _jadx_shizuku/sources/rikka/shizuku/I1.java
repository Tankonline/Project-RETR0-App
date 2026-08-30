package rikka.shizuku;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$RecycleListView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class I1 implements InterfaceC0449o0 {
    public int a;
    public final Object b;

    public /* synthetic */ I1(Object obj) {
        this.a = 0;
        this.b = obj;
    }

    public void a() {
        Drawable drawable = ((ImageView) this.b).getDrawable();
        if (drawable != null) {
            AbstractC0304ja.a(drawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.widget.ListAdapter] */
    public J1 b() {
        ?? g1;
        E1 e1 = (E1) this.b;
        J1 j1 = new J1(e1.a, this.a);
        View view = e1.e;
        H1 h1 = j1.f;
        if (view != null) {
            h1.w = view;
        } else {
            CharSequence charSequence = e1.d;
            if (charSequence != null) {
                h1.d = charSequence;
                TextView textView = h1.u;
                if (textView != null) {
                    textView.setText(charSequence);
                }
            }
            Drawable drawable = e1.c;
            if (drawable != null) {
                h1.s = drawable;
                ImageView imageView = h1.t;
                if (imageView != null) {
                    imageView.setVisibility(0);
                    h1.t.setImageDrawable(drawable);
                }
            }
        }
        CharSequence charSequence2 = e1.f;
        if (charSequence2 != null) {
            h1.e = charSequence2;
            TextView textView2 = h1.v;
            if (textView2 != null) {
                textView2.setText(charSequence2);
            }
        }
        CharSequence charSequence3 = e1.g;
        if (charSequence3 != null) {
            h1.c(-1, charSequence3, e1.h);
        }
        CharSequence charSequence4 = e1.i;
        if (charSequence4 != null) {
            h1.c(-2, charSequence4, e1.j);
        }
        CharSequence charSequence5 = e1.k;
        if (charSequence5 != null) {
            h1.c(-3, charSequence5, e1.l);
        }
        if (e1.p != null || e1.q != null) {
            AlertController$RecycleListView alertController$RecycleListView = (AlertController$RecycleListView) e1.b.inflate(h1.A, (ViewGroup) null);
            boolean z = e1.u;
            ContextThemeWrapper contextThemeWrapper = e1.a;
            if (z) {
                g1 = new B1(e1, contextThemeWrapper, h1.B, e1.p, alertController$RecycleListView);
            } else {
                int i = e1.v ? h1.C : h1.D;
                Object obj = e1.q;
                g1 = obj;
                if (obj == null) {
                    g1 = new G1(contextThemeWrapper, i, R.id.text1, e1.p);
                }
            }
            h1.x = g1;
            h1.y = e1.w;
            if (e1.r != null) {
                alertController$RecycleListView.setOnItemClickListener(new C1(e1, h1));
            } else if (e1.x != null) {
                alertController$RecycleListView.setOnItemClickListener(new D1(e1, alertController$RecycleListView, h1));
            }
            if (e1.v) {
                alertController$RecycleListView.setChoiceMode(1);
            } else if (e1.u) {
                alertController$RecycleListView.setChoiceMode(2);
            }
            h1.f = alertController$RecycleListView;
        }
        View view2 = e1.s;
        if (view2 != null) {
            h1.g = view2;
            h1.h = false;
        }
        j1.setCancelable(e1.m);
        if (e1.m) {
            j1.setCanceledOnTouchOutside(true);
        }
        j1.setOnCancelListener(null);
        j1.setOnDismissListener(e1.n);
        DialogInterfaceOnKeyListenerC0159ei dialogInterfaceOnKeyListenerC0159ei = e1.o;
        if (dialogInterfaceOnKeyListenerC0159ei != null) {
            j1.setOnKeyListener(dialogInterfaceOnKeyListenerC0159ei);
        }
        return j1;
    }

    @Override // rikka.shizuku.InterfaceC0449o0
    public boolean c(View view) {
        ((BottomSheetBehavior) this.b).C(this.a);
        return true;
    }

    public void d(AttributeSet attributeSet, int i) {
        int resourceId;
        ImageView imageView = (ImageView) this.b;
        Context context = imageView.getContext();
        int[] iArr = Nk.f;
        K3 k3N = K3.n(context, attributeSet, iArr, i);
        Hs.l(imageView, imageView.getContext(), iArr, attributeSet, (TypedArray) k3N.a, i);
        try {
            Drawable drawable = imageView.getDrawable();
            TypedArray typedArray = (TypedArray) k3N.a;
            if (drawable == null && (resourceId = typedArray.getResourceId(1, -1)) != -1 && (drawable = AbstractC0744xh.v(imageView.getContext(), resourceId)) != null) {
                imageView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                AbstractC0304ja.a(drawable);
            }
            if (typedArray.hasValue(2)) {
                imageView.setImageTintList(k3N.h(2));
            }
            if (typedArray.hasValue(3)) {
                imageView.setImageTintMode(AbstractC0304ja.c(typedArray.getInt(3, -1), null));
            }
        } finally {
            k3N.o();
        }
    }

    public void e(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        E1 e1 = (E1) this.b;
        e1.g = charSequence;
        e1.h = onClickListener;
    }

    public void f() {
        b().show();
    }

    public I1(Context context) {
        this(context, J1.h(context, 0));
    }

    public I1(Context context, int i) {
        this.b = new E1(new ContextThemeWrapper(context, J1.h(context, i)));
        this.a = i;
    }

    public I1() {
        this.a = 1;
        this.b = Collections.singletonList(null);
    }

    public I1(BottomSheetBehavior bottomSheetBehavior, int i) {
        this.b = bottomSheetBehavior;
        this.a = i;
    }
}
