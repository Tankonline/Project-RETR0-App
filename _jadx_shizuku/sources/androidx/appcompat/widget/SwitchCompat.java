package androidx.appcompat.widget;

import android.R;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0304ja;
import rikka.shizuku.AbstractC0323jt;
import rikka.shizuku.AbstractC0505pp;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.An;
import rikka.shizuku.C0544r3;
import rikka.shizuku.C0693vs;
import rikka.shizuku.Hs;
import rikka.shizuku.K3;
import rikka.shizuku.K5;
import rikka.shizuku.L1;
import rikka.shizuku.Lp;
import rikka.shizuku.Ma;
import rikka.shizuku.Nk;
import rikka.shizuku.R2;
import rikka.shizuku.Ys;
import rikka.shizuku.Za;

/* JADX INFO: loaded from: classes.dex */
public class SwitchCompat extends CompoundButton {
    public static final K5 R = new K5(Float.class, "thumbPos", 5);
    public static final int[] S = {R.attr.state_checked};
    public int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public int G;
    public boolean H;
    public final TextPaint I;
    public final ColorStateList J;
    public StaticLayout K;
    public StaticLayout L;
    public final L1 M;
    public ObjectAnimator N;
    public R2 O;
    public Za P;
    public final Rect Q;
    public Drawable a;
    public ColorStateList b;
    public final PorterDuff.Mode c;
    public boolean d;
    public final boolean e;
    public Drawable f;
    public ColorStateList g;
    public final PorterDuff.Mode h;
    public boolean i;
    public final boolean j;
    public final int k;
    public int l;
    public final int m;
    public final boolean n;
    public CharSequence o;
    public CharSequence p;
    public CharSequence q;
    public CharSequence r;
    public final boolean s;
    public int t;
    public final int u;
    public float v;
    public float w;
    public final VelocityTracker x;
    public final int y;
    public float z;

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040489);
    }

    public final void a() {
        Drawable drawable = this.a;
        if (drawable != null) {
            boolean z = this.d;
            boolean z2 = this.e;
            if (z || z2) {
                Drawable drawableMutate = drawable.mutate();
                this.a = drawableMutate;
                if (this.d) {
                    drawableMutate.setTintList(this.b);
                }
                if (z2) {
                    this.a.setTintMode(this.c);
                }
                if (this.a.isStateful()) {
                    this.a.setState(getDrawableState());
                }
            }
        }
    }

    public final void b() {
        Drawable drawable = this.f;
        if (drawable != null) {
            boolean z = this.i;
            boolean z2 = this.j;
            if (z || z2) {
                Drawable drawableMutate = drawable.mutate();
                this.f = drawableMutate;
                if (this.i) {
                    drawableMutate.setTintList(this.g);
                }
                if (z2) {
                    this.f.setTintMode(this.h);
                }
                if (this.f.isStateful()) {
                    this.f.setState(getDrawableState());
                }
            }
        }
    }

    public final R2 c() {
        if (this.O == null) {
            this.O = new R2(this);
        }
        return this.O;
    }

    public final int d() {
        Drawable drawable = this.f;
        if (drawable == null) {
            return 0;
        }
        Rect rect = this.Q;
        drawable.getPadding(rect);
        Drawable drawable2 = this.a;
        Rect rectB = drawable2 != null ? AbstractC0304ja.b(drawable2) : AbstractC0304ja.c;
        return ((((this.A - this.C) - rect.left) - rect.right) - rectB.left) - rectB.right;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int i;
        int i2;
        int i3 = this.D;
        int i4 = this.E;
        int i5 = this.F;
        int i6 = this.G;
        int iD = ((int) (((AbstractC0323jt.a(this) ? 1.0f - this.z : this.z) * d()) + 0.5f)) + i3;
        Drawable drawable = this.a;
        Rect rectB = drawable != null ? AbstractC0304ja.b(drawable) : AbstractC0304ja.c;
        Drawable drawable2 = this.f;
        Rect rect = this.Q;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            int i7 = rect.left;
            iD += i7;
            if (rectB != null) {
                int i8 = rectB.left;
                if (i8 > i7) {
                    i3 += i8 - i7;
                }
                int i9 = rectB.top;
                int i10 = rect.top;
                i = i9 > i10 ? (i9 - i10) + i4 : i4;
                int i11 = rectB.right;
                int i12 = rect.right;
                if (i11 > i12) {
                    i5 -= i11 - i12;
                }
                int i13 = rectB.bottom;
                int i14 = rect.bottom;
                if (i13 > i14) {
                    i2 = i6 - (i13 - i14);
                }
                this.f.setBounds(i3, i, i5, i2);
            } else {
                i = i4;
            }
            i2 = i6;
            this.f.setBounds(i3, i, i5, i2);
        }
        Drawable drawable3 = this.a;
        if (drawable3 != null) {
            drawable3.getPadding(rect);
            int i15 = iD - rect.left;
            int i16 = iD + this.C + rect.right;
            this.a.setBounds(i15, i4, i16, i6);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(i15, i4, i16, i6);
            }
        }
        super.draw(canvas);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
        Drawable drawable2 = this.f;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.a;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.f;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    public final void e(Typeface typeface) {
        TextPaint textPaint = this.I;
        if ((textPaint.getTypeface() == null || textPaint.getTypeface().equals(typeface)) && (textPaint.getTypeface() != null || typeface == null)) {
            return;
        }
        textPaint.setTypeface(typeface);
        requestLayout();
        invalidate();
    }

    public final void f(CharSequence charSequence) {
        this.q = charSequence;
        R2 r2C = c();
        TransformationMethod transformationMethodZ = ((AbstractC0046as) ((Ys) r2C.c).b).Z(this.M);
        if (transformationMethodZ != null) {
            charSequence = transformationMethodZ.getTransformation(charSequence, this);
        }
        this.r = charSequence;
        this.L = null;
        if (this.s) {
            h();
        }
    }

    public final void g(CharSequence charSequence) {
        this.o = charSequence;
        R2 r2C = c();
        TransformationMethod transformationMethodZ = ((AbstractC0046as) ((Ys) r2C.c).b).Z(this.M);
        if (transformationMethodZ != null) {
            charSequence = transformationMethodZ.getTransformation(charSequence, this);
        }
        this.p = charSequence;
        this.K = null;
        if (this.s) {
            h();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public final int getCompoundPaddingLeft() {
        if (!AbstractC0323jt.a(this)) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.A;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.m : compoundPaddingLeft;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public final int getCompoundPaddingRight() {
        if (AbstractC0323jt.a(this)) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.A;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.m : compoundPaddingRight;
    }

    @Override // android.widget.TextView
    public final ActionMode.Callback getCustomSelectionActionModeCallback() {
        return An.t(super.getCustomSelectionActionModeCallback());
    }

    public final void h() {
        if (this.P == null && ((AbstractC0046as) ((Ys) this.O.c).b).E() && Ma.j != null) {
            Ma maA = Ma.a();
            int iB = maA.b();
            if (iB == 3 || iB == 0) {
                Za za = new Za(this);
                this.P = za;
                maA.f(za);
            }
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.f;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ObjectAnimator objectAnimator = this.N;
        if (objectAnimator == null || !objectAnimator.isStarted()) {
            return;
        }
        this.N.end();
        this.N = null;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, S);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        int width;
        super.onDraw(canvas);
        Drawable drawable = this.f;
        Rect rect = this.Q;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i = this.E;
        int i2 = this.G;
        int i3 = i + rect.top;
        int i4 = i2 - rect.bottom;
        Drawable drawable2 = this.a;
        if (drawable != null) {
            if (!this.n || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Rect rectB = AbstractC0304ja.b(drawable2);
                drawable2.copyBounds(rect);
                rect.left += rectB.left;
                rect.right -= rectB.right;
                int iSave = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(iSave);
            }
        }
        int iSave2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        StaticLayout staticLayout = this.z > 0.5f ? this.K : this.L;
        if (staticLayout != null) {
            int[] drawableState = getDrawableState();
            ColorStateList colorStateList = this.J;
            TextPaint textPaint = this.I;
            if (colorStateList != null) {
                textPaint.setColor(colorStateList.getColorForState(drawableState, 0));
            }
            textPaint.drawableState = drawableState;
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds();
                width = bounds.left + bounds.right;
            } else {
                width = getWidth();
            }
            canvas.translate((width / 2) - (staticLayout.getWidth() / 2), ((i3 + i4) / 2) - (staticLayout.getHeight() / 2));
            staticLayout.draw(canvas);
        }
        canvas.restoreToCount(iSave2);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
        if (Build.VERSION.SDK_INT < 30) {
            CharSequence charSequence = isChecked() ? this.o : this.q;
            if (TextUtils.isEmpty(charSequence)) {
                return;
            }
            CharSequence text = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty(text)) {
                accessibilityNodeInfo.setText(charSequence);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(text);
            sb.append(' ');
            sb.append(charSequence);
            accessibilityNodeInfo.setText(sb);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iMax;
        int width;
        int paddingLeft;
        int height;
        int paddingTop;
        super.onLayout(z, i, i2, i3, i4);
        int iMax2 = 0;
        if (this.a != null) {
            Drawable drawable = this.f;
            Rect rect = this.Q;
            if (drawable != null) {
                drawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect rectB = AbstractC0304ja.b(this.a);
            iMax = Math.max(0, rectB.left - rect.left);
            iMax2 = Math.max(0, rectB.right - rect.right);
        } else {
            iMax = 0;
        }
        if (AbstractC0323jt.a(this)) {
            paddingLeft = getPaddingLeft() + iMax;
            width = ((this.A + paddingLeft) - iMax) - iMax2;
        } else {
            width = (getWidth() - getPaddingRight()) - iMax2;
            paddingLeft = (width - this.A) + iMax + iMax2;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            int height2 = ((getHeight() + getPaddingTop()) - getPaddingBottom()) / 2;
            int i5 = this.B;
            int i6 = height2 - (i5 / 2);
            height = i5 + i6;
            paddingTop = i6;
        } else if (gravity != 80) {
            paddingTop = getPaddingTop();
            height = this.B + paddingTop;
        } else {
            height = getHeight() - getPaddingBottom();
            paddingTop = height - this.B;
        }
        this.D = paddingLeft;
        this.E = paddingTop;
        this.G = height;
        this.F = width;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        int intrinsicWidth;
        int intrinsicHeight;
        int iMax;
        boolean z = this.s;
        int intrinsicHeight2 = 0;
        if (z) {
            StaticLayout staticLayout = this.K;
            TextPaint textPaint = this.I;
            if (staticLayout == null) {
                CharSequence charSequence = this.p;
                this.K = new StaticLayout(charSequence, textPaint, charSequence != null ? (int) Math.ceil(Layout.getDesiredWidth(charSequence, textPaint)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            }
            if (this.L == null) {
                CharSequence charSequence2 = this.r;
                this.L = new StaticLayout(charSequence2, textPaint, charSequence2 != null ? (int) Math.ceil(Layout.getDesiredWidth(charSequence2, textPaint)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            }
        }
        Drawable drawable = this.a;
        Rect rect = this.Q;
        if (drawable != null) {
            drawable.getPadding(rect);
            intrinsicWidth = (this.a.getIntrinsicWidth() - rect.left) - rect.right;
            intrinsicHeight = this.a.getIntrinsicHeight();
        } else {
            intrinsicWidth = 0;
            intrinsicHeight = 0;
        }
        if (z) {
            iMax = (this.k * 2) + Math.max(this.K.getWidth(), this.L.getWidth());
        } else {
            iMax = 0;
        }
        this.C = Math.max(iMax, intrinsicWidth);
        Drawable drawable2 = this.f;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            intrinsicHeight2 = this.f.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int iMax2 = rect.left;
        int iMax3 = rect.right;
        Drawable drawable3 = this.a;
        if (drawable3 != null) {
            Rect rectB = AbstractC0304ja.b(drawable3);
            iMax2 = Math.max(iMax2, rectB.left);
            iMax3 = Math.max(iMax3, rectB.right);
        }
        int iMax4 = this.H ? Math.max(this.l, (this.C * 2) + iMax2 + iMax3) : this.l;
        int iMax5 = Math.max(intrinsicHeight2, intrinsicHeight);
        this.A = iMax4;
        this.B = iMax5;
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < iMax5) {
            setMeasuredDimension(getMeasuredWidthAndState(), iMax5);
        }
    }

    @Override // android.view.View
    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = isChecked() ? this.o : this.q;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0090  */
    /* JADX WARN: Code duplicated, block: B:42:0x0095  */
    /* JADX WARN: Code duplicated, block: B:47:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:50:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:64:0x00da  */
    /* JADX WARN: Code duplicated, block: B:66:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:69:0x00f4  */
    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean zIsChecked;
        boolean z2;
        float f;
        VelocityTracker velocityTracker = this.x;
        velocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int i = this.u;
        float f2 = 1.0f;
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (isEnabled() && this.a != null) {
                int iD = (int) (((AbstractC0323jt.a(this) ? 1.0f - this.z : this.z) * d()) + 0.5f);
                Drawable drawable = this.a;
                Rect rect = this.Q;
                drawable.getPadding(rect);
                int i2 = this.E - i;
                int i3 = (this.D + iD) - i;
                int i4 = this.C + i3 + rect.left + rect.right + i;
                int i5 = this.G + i;
                if (x > i3 && x < i4 && y > i2 && y < i5) {
                    this.t = 1;
                    this.v = x;
                    this.w = y;
                }
            }
        } else if (actionMasked == 1) {
            if (this.t == 2) {
                this.t = 0;
                if (motionEvent.getAction() == 1 || !isEnabled()) {
                    z = false;
                } else {
                    z = true;
                }
                zIsChecked = isChecked();
                if (z) {
                    velocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = velocityTracker.getXVelocity();
                    z2 = Math.abs(xVelocity) > ((float) this.y) ? this.z > 0.5f : !(!AbstractC0323jt.a(this) ? xVelocity > 0.0f : xVelocity < 0.0f);
                } else {
                    z2 = zIsChecked;
                }
                if (z2 != zIsChecked) {
                    playSoundEffect(0);
                }
                setChecked(z2);
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.setAction(3);
                super.onTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                super.onTouchEvent(motionEvent);
                return true;
            }
            this.t = 0;
            velocityTracker.clear();
        } else if (actionMasked == 2) {
            int i6 = this.t;
            if (i6 == 1) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                float f3 = i;
                if (Math.abs(x2 - this.v) > f3 || Math.abs(y2 - this.w) > f3) {
                    this.t = 2;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    this.v = x2;
                    this.w = y2;
                    return true;
                }
            } else if (i6 == 2) {
                float x3 = motionEvent.getX();
                int iD2 = d();
                float f4 = x3 - this.v;
                if (iD2 != 0) {
                    f = f4 / iD2;
                } else {
                    f = f4 > 0.0f ? 1.0f : -1.0f;
                }
                if (AbstractC0323jt.a(this)) {
                    f = -f;
                }
                float f5 = this.z;
                float f6 = f + f5;
                if (f6 < 0.0f) {
                    f2 = 0.0f;
                } else if (f6 <= 1.0f) {
                    f2 = f6;
                }
                if (f2 != f5) {
                    this.v = x3;
                    this.z = f2;
                    invalidate();
                }
                return true;
            }
        } else if (actionMasked == 3) {
            if (this.t == 2) {
                this.t = 0;
                if (motionEvent.getAction() == 1) {
                    z = false;
                } else {
                    z = false;
                }
                zIsChecked = isChecked();
                if (z) {
                    velocityTracker.computeCurrentVelocity(1000);
                    float xVelocity2 = velocityTracker.getXVelocity();
                    if (Math.abs(xVelocity2) > ((float) this.y)) {
                    }
                } else {
                    z2 = zIsChecked;
                }
                if (z2 != zIsChecked) {
                    playSoundEffect(0);
                }
                setChecked(z2);
                MotionEvent motionEventObtain2 = MotionEvent.obtain(motionEvent);
                motionEventObtain2.setAction(3);
                super.onTouchEvent(motionEventObtain2);
                motionEventObtain2.recycle();
                super.onTouchEvent(motionEvent);
                return true;
            }
            this.t = 0;
            velocityTracker.clear();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView
    public final void setAllCaps(boolean z) {
        super.setAllCaps(z);
        c().X(z);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void setChecked(boolean z) {
        super.setChecked(z);
        boolean zIsChecked = isChecked();
        if (zIsChecked) {
            if (Build.VERSION.SDK_INT >= 30) {
                Object string = this.o;
                if (string == null) {
                    string = getResources().getString(moe.shizuku.privileged.api.R.string._0_resource_name_obfuscated_res_0x7f110007);
                }
                Object obj = string;
                WeakHashMap weakHashMap = Hs.a;
                new C0693vs(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901c5, CharSequence.class, 64, 30, 2).d(this, obj);
            }
        } else if (Build.VERSION.SDK_INT >= 30) {
            Object string2 = this.q;
            if (string2 == null) {
                string2 = getResources().getString(moe.shizuku.privileged.api.R.string._0_resource_name_obfuscated_res_0x7f110006);
            }
            Object obj2 = string2;
            WeakHashMap weakHashMap2 = Hs.a;
            new C0693vs(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901c5, CharSequence.class, 64, 30, 2).d(this, obj2);
        }
        if (getWindowToken() != null) {
            WeakHashMap weakHashMap3 = Hs.a;
            if (isLaidOut()) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, R, zIsChecked ? 1.0f : 0.0f);
                this.N = objectAnimatorOfFloat;
                objectAnimatorOfFloat.setDuration(250L);
                AbstractC0505pp.a(this.N, true);
                this.N.start();
                return;
            }
        }
        ObjectAnimator objectAnimator = this.N;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        this.z = zIsChecked ? 1.0f : 0.0f;
        invalidate();
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(An.u(callback, this));
    }

    @Override // android.widget.TextView
    public final void setFilters(InputFilter[] inputFilterArr) {
        super.setFilters(c().B(inputFilterArr));
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void toggle() {
        setChecked(!isChecked());
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.a || drawable == this.f;
    }

    public SwitchCompat(Context context, AttributeSet attributeSet, int i) {
        Typeface typeface;
        int resourceId;
        super(context, attributeSet, i);
        this.b = null;
        this.c = null;
        this.d = false;
        this.e = false;
        this.g = null;
        this.h = null;
        this.i = false;
        this.j = false;
        this.x = VelocityTracker.obtain();
        this.H = true;
        this.Q = new Rect();
        Lp.a(this, getContext());
        TextPaint textPaint = new TextPaint(1);
        this.I = textPaint;
        textPaint.density = getResources().getDisplayMetrics().density;
        int[] iArr = Nk.w;
        K3 k3N = K3.n(context, attributeSet, iArr, i);
        Hs.l(this, context, iArr, attributeSet, (TypedArray) k3N.a, i);
        Drawable drawableI = k3N.i(2);
        this.a = drawableI;
        if (drawableI != null) {
            drawableI.setCallback(this);
        }
        Drawable drawableI2 = k3N.i(11);
        this.f = drawableI2;
        if (drawableI2 != null) {
            drawableI2.setCallback(this);
        }
        TypedArray typedArray = (TypedArray) k3N.a;
        g(typedArray.getText(0));
        f(typedArray.getText(1));
        this.s = typedArray.getBoolean(3, true);
        this.k = typedArray.getDimensionPixelSize(8, 0);
        this.l = typedArray.getDimensionPixelSize(5, 0);
        this.m = typedArray.getDimensionPixelSize(6, 0);
        this.n = typedArray.getBoolean(4, false);
        ColorStateList colorStateListH = k3N.h(9);
        if (colorStateListH != null) {
            this.b = colorStateListH;
            this.d = true;
        }
        PorterDuff.Mode modeC = AbstractC0304ja.c(typedArray.getInt(10, -1), null);
        if (modeC != null) {
            this.c = modeC;
            this.e = true;
        }
        if (this.d || this.e) {
            a();
        }
        ColorStateList colorStateListH2 = k3N.h(12);
        if (colorStateListH2 != null) {
            this.g = colorStateListH2;
            this.i = true;
        }
        PorterDuff.Mode modeC2 = AbstractC0304ja.c(typedArray.getInt(13, -1), null);
        if (modeC2 != null) {
            this.h = modeC2;
            this.j = true;
        }
        if (this.i || this.j) {
            b();
        }
        int resourceId2 = typedArray.getResourceId(7, 0);
        if (resourceId2 != 0) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(resourceId2, Nk.x);
            ColorStateList colorStateList = (!typedArrayObtainStyledAttributes.hasValue(3) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(3, 0)) == 0 || (colorStateList = AbstractC0599sr.o(context, resourceId)) == null) ? typedArrayObtainStyledAttributes.getColorStateList(3) : colorStateList;
            if (colorStateList != null) {
                this.J = colorStateList;
            } else {
                this.J = getTextColors();
            }
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
            if (dimensionPixelSize != 0) {
                float f = dimensionPixelSize;
                if (f != textPaint.getTextSize()) {
                    textPaint.setTextSize(f);
                    requestLayout();
                }
            }
            int i2 = typedArrayObtainStyledAttributes.getInt(1, -1);
            int i3 = typedArrayObtainStyledAttributes.getInt(2, -1);
            if (i2 == 1) {
                typeface = Typeface.SANS_SERIF;
            } else if (i2 != 2) {
                typeface = i2 != 3 ? null : Typeface.MONOSPACE;
            } else {
                typeface = Typeface.SERIF;
            }
            if (i3 > 0) {
                Typeface typefaceDefaultFromStyle = typeface == null ? Typeface.defaultFromStyle(i3) : Typeface.create(typeface, i3);
                e(typefaceDefaultFromStyle);
                int i4 = (~(typefaceDefaultFromStyle != null ? typefaceDefaultFromStyle.getStyle() : 0)) & i3;
                textPaint.setFakeBoldText((i4 & 1) != 0);
                textPaint.setTextSkewX((2 & i4) != 0 ? -0.25f : 0.0f);
            } else {
                textPaint.setFakeBoldText(false);
                textPaint.setTextSkewX(0.0f);
                e(typeface);
            }
            if (typedArrayObtainStyledAttributes.getBoolean(14, false)) {
                Context context2 = getContext();
                L1 l1 = new L1();
                l1.a = context2.getResources().getConfiguration().locale;
                this.M = l1;
            } else {
                this.M = null;
            }
            g(this.o);
            f(this.q);
            typedArrayObtainStyledAttributes.recycle();
        }
        new C0544r3(this).d(attributeSet, i);
        k3N.o();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.u = viewConfiguration.getScaledTouchSlop();
        this.y = viewConfiguration.getScaledMinimumFlingVelocity();
        c().H(attributeSet, i);
        refreshDrawableState();
        setChecked(isChecked());
    }
}
