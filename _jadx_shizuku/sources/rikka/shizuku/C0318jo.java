package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.Array;
import java.util.Arrays;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.jo, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0318jo extends PopupWindow {
    public final int[] a;
    public final int[][] b;
    public final int[][] c;
    public final int d;
    public final int e;
    public final int f;
    public final int g;
    public int h;
    public boolean i;
    public final C0227go j;
    public C0610t7 k;
    public CharSequence[] l;
    public int m;
    public int n;

    public C0318jo(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, 0, i);
        this.a = new int[]{(int) typedArrayObtainStyledAttributes.getDimension(5, 4.0f), (int) typedArrayObtainStyledAttributes.getDimension(0, 48.0f)};
        Class cls = Integer.TYPE;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) cls, 2, 2);
        this.b = iArr;
        int[][] iArr2 = (int[][]) Array.newInstance((Class<?>) cls, 2, 2);
        this.c = iArr2;
        this.h = 0;
        this.i = true;
        setFocusable(true);
        setOutsideTouchable(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Ck.b, 0, i);
        iArr[0][0] = (int) typedArrayObtainStyledAttributes.getDimension(7, 0.0f);
        iArr[0][1] = (int) typedArrayObtainStyledAttributes.getDimension(8, 0.0f);
        iArr[1][0] = (int) typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
        iArr[1][1] = (int) typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
        iArr2[0][0] = (int) typedArrayObtainStyledAttributes.getDimension(6, 0.0f);
        iArr2[1][0] = (int) typedArrayObtainStyledAttributes.getDimension(1, 0.0f);
        this.e = (int) typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
        this.f = (int) typedArrayObtainStyledAttributes.getDimension(10, 0.0f);
        this.g = typedArrayObtainStyledAttributes.getInteger(9, 0);
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c00a0, (ViewGroup) null);
        recyclerView.setFocusable(true);
        recyclerView.i0(new LinearLayoutManager(1));
        C0458o9 c0458o9 = recyclerView.L;
        if (c0458o9 != null) {
            c0458o9.h();
            recyclerView.L.a = null;
        }
        recyclerView.L = null;
        recyclerView.setOutlineProvider(new Z5(1, this));
        recyclerView.setClipToOutline(true);
        setContentView(recyclerView);
        C0227go c0227go = new C0227go(this);
        this.j = c0227go;
        recyclerView.h0(c0227go);
        typedArrayObtainStyledAttributes.recycle();
        this.d = Math.round(context.getResources().getDisplayMetrics().density * 48.0f);
        int[] iArr3 = iArr2[0];
        int[] iArr4 = iArr2[1];
        int iRound = Math.round(context.getResources().getDisplayMetrics().density * 8.0f);
        iArr4[1] = iRound;
        iArr3[1] = iRound;
    }

    public static void a(C0318jo c0318jo, int i, int i2, int i3, int i4, Rect rect, int i5, int i6, int i7) {
        An.r(c0318jo.getBackground(), (RecyclerView) super.getContentView(), i, i2, i3, i4, rect, i5, i6, i7);
    }

    public static void b(C0318jo c0318jo, int i, int i2) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) ((RecyclerView) super.getContentView()).n;
        View viewP0 = linearLayoutManager.P0(0, linearLayoutManager.v(), true, false);
        if ((viewP0 == null ? -1 : AbstractC0162el.G(viewP0)) == 0) {
            View viewP1 = linearLayoutManager.P0(linearLayoutManager.v() - 1, -1, true, false);
            if ((viewP1 != null ? AbstractC0162el.G(viewP1) : -1) == i - 1) {
                ((RecyclerView) super.getContentView()).setOverScrollMode(2);
            }
        }
        int width = ((RecyclerView) super.getContentView()).getWidth();
        int height = ((RecyclerView) super.getContentView()).getHeight();
        int i3 = width / 2;
        int i4 = height / 2;
        An.r(c0318jo.getBackground(), (RecyclerView) super.getContentView(), width, height, i3, i4, new Rect(i3, i4, i3, i4), c0318jo.d, c0318jo.a[1] / 4, i2);
    }

    public static void c(C0318jo c0318jo, int i, int i2) {
        ((RecyclerView) super.getContentView()).scrollBy(0, -i);
        ((RecyclerView) super.getContentView()).scrollBy(0, i2);
    }

    @Override // android.widget.PopupWindow
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final C0302j8 getBackground() {
        Drawable background = super.getBackground();
        if (background != null && !(background instanceof C0302j8)) {
            setBackgroundDrawable(background);
        }
        return (C0302j8) super.getBackground();
    }

    public final void e(View view, View view2, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int iMax;
        int i7;
        int i8;
        int i9;
        int width = view2.getWidth();
        int[][] iArr = this.b;
        int i10 = 2;
        int i11 = width - (iArr[0][0] * 2);
        CharSequence[] charSequenceArr = this.l;
        boolean z = this.i;
        int[][] iArr2 = this.c;
        int i12 = this.f;
        if (z) {
            this.i = false;
            CharSequence[] charSequenceArr2 = (CharSequence[]) Arrays.copyOf(charSequenceArr, charSequenceArr.length);
            Arrays.sort(charSequenceArr2, new C0184fc(1));
            Context context = ((RecyclerView) super.getContentView()).getContext();
            int iMin = Math.min(this.g * i12, i11);
            Rect rect = new Rect();
            TextPaint paint = ((TextView) LayoutInflater.from(context).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c009f, (ViewGroup) null, false).findViewById(android.R.id.text1)).getPaint();
            int length = charSequenceArr2.length;
            int iMax2 = 0;
            int i13 = 0;
            while (true) {
                if (i13 >= length) {
                    i2 = i10;
                    i3 = 1;
                    i4 = 0;
                    while (iMax2 > i4) {
                        i4 += i12;
                    }
                    break;
                }
                CharSequence charSequence = charSequenceArr2[i13];
                i2 = i10;
                i3 = 1;
                paint.getTextBounds(charSequence.toString(), 0, charSequence.toString().length(), rect);
                iMax2 = Math.max(iMax2, Math.round((iArr2[0][0] * 2) + 1) + rect.right + 1);
                if (iMax2 > iMin || charSequence.toString().contains("\n")) {
                    i4 = -1;
                    break;
                } else {
                    i13++;
                    i10 = i2;
                }
            }
        } else {
            i4 = 0;
            i2 = 2;
            i3 = 1;
        }
        if (i4 == -1) {
            this.h = i3;
        } else if (i4 != 0) {
            this.h = 0;
            this.n = i4;
        }
        this.j.a.b();
        Rect rect2 = new Rect();
        C0302j8 background = getBackground();
        background.getClass();
        background.a.setBounds(rect2.left, rect2.top, rect2.right, rect2.bottom);
        ((RecyclerView) super.getContentView()).invalidateOutline();
        int i14 = this.h;
        int[] iArr3 = this.a;
        if (i14 != 0) {
            final int iMax3 = Math.max(0, this.m);
            final int length2 = this.l.length;
            ((RecyclerView) super.getContentView()).setOverScrollMode(1);
            ((RecyclerView) super.getContentView()).g0(iMax3);
            setWidth(Math.min(this.e, view2.getWidth() - (iArr[1][0] * 2)));
            setHeight(-2);
            setAnimationStyle(R.style._0_resource_name_obfuscated_res_0x7f12000c);
            setElevation(iArr3[1]);
            super.showAtLocation(view, 16, 0, 0);
            final int i15 = 1;
            ((RecyclerView) super.getContentView()).post(new Runnable(this) { // from class: rikka.shizuku.ho
                public final /* synthetic */ C0318jo b;

                {
                    this.b = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i15) {
                        case 0:
                            C0318jo.c(this.b, length2, iMax3);
                            break;
                        default:
                            C0318jo.b(this.b, length2, iMax3);
                            break;
                    }
                }
            });
            return;
        }
        final int i16 = this.n;
        boolean z2 = view2.getResources().getConfiguration().getLayoutDirection() == 1;
        final int iMax4 = Math.max(0, this.m);
        int length3 = this.l.length;
        int top = view.getTop() - view2.getPaddingTop();
        int height = view.getHeight();
        int i17 = this.d;
        final int i18 = (iArr2[0][1] * 2) + (length3 * i17);
        int[] iArr4 = new int[i2];
        view2.getLocationInWindow(iArr4);
        int paddingTop = view2.getPaddingTop() + iArr4[1];
        int height2 = (view2.getHeight() - view2.getPaddingTop()) - view2.getPaddingBottom();
        int i19 = iArr3[0];
        if (z2) {
            i5 = (iArr4[0] + i) - i16;
            i6 = iArr2[0][0];
        } else {
            i5 = iArr4[0] + i;
            i6 = iArr2[0][0];
        }
        final int i20 = i5 + i6;
        int i21 = iArr2[0][1];
        final int i22 = (i21 * 2) + i17;
        if (i18 > height2) {
            int i23 = iArr[0][1];
            iMax = paddingTop + i23;
            i7 = i17 * iMax4;
            final int i24 = (i17 / 2) + ((((i7 - top) + i21) + i23) - (height / 2));
            final int i25 = 0;
            ((RecyclerView) super.getContentView()).post(new Runnable(this) { // from class: rikka.shizuku.ho
                public final /* synthetic */ C0318jo b;

                {
                    this.b = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i25) {
                        case 0:
                            C0318jo.c(this.b, i18, i24);
                            break;
                        default:
                            C0318jo.b(this.b, i18, i24);
                            break;
                    }
                }
            });
            ((RecyclerView) super.getContentView()).setOverScrollMode(1);
            i18 = height2 - (iArr[0][1] * 2);
        } else {
            int i26 = iMax4 * i17;
            iMax = Math.max(Math.min(((((height / 2) + (paddingTop + top)) - (i17 / 2)) - i21) - i26, ((paddingTop + height2) - i18) - iArr[0][1]), paddingTop + iArr[0][1]);
            ((RecyclerView) super.getContentView()).setOverScrollMode(2);
            i7 = (int) ((((double) i17) * 0.5d) + ((double) (i26 + iArr2[0][1])));
        }
        final int i27 = i18;
        int i28 = iMax;
        setWidth(i16);
        setHeight(i27);
        setElevation(i19);
        setAnimationStyle(R.style._0_resource_name_obfuscated_res_0x7f12000c);
        setEnterTransition(null);
        setExitTransition(null);
        super.showAtLocation(view, 0, i20, i28);
        int i29 = (int) (((double) i17) * 0.2d);
        int i30 = i7 - i29;
        int i31 = i7 + i29;
        if (z2) {
            int i32 = i20 + i16;
            i8 = i32 - i12;
            i9 = i32;
        } else {
            i9 = i12 + i20;
            i8 = i20;
        }
        final Rect rect3 = new Rect(i8, i30, i9, i31);
        final int iRound = (int) Math.round(((double) i19) * 0.25d);
        final int i33 = i7;
        ((RecyclerView) super.getContentView()).post(new Runnable() { // from class: rikka.shizuku.io
            @Override // java.lang.Runnable
            public final void run() {
                C0318jo.a(this.a, i16, i27, i20, i33, rect3, i22, iRound, iMax4);
            }
        });
    }

    @Override // android.widget.PopupWindow
    public final View getContentView() {
        return (RecyclerView) super.getContentView();
    }

    @Override // android.widget.PopupWindow
    public final void setBackgroundDrawable(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalStateException("SimpleMenuPopupWindow must have a background");
        }
        if (!(drawable instanceof C0302j8)) {
            C0302j8 c0302j8 = new C0302j8();
            Drawable drawable2 = c0302j8.a;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            c0302j8.a = drawable;
            drawable.setCallback(c0302j8);
            drawable = c0302j8;
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.widget.PopupWindow
    public final void showAsDropDown(View view) {
        throw new UnsupportedOperationException("use show(anchor) to show the window");
    }

    @Override // android.widget.PopupWindow
    public final void showAtLocation(View view, int i, int i2, int i3) {
        throw new UnsupportedOperationException("use show(anchor) to show the window");
    }

    @Override // android.widget.PopupWindow
    public final void showAsDropDown(View view, int i, int i2) {
        throw new UnsupportedOperationException("use show(anchor) to show the window");
    }

    @Override // android.widget.PopupWindow
    public final void showAsDropDown(View view, int i, int i2, int i3) {
        throw new UnsupportedOperationException("use show(anchor) to show the window");
    }
}
