package rikka.shizuku;

import android.animation.ValueAnimator;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.WeakHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.t4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0607t4 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ RunnableC0607t4(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // java.lang.Runnable
    public final void run() {
        Object obj;
        switch (this.a) {
            case 0:
                ViewOnTouchListenerC0128dh viewOnTouchListenerC0128dh = (ViewOnTouchListenerC0128dh) this.b;
                if (viewOnTouchListenerC0128dh.o) {
                    boolean z = viewOnTouchListenerC0128dh.m;
                    C0576s4 c0576s4 = viewOnTouchListenerC0128dh.a;
                    if (z) {
                        viewOnTouchListenerC0128dh.m = false;
                        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                        c0576s4.e = jCurrentAnimationTimeMillis;
                        c0576s4.g = -1L;
                        c0576s4.f = jCurrentAnimationTimeMillis;
                        c0576s4.h = 0.5f;
                    }
                    if ((c0576s4.g > 0 && AnimationUtils.currentAnimationTimeMillis() > c0576s4.g + ((long) c0576s4.i)) || !viewOnTouchListenerC0128dh.e()) {
                        viewOnTouchListenerC0128dh.o = false;
                        return;
                    }
                    boolean z2 = viewOnTouchListenerC0128dh.n;
                    ListView listView = viewOnTouchListenerC0128dh.c;
                    if (z2) {
                        viewOnTouchListenerC0128dh.n = false;
                        long jUptimeMillis = SystemClock.uptimeMillis();
                        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                        listView.onTouchEvent(motionEventObtain);
                        motionEventObtain.recycle();
                    }
                    if (c0576s4.f == 0) {
                        throw new RuntimeException("Cannot compute scroll delta before calling start()");
                    }
                    long jCurrentAnimationTimeMillis2 = AnimationUtils.currentAnimationTimeMillis();
                    float fA = c0576s4.a(jCurrentAnimationTimeMillis2);
                    long j = jCurrentAnimationTimeMillis2 - c0576s4.f;
                    c0576s4.f = jCurrentAnimationTimeMillis2;
                    viewOnTouchListenerC0128dh.q.scrollListBy((int) (j * ((fA * 4.0f) + ((-4.0f) * fA * fA)) * c0576s4.d));
                    WeakHashMap weakHashMap = Hs.a;
                    listView.postOnAnimation(this);
                    return;
                }
                return;
            case 1:
                C0423n5 c0423n5 = (C0423n5) this.b;
                c0423n5.c = false;
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) c0423n5.e;
                Js js = bottomSheetBehavior.M;
                if (js != null && js.f()) {
                    c0423n5.a(c0423n5.b);
                    return;
                } else {
                    if (bottomSheetBehavior.L == 2) {
                        bottomSheetBehavior.D(c0423n5.b);
                        return;
                    }
                    return;
                }
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                try {
                    super/*android.app.Activity*/.onBackPressed();
                    return;
                } catch (IllegalStateException e) {
                    if (!TextUtils.equals(e.getMessage(), "Can not perform this action after onSaveInstanceState")) {
                        throw e;
                    }
                    return;
                } catch (NullPointerException e2) {
                    if (!TextUtils.equals(e2.getMessage(), "Attempt to invoke virtual method 'android.os.Handler android.app.FragmentHostCallback.getHandler()' on a null object reference")) {
                        throw e2;
                    }
                    return;
                }
            case 3:
                U9 u9 = (U9) this.b;
                u9.Z.onDismiss(u9.h0);
                return;
            case 4:
                C0490pa c0490pa = (C0490pa) this.b;
                c0490pa.l = null;
                c0490pa.drawableStateChanged();
                return;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                ((Ca) this.b).U();
                return;
            case 6:
                Tb tb = (Tb) this.b;
                int i = tb.A;
                ValueAnimator valueAnimator = tb.z;
                if (i == 1) {
                    valueAnimator.cancel();
                } else if (i != 2) {
                    return;
                }
                tb.A = 3;
                valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
                valueAnimator.setDuration(500);
                valueAnimator.start();
                return;
            case 7:
                Ac ac = (Ac) this.b;
                if (ac.J != null) {
                    ac.f().getClass();
                    return;
                }
                return;
            case 8:
                ((Qc) this.b).z(true);
                return;
            case 9:
                synchronized (((Pi) this.b).a) {
                    obj = ((Pi) this.b).f;
                    ((Pi) this.b).f = Pi.k;
                    break;
                }
                ((Pi) this.b).f(obj);
                return;
            case 10:
                RecyclerView recyclerView = ((AbstractC0284ik) this.b).Y;
                recyclerView.focusableViewAvailable(recyclerView);
                return;
            case 11:
                ((C0376lk) this.b).n();
                return;
            case 12:
                SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) this.b;
                if (searchAutoComplete.g) {
                    ((InputMethodManager) searchAutoComplete.getContext().getSystemService("input_method")).showSoftInput(searchAutoComplete, 0);
                    searchAutoComplete.g = false;
                    return;
                }
                return;
            case 13:
                Qn qn = ((Rn) this.b).t;
                AtomicFile atomicFile = Rn.w;
                synchronized (atomicFile) {
                    try {
                        try {
                            FileOutputStream fileOutputStreamStartWrite = atomicFile.startWrite();
                            try {
                                fileOutputStreamStartWrite.write(Rn.v.e(qn).getBytes());
                                atomicFile.finishWrite(fileOutputStreamStartWrite);
                                Log.println(2, "ConfigManager", "config saved");
                            } catch (Throwable th) {
                                AtomicFile atomicFile2 = Rn.w;
                                Log.println(5, "ConfigManager", String.format(Locale.ENGLISH, "can't save %s, restoring backup.", atomicFile2.getBaseFile()) + '\n' + Log.getStackTraceString(th));
                                atomicFile2.failWrite(fileOutputStreamStartWrite);
                            }
                        } catch (IOException e3) {
                            Log.println(5, "ConfigManager", "failed to write state: " + e3);
                            return;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    break;
                }
                return;
            case 14:
                ((StaggeredGridLayoutManager) this.b).D0();
                return;
            case 15:
                CheckableImageButton checkableImageButton = ((TextInputLayout) this.b).c.f;
                checkableImageButton.performClick();
                checkableImageButton.jumpDrawablesToCurrentState();
                return;
            case 16:
                ((Toolbar) this.b).C();
                return;
            case 17:
                C0260hq c0260hq = (C0260hq) this.b;
                C2 c2 = c0260hq.r;
                MenuC0129di menuC0129diD0 = c0260hq.d0();
                MenuC0129di menuC0129di = menuC0129diD0 != null ? menuC0129diD0 : null;
                if (menuC0129di != null) {
                    menuC0129di.w();
                }
                try {
                    menuC0129diD0.clear();
                    if (!c2.onCreatePanelMenu(0, menuC0129diD0) || !c2.onPreparePanel(0, null, menuC0129diD0)) {
                        menuC0129diD0.clear();
                    }
                    if (menuC0129di != null) {
                        return;
                    } else {
                        return;
                    }
                } finally {
                    if (menuC0129di != null) {
                        menuC0129di.v();
                    }
                }
                break;
            default:
                ((Js) this.b).n(0);
                return;
        }
    }
}
