package rikka.shizuku;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.HashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Hb extends AbstractC0722wq {
    public static final String[] D = {"android:visibility:visibility", "android:visibility:parent"};
    public final int C;

    public Hb(int i) {
        this();
        this.C = i;
    }

    public static void L(Eq eq) {
        int visibility = eq.b.getVisibility();
        HashMap map = eq.a;
        map.put("android:visibility:visibility", Integer.valueOf(visibility));
        View view = eq.b;
        map.put("android:visibility:parent", view.getParent());
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        map.put("android:visibility:screenLocation", iArr);
    }

    public static float N(Eq eq, float f) {
        Float f2;
        return (eq == null || (f2 = (Float) eq.a.get("android:fade:transitionAlpha")) == null) ? f : f2.floatValue();
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0052  */
    /* JADX WARN: Code duplicated, block: B:7:0x002f  */
    public static C0663ut O(Eq eq, Eq eq2) {
        C0663ut c0663ut = new C0663ut();
        c0663ut.a = false;
        c0663ut.b = false;
        if (eq != null) {
            HashMap map = eq.a;
            if (map.containsKey("android:visibility:visibility")) {
                c0663ut.c = ((Integer) map.get("android:visibility:visibility")).intValue();
                c0663ut.e = (ViewGroup) map.get("android:visibility:parent");
            } else {
                c0663ut.c = -1;
                c0663ut.e = null;
            }
        } else {
            c0663ut.c = -1;
            c0663ut.e = null;
        }
        if (eq2 != null) {
            HashMap map2 = eq2.a;
            if (map2.containsKey("android:visibility:visibility")) {
                c0663ut.d = ((Integer) map2.get("android:visibility:visibility")).intValue();
                c0663ut.f = (ViewGroup) map2.get("android:visibility:parent");
            } else {
                c0663ut.d = -1;
                c0663ut.f = null;
            }
        } else {
            c0663ut.d = -1;
            c0663ut.f = null;
        }
        if (eq != null && eq2 != null) {
            int i = c0663ut.c;
            int i2 = c0663ut.d;
            if (i != i2 || c0663ut.e != c0663ut.f) {
                if (i != i2) {
                    if (i == 0) {
                        c0663ut.b = false;
                        c0663ut.a = true;
                        return c0663ut;
                    }
                    if (i2 == 0) {
                        c0663ut.b = true;
                        c0663ut.a = true;
                        return c0663ut;
                    }
                } else {
                    if (c0663ut.f == null) {
                        c0663ut.b = false;
                        c0663ut.a = true;
                        return c0663ut;
                    }
                    if (c0663ut.e == null) {
                        c0663ut.b = true;
                        c0663ut.a = true;
                        return c0663ut;
                    }
                }
            }
        } else {
            if (eq == null && c0663ut.d == 0) {
                c0663ut.b = true;
                c0663ut.a = true;
                return c0663ut;
            }
            if (eq2 == null && c0663ut.c == 0) {
                c0663ut.b = false;
                c0663ut.a = true;
            }
        }
        return c0663ut;
    }

    public final ObjectAnimator M(View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        AbstractC0263ht.a.p(view, f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, AbstractC0263ht.b, f2);
        Gb gb = new Gb(view);
        objectAnimatorOfFloat.addListener(gb);
        o().a(gb);
        return objectAnimatorOfFloat;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void d(Eq eq) {
        L(eq);
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void g(Eq eq) {
        L(eq);
        View view = eq.b;
        Float fValueOf = (Float) view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901ed);
        if (fValueOf == null) {
            fValueOf = view.getVisibility() == 0 ? Float.valueOf(AbstractC0263ht.a.c(view)) : Float.valueOf(0.0f);
        }
        eq.a.put("android:fade:transitionAlpha", fValueOf);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x009f  */
    /* JADX WARN: Code duplicated, block: B:50:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:51:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:53:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:55:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:57:0x0131  */
    /* JADX WARN: Code duplicated, block: B:60:0x013a  */
    /* JADX WARN: Code duplicated, block: B:62:0x013e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:63:0x0140  */
    /* JADX WARN: Code duplicated, block: B:64:0x0148  */
    /* JADX WARN: Code duplicated, block: B:65:0x0160  */
    /* JADX WARN: Code duplicated, block: B:68:0x017c A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:73:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:75:0x01d4  */
    /* JADX WARN: Code duplicated, block: B:77:0x01e0  */
    /* JADX WARN: Code duplicated, block: B:80:0x01fd  */
    /* JADX WARN: Code duplicated, block: B:82:0x020d  */
    /* JADX WARN: Code duplicated, block: B:85:0x0214  */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0048, code lost:
    
        if (O(n(r3, false), r(r3, false)).a != false) goto L9;
     */
    @Override // rikka.shizuku.AbstractC0722wq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Animator k(ViewGroup viewGroup, Eq eq, Eq eq2) {
        boolean z;
        View view;
        int i;
        int i2;
        char c;
        View view2;
        Animator animator;
        View view3;
        View view4;
        int id;
        ImageView imageView;
        boolean zIsAttachedToWindow;
        boolean z2;
        ViewGroup viewGroup2;
        int i3;
        int iRound;
        Bitmap bitmapCreateBitmap;
        int i4 = this.C;
        C0663ut c0663utO = O(eq, eq2);
        if (c0663utO.a && (c0663utO.e != null || c0663utO.f != null)) {
            int i5 = 0;
            if (!c0663utO.b) {
                int i6 = c0663utO.d;
                if ((i4 & 2) == 2 && eq != null) {
                    View view5 = eq2 != null ? eq2.b : null;
                    View view6 = eq.b;
                    View view7 = (View) view6.getTag(R.id._0_resource_name_obfuscated_res_0x7f090171);
                    if (view7 != null) {
                        i = i6;
                        i2 = 0;
                        i5 = 1;
                        c = 1;
                        view3 = null;
                        animator = null;
                    } else {
                        if (view5 == null || view5.getParent() == null) {
                            if (view5 != null) {
                                z = false;
                            }
                            view = null;
                            if (!z) {
                                i = i6;
                                i2 = 0;
                                c = 1;
                                view2 = view;
                                animator = null;
                                view7 = view5;
                                i5 = i2;
                                view3 = view2;
                            } else if (view6.getParent() == null) {
                                i = i6;
                                i2 = 0;
                                c = 1;
                                view3 = view;
                                animator = null;
                                view7 = view6;
                            } else {
                                if (view6.getParent() instanceof View) {
                                    view4 = (View) view6.getParent();
                                    animator = null;
                                    i2 = 0;
                                    if (O(r(view4, true), n(view4, true)).a) {
                                        i = i6;
                                        c = 1;
                                        view2 = view;
                                        id = view4.getId();
                                        if (view4.getParent() == null && id != -1) {
                                            viewGroup.findViewById(id);
                                        }
                                    } else {
                                        boolean z3 = Dq.a;
                                        Matrix matrix = new Matrix();
                                        matrix.setTranslate(-view4.getScrollX(), -view4.getScrollY());
                                        C0478ot c0478ot = AbstractC0263ht.a;
                                        c0478ot.w(view6, matrix);
                                        c0478ot.x(viewGroup, matrix);
                                        RectF rectF = new RectF(0.0f, 0.0f, view6.getWidth(), view6.getHeight());
                                        matrix.mapRect(rectF);
                                        int iRound2 = Math.round(rectF.left);
                                        int iRound3 = Math.round(rectF.top);
                                        c = 1;
                                        int iRound4 = Math.round(rectF.right);
                                        int iRound5 = Math.round(rectF.bottom);
                                        imageView = new ImageView(view6.getContext());
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        zIsAttachedToWindow = view6.isAttachedToWindow();
                                        if (viewGroup == null && viewGroup.isAttachedToWindow()) {
                                            z2 = true;
                                        } else {
                                            z2 = false;
                                        }
                                        if (zIsAttachedToWindow) {
                                            viewGroup2 = null;
                                            i3 = 0;
                                        } else {
                                            if (z2) {
                                                viewGroup2 = (ViewGroup) view6.getParent();
                                                int iIndexOfChild = viewGroup2.indexOfChild(view6);
                                                viewGroup.getOverlay().add(view6);
                                                i3 = iIndexOfChild;
                                            } else {
                                                i = i6;
                                                view2 = view;
                                                bitmapCreateBitmap = null;
                                            }
                                            if (bitmapCreateBitmap != null) {
                                                imageView.setImageBitmap(bitmapCreateBitmap);
                                            }
                                            imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound4 - iRound2, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound5 - iRound3, 1073741824));
                                            imageView.layout(iRound2, iRound3, iRound4, iRound5);
                                            view7 = imageView;
                                        }
                                        view2 = view;
                                        iRound = Math.round(rectF.width());
                                        i = i6;
                                        int iRound6 = Math.round(rectF.height());
                                        if (iRound > 0 || iRound6 <= 0) {
                                            bitmapCreateBitmap = null;
                                        } else {
                                            float fMin = Math.min(1.0f, 1048576.0f / (iRound * iRound6));
                                            int iRound7 = Math.round(iRound * fMin);
                                            int iRound8 = Math.round(iRound6 * fMin);
                                            matrix.postTranslate(-rectF.left, -rectF.top);
                                            matrix.postScale(fMin, fMin);
                                            if (Dq.a) {
                                                Picture picture = new Picture();
                                                Canvas canvasBeginRecording = picture.beginRecording(iRound7, iRound8);
                                                canvasBeginRecording.concat(matrix);
                                                view6.draw(canvasBeginRecording);
                                                picture.endRecording();
                                                bitmapCreateBitmap = Cq.a(picture);
                                            } else {
                                                bitmapCreateBitmap = Bitmap.createBitmap(iRound7, iRound8, Bitmap.Config.ARGB_8888);
                                                Canvas canvas = new Canvas(bitmapCreateBitmap);
                                                canvas.concat(matrix);
                                                view6.draw(canvas);
                                            }
                                        }
                                        if (!zIsAttachedToWindow) {
                                            viewGroup.getOverlay().remove(view6);
                                            viewGroup2.addView(view6, i3);
                                        }
                                        if (bitmapCreateBitmap != null) {
                                            imageView.setImageBitmap(bitmapCreateBitmap);
                                        }
                                        imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound4 - iRound2, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound5 - iRound3, 1073741824));
                                        imageView.layout(iRound2, iRound3, iRound4, iRound5);
                                        view7 = imageView;
                                    }
                                    i5 = i2;
                                    view3 = view2;
                                } else {
                                    i = i6;
                                    i2 = 0;
                                    c = 1;
                                    view2 = view;
                                    animator = null;
                                }
                                view7 = view5;
                                i5 = i2;
                                view3 = view2;
                            }
                        } else {
                            if (i6 == 4 || view6 == view5) {
                                view = view5;
                                z = false;
                                view5 = null;
                            }
                            if (!z) {
                                i = i6;
                                i2 = 0;
                                c = 1;
                                view2 = view;
                                animator = null;
                                view7 = view5;
                                i5 = i2;
                                view3 = view2;
                            } else if (view6.getParent() == null) {
                                i = i6;
                                i2 = 0;
                                c = 1;
                                view3 = view;
                                animator = null;
                                view7 = view6;
                            } else {
                                if (view6.getParent() instanceof View) {
                                    view4 = (View) view6.getParent();
                                    animator = null;
                                    i2 = 0;
                                    if (O(r(view4, true), n(view4, true)).a) {
                                        boolean z4 = Dq.a;
                                        Matrix matrix2 = new Matrix();
                                        matrix2.setTranslate(-view4.getScrollX(), -view4.getScrollY());
                                        C0478ot c0478ot2 = AbstractC0263ht.a;
                                        c0478ot2.w(view6, matrix2);
                                        c0478ot2.x(viewGroup, matrix2);
                                        RectF rectF2 = new RectF(0.0f, 0.0f, view6.getWidth(), view6.getHeight());
                                        matrix2.mapRect(rectF2);
                                        int iRound9 = Math.round(rectF2.left);
                                        int iRound10 = Math.round(rectF2.top);
                                        c = 1;
                                        int iRound11 = Math.round(rectF2.right);
                                        int iRound12 = Math.round(rectF2.bottom);
                                        imageView = new ImageView(view6.getContext());
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        zIsAttachedToWindow = view6.isAttachedToWindow();
                                        if (viewGroup == null) {
                                            z2 = false;
                                        } else {
                                            z2 = false;
                                        }
                                        if (zIsAttachedToWindow) {
                                            if (z2) {
                                                i = i6;
                                                view2 = view;
                                                bitmapCreateBitmap = null;
                                            } else {
                                                viewGroup2 = (ViewGroup) view6.getParent();
                                                int iIndexOfChild2 = viewGroup2.indexOfChild(view6);
                                                viewGroup.getOverlay().add(view6);
                                                i3 = iIndexOfChild2;
                                            }
                                            if (bitmapCreateBitmap != null) {
                                                imageView.setImageBitmap(bitmapCreateBitmap);
                                            }
                                            imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound11 - iRound9, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound12 - iRound10, 1073741824));
                                            imageView.layout(iRound9, iRound10, iRound11, iRound12);
                                            view7 = imageView;
                                        } else {
                                            viewGroup2 = null;
                                            i3 = 0;
                                        }
                                        view2 = view;
                                        iRound = Math.round(rectF2.width());
                                        i = i6;
                                        int iRound13 = Math.round(rectF2.height());
                                        if (iRound > 0) {
                                            bitmapCreateBitmap = null;
                                        } else {
                                            bitmapCreateBitmap = null;
                                        }
                                        if (!zIsAttachedToWindow) {
                                            viewGroup.getOverlay().remove(view6);
                                            viewGroup2.addView(view6, i3);
                                        }
                                        if (bitmapCreateBitmap != null) {
                                            imageView.setImageBitmap(bitmapCreateBitmap);
                                        }
                                        imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound11 - iRound9, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound12 - iRound10, 1073741824));
                                        imageView.layout(iRound9, iRound10, iRound11, iRound12);
                                        view7 = imageView;
                                    } else {
                                        i = i6;
                                        c = 1;
                                        view2 = view;
                                        id = view4.getId();
                                        if (view4.getParent() == null) {
                                            viewGroup.findViewById(id);
                                        }
                                    }
                                    i5 = i2;
                                    view3 = view2;
                                } else {
                                    i = i6;
                                    i2 = 0;
                                    c = 1;
                                    view2 = view;
                                    animator = null;
                                }
                                view7 = view5;
                                i5 = i2;
                                view3 = view2;
                            }
                        }
                        z = true;
                        view5 = null;
                        view = null;
                        if (!z) {
                            i = i6;
                            i2 = 0;
                            c = 1;
                            view2 = view;
                            animator = null;
                            view7 = view5;
                            i5 = i2;
                            view3 = view2;
                        } else if (view6.getParent() == null) {
                            i = i6;
                            i2 = 0;
                            c = 1;
                            view3 = view;
                            animator = null;
                            view7 = view6;
                        } else {
                            if (view6.getParent() instanceof View) {
                                view4 = (View) view6.getParent();
                                animator = null;
                                i2 = 0;
                                if (O(r(view4, true), n(view4, true)).a) {
                                    boolean z5 = Dq.a;
                                    Matrix matrix3 = new Matrix();
                                    matrix3.setTranslate(-view4.getScrollX(), -view4.getScrollY());
                                    C0478ot c0478ot3 = AbstractC0263ht.a;
                                    c0478ot3.w(view6, matrix3);
                                    c0478ot3.x(viewGroup, matrix3);
                                    RectF rectF3 = new RectF(0.0f, 0.0f, view6.getWidth(), view6.getHeight());
                                    matrix3.mapRect(rectF3);
                                    int iRound14 = Math.round(rectF3.left);
                                    int iRound15 = Math.round(rectF3.top);
                                    c = 1;
                                    int iRound16 = Math.round(rectF3.right);
                                    int iRound17 = Math.round(rectF3.bottom);
                                    imageView = new ImageView(view6.getContext());
                                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    zIsAttachedToWindow = view6.isAttachedToWindow();
                                    if (viewGroup == null) {
                                        z2 = false;
                                    } else {
                                        z2 = false;
                                    }
                                    if (zIsAttachedToWindow) {
                                        if (z2) {
                                            i = i6;
                                            view2 = view;
                                            bitmapCreateBitmap = null;
                                        } else {
                                            viewGroup2 = (ViewGroup) view6.getParent();
                                            int iIndexOfChild3 = viewGroup2.indexOfChild(view6);
                                            viewGroup.getOverlay().add(view6);
                                            i3 = iIndexOfChild3;
                                        }
                                        if (bitmapCreateBitmap != null) {
                                            imageView.setImageBitmap(bitmapCreateBitmap);
                                        }
                                        imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound16 - iRound14, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound17 - iRound15, 1073741824));
                                        imageView.layout(iRound14, iRound15, iRound16, iRound17);
                                        view7 = imageView;
                                    } else {
                                        viewGroup2 = null;
                                        i3 = 0;
                                    }
                                    view2 = view;
                                    iRound = Math.round(rectF3.width());
                                    i = i6;
                                    int iRound18 = Math.round(rectF3.height());
                                    if (iRound > 0) {
                                        bitmapCreateBitmap = null;
                                    } else {
                                        bitmapCreateBitmap = null;
                                    }
                                    if (!zIsAttachedToWindow) {
                                        viewGroup.getOverlay().remove(view6);
                                        viewGroup2.addView(view6, i3);
                                    }
                                    if (bitmapCreateBitmap != null) {
                                        imageView.setImageBitmap(bitmapCreateBitmap);
                                    }
                                    imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound16 - iRound14, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound17 - iRound15, 1073741824));
                                    imageView.layout(iRound14, iRound15, iRound16, iRound17);
                                    view7 = imageView;
                                } else {
                                    i = i6;
                                    c = 1;
                                    view2 = view;
                                    id = view4.getId();
                                    if (view4.getParent() == null) {
                                        viewGroup.findViewById(id);
                                    }
                                }
                                i5 = i2;
                                view3 = view2;
                            } else {
                                i = i6;
                                i2 = 0;
                                c = 1;
                                view2 = view;
                                animator = null;
                            }
                            view7 = view5;
                            i5 = i2;
                            view3 = view2;
                        }
                    }
                    if (view7 == null) {
                        if (view3 == null) {
                            return animator;
                        }
                        int visibility = view3.getVisibility();
                        AbstractC0263ht.b(view3, i2);
                        C0478ot c0478ot4 = AbstractC0263ht.a;
                        c0478ot4.getClass();
                        ObjectAnimator objectAnimatorM = M(view3, N(eq, 1.0f), 0.0f);
                        if (objectAnimatorM == null) {
                            c0478ot4.p(view3, N(eq2, 1.0f));
                        }
                        if (objectAnimatorM == null) {
                            AbstractC0263ht.b(view3, visibility);
                            return objectAnimatorM;
                        }
                        C0601st c0601st = new C0601st(view3, i);
                        objectAnimatorM.addListener(c0601st);
                        o().a(c0601st);
                        return objectAnimatorM;
                    }
                    if (i5 == 0) {
                        int[] iArr = (int[]) eq.a.get("android:visibility:screenLocation");
                        int i7 = iArr[i2];
                        int i8 = iArr[c];
                        int[] iArr2 = new int[2];
                        viewGroup.getLocationOnScreen(iArr2);
                        view7.offsetLeftAndRight((i7 - iArr2[i2]) - view7.getLeft());
                        view7.offsetTopAndBottom((i8 - iArr2[c]) - view7.getTop());
                        viewGroup.getOverlay().add(view7);
                    }
                    C0478ot c0478ot5 = AbstractC0263ht.a;
                    c0478ot5.getClass();
                    ObjectAnimator objectAnimatorM2 = M(view7, N(eq, 1.0f), 0.0f);
                    if (objectAnimatorM2 == null) {
                        c0478ot5.p(view7, N(eq2, 1.0f));
                    }
                    if (i5 == 0) {
                        if (objectAnimatorM2 == null) {
                            viewGroup.getOverlay().remove(view7);
                            return objectAnimatorM2;
                        }
                        view6.setTag(R.id._0_resource_name_obfuscated_res_0x7f090171, view7);
                        C0632tt c0632tt = new C0632tt(this, viewGroup, view7, view6);
                        objectAnimatorM2.addListener(c0632tt);
                        objectAnimatorM2.addPauseListener(c0632tt);
                        o().a(c0632tt);
                    }
                    return objectAnimatorM2;
                }
            } else if ((i4 & 1) == 1 && eq2 != null) {
                View view8 = eq2.b;
                if (eq == null) {
                    View view9 = (View) view8.getParent();
                }
                AbstractC0263ht.a.getClass();
                return M(view8, N(eq, 0.0f), 1.0f);
            }
        }
        return null;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final String[] q() {
        return D;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final boolean t(Eq eq, Eq eq2) {
        if (eq == null && eq2 == null) {
            return false;
        }
        if (eq != null && eq2 != null && eq2.a.containsKey("android:visibility:visibility") != eq.a.containsKey("android:visibility:visibility")) {
            return false;
        }
        C0663ut c0663utO = O(eq, eq2);
        if (c0663utO.a) {
            return c0663utO.c == 0 || c0663utO.d == 0;
        }
        return false;
    }

    public Hb() {
        this.C = 3;
    }
}
