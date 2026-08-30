package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import java.util.Locale;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.Lk;
import rikka.shizuku.M2;
import rikka.shizuku.X8;

/* JADX INFO: loaded from: classes.dex */
public class TextInputEditText extends M2 {
    public final Rect g;
    public final boolean h;

    public TextInputEditText(Context context, AttributeSet attributeSet) {
        super(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0401a9, 0), attributeSet);
        this.g = new Rect();
        int[] iArr = Lk.F;
        X8.j(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0401a9, R.style._0_resource_name_obfuscated_res_0x7f12039b);
        X8.p(context, attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f0401a9, R.style._0_resource_name_obfuscated_res_0x7f12039b, new int[0]);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f0401a9, R.style._0_resource_name_obfuscated_res_0x7f12039b);
        this.h = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final TextInputLayout b() {
        for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final void getFocusedRect(Rect rect) {
        super.getFocusedRect(rect);
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB == null || !this.h || rect == null) {
            return;
        }
        Rect rect2 = this.g;
        textInputLayoutB.getFocusedRect(rect2);
        rect.bottom = rect2.bottom;
    }

    @Override // android.view.View
    public final boolean getGlobalVisibleRect(Rect rect, Point point) {
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB == null || !this.h) {
            return super.getGlobalVisibleRect(rect, point);
        }
        boolean globalVisibleRect = textInputLayoutB.getGlobalVisibleRect(rect, point);
        if (globalVisibleRect && point != null) {
            point.offset(-getScrollX(), -getScrollY());
        }
        return globalVisibleRect;
    }

    @Override // android.widget.TextView
    public final CharSequence getHint() {
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB == null || !textInputLayoutB.E) {
            return super.getHint();
        }
        if (textInputLayoutB.C) {
            return textInputLayoutB.D;
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB != null && textInputLayoutB.E && super.getHint() == null) {
            String str = Build.MANUFACTURER;
            if ((str != null ? str.toLowerCase(Locale.ENGLISH) : "").equals("meizu")) {
                setHint("");
            }
        }
    }

    @Override // rikka.shizuku.M2, android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if (inputConnectionOnCreateInputConnection != null && editorInfo.hintText == null) {
            TextInputLayout textInputLayoutB = b();
            CharSequence charSequence = null;
            if (textInputLayoutB != null && textInputLayoutB.C) {
                charSequence = textInputLayoutB.D;
            }
            editorInfo.hintText = charSequence;
        }
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        b();
    }

    @Override // android.view.View
    public final boolean requestRectangleOnScreen(Rect rect) {
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB == null || !this.h || rect == null) {
            return super.requestRectangleOnScreen(rect);
        }
        int height = textInputLayoutB.getHeight() - getHeight();
        int i = rect.left;
        int i2 = rect.top;
        int i3 = rect.right;
        int i4 = rect.bottom + height;
        Rect rect2 = this.g;
        rect2.set(i, i2, i3, i4);
        return super.requestRectangleOnScreen(rect2);
    }
}
