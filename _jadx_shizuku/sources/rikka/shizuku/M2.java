package rikka.shizuku;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.widget.EditText;
import android.widget.TextView;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public class M2 extends EditText implements InterfaceC0715wj {
    public final T0 a;
    public final C0544r3 b;
    public final R2 c;
    public final Jp d;
    public final R2 e;
    public Ys f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public M2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0401a9);
        Sp.a(context);
        Lp.a(this, getContext());
        T0 t0 = new T0(this);
        this.a = t0;
        t0.i(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0401a9);
        C0544r3 c0544r3 = new C0544r3(this);
        this.b = c0544r3;
        c0544r3.d(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0401a9);
        c0544r3.b();
        R2 r2 = new R2(6, false);
        r2.b = this;
        this.c = r2;
        this.d = new Jp();
        R2 r3 = new R2(this, 4);
        this.e = r3;
        r3.H(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0401a9);
        KeyListener keyListener = getKeyListener();
        if (keyListener instanceof NumberKeyListener) {
            return;
        }
        boolean zIsFocusable = super.isFocusable();
        boolean zIsClickable = super.isClickable();
        boolean zIsLongClickable = super.isLongClickable();
        int inputType = super.getInputType();
        KeyListener keyListenerC = r3.C(keyListener);
        if (keyListenerC == keyListener) {
            return;
        }
        super.setKeyListener(keyListenerC);
        super.setRawInputType(inputType);
        super.setFocusable(zIsFocusable);
        super.setClickable(zIsClickable);
        super.setLongClickable(zIsLongClickable);
    }

    @Override // rikka.shizuku.InterfaceC0715wj
    public final B7 a(B7 b7) {
        this.d.getClass();
        return Jp.a(this, b7);
    }

    @Override // android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        T0 t0 = this.a;
        if (t0 != null) {
            t0.a();
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final ActionMode.Callback getCustomSelectionActionModeCallback() {
        return An.t(super.getCustomSelectionActionModeCallback());
    }

    @Override // android.widget.TextView
    public final TextClassifier getTextClassifier() {
        R2 r2;
        if (Build.VERSION.SDK_INT < 28 && (r2 = this.c) != null) {
            TextClassifier textClassifier = (TextClassifier) r2.c;
            return textClassifier == null ? AbstractC0328k3.a((TextView) r2.b) : textClassifier;
        }
        if (this.f == null) {
            this.f = new Ys(7, this);
        }
        return super.getTextClassifier();
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0057 A[PHI: r1
  0x0057: PHI (r1v10 java.lang.String[]) = (r1v5 java.lang.String[]), (r1v11 java.lang.String[]) binds: [B:30:0x006a, B:22:0x0055] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        int i;
        String[] strArrG;
        String[] stringArray;
        InputConnection c0464of;
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        this.b.getClass();
        C0544r3.f(editorInfo, inputConnectionOnCreateInputConnection, this);
        AbstractC0046as.I(editorInfo, inputConnectionOnCreateInputConnection, this);
        if (inputConnectionOnCreateInputConnection != null && (i = Build.VERSION.SDK_INT) <= 30 && (strArrG = Hs.g(this)) != null) {
            if (i >= 25) {
                editorInfo.contentMimeTypes = strArrG;
            } else {
                if (editorInfo.extras == null) {
                    editorInfo.extras = new Bundle();
                }
                editorInfo.extras.putStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", strArrG);
                editorInfo.extras.putStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", strArrG);
            }
            C0610t7 c0610t7 = new C0610t7(3, this);
            if (i >= 25) {
                c0464of = new C0433nf(inputConnectionOnCreateInputConnection, c0610t7);
            } else {
                String[] strArr = AbstractC0046as.c;
                if (i >= 25) {
                    stringArray = editorInfo.contentMimeTypes;
                    if (stringArray != null) {
                        strArr = stringArray;
                    }
                } else {
                    Bundle bundle = editorInfo.extras;
                    if (bundle != null) {
                        stringArray = bundle.getStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
                        if (stringArray == null) {
                            stringArray = editorInfo.extras.getStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
                        }
                        if (stringArray != null) {
                            strArr = stringArray;
                        }
                    }
                }
                if (strArr.length != 0) {
                    c0464of = new C0464of(inputConnectionOnCreateInputConnection, c0610t7);
                }
            }
            inputConnectionOnCreateInputConnection = c0464of;
        }
        return this.e.K(inputConnectionOnCreateInputConnection, editorInfo);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onDragEvent(DragEvent dragEvent) {
        Activity activity;
        boolean zA = false;
        if (Build.VERSION.SDK_INT < 31 && dragEvent.getLocalState() == null && Hs.g(this) != null) {
            Context context = getContext();
            while (true) {
                if (!(context instanceof ContextWrapper)) {
                    activity = null;
                    break;
                }
                if (context instanceof Activity) {
                    activity = (Activity) context;
                    break;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
            if (activity == null) {
                Log.i("ReceiveContent", "Can't handle drop: no activity: view=" + this);
            } else if (dragEvent.getAction() != 1 && dragEvent.getAction() == 3) {
                zA = U2.a(dragEvent, this, activity);
            }
        }
        if (zA) {
            return true;
        }
        return super.onDragEvent(dragEvent);
    }

    @Override // android.widget.EditText, android.widget.TextView
    public final boolean onTextContextMenuItem(int i) {
        C0796z7 c0796z7;
        InterfaceC0765y7 interfaceC0765y7;
        int i2;
        Ys ys;
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 31 || Hs.g(this) == null || !(i == 16908322 || i == 16908337)) {
            return super.onTextContextMenuItem(i);
        }
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        ClipData primaryClip = clipboardManager == null ? null : clipboardManager.getPrimaryClip();
        if (primaryClip != null && primaryClip.getItemCount() > 0) {
            if (i3 >= 31) {
                ys = new Ys(primaryClip, 1);
            } else {
                c0796z7 = new C0796z7();
                c0796z7.b = primaryClip;
                c0796z7.c = 1;
            }
            if (i == 16908322) {
                interfaceC0765y7 = c0796z7;
                interfaceC0765y7 = ys;
                i2 = 0;
            } else {
                interfaceC0765y7 = c0796z7;
                interfaceC0765y7 = ys;
                i2 = 1;
            }
            interfaceC0765y7.o(i2);
            Hs.i(this, interfaceC0765y7.d());
        }
        return true;
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.k();
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.l(i);
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(An.u(callback, this));
    }

    @Override // android.widget.TextView
    public final void setKeyListener(KeyListener keyListener) {
        super.setKeyListener(this.e.C(keyListener));
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.e(context, i);
        }
    }

    @Override // android.widget.TextView
    public final void setTextClassifier(TextClassifier textClassifier) {
        R2 r2;
        if (Build.VERSION.SDK_INT < 28 && (r2 = this.c) != null) {
            r2.c = textClassifier;
            return;
        }
        if (this.f == null) {
            this.f = new Ys(7, this);
        }
        super.setTextClassifier(textClassifier);
    }

    @Override // android.widget.EditText, android.widget.TextView
    public final Editable getText() {
        return Build.VERSION.SDK_INT >= 28 ? super.getText() : super.getEditableText();
    }
}
