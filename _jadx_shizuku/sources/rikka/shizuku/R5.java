package rikka.shizuku;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.textfield.TextInputLayout;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class R5 extends S {
    public final /* synthetic */ int d;
    public final Object e;

    public /* synthetic */ R5(int i, Object obj) {
        this.d = i;
        this.e = obj;
    }

    @Override // rikka.shizuku.S
    public void c(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.d) {
            case 0:
                super.c(view, accessibilityEvent);
                accessibilityEvent.setChecked(((CheckableImageButton) this.e).d);
                break;
            default:
                super.c(view, accessibilityEvent);
                break;
        }
    }

    @Override // rikka.shizuku.S
    public final void d(View view, C0141e0 c0141e0) {
        int i;
        RecyclerView recyclerView;
        C0606t3 c0606t3;
        View.AccessibilityDelegate accessibilityDelegate = this.a;
        Object obj = this.e;
        switch (this.d) {
            case 0:
                AccessibilityNodeInfo accessibilityNodeInfo = c0141e0.a;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                CheckableImageButton checkableImageButton = (CheckableImageButton) obj;
                accessibilityNodeInfo.setCheckable(checkableImageButton.e);
                accessibilityNodeInfo.setChecked(checkableImageButton.d);
                break;
            case 1:
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, c0141e0.a);
                int i2 = MaterialButtonToggleGroup.k;
                MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) obj;
                if (view instanceof MaterialButton) {
                    int i3 = 0;
                    for (int i4 = 0; i4 < materialButtonToggleGroup.getChildCount(); i4++) {
                        if (materialButtonToggleGroup.getChildAt(i4) == view) {
                            i = i3;
                        } else {
                            if ((materialButtonToggleGroup.getChildAt(i4) instanceof MaterialButton) && materialButtonToggleGroup.c(i4)) {
                                i3++;
                            }
                        }
                    }
                    i = -1;
                } else {
                    i = -1;
                }
                c0141e0.j(Ys.z(((MaterialButton) view).o, 0, 1, i, 1));
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, c0141e0.a);
                Oh oh = (Oh) obj;
                c0141e0.k(oh.h0.getVisibility() == 0 ? oh.m().getString(R.string._0_resource_name_obfuscated_res_0x7f1100e6) : oh.m().getString(R.string._0_resource_name_obfuscated_res_0x7f1100e4));
                break;
            case 3:
                AccessibilityNodeInfo accessibilityNodeInfo2 = c0141e0.a;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo2);
                accessibilityNodeInfo2.setCheckable(((NavigationMenuItemView) obj).w);
                break;
            case 4:
                C0469ok c0469ok = (C0469ok) obj;
                c0469ok.g.d(view, c0141e0);
                RecyclerView recyclerView2 = c0469ok.f;
                recyclerView2.getClass();
                AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
                int iH = (abstractC0470olK == null || (recyclerView = abstractC0470olK.r) == null) ? -1 : recyclerView.H(abstractC0470olK);
                Yk yk = recyclerView2.m;
                if (yk instanceof C0376lk) {
                    ((C0376lk) yk).m(iH);
                    break;
                }
                break;
            default:
                AccessibilityNodeInfo accessibilityNodeInfo3 = c0141e0.a;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo3);
                TextInputLayout textInputLayout = (TextInputLayout) obj;
                EditText editText = textInputLayout.d;
                CharSequence contentDescription = null;
                CharSequence text = editText != null ? editText.getText() : null;
                CharSequence charSequence = textInputLayout.C ? textInputLayout.D : null;
                C0371lf c0371lf = textInputLayout.j;
                CharSequence charSequence2 = c0371lf.q ? c0371lf.p : null;
                CharSequence charSequence3 = textInputLayout.s ? textInputLayout.r : null;
                int i5 = textInputLayout.l;
                if (textInputLayout.k && textInputLayout.m && (c0606t3 = textInputLayout.o) != null) {
                    contentDescription = c0606t3.getContentDescription();
                }
                boolean zIsEmpty = TextUtils.isEmpty(text);
                boolean zIsEmpty2 = TextUtils.isEmpty(charSequence);
                boolean z = textInputLayout.t0;
                boolean zIsEmpty3 = TextUtils.isEmpty(charSequence2);
                boolean z2 = (zIsEmpty3 && TextUtils.isEmpty(contentDescription)) ? false : true;
                String string = !zIsEmpty2 ? charSequence.toString() : "";
                Po po = textInputLayout.b;
                C0606t3 c0606t4 = po.b;
                if (c0606t4.getVisibility() == 0) {
                    accessibilityNodeInfo3.setLabelFor(c0606t4);
                    accessibilityNodeInfo3.setTraversalAfter(c0606t4);
                } else {
                    accessibilityNodeInfo3.setTraversalAfter(po.d);
                }
                if (!zIsEmpty) {
                    c0141e0.m(text);
                } else if (!TextUtils.isEmpty(string)) {
                    c0141e0.m(string);
                    if (!z && charSequence3 != null) {
                        c0141e0.m(string + ", " + ((Object) charSequence3));
                    }
                } else if (charSequence3 != null) {
                    c0141e0.m(charSequence3);
                }
                if (!TextUtils.isEmpty(string)) {
                    int i6 = Build.VERSION.SDK_INT;
                    if (i6 >= 26) {
                        c0141e0.k(string);
                    } else {
                        if (!zIsEmpty) {
                            string = ((Object) text) + ", " + string;
                        }
                        c0141e0.m(string);
                    }
                    if (i6 >= 26) {
                        accessibilityNodeInfo3.setShowingHintText(zIsEmpty);
                    } else {
                        c0141e0.h(4, zIsEmpty);
                    }
                }
                accessibilityNodeInfo3.setMaxTextLength((text == null || text.length() != i5) ? -1 : i5);
                if (z2) {
                    if (zIsEmpty3) {
                        charSequence2 = contentDescription;
                    }
                    accessibilityNodeInfo3.setError(charSequence2);
                }
                C0606t3 c0606t5 = c0371lf.y;
                if (c0606t5 != null) {
                    accessibilityNodeInfo3.setLabelFor(c0606t5);
                }
                textInputLayout.c.b().n(c0141e0);
                break;
        }
    }

    @Override // rikka.shizuku.S
    public void e(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.d) {
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                super.e(view, accessibilityEvent);
                ((TextInputLayout) this.e).c.b().o(accessibilityEvent);
                break;
            default:
                super.e(view, accessibilityEvent);
                break;
        }
    }

    @Override // rikka.shizuku.S
    public boolean g(View view, int i, Bundle bundle) {
        switch (this.d) {
            case 4:
                return ((C0469ok) this.e).g.g(view, i, bundle);
            default:
                return super.g(view, i, bundle);
        }
    }

    public R5(TextInputLayout textInputLayout) {
        this.d = 5;
        this.e = textInputLayout;
    }
}
