package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class Eg extends ViewGroup {
    public boolean a;
    public final int b;
    public int c;
    public int d;
    public final int e;
    public int f;
    public final float g;
    public final boolean h;
    public int[] i;
    public int[] j;
    public final Drawable k;
    public final int l;
    public final int m;
    public final int n;
    public final int o;

    public Eg(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = true;
        this.b = -1;
        this.c = 0;
        this.e = 8388659;
        int[] iArr = Nk.n;
        K3 k3N = K3.n(context, attributeSet, iArr, i);
        Hs.l(this, context, iArr, attributeSet, (TypedArray) k3N.a, i);
        TypedArray typedArray = (TypedArray) k3N.a;
        int i2 = typedArray.getInt(1, -1);
        if (i2 >= 0 && this.d != i2) {
            this.d = i2;
            requestLayout();
        }
        int i3 = typedArray.getInt(0, -1);
        if (i3 >= 0 && this.e != i3) {
            i3 = (8388615 & i3) == 0 ? i3 | 8388611 : i3;
            this.e = (i3 & 112) == 0 ? i3 | 48 : i3;
            requestLayout();
        }
        boolean z = typedArray.getBoolean(2, true);
        if (!z) {
            this.a = z;
        }
        this.g = typedArray.getFloat(4, -1.0f);
        this.b = typedArray.getInt(3, -1);
        this.h = typedArray.getBoolean(7, false);
        Drawable drawableI = k3N.i(5);
        if (drawableI != this.k) {
            this.k = drawableI;
            if (drawableI != null) {
                this.l = drawableI.getIntrinsicWidth();
                this.m = drawableI.getIntrinsicHeight();
            } else {
                this.l = 0;
                this.m = 0;
            }
            setWillNotDraw(drawableI == null);
            requestLayout();
        }
        this.n = typedArray.getInt(8, 0);
        this.o = typedArray.getDimensionPixelSize(6, 0);
        k3N.o();
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof Dg;
    }

    public final void g(Canvas canvas, int i) {
        Drawable drawable = this.k;
        int paddingLeft = getPaddingLeft();
        int i2 = this.o;
        drawable.setBounds(paddingLeft + i2, i, (getWidth() - getPaddingRight()) - i2, this.m + i);
        this.k.draw(canvas);
    }

    @Override // android.view.View
    public final int getBaseline() {
        int i;
        int i2 = this.b;
        if (i2 < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= i2) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (i2 == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.c;
        if (this.d == 1 && (i = this.e & 112) != 48) {
            if (i == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.f) / 2;
            } else if (i == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.f;
            }
        }
        return bottom + ((LinearLayout.LayoutParams) ((Dg) childAt.getLayoutParams())).topMargin + baseline;
    }

    public final void h(Canvas canvas, int i) {
        Drawable drawable = this.k;
        int paddingTop = getPaddingTop();
        int i2 = this.o;
        drawable.setBounds(i, paddingTop + i2, this.l + i, (getHeight() - getPaddingBottom()) - i2);
        this.k.draw(canvas);
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
    public Dg generateDefaultLayoutParams() {
        int i = this.d;
        if (i == 0) {
            return new Dg(-2, -2);
        }
        if (i == 1) {
            return new Dg(-1, -2);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public Dg generateLayoutParams(AttributeSet attributeSet) {
        return new Dg(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public Dg generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new Dg(layoutParams);
    }

    public final boolean l(int i) {
        int i2 = this.n;
        if (i == 0) {
            return (i2 & 1) != 0;
        }
        if (i == getChildCount()) {
            return (i2 & 4) != 0;
        }
        if ((i2 & 2) != 0) {
            for (int i3 = i - 1; i3 >= 0; i3--) {
                if (getChildAt(i3).getVisibility() != 8) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int right;
        int left;
        int i;
        int bottom;
        if (this.k == null) {
            return;
        }
        int i2 = 0;
        if (this.d == 1) {
            int childCount = getChildCount();
            while (i2 < childCount) {
                View childAt = getChildAt(i2);
                if (childAt != null && childAt.getVisibility() != 8 && l(i2)) {
                    g(canvas, (childAt.getTop() - ((LinearLayout.LayoutParams) ((Dg) childAt.getLayoutParams())).topMargin) - this.m);
                }
                i2++;
            }
            if (l(childCount)) {
                View childAt2 = getChildAt(childCount - 1);
                if (childAt2 == null) {
                    bottom = (getHeight() - getPaddingBottom()) - this.m;
                } else {
                    bottom = childAt2.getBottom() + ((LinearLayout.LayoutParams) ((Dg) childAt2.getLayoutParams())).bottomMargin;
                }
                g(canvas, bottom);
                return;
            }
            return;
        }
        int childCount2 = getChildCount();
        boolean zA = AbstractC0323jt.a(this);
        while (i2 < childCount2) {
            View childAt3 = getChildAt(i2);
            if (childAt3 != null && childAt3.getVisibility() != 8 && l(i2)) {
                Dg dg = (Dg) childAt3.getLayoutParams();
                h(canvas, zA ? childAt3.getRight() + ((LinearLayout.LayoutParams) dg).rightMargin : (childAt3.getLeft() - ((LinearLayout.LayoutParams) dg).leftMargin) - this.l);
            }
            i2++;
        }
        if (l(childCount2)) {
            View childAt4 = getChildAt(childCount2 - 1);
            if (childAt4 != null) {
                Dg dg2 = (Dg) childAt4.getLayoutParams();
                if (zA) {
                    left = childAt4.getLeft() - ((LinearLayout.LayoutParams) dg2).leftMargin;
                    i = this.l;
                    right = left - i;
                } else {
                    right = childAt4.getRight() + ((LinearLayout.LayoutParams) dg2).rightMargin;
                }
            } else if (zA) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i = this.l;
                right = left - i;
            }
            h(canvas, right);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    /* JADX WARN: Code duplicated, block: B:29:0x009f  */
    /* JADX WARN: Code duplicated, block: B:58:0x0156  */
    /* JADX WARN: Code duplicated, block: B:61:0x0160  */
    /* JADX WARN: Code duplicated, block: B:63:0x0164  */
    /* JADX WARN: Code duplicated, block: B:65:0x0168  */
    /* JADX WARN: Code duplicated, block: B:66:0x016c  */
    /* JADX WARN: Code duplicated, block: B:68:0x0174  */
    /* JADX WARN: Code duplicated, block: B:70:0x0180  */
    /* JADX WARN: Code duplicated, block: B:72:0x0187  */
    /* JADX WARN: Code duplicated, block: B:73:0x018e  */
    /* JADX WARN: Code duplicated, block: B:76:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:77:0x01a6  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        int i7;
        int i8;
        int baseline;
        int i9;
        int i10;
        int i11;
        int i12;
        int measuredHeight;
        int i13;
        int paddingTop;
        int i14;
        int i15;
        int i16;
        int i17 = this.e;
        int i18 = 8;
        char c = 2;
        if (this.d == 1) {
            int paddingLeft2 = getPaddingLeft();
            int i19 = i3 - i;
            int paddingRight = i19 - getPaddingRight();
            int paddingRight2 = (i19 - paddingLeft2) - getPaddingRight();
            int childCount = getChildCount();
            int i20 = i17 & 112;
            int i21 = i17 & 8388615;
            if (i20 != 16) {
                paddingTop = i20 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.f;
            } else {
                paddingTop = getPaddingTop() + (((i4 - i2) - this.f) / 2);
            }
            int i22 = 0;
            while (i22 < childCount) {
                View childAt = getChildAt(i22);
                if (childAt != null && childAt.getVisibility() != i18) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight2 = childAt.getMeasuredHeight();
                    Dg dg = (Dg) childAt.getLayoutParams();
                    int i23 = ((LinearLayout.LayoutParams) dg).gravity;
                    if (i23 < 0) {
                        i23 = i21;
                    }
                    WeakHashMap weakHashMap = Hs.a;
                    int absoluteGravity = Gravity.getAbsoluteGravity(i23, getLayoutDirection()) & 7;
                    if (absoluteGravity != 1) {
                        if (absoluteGravity != 5) {
                            i16 = ((LinearLayout.LayoutParams) dg).leftMargin + paddingLeft2;
                        } else {
                            i14 = paddingRight - measuredWidth;
                            i15 = ((LinearLayout.LayoutParams) dg).rightMargin;
                        }
                        if (l(i22)) {
                            paddingTop += this.m;
                        }
                        int i24 = paddingTop + ((LinearLayout.LayoutParams) dg).topMargin;
                        childAt.layout(i16, i24, measuredWidth + i16, i24 + measuredHeight2);
                        paddingTop = measuredHeight2 + ((LinearLayout.LayoutParams) dg).bottomMargin + i24;
                    } else {
                        i14 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft2 + ((LinearLayout.LayoutParams) dg).leftMargin;
                        i15 = ((LinearLayout.LayoutParams) dg).rightMargin;
                    }
                    i16 = i14 - i15;
                    if (l(i22)) {
                        paddingTop += this.m;
                    }
                    int i25 = paddingTop + ((LinearLayout.LayoutParams) dg).topMargin;
                    childAt.layout(i16, i25, measuredWidth + i16, i25 + measuredHeight2);
                    paddingTop = measuredHeight2 + ((LinearLayout.LayoutParams) dg).bottomMargin + i25;
                }
                i22++;
                c = c;
                i18 = 8;
            }
            return;
        }
        boolean zA = AbstractC0323jt.a(this);
        int paddingTop2 = getPaddingTop();
        int i26 = i4 - i2;
        int paddingBottom = i26 - getPaddingBottom();
        int paddingBottom2 = (i26 - paddingTop2) - getPaddingBottom();
        int childCount2 = getChildCount();
        int i27 = 8388615 & i17;
        int i28 = i17 & 112;
        boolean z2 = this.a;
        int[] iArr = this.i;
        int[] iArr2 = this.j;
        WeakHashMap weakHashMap2 = Hs.a;
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i27, getLayoutDirection());
        if (absoluteGravity2 != 1) {
            paddingLeft = absoluteGravity2 != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.f;
        } else {
            paddingLeft = getPaddingLeft() + (((i3 - i) - this.f) / 2);
        }
        if (zA) {
            i6 = childCount2 - 1;
            i5 = -1;
        } else {
            i5 = 1;
            i6 = 0;
        }
        int i29 = 0;
        while (i29 < childCount2) {
            int i30 = (i5 * i29) + i6;
            View childAt2 = getChildAt(i30);
            if (childAt2 == null) {
                i7 = i28;
            } else {
                i7 = i28;
                if (childAt2.getVisibility() != 8) {
                    int measuredWidth2 = childAt2.getMeasuredWidth();
                    int measuredHeight3 = childAt2.getMeasuredHeight();
                    Dg dg2 = (Dg) childAt2.getLayoutParams();
                    i6 = i6;
                    if (z2) {
                        i8 = paddingLeft;
                        baseline = ((LinearLayout.LayoutParams) dg2).height != -1 ? childAt2.getBaseline() : -1;
                        i9 = ((LinearLayout.LayoutParams) dg2).gravity;
                        if (i9 < 0) {
                            i9 = i7;
                        }
                        i10 = i9 & 112;
                        i11 = paddingTop2;
                        if (i10 != 16) {
                            if (i10 != 48) {
                                i12 = i11 + ((LinearLayout.LayoutParams) dg2).topMargin;
                                if (baseline != -1) {
                                    i12 = (iArr[1] - baseline) + i12;
                                }
                            } else if (i10 != 80) {
                                i12 = i11;
                            } else {
                                i12 = (paddingBottom - measuredHeight3) - ((LinearLayout.LayoutParams) dg2).bottomMargin;
                                if (baseline != -1) {
                                    measuredHeight = iArr2[2] - (childAt2.getMeasuredHeight() - baseline);
                                }
                            }
                            if (l(i30)) {
                                i13 = i8 + this.l;
                            } else {
                                i13 = i8;
                            }
                            int i31 = i13 + ((LinearLayout.LayoutParams) dg2).leftMargin;
                            childAt2.layout(i31, i12, i31 + measuredWidth2, i12 + measuredHeight3);
                            paddingLeft = measuredWidth2 + ((LinearLayout.LayoutParams) dg2).rightMargin + i31;
                        } else {
                            i12 = ((paddingBottom2 - measuredHeight3) / 2) + i11 + ((LinearLayout.LayoutParams) dg2).topMargin;
                            measuredHeight = ((LinearLayout.LayoutParams) dg2).bottomMargin;
                        }
                        i12 -= measuredHeight;
                        if (l(i30)) {
                            i13 = i8 + this.l;
                        } else {
                            i13 = i8;
                        }
                        int i32 = i13 + ((LinearLayout.LayoutParams) dg2).leftMargin;
                        childAt2.layout(i32, i12, i32 + measuredWidth2, i12 + measuredHeight3);
                        paddingLeft = measuredWidth2 + ((LinearLayout.LayoutParams) dg2).rightMargin + i32;
                    } else {
                        i8 = paddingLeft;
                    }
                    i9 = ((LinearLayout.LayoutParams) dg2).gravity;
                    if (i9 < 0) {
                        i9 = i7;
                    }
                    i10 = i9 & 112;
                    i11 = paddingTop2;
                    if (i10 != 16) {
                        if (i10 != 48) {
                            i12 = i11 + ((LinearLayout.LayoutParams) dg2).topMargin;
                            if (baseline != -1) {
                                i12 = (iArr[1] - baseline) + i12;
                            }
                        } else if (i10 != 80) {
                            i12 = i11;
                        } else {
                            i12 = (paddingBottom - measuredHeight3) - ((LinearLayout.LayoutParams) dg2).bottomMargin;
                            if (baseline != -1) {
                                measuredHeight = iArr2[2] - (childAt2.getMeasuredHeight() - baseline);
                            }
                        }
                        if (l(i30)) {
                            i13 = i8 + this.l;
                        } else {
                            i13 = i8;
                        }
                        int i33 = i13 + ((LinearLayout.LayoutParams) dg2).leftMargin;
                        childAt2.layout(i33, i12, i33 + measuredWidth2, i12 + measuredHeight3);
                        paddingLeft = measuredWidth2 + ((LinearLayout.LayoutParams) dg2).rightMargin + i33;
                    } else {
                        i12 = ((paddingBottom2 - measuredHeight3) / 2) + i11 + ((LinearLayout.LayoutParams) dg2).topMargin;
                        measuredHeight = ((LinearLayout.LayoutParams) dg2).bottomMargin;
                    }
                    i12 -= measuredHeight;
                    if (l(i30)) {
                        i13 = i8 + this.l;
                    } else {
                        i13 = i8;
                    }
                    int i34 = i13 + ((LinearLayout.LayoutParams) dg2).leftMargin;
                    childAt2.layout(i34, i12, i34 + measuredWidth2, i12 + measuredHeight3);
                    paddingLeft = measuredWidth2 + ((LinearLayout.LayoutParams) dg2).rightMargin + i34;
                }
                i29++;
                i28 = i7;
                i6 = i6;
                paddingTop2 = i11;
            }
            i11 = paddingTop2;
            i29++;
            i28 = i7;
            i6 = i6;
            paddingTop2 = i11;
        }
    }

    /* JADX WARN: Code duplicated, block: B:234:0x051d  */
    /* JADX WARN: Code duplicated, block: B:237:0x0532  */
    /* JADX WARN: Code duplicated, block: B:239:0x053b  */
    /* JADX WARN: Code duplicated, block: B:241:0x053f  */
    /* JADX WARN: Code duplicated, block: B:243:0x0560  */
    /* JADX WARN: Code duplicated, block: B:249:0x0570  */
    /* JADX WARN: Code duplicated, block: B:252:0x0577 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:254:0x057a  */
    /* JADX WARN: Code duplicated, block: B:256:0x0581 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:258:0x0584  */
    /* JADX WARN: Code duplicated, block: B:372:0x07c5  */
    /* JADX WARN: Code duplicated, block: B:66:0x0150 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:68:0x0153  */
    /* JADX WARN: Code duplicated, block: B:70:0x0159 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:72:0x015c  */
    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int iMax;
        int i5;
        int baseline;
        int i6;
        int i7;
        int i8;
        boolean z;
        int i9;
        Dg dg;
        int i10;
        boolean z2;
        int i11;
        int measuredHeight;
        boolean z3;
        int iMax2;
        boolean z4;
        int baseline2;
        int i12;
        int i13;
        int i14;
        int i15;
        int iCombineMeasuredStates;
        int iMax3;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        boolean z5;
        boolean z6;
        Eg eg = this;
        int i23 = eg.d;
        float f = eg.g;
        int i24 = -2;
        boolean z7 = eg.h;
        int i25 = 1073741824;
        int i26 = 8;
        int i27 = Integer.MIN_VALUE;
        int iMax4 = 0;
        boolean z8 = true;
        if (i23 == 1) {
            eg.f = 0;
            int childCount = eg.getChildCount();
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            boolean z9 = true;
            int i28 = 0;
            int iMax5 = 0;
            int iMax6 = 0;
            boolean z10 = false;
            int iMax7 = 0;
            int i29 = 0;
            boolean z11 = false;
            float f2 = 0.0f;
            while (i28 < childCount) {
                int i30 = childCount;
                View childAt = eg.getChildAt(i28);
                if (childAt == null) {
                    eg.f = eg.f;
                    z5 = z8;
                } else {
                    if (childAt.getVisibility() == i26) {
                        z5 = true;
                    } else {
                        if (eg.l(i28)) {
                            eg.f += eg.m;
                        }
                        Dg dg2 = (Dg) childAt.getLayoutParams();
                        float f3 = ((LinearLayout.LayoutParams) dg2).weight;
                        f2 += f3;
                        if (mode2 == i25 && ((LinearLayout.LayoutParams) dg2).height == 0 && f3 > 0.0f) {
                            int i31 = eg.f;
                            eg.f = Math.max(i31, ((LinearLayout.LayoutParams) dg2).topMargin + i31 + ((LinearLayout.LayoutParams) dg2).bottomMargin);
                            mode = mode;
                            mode2 = mode2;
                            i22 = i28;
                            i30 = i30;
                            z10 = true;
                            z5 = true;
                        } else {
                            if (((LinearLayout.LayoutParams) dg2).height != 0 || f3 <= 0.0f) {
                                i19 = i27;
                            } else {
                                ((LinearLayout.LayoutParams) dg2).height = i24;
                                i19 = 0;
                            }
                            if (f2 == 0.0f) {
                                int i32 = i28;
                                i21 = eg.f;
                                i20 = i32;
                            } else {
                                i20 = i28;
                                i21 = 0;
                            }
                            i22 = i20;
                            mode2 = mode2;
                            mode = mode;
                            i30 = i30;
                            z5 = true;
                            eg.measureChildWithMargins(childAt, i, 0, i2, i21);
                            if (i19 != i27) {
                                ((LinearLayout.LayoutParams) dg2).height = i19;
                            }
                            int measuredHeight2 = childAt.getMeasuredHeight();
                            int i33 = eg.f;
                            eg.f = Math.max(i33, i33 + measuredHeight2 + ((LinearLayout.LayoutParams) dg2).topMargin + ((LinearLayout.LayoutParams) dg2).bottomMargin);
                            if (z7) {
                                iMax4 = Math.max(measuredHeight2, iMax4);
                            }
                        }
                        int i34 = eg.b;
                        if (i34 >= 0) {
                            i28 = i22;
                            if (i34 == i28 + 1) {
                                eg.c = eg.f;
                            }
                        } else {
                            i28 = i22;
                        }
                        if (i28 < i34 && ((LinearLayout.LayoutParams) dg2).weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        if (mode == 1073741824 || ((LinearLayout.LayoutParams) dg2).width != -1) {
                            z6 = false;
                        } else {
                            z6 = z5;
                            z11 = z6;
                        }
                        int i35 = ((LinearLayout.LayoutParams) dg2).leftMargin + ((LinearLayout.LayoutParams) dg2).rightMargin;
                        int measuredWidth = childAt.getMeasuredWidth() + i35;
                        iMax7 = Math.max(iMax7, measuredWidth);
                        boolean z12 = z6;
                        int iCombineMeasuredStates2 = View.combineMeasuredStates(i29, childAt.getMeasuredState());
                        if (z9) {
                            i29 = iCombineMeasuredStates2;
                            boolean z13 = ((LinearLayout.LayoutParams) dg2).width == -1 ? z5 : false;
                            if (((LinearLayout.LayoutParams) dg2).weight > 0.0f) {
                                if (!z12) {
                                    i35 = measuredWidth;
                                }
                                iMax6 = Math.max(iMax6, i35);
                            } else {
                                if (!z12) {
                                    i35 = measuredWidth;
                                }
                                iMax5 = Math.max(iMax5, i35);
                            }
                            z9 = z13;
                        } else {
                            i29 = iCombineMeasuredStates2;
                        }
                        if (((LinearLayout.LayoutParams) dg2).weight > 0.0f) {
                            if (!z12) {
                                i35 = measuredWidth;
                            }
                            iMax6 = Math.max(iMax6, i35);
                        } else {
                            if (!z12) {
                                i35 = measuredWidth;
                            }
                            iMax5 = Math.max(iMax5, i35);
                        }
                        z9 = z13;
                    }
                    i28++;
                    mode = mode;
                    childCount = i30;
                    z8 = z5;
                    mode2 = mode2;
                    i24 = -2;
                    i25 = 1073741824;
                    i26 = 8;
                    i27 = Integer.MIN_VALUE;
                }
                i28++;
                mode = mode;
                childCount = i30;
                z8 = z5;
                mode2 = mode2;
                i24 = -2;
                i25 = 1073741824;
                i26 = 8;
                i27 = Integer.MIN_VALUE;
            }
            int i36 = childCount;
            boolean z14 = z8;
            int i37 = mode;
            int i38 = mode2;
            int i39 = iMax7;
            int i40 = i29;
            if (eg.f > 0 && eg.l(i36)) {
                eg.f += eg.m;
            }
            if (z7) {
                i15 = i38;
                if (i15 == Integer.MIN_VALUE || i15 == 0) {
                    eg.f = 0;
                    int i41 = 0;
                    while (i41 < i36) {
                        View childAt2 = eg.getChildAt(i41);
                        if (childAt2 == null) {
                            eg.f = eg.f;
                            i18 = i39;
                        } else {
                            i18 = i39;
                            if (childAt2.getVisibility() != 8) {
                                Dg dg3 = (Dg) childAt2.getLayoutParams();
                                int i42 = eg.f;
                                eg.f = Math.max(i42, i42 + iMax4 + ((LinearLayout.LayoutParams) dg3).topMargin + ((LinearLayout.LayoutParams) dg3).bottomMargin);
                            }
                            i41++;
                            i39 = i18;
                            i40 = i40;
                        }
                        i41++;
                        i39 = i18;
                        i40 = i40;
                    }
                }
                i13 = i39;
                i14 = i40;
            } else {
                i13 = i39;
                i14 = i40;
                i15 = i38;
            }
            int paddingBottom = eg.getPaddingBottom() + eg.getPaddingTop() + eg.f;
            eg.f = paddingBottom;
            int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingBottom, eg.getSuggestedMinimumHeight()), i2, 0);
            int i43 = (iResolveSizeAndState & 16777215) - eg.f;
            if (z10 || (i43 != 0 && f2 > 0.0f)) {
                if (f <= 0.0f) {
                    f = f2;
                }
                eg.f = 0;
                int iMax8 = i13;
                iCombineMeasuredStates = i14;
                for (int i44 = 0; i44 < i36; i44++) {
                    View childAt3 = eg.getChildAt(i44);
                    if (childAt3.getVisibility() != 8) {
                        Dg dg4 = (Dg) childAt3.getLayoutParams();
                        float f4 = ((LinearLayout.LayoutParams) dg4).weight;
                        if (f4 > 0.0f) {
                            int i45 = (int) ((i43 * f4) / f);
                            f -= f4;
                            int i46 = i43 - i45;
                            int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, eg.getPaddingRight() + eg.getPaddingLeft() + ((LinearLayout.LayoutParams) dg4).leftMargin + ((LinearLayout.LayoutParams) dg4).rightMargin, ((LinearLayout.LayoutParams) dg4).width);
                            if (((LinearLayout.LayoutParams) dg4).height == 0) {
                                i17 = 1073741824;
                                if (i15 == 1073741824) {
                                    if (i45 <= 0) {
                                        i45 = 0;
                                    }
                                    childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i45, 1073741824));
                                }
                                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt3.getMeasuredState() & (-256));
                                i43 = i46;
                            } else {
                                i17 = 1073741824;
                            }
                            int measuredHeight3 = childAt3.getMeasuredHeight() + i45;
                            if (measuredHeight3 < 0) {
                                measuredHeight3 = 0;
                            }
                            childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight3, i17));
                            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt3.getMeasuredState() & (-256));
                            i43 = i46;
                        }
                        int i47 = ((LinearLayout.LayoutParams) dg4).leftMargin + ((LinearLayout.LayoutParams) dg4).rightMargin;
                        int measuredWidth2 = childAt3.getMeasuredWidth() + i47;
                        iMax8 = Math.max(iMax8, measuredWidth2);
                        int i48 = i43;
                        if (i37 != 1073741824) {
                            i16 = -1;
                            if (((LinearLayout.LayoutParams) dg4).width == -1) {
                                measuredWidth2 = i47;
                            }
                        } else {
                            i16 = -1;
                        }
                        int iMax9 = Math.max(iMax5, measuredWidth2);
                        boolean z15 = (z9 && ((LinearLayout.LayoutParams) dg4).width == i16) ? z14 : false;
                        int i49 = eg.f;
                        eg.f = Math.max(i49, childAt3.getMeasuredHeight() + i49 + ((LinearLayout.LayoutParams) dg4).topMargin + ((LinearLayout.LayoutParams) dg4).bottomMargin);
                        iMax5 = iMax9;
                        z9 = z15;
                        i43 = i48;
                    }
                }
                eg.f = eg.getPaddingBottom() + eg.getPaddingTop() + eg.f;
                iMax3 = iMax5;
                i13 = iMax8;
            } else {
                iMax3 = Math.max(iMax5, iMax6);
                if (z7 && i15 != 1073741824) {
                    for (int i50 = 0; i50 < i36; i50++) {
                        View childAt4 = eg.getChildAt(i50);
                        if (childAt4 != null && childAt4.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((Dg) childAt4.getLayoutParams())).weight > 0.0f) {
                            childAt4.measure(View.MeasureSpec.makeMeasureSpec(childAt4.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(iMax4, 1073741824));
                        }
                    }
                }
                iCombineMeasuredStates = i14;
            }
            if (z9 || i37 == 1073741824) {
                iMax3 = i13;
            }
            eg.setMeasuredDimension(View.resolveSizeAndState(Math.max(eg.getPaddingRight() + eg.getPaddingLeft() + iMax3, eg.getSuggestedMinimumWidth()), i, iCombineMeasuredStates), iResolveSizeAndState);
            if (z11) {
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(eg.getMeasuredWidth(), 1073741824);
                for (int i51 = 0; i51 < i36; i51++) {
                    View childAt5 = eg.getChildAt(i51);
                    if (childAt5.getVisibility() != 8) {
                        Dg dg5 = (Dg) childAt5.getLayoutParams();
                        if (((LinearLayout.LayoutParams) dg5).width == -1) {
                            int i52 = ((LinearLayout.LayoutParams) dg5).height;
                            ((LinearLayout.LayoutParams) dg5).height = childAt5.getMeasuredHeight();
                            eg.measureChildWithMargins(childAt5, iMakeMeasureSpec, 0, i2, 0);
                            ((LinearLayout.LayoutParams) dg5).height = i52;
                        }
                    }
                }
                return;
            }
            return;
        }
        int i53 = i;
        eg.f = 0;
        int childCount2 = eg.getChildCount();
        int mode3 = View.MeasureSpec.getMode(i53);
        int mode4 = View.MeasureSpec.getMode(i2);
        if (eg.i == null || eg.j == null) {
            eg.i = new int[4];
            eg.j = new int[4];
        }
        int[] iArr = eg.i;
        int[] iArr2 = eg.j;
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        boolean z16 = eg.a;
        boolean z17 = mode3 == 1073741824;
        float f5 = 0.0f;
        boolean z18 = true;
        int i54 = 0;
        int i55 = 0;
        int iMax10 = 0;
        int iMax11 = 0;
        int iMax12 = 0;
        int iCombineMeasuredStates3 = 0;
        boolean z19 = false;
        boolean z20 = false;
        while (i54 < childCount2) {
            boolean z21 = z16;
            View childAt6 = eg.getChildAt(i54);
            if (childAt6 == null) {
                eg.f = eg.f;
                iMax2 = i55;
            } else {
                int i56 = i55;
                if (childAt6.getVisibility() == 8) {
                    i53 = i;
                    iMax2 = i56;
                } else {
                    if (eg.l(i54)) {
                        eg.f += eg.l;
                    }
                    Dg dg6 = (Dg) childAt6.getLayoutParams();
                    float f6 = ((LinearLayout.LayoutParams) dg6).weight;
                    f5 += f6;
                    i54 = i54;
                    if (mode3 == 1073741824 && ((LinearLayout.LayoutParams) dg6).width == 0 && f6 > 0.0f) {
                        if (z17) {
                            eg.f = ((LinearLayout.LayoutParams) dg6).leftMargin + ((LinearLayout.LayoutParams) dg6).rightMargin + eg.f;
                        } else {
                            int i57 = eg.f;
                            eg.f = Math.max(i57, ((LinearLayout.LayoutParams) dg6).leftMargin + i57 + ((LinearLayout.LayoutParams) dg6).rightMargin);
                        }
                        if (z21) {
                            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                            childAt6.measure(iMakeMeasureSpec2, iMakeMeasureSpec2);
                            childAt6 = childAt6;
                            f = f;
                            iArr = iArr;
                            iArr2 = iArr2;
                            i8 = i56;
                            dg = dg6;
                            z21 = z21;
                            i53 = i;
                            z = z7;
                            i9 = iMax10;
                        } else {
                            childAt6 = childAt6;
                            f = f;
                            iArr = iArr;
                            iArr2 = iArr2;
                            z19 = true;
                            i8 = i56;
                            i10 = 1073741824;
                            dg = dg6;
                            z21 = z21;
                            i53 = i;
                            z = z7;
                            i9 = iMax10;
                        }
                        if (mode4 == i10 && ((LinearLayout.LayoutParams) dg).height == -1) {
                            z2 = true;
                            z20 = true;
                        } else {
                            z2 = false;
                        }
                        i11 = ((LinearLayout.LayoutParams) dg).topMargin + ((LinearLayout.LayoutParams) dg).bottomMargin;
                        measuredHeight = childAt6.getMeasuredHeight() + i11;
                        int iCombineMeasuredStates4 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt6.getMeasuredState());
                        if (z21) {
                            baseline2 = childAt6.getBaseline();
                            z3 = z2;
                            if (baseline2 != -1) {
                                i12 = ((LinearLayout.LayoutParams) dg).gravity;
                                if (i12 < 0) {
                                    i12 = eg.e;
                                }
                                int i58 = (((i12 & 112) >> 4) & (-2)) >> 1;
                                iArr[i58] = Math.max(iArr[i58], baseline2);
                                iArr2[i58] = Math.max(iArr2[i58], measuredHeight - baseline2);
                            }
                        } else {
                            z3 = z2;
                        }
                        iMax2 = Math.max(i8, measuredHeight);
                        if (z18 || ((LinearLayout.LayoutParams) dg).height != -1) {
                            z4 = false;
                        } else {
                            z4 = true;
                        }
                        if (((LinearLayout.LayoutParams) dg).weight > 0.0f) {
                            if (!z3) {
                                i11 = measuredHeight;
                            }
                            iMax11 = Math.max(iMax11, i11);
                            iMax10 = i9;
                        } else {
                            if (!z3) {
                                i11 = measuredHeight;
                            }
                            iMax10 = Math.max(i9, i11);
                        }
                        z18 = z4;
                        iCombineMeasuredStates3 = iCombineMeasuredStates4;
                    } else {
                        if (((LinearLayout.LayoutParams) dg6).width != 0 || f6 <= 0.0f) {
                            i7 = Integer.MIN_VALUE;
                        } else {
                            ((LinearLayout.LayoutParams) dg6).width = -2;
                            i7 = 0;
                        }
                        f = f;
                        iArr2 = iArr2;
                        i8 = i56;
                        int i59 = i7;
                        z21 = z21;
                        z = z7;
                        i9 = iMax10;
                        iArr = iArr;
                        dg = dg6;
                        i53 = i;
                        eg.measureChildWithMargins(childAt6, i53, f5 == 0.0f ? eg.f : 0, i2, 0);
                        if (i59 != Integer.MIN_VALUE) {
                            ((LinearLayout.LayoutParams) dg).width = i59;
                        }
                        int measuredWidth3 = childAt6.getMeasuredWidth();
                        if (z17) {
                            eg.f = ((LinearLayout.LayoutParams) dg).leftMargin + measuredWidth3 + ((LinearLayout.LayoutParams) dg).rightMargin + eg.f;
                        } else {
                            int i60 = eg.f;
                            eg.f = Math.max(i60, i60 + measuredWidth3 + ((LinearLayout.LayoutParams) dg).leftMargin + ((LinearLayout.LayoutParams) dg).rightMargin);
                        }
                        if (z) {
                            iMax12 = Math.max(measuredWidth3, iMax12);
                        }
                    }
                    i10 = 1073741824;
                    if (mode4 == i10) {
                        z2 = false;
                    } else {
                        z2 = false;
                    }
                    i11 = ((LinearLayout.LayoutParams) dg).topMargin + ((LinearLayout.LayoutParams) dg).bottomMargin;
                    measuredHeight = childAt6.getMeasuredHeight() + i11;
                    int iCombineMeasuredStates5 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt6.getMeasuredState());
                    if (z21) {
                        baseline2 = childAt6.getBaseline();
                        z3 = z2;
                        if (baseline2 != -1) {
                            i12 = ((LinearLayout.LayoutParams) dg).gravity;
                            if (i12 < 0) {
                                i12 = eg.e;
                            }
                            int i510 = (((i12 & 112) >> 4) & (-2)) >> 1;
                            iArr[i510] = Math.max(iArr[i510], baseline2);
                            iArr2[i510] = Math.max(iArr2[i510], measuredHeight - baseline2);
                        }
                    } else {
                        z3 = z2;
                    }
                    iMax2 = Math.max(i8, measuredHeight);
                    if (z18) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    if (((LinearLayout.LayoutParams) dg).weight > 0.0f) {
                        if (!z3) {
                            i11 = measuredHeight;
                        }
                        iMax11 = Math.max(iMax11, i11);
                        iMax10 = i9;
                    } else {
                        if (!z3) {
                            i11 = measuredHeight;
                        }
                        iMax10 = Math.max(i9, i11);
                    }
                    z18 = z4;
                    iCombineMeasuredStates3 = iCombineMeasuredStates5;
                }
                i54++;
                i55 = iMax2;
                z7 = z;
                z16 = z21;
                iArr = iArr;
                f = f;
                iArr2 = iArr2;
            }
            z = z7;
            i54++;
            i55 = iMax2;
            z7 = z;
            z16 = z21;
            iArr = iArr;
            f = f;
            iArr2 = iArr2;
        }
        boolean z22 = z16;
        float f7 = f;
        boolean z23 = z7;
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        int i61 = i55;
        int i62 = iMax10;
        if (eg.f > 0 && eg.l(childCount2)) {
            eg.f += eg.l;
        }
        int i63 = iArr3[1];
        int iMax13 = (i63 == -1 && iArr3[0] == -1 && iArr3[2] == -1 && iArr3[3] == -1) ? i61 : Math.max(i61, Math.max(iArr4[3], Math.max(iArr4[0], Math.max(iArr4[1], iArr4[2]))) + Math.max(iArr3[3], Math.max(iArr3[0], Math.max(i63, iArr3[2]))));
        if (z23 && (mode3 == Integer.MIN_VALUE || mode3 == 0)) {
            eg.f = 0;
            for (int i64 = 0; i64 < childCount2; i64++) {
                View childAt7 = eg.getChildAt(i64);
                if (childAt7 == null) {
                    eg.f = eg.f;
                } else if (childAt7.getVisibility() != 8) {
                    Dg dg7 = (Dg) childAt7.getLayoutParams();
                    if (z17) {
                        eg.f = ((LinearLayout.LayoutParams) dg7).leftMargin + iMax12 + ((LinearLayout.LayoutParams) dg7).rightMargin + eg.f;
                    } else {
                        int i65 = eg.f;
                        eg.f = Math.max(i65, i65 + iMax12 + ((LinearLayout.LayoutParams) dg7).leftMargin + ((LinearLayout.LayoutParams) dg7).rightMargin);
                    }
                }
            }
        }
        int paddingRight = eg.getPaddingRight() + eg.getPaddingLeft() + eg.f;
        eg.f = paddingRight;
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingRight, eg.getSuggestedMinimumWidth()), i53, 0);
        int i66 = (iResolveSizeAndState2 & 16777215) - eg.f;
        if (z19 || (i66 != 0 && f5 > 0.0f)) {
            if (f7 > 0.0f) {
                f5 = f7;
            }
            iArr3[3] = -1;
            iArr3[2] = -1;
            iArr3[1] = -1;
            iArr3[0] = -1;
            iArr4[3] = -1;
            iArr4[2] = -1;
            iArr4[1] = -1;
            iArr4[0] = -1;
            eg.f = 0;
            iMax13 = -1;
            for (int i67 = 0; i67 < childCount2; i67++) {
                View childAt8 = eg.getChildAt(i67);
                if (childAt8 != null && childAt8.getVisibility() != 8) {
                    Dg dg8 = (Dg) childAt8.getLayoutParams();
                    float f8 = ((LinearLayout.LayoutParams) dg8).weight;
                    if (f8 > 0.0f) {
                        int i68 = (int) ((i66 * f8) / f5);
                        f5 -= f8;
                        i66 -= i68;
                        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, eg.getPaddingBottom() + eg.getPaddingTop() + ((LinearLayout.LayoutParams) dg8).topMargin + ((LinearLayout.LayoutParams) dg8).bottomMargin, ((LinearLayout.LayoutParams) dg8).height);
                        if (((LinearLayout.LayoutParams) dg8).width == 0) {
                            i6 = 1073741824;
                            if (mode3 == 1073741824) {
                                if (i68 <= 0) {
                                    i68 = 0;
                                }
                                childAt8.measure(View.MeasureSpec.makeMeasureSpec(i68, 1073741824), childMeasureSpec2);
                            }
                            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt8.getMeasuredState() & (-16777216));
                        } else {
                            i6 = 1073741824;
                        }
                        int measuredWidth4 = childAt8.getMeasuredWidth() + i68;
                        if (measuredWidth4 < 0) {
                            measuredWidth4 = 0;
                        }
                        childAt8.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth4, i6), childMeasureSpec2);
                        iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt8.getMeasuredState() & (-16777216));
                    }
                    if (z17) {
                        eg.f = childAt8.getMeasuredWidth() + ((LinearLayout.LayoutParams) dg8).leftMargin + ((LinearLayout.LayoutParams) dg8).rightMargin + eg.f;
                    } else {
                        int i69 = eg.f;
                        eg.f = Math.max(i69, childAt8.getMeasuredWidth() + i69 + ((LinearLayout.LayoutParams) dg8).leftMargin + ((LinearLayout.LayoutParams) dg8).rightMargin);
                    }
                    boolean z24 = mode4 != 1073741824 && ((LinearLayout.LayoutParams) dg8).height == -1;
                    int i70 = ((LinearLayout.LayoutParams) dg8).topMargin + ((LinearLayout.LayoutParams) dg8).bottomMargin;
                    int measuredHeight4 = childAt8.getMeasuredHeight() + i70;
                    iMax13 = Math.max(iMax13, measuredHeight4);
                    if (!z24) {
                        i70 = measuredHeight4;
                    }
                    int iMax14 = Math.max(i62, i70);
                    if (z18) {
                        i5 = -1;
                        boolean z25 = ((LinearLayout.LayoutParams) dg8).height == -1;
                        if (!z22 && (baseline = childAt8.getBaseline()) != i5) {
                            int i71 = ((LinearLayout.LayoutParams) dg8).gravity;
                            if (i71 < 0) {
                                i71 = eg.e;
                            }
                            int i72 = (((i71 & 112) >> 4) & (-2)) >> 1;
                            iArr3[i72] = Math.max(iArr3[i72], baseline);
                            iArr4[i72] = Math.max(iArr4[i72], measuredHeight4 - baseline);
                        }
                        i62 = iMax14;
                        z18 = z25;
                    } else {
                        i5 = -1;
                    }
                    if (!z22) {
                    }
                    i62 = iMax14;
                    z18 = z25;
                }
            }
            i3 = -16777216;
            eg.f = eg.getPaddingRight() + eg.getPaddingLeft() + eg.f;
            int i73 = iArr3[1];
            if (i73 == -1 && iArr3[0] == -1 && iArr3[2] == -1 && iArr3[3] == -1) {
                i4 = 0;
            } else {
                i4 = 0;
                iMax13 = Math.max(iMax13, Math.max(iArr4[3], Math.max(iArr4[0], Math.max(iArr4[1], iArr4[2]))) + Math.max(iArr3[3], Math.max(iArr3[0], Math.max(i73, iArr3[2]))));
            }
            iMax = i62;
        } else {
            iMax = Math.max(i62, iMax11);
            if (z23 && mode3 != 1073741824) {
                for (int i74 = 0; i74 < childCount2; i74++) {
                    View childAt9 = eg.getChildAt(i74);
                    if (childAt9 != null && childAt9.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((Dg) childAt9.getLayoutParams())).weight > 0.0f) {
                        childAt9.measure(View.MeasureSpec.makeMeasureSpec(iMax12, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt9.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i3 = -16777216;
            i4 = 0;
        }
        if (!z18 && mode4 != 1073741824) {
            iMax13 = iMax;
        }
        eg.setMeasuredDimension((iCombineMeasuredStates3 & i3) | iResolveSizeAndState2, View.resolveSizeAndState(Math.max(eg.getPaddingBottom() + eg.getPaddingTop() + iMax13, eg.getSuggestedMinimumHeight()), i2, iCombineMeasuredStates3 << 16));
        if (z20) {
            int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(eg.getMeasuredHeight(), 1073741824);
            int i75 = i4;
            while (i75 < childCount2) {
                View childAt10 = eg.getChildAt(i75);
                if (childAt10.getVisibility() != 8) {
                    Dg dg9 = (Dg) childAt10.getLayoutParams();
                    if (((LinearLayout.LayoutParams) dg9).height == -1) {
                        int i76 = ((LinearLayout.LayoutParams) dg9).width;
                        ((LinearLayout.LayoutParams) dg9).width = childAt10.getMeasuredWidth();
                        eg.measureChildWithMargins(childAt10, i, 0, iMakeMeasureSpec3, 0);
                        ((LinearLayout.LayoutParams) dg9).width = i76;
                    }
                }
                i75++;
                eg = this;
            }
        }
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }
}
