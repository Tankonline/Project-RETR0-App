package rikka.shizuku;

import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.a6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0024a6 extends S {
    public static final Rect n = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public final AccessibilityManager h;
    public final Chip i;
    public Cb j;
    public final /* synthetic */ Chip m;
    public final Rect d = new Rect();
    public final Rect e = new Rect();
    public final Rect f = new Rect();
    public final int[] g = new int[2];
    public int k = Integer.MIN_VALUE;
    public int l = Integer.MIN_VALUE;

    public C0024a6(Chip chip, Chip chip2) {
        this.m = chip;
        this.i = chip2;
        this.h = (AccessibilityManager) chip2.getContext().getSystemService("accessibility");
        chip2.setFocusable(true);
        WeakHashMap weakHashMap = Hs.a;
        if (chip2.getImportantForAccessibility() == 0) {
            chip2.setImportantForAccessibility(1);
        }
    }

    @Override // rikka.shizuku.S
    public final Ys b(View view) {
        if (this.j == null) {
            this.j = new Cb(this);
        }
        return this.j;
    }

    @Override // rikka.shizuku.S
    public final void d(View view, C0141e0 c0141e0) {
        View.AccessibilityDelegate accessibilityDelegate = this.a;
        AccessibilityNodeInfo accessibilityNodeInfo = c0141e0.a;
        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        Chip chip = this.m;
        C0086c6 c0086c6 = chip.e;
        accessibilityNodeInfo.setCheckable(c0086c6 != null && c0086c6.Q);
        accessibilityNodeInfo.setClickable(chip.isClickable());
        c0141e0.i(chip.getAccessibilityClassName());
        c0141e0.m(chip.getText());
    }

    public final C0141e0 j(int i) {
        Object obj;
        Object obj2;
        Chip chip = this.i;
        if (i == -1) {
            AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain(chip);
            C0141e0 c0141e0 = new C0141e0(accessibilityNodeInfoObtain);
            WeakHashMap weakHashMap = Hs.a;
            chip.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoObtain);
            ArrayList arrayList = new ArrayList();
            arrayList.add(0);
            Rect rect = Chip.t;
            C0086c6 c0086c6 = this.m.e;
            if (c0086c6 != null && (obj2 = c0086c6.M) != null && (obj2 instanceof ju)) {
            }
            if (accessibilityNodeInfoObtain.getChildCount() > 0 && arrayList.size() > 0) {
                throw new RuntimeException("Views cannot have both real and virtual children");
            }
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                c0141e0.a.addChild(chip, ((Integer) arrayList.get(i2)).intValue());
            }
            return c0141e0;
        }
        AccessibilityNodeInfo accessibilityNodeInfoObtain2 = AccessibilityNodeInfo.obtain();
        C0141e0 c0141e1 = new C0141e0(accessibilityNodeInfoObtain2);
        accessibilityNodeInfoObtain2.setEnabled(true);
        accessibilityNodeInfoObtain2.setFocusable(true);
        c0141e1.i("android.view.View");
        Rect rect2 = n;
        accessibilityNodeInfoObtain2.setBoundsInParent(rect2);
        accessibilityNodeInfoObtain2.setBoundsInScreen(rect2);
        accessibilityNodeInfoObtain2.setParent(chip);
        AccessibilityNodeInfo accessibilityNodeInfo = c0141e1.a;
        if (i == 1) {
            Chip chip2 = this.m;
            CharSequence text = chip2.getText();
            accessibilityNodeInfo.setContentDescription(chip2.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f1100bf, TextUtils.isEmpty(text) ? "" : text).trim());
            RectF rectF = chip2.r;
            rectF.setEmpty();
            C0086c6 c0086c7 = chip2.e;
            if (c0086c7 != null && (obj = c0086c7.M) != null && (obj instanceof ju)) {
            }
            int i3 = (int) rectF.left;
            int i4 = (int) rectF.top;
            int i5 = (int) rectF.right;
            int i6 = (int) rectF.bottom;
            Rect rect3 = chip2.q;
            rect3.set(i3, i4, i5, i6);
            accessibilityNodeInfo.setBoundsInParent(rect3);
            c0141e1.b(C0018a0.e);
            accessibilityNodeInfo.setEnabled(chip2.isEnabled());
        } else {
            accessibilityNodeInfo.setContentDescription("");
            accessibilityNodeInfo.setBoundsInParent(Chip.t);
        }
        if (c0141e1.g() == null && accessibilityNodeInfoObtain2.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        Rect rect4 = this.e;
        c0141e1.f(rect4);
        if (rect4.equals(rect2)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = accessibilityNodeInfoObtain2.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoObtain2.setPackageName(chip.getContext().getPackageName());
        c0141e1.b = i;
        accessibilityNodeInfoObtain2.setSource(chip, i);
        if (this.k == i) {
            accessibilityNodeInfoObtain2.setAccessibilityFocused(true);
            c0141e1.a(128);
        } else {
            accessibilityNodeInfoObtain2.setAccessibilityFocused(false);
            c0141e1.a(64);
        }
        boolean z = this.l == i;
        if (z) {
            c0141e1.a(2);
        } else if (accessibilityNodeInfoObtain2.isFocusable()) {
            c0141e1.a(1);
        }
        accessibilityNodeInfoObtain2.setFocused(z);
        int[] iArr = this.g;
        chip.getLocationOnScreen(iArr);
        Rect rect5 = this.d;
        accessibilityNodeInfoObtain2.getBoundsInScreen(rect5);
        if (rect5.equals(rect2)) {
            c0141e1.f(rect5);
            rect5.offset(iArr[0] - chip.getScrollX(), iArr[1] - chip.getScrollY());
        }
        Rect rect6 = this.f;
        if (chip.getLocalVisibleRect(rect6)) {
            rect6.offset(iArr[0] - chip.getScrollX(), iArr[1] - chip.getScrollY());
            if (rect5.intersect(rect6)) {
                accessibilityNodeInfoObtain2.setBoundsInScreen(rect5);
                if (!rect5.isEmpty() && chip.getWindowVisibility() == 0) {
                    Object parent = chip.getParent();
                    while (parent instanceof View) {
                        View view = (View) parent;
                        if (view.getAlpha() > 0.0f && view.getVisibility() == 0) {
                            parent = view.getParent();
                        }
                    }
                    if (parent != null) {
                        accessibilityNodeInfoObtain2.setVisibleToUser(true);
                    }
                }
            }
        }
        return c0141e1;
    }

    public final void k(int i, int i2) {
        View view;
        ViewParent parent;
        AccessibilityEvent accessibilityEventObtain;
        if (i == Integer.MIN_VALUE || !this.h.isEnabled() || (parent = (view = this.i).getParent()) == null) {
            return;
        }
        if (i != -1) {
            accessibilityEventObtain = AccessibilityEvent.obtain(i2);
            C0141e0 c0141e0J = j(i);
            accessibilityEventObtain.getText().add(c0141e0J.g());
            AccessibilityNodeInfo accessibilityNodeInfo = c0141e0J.a;
            accessibilityEventObtain.setContentDescription(accessibilityNodeInfo.getContentDescription());
            accessibilityEventObtain.setScrollable(accessibilityNodeInfo.isScrollable());
            accessibilityEventObtain.setPassword(accessibilityNodeInfo.isPassword());
            accessibilityEventObtain.setEnabled(accessibilityNodeInfo.isEnabled());
            accessibilityEventObtain.setChecked(accessibilityNodeInfo.isChecked());
            if (accessibilityEventObtain.getText().isEmpty() && accessibilityEventObtain.getContentDescription() == null) {
                throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
            }
            accessibilityEventObtain.setClassName(accessibilityNodeInfo.getClassName());
            accessibilityEventObtain.setSource(view, i);
            accessibilityEventObtain.setPackageName(view.getContext().getPackageName());
        } else {
            accessibilityEventObtain = AccessibilityEvent.obtain(i2);
            view.onInitializeAccessibilityEvent(accessibilityEventObtain);
        }
        parent.requestSendAccessibilityEvent(view, accessibilityEventObtain);
    }
}
