package rikka.shizuku;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.b2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractActivityC0051b2 extends T6 implements InterfaceC0451o2 {
    public boolean u;
    public boolean v;
    public I2 x;
    public final C0536qp y;
    public String z;
    public final Ys s = new Ys(18, new Cc(this));
    public final C0805zg t = new C0805zg(this);
    public boolean w = true;

    public AbstractActivityC0051b2() {
        ((R2) this.e.c).S("android:support:lifecycle", new L6(1, this));
        final int i = 0;
        g(new InterfaceC0734x7(this) { // from class: rikka.shizuku.Bc
            public final /* synthetic */ AbstractActivityC0051b2 b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0734x7
            public final void a(Object obj) {
                switch (i) {
                    case 0:
                        this.b.s.y();
                        break;
                    default:
                        this.b.s.y();
                        break;
                }
            }
        });
        final int i2 = 1;
        this.n.add(new InterfaceC0734x7(this) { // from class: rikka.shizuku.Bc
            public final /* synthetic */ AbstractActivityC0051b2 b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0734x7
            public final void a(Object obj) {
                switch (i2) {
                    case 0:
                        this.b.s.y();
                        break;
                    default:
                        this.b.s.y();
                        break;
                }
            }
        });
        h(new M6(this, 1));
        ((R2) this.e.c).S("androidx:appcompat", new C0327k2(this));
        h(new C0358l2(this));
        this.y = new C0536qp(new Z3(4));
    }

    public static boolean n(Qc qc) {
        boolean zN = false;
        for (Ac ac : qc.c.m()) {
            if (ac != null) {
                Cc cc = ac.u;
                if ((cc == null ? null : cc.m) != null) {
                    zN |= n(ac.h());
                }
                C0093cd c0093cd = ac.Q;
                EnumC0588sg enumC0588sg = EnumC0588sg.d;
                EnumC0588sg enumC0588sg2 = EnumC0588sg.c;
                if (c0093cd != null) {
                    c0093cd.f();
                    if (c0093cd.d.c.a(enumC0588sg)) {
                        C0805zg c0805zg = ac.Q.d;
                        c0805zg.c("setCurrentState");
                        c0805zg.e(enumC0588sg2);
                        zN = true;
                    }
                }
                if (ac.P.c.a(enumC0588sg)) {
                    C0805zg c0805zg2 = ac.P;
                    c0805zg2.c("setCurrentState");
                    c0805zg2.e(enumC0588sg2);
                    zN = true;
                }
            }
        }
        return zN;
    }

    @Override // android.app.Activity
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        m();
        I2 i2 = (I2) k();
        i2.y();
        ((ViewGroup) i2.A.findViewById(R.id.content)).addView(view, layoutParams);
        i2.m.a(i2.l.getCallback());
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        C0281ih c0281ih = (C0281ih) this.y.getValue();
        Locale locale = C0281ih.b;
        c0281ih.a = locale;
        configuration.setLocale(locale);
        j(context.createConfigurationContext(configuration));
    }

    @Override // android.app.Activity
    public final void closeOptionsMenu() {
        AbstractC0744xh abstractC0744xhL = l();
        if (getWindow().hasFeature(0)) {
            if (abstractC0744xhL == null || !abstractC0744xhL.j()) {
                super.closeOptionsMenu();
            }
        }
    }

    @Override // rikka.shizuku.S6, android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        AbstractC0744xh abstractC0744xhL = l();
        if (keyCode == 82 && abstractC0744xhL != null && abstractC0744xhL.M(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:28:0x0046  */
    /* JADX WARN: Code duplicated, block: B:54:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.app.Activity
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (strArr != null && strArr.length != 0) {
            String str2 = strArr[0];
            switch (str2.hashCode()) {
                case -645125871:
                    if (str2.equals("--translation") && Build.VERSION.SDK_INT >= 31) {
                        return;
                    }
                    break;
                case 100470631:
                    if (str2.equals("--dump-dumpable")) {
                        if (Build.VERSION.SDK_INT >= 33) {
                            return;
                        }
                    }
                    break;
                case 472614934:
                    if (str2.equals("--list-dumpables")) {
                        if (Build.VERSION.SDK_INT >= 33) {
                            return;
                        }
                    }
                    break;
                case 1159329357:
                    if (str2.equals("--contentcapture") && Build.VERSION.SDK_INT >= 29) {
                        return;
                    }
                    break;
                case 1455016274:
                    if (str2.equals("--autofill") && Build.VERSION.SDK_INT >= 26) {
                        return;
                    }
                    break;
            }
        }
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str3 = str + "  ";
        printWriter.print(str3);
        printWriter.print("mCreated=");
        printWriter.print(this.u);
        printWriter.print(" mResumed=");
        printWriter.print(this.v);
        printWriter.print(" mStopped=");
        printWriter.print(this.w);
        if (getApplication() != null) {
            C0689vo c0689vo = ((C0251hh) new Ys(d(), C0251hh.c).r(C0251hh.class)).b;
            if (c0689vo.c > 0) {
                printWriter.print(str3);
                printWriter.println("Loaders:");
                if (c0689vo.c > 0) {
                    if (c0689vo.b[0] != null) {
                        throw new ClassCastException();
                    }
                    printWriter.print(str3);
                    printWriter.print("  #");
                    printWriter.print(c0689vo.a[0]);
                    printWriter.print(": ");
                    throw null;
                }
            }
        }
        ((Cc) this.s.b).l.v(str, fileDescriptor, printWriter, strArr);
    }

    @Override // android.app.Activity
    public final View findViewById(int i) {
        I2 i2 = (I2) k();
        i2.y();
        return i2.l.findViewById(i);
    }

    @Override // android.app.Activity
    public final MenuInflater getMenuInflater() {
        I2 i2 = (I2) k();
        if (i2.p == null) {
            i2.C();
            AbstractC0744xh abstractC0744xh = i2.o;
            i2.p = new C0381lp(abstractC0744xh != null ? abstractC0744xh.z() : i2.k);
        }
        return i2.p;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        int i = AbstractC0415ms.a;
        return super.getResources();
    }

    @Override // android.app.Activity
    public final void invalidateOptionsMenu() {
        k().d();
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0183  */
    /* JADX WARN: Code duplicated, block: B:103:0x019f  */
    /* JADX WARN: Code duplicated, block: B:106:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:109:0x01c6  */
    /* JADX WARN: Code duplicated, block: B:112:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:115:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:118:0x01e1  */
    /* JADX WARN: Code duplicated, block: B:121:0x01e9  */
    /* JADX WARN: Code duplicated, block: B:122:0x01ec  */
    /* JADX WARN: Code duplicated, block: B:126:0x0202  */
    /* JADX WARN: Code duplicated, block: B:128:0x020a  */
    /* JADX WARN: Code duplicated, block: B:129:0x020e  */
    /* JADX WARN: Code duplicated, block: B:140:0x022f A[Catch: all -> 0x0223, TRY_LEAVE, TryCatch #0 {, blocks: (B:131:0x0211, B:133:0x0215, B:139:0x022d, B:140:0x022f, B:142:0x0233, B:148:0x0243, B:147:0x023a, B:138:0x0226), top: B:158:0x0211, inners: #4 }] */
    /* JADX WARN: Code duplicated, block: B:158:0x0211 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:164:0x0215 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:169:0x0233 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x009c  */
    /* JADX WARN: Code duplicated, block: B:49:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:52:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:54:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:57:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:59:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:62:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:65:0x0100  */
    /* JADX WARN: Code duplicated, block: B:68:0x010b  */
    /* JADX WARN: Code duplicated, block: B:71:0x0113  */
    /* JADX WARN: Code duplicated, block: B:74:0x011b  */
    /* JADX WARN: Code duplicated, block: B:77:0x0123  */
    /* JADX WARN: Code duplicated, block: B:80:0x012b  */
    /* JADX WARN: Code duplicated, block: B:83:0x0133  */
    /* JADX WARN: Code duplicated, block: B:86:0x013f  */
    /* JADX WARN: Code duplicated, block: B:89:0x014e  */
    /* JADX WARN: Code duplicated, block: B:92:0x015d  */
    /* JADX WARN: Code duplicated, block: B:95:0x016c  */
    /* JADX WARN: Code duplicated, block: B:98:0x0175  */
    public final void j(Context context) {
        int i;
        Configuration configuration;
        Configuration configuration2;
        Configuration configuration3;
        F7 f7;
        Resources.Theme theme;
        Method method;
        float f;
        float f2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        int i31;
        int i32;
        int i33;
        int i34;
        int i35;
        int i36;
        int i37;
        int i38 = 1;
        I2 i39 = (I2) k();
        i39.N = true;
        int i40 = i39.R;
        if (i40 == -100) {
            i40 = AbstractC0667v2.b;
        }
        int iE = i39.E(context, i40);
        if (AbstractC0667v2.e(context) && AbstractC0667v2.e(context)) {
            if (!AbstractC0454o5.a()) {
                synchronized (AbstractC0667v2.i) {
                    try {
                        C0342kh c0342kh = AbstractC0667v2.c;
                        if (c0342kh == null) {
                            if (AbstractC0667v2.d == null) {
                                AbstractC0667v2.d = C0342kh.a(X8.R(context));
                            }
                            if (!AbstractC0667v2.d.a.a.isEmpty()) {
                                AbstractC0667v2.c = AbstractC0667v2.d;
                            }
                        } else if (!c0342kh.equals(AbstractC0667v2.d)) {
                            C0342kh c0342kh2 = AbstractC0667v2.c;
                            AbstractC0667v2.d = c0342kh2;
                            X8.O(context, c0342kh2.a.a.toLanguageTags());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } else if (!AbstractC0667v2.f) {
                AbstractC0667v2.a.execute(new G0(i38, context));
            }
        }
        C0342kh c0342khR = I2.r(context);
        if (I2.h0 && (context instanceof ContextThemeWrapper)) {
            try {
                ((ContextThemeWrapper) context).applyOverrideConfiguration(I2.v(context, iE, c0342khR, null, false));
            } catch (IllegalStateException unused) {
                if (context instanceof F7) {
                    ((F7) context).a(I2.v(context, iE, c0342khR, null, false));
                } else if (I2.g0) {
                    i = Build.VERSION.SDK_INT;
                    Configuration configuration4 = new Configuration();
                    configuration4.uiMode = -1;
                    configuration4.fontScale = 0.0f;
                    configuration = context.createConfigurationContext(configuration4).getResources().getConfiguration();
                    configuration2 = context.getResources().getConfiguration();
                    configuration.uiMode = configuration2.uiMode;
                    if (configuration.equals(configuration2)) {
                        configuration3 = null;
                    } else {
                        configuration3 = new Configuration();
                        configuration3.fontScale = 0.0f;
                        if (configuration.diff(configuration2) != 0) {
                            f = configuration.fontScale;
                            f2 = configuration2.fontScale;
                            if (f != f2) {
                                configuration3.fontScale = f2;
                            }
                            i2 = configuration.mcc;
                            i3 = configuration2.mcc;
                            if (i2 != i3) {
                                configuration3.mcc = i3;
                            }
                            i4 = configuration.mnc;
                            i5 = configuration2.mnc;
                            if (i4 != i5) {
                                configuration3.mnc = i5;
                            }
                            AbstractC0791z2.a(configuration, configuration2, configuration3);
                            i6 = configuration.touchscreen;
                            i7 = configuration2.touchscreen;
                            if (i6 != i7) {
                                configuration3.touchscreen = i7;
                            }
                            i8 = configuration.keyboard;
                            i9 = configuration2.keyboard;
                            if (i8 != i9) {
                                configuration3.keyboard = i9;
                            }
                            i10 = configuration.keyboardHidden;
                            i11 = configuration2.keyboardHidden;
                            if (i10 != i11) {
                                configuration3.keyboardHidden = i11;
                            }
                            i12 = configuration.navigation;
                            i13 = configuration2.navigation;
                            if (i12 != i13) {
                                configuration3.navigation = i13;
                            }
                            i14 = configuration.navigationHidden;
                            i15 = configuration2.navigationHidden;
                            if (i14 != i15) {
                                configuration3.navigationHidden = i15;
                            }
                            i16 = configuration.orientation;
                            i17 = configuration2.orientation;
                            if (i16 != i17) {
                                configuration3.orientation = i17;
                            }
                            i18 = configuration.screenLayout & 15;
                            i19 = configuration2.screenLayout & 15;
                            if (i18 != i19) {
                                configuration3.screenLayout |= i19;
                            }
                            i20 = configuration.screenLayout & 192;
                            i21 = configuration2.screenLayout & 192;
                            if (i20 != i21) {
                                configuration3.screenLayout |= i21;
                            }
                            i22 = configuration.screenLayout & 48;
                            i23 = configuration2.screenLayout & 48;
                            if (i22 != i23) {
                                configuration3.screenLayout |= i23;
                            }
                            i24 = configuration.screenLayout & 768;
                            i25 = configuration2.screenLayout & 768;
                            if (i24 != i25) {
                                configuration3.screenLayout |= i25;
                            }
                            if (i >= 26) {
                                if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
                                    configuration3.colorMode |= configuration2.colorMode & 3;
                                }
                                if ((configuration.colorMode & 12) != (configuration2.colorMode & 12)) {
                                    configuration3.colorMode |= configuration2.colorMode & 12;
                                }
                            }
                            i26 = configuration.uiMode & 15;
                            i27 = configuration2.uiMode & 15;
                            if (i26 != i27) {
                                configuration3.uiMode |= i27;
                            }
                            i28 = configuration.uiMode & 48;
                            i29 = configuration2.uiMode & 48;
                            if (i28 != i29) {
                                configuration3.uiMode |= i29;
                            }
                            i30 = configuration.screenWidthDp;
                            i31 = configuration2.screenWidthDp;
                            if (i30 != i31) {
                                configuration3.screenWidthDp = i31;
                            }
                            i32 = configuration.screenHeightDp;
                            i33 = configuration2.screenHeightDp;
                            if (i32 != i33) {
                                configuration3.screenHeightDp = i33;
                            }
                            i34 = configuration.smallestScreenWidthDp;
                            i35 = configuration2.smallestScreenWidthDp;
                            if (i34 != i35) {
                                configuration3.smallestScreenWidthDp = i35;
                            }
                            i36 = configuration.densityDpi;
                            i37 = configuration2.densityDpi;
                            if (i36 != i37) {
                                configuration3.densityDpi = i37;
                            }
                        }
                    }
                    Configuration configurationV = I2.v(context, iE, c0342khR, configuration3, true);
                    f7 = new F7(context, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120267);
                    f7.a(configurationV);
                    if (context.getTheme() != null) {
                        theme = f7.getTheme();
                        if (i >= 29) {
                            Q3.d(theme);
                        } else {
                            synchronized (X8.l) {
                                if (X8.n) {
                                    method = X8.m;
                                    if (method != null) {
                                        method.invoke(theme, null);
                                    }
                                } else {
                                    Method declaredMethod = Resources.Theme.class.getDeclaredMethod("rebase", null);
                                    X8.m = declaredMethod;
                                    declaredMethod.setAccessible(true);
                                    X8.n = true;
                                    method = X8.m;
                                    if (method != null) {
                                        method.invoke(theme, null);
                                    }
                                }
                            }
                        }
                    }
                    context = f7;
                }
            }
        } else if (context instanceof F7) {
            try {
                ((F7) context).a(I2.v(context, iE, c0342khR, null, false));
            } catch (IllegalStateException unused2) {
                if (I2.g0) {
                    i = Build.VERSION.SDK_INT;
                    Configuration configuration5 = new Configuration();
                    configuration5.uiMode = -1;
                    configuration5.fontScale = 0.0f;
                    configuration = context.createConfigurationContext(configuration5).getResources().getConfiguration();
                    configuration2 = context.getResources().getConfiguration();
                    configuration.uiMode = configuration2.uiMode;
                    if (configuration.equals(configuration2)) {
                        configuration3 = new Configuration();
                        configuration3.fontScale = 0.0f;
                        if (configuration.diff(configuration2) != 0) {
                            f = configuration.fontScale;
                            f2 = configuration2.fontScale;
                            if (f != f2) {
                                configuration3.fontScale = f2;
                            }
                            i2 = configuration.mcc;
                            i3 = configuration2.mcc;
                            if (i2 != i3) {
                                configuration3.mcc = i3;
                            }
                            i4 = configuration.mnc;
                            i5 = configuration2.mnc;
                            if (i4 != i5) {
                                configuration3.mnc = i5;
                            }
                            AbstractC0791z2.a(configuration, configuration2, configuration3);
                            i6 = configuration.touchscreen;
                            i7 = configuration2.touchscreen;
                            if (i6 != i7) {
                                configuration3.touchscreen = i7;
                            }
                            i8 = configuration.keyboard;
                            i9 = configuration2.keyboard;
                            if (i8 != i9) {
                                configuration3.keyboard = i9;
                            }
                            i10 = configuration.keyboardHidden;
                            i11 = configuration2.keyboardHidden;
                            if (i10 != i11) {
                                configuration3.keyboardHidden = i11;
                            }
                            i12 = configuration.navigation;
                            i13 = configuration2.navigation;
                            if (i12 != i13) {
                                configuration3.navigation = i13;
                            }
                            i14 = configuration.navigationHidden;
                            i15 = configuration2.navigationHidden;
                            if (i14 != i15) {
                                configuration3.navigationHidden = i15;
                            }
                            i16 = configuration.orientation;
                            i17 = configuration2.orientation;
                            if (i16 != i17) {
                                configuration3.orientation = i17;
                            }
                            i18 = configuration.screenLayout & 15;
                            i19 = configuration2.screenLayout & 15;
                            if (i18 != i19) {
                                configuration3.screenLayout |= i19;
                            }
                            i20 = configuration.screenLayout & 192;
                            i21 = configuration2.screenLayout & 192;
                            if (i20 != i21) {
                                configuration3.screenLayout |= i21;
                            }
                            i22 = configuration.screenLayout & 48;
                            i23 = configuration2.screenLayout & 48;
                            if (i22 != i23) {
                                configuration3.screenLayout |= i23;
                            }
                            i24 = configuration.screenLayout & 768;
                            i25 = configuration2.screenLayout & 768;
                            if (i24 != i25) {
                                configuration3.screenLayout |= i25;
                            }
                            if (i >= 26) {
                                if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
                                    configuration3.colorMode |= configuration2.colorMode & 3;
                                }
                                if ((configuration.colorMode & 12) != (configuration2.colorMode & 12)) {
                                    configuration3.colorMode |= configuration2.colorMode & 12;
                                }
                            }
                            i26 = configuration.uiMode & 15;
                            i27 = configuration2.uiMode & 15;
                            if (i26 != i27) {
                                configuration3.uiMode |= i27;
                            }
                            i28 = configuration.uiMode & 48;
                            i29 = configuration2.uiMode & 48;
                            if (i28 != i29) {
                                configuration3.uiMode |= i29;
                            }
                            i30 = configuration.screenWidthDp;
                            i31 = configuration2.screenWidthDp;
                            if (i30 != i31) {
                                configuration3.screenWidthDp = i31;
                            }
                            i32 = configuration.screenHeightDp;
                            i33 = configuration2.screenHeightDp;
                            if (i32 != i33) {
                                configuration3.screenHeightDp = i33;
                            }
                            i34 = configuration.smallestScreenWidthDp;
                            i35 = configuration2.smallestScreenWidthDp;
                            if (i34 != i35) {
                                configuration3.smallestScreenWidthDp = i35;
                            }
                            i36 = configuration.densityDpi;
                            i37 = configuration2.densityDpi;
                            if (i36 != i37) {
                                configuration3.densityDpi = i37;
                            }
                        }
                    } else {
                        configuration3 = null;
                    }
                    Configuration configurationV2 = I2.v(context, iE, c0342khR, configuration3, true);
                    f7 = new F7(context, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120267);
                    f7.a(configurationV2);
                    try {
                        if (context.getTheme() != null) {
                            theme = f7.getTheme();
                            if (i >= 29) {
                                Q3.d(theme);
                            } else {
                                synchronized (X8.l) {
                                    if (X8.n) {
                                        try {
                                            Method declaredMethod2 = Resources.Theme.class.getDeclaredMethod("rebase", null);
                                            X8.m = declaredMethod2;
                                            declaredMethod2.setAccessible(true);
                                        } catch (NoSuchMethodException e) {
                                            Log.i("ResourcesCompat", "Failed to retrieve rebase() method", e);
                                        }
                                        X8.n = true;
                                        method = X8.m;
                                        if (method != null) {
                                            try {
                                                method.invoke(theme, null);
                                            } catch (IllegalAccessException | InvocationTargetException e2) {
                                                Log.i("ResourcesCompat", "Failed to invoke rebase() method via reflection", e2);
                                                X8.m = null;
                                            }
                                        }
                                    } else {
                                        method = X8.m;
                                        if (method != null) {
                                            method.invoke(theme, null);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (NullPointerException unused3) {
                    }
                    context = f7;
                }
            }
        } else if (I2.g0) {
            i = Build.VERSION.SDK_INT;
            Configuration configuration6 = new Configuration();
            configuration6.uiMode = -1;
            configuration6.fontScale = 0.0f;
            configuration = context.createConfigurationContext(configuration6).getResources().getConfiguration();
            configuration2 = context.getResources().getConfiguration();
            configuration.uiMode = configuration2.uiMode;
            if (configuration.equals(configuration2)) {
                configuration3 = new Configuration();
                configuration3.fontScale = 0.0f;
                if (configuration.diff(configuration2) != 0) {
                    f = configuration.fontScale;
                    f2 = configuration2.fontScale;
                    if (f != f2) {
                        configuration3.fontScale = f2;
                    }
                    i2 = configuration.mcc;
                    i3 = configuration2.mcc;
                    if (i2 != i3) {
                        configuration3.mcc = i3;
                    }
                    i4 = configuration.mnc;
                    i5 = configuration2.mnc;
                    if (i4 != i5) {
                        configuration3.mnc = i5;
                    }
                    AbstractC0791z2.a(configuration, configuration2, configuration3);
                    i6 = configuration.touchscreen;
                    i7 = configuration2.touchscreen;
                    if (i6 != i7) {
                        configuration3.touchscreen = i7;
                    }
                    i8 = configuration.keyboard;
                    i9 = configuration2.keyboard;
                    if (i8 != i9) {
                        configuration3.keyboard = i9;
                    }
                    i10 = configuration.keyboardHidden;
                    i11 = configuration2.keyboardHidden;
                    if (i10 != i11) {
                        configuration3.keyboardHidden = i11;
                    }
                    i12 = configuration.navigation;
                    i13 = configuration2.navigation;
                    if (i12 != i13) {
                        configuration3.navigation = i13;
                    }
                    i14 = configuration.navigationHidden;
                    i15 = configuration2.navigationHidden;
                    if (i14 != i15) {
                        configuration3.navigationHidden = i15;
                    }
                    i16 = configuration.orientation;
                    i17 = configuration2.orientation;
                    if (i16 != i17) {
                        configuration3.orientation = i17;
                    }
                    i18 = configuration.screenLayout & 15;
                    i19 = configuration2.screenLayout & 15;
                    if (i18 != i19) {
                        configuration3.screenLayout |= i19;
                    }
                    i20 = configuration.screenLayout & 192;
                    i21 = configuration2.screenLayout & 192;
                    if (i20 != i21) {
                        configuration3.screenLayout |= i21;
                    }
                    i22 = configuration.screenLayout & 48;
                    i23 = configuration2.screenLayout & 48;
                    if (i22 != i23) {
                        configuration3.screenLayout |= i23;
                    }
                    i24 = configuration.screenLayout & 768;
                    i25 = configuration2.screenLayout & 768;
                    if (i24 != i25) {
                        configuration3.screenLayout |= i25;
                    }
                    if (i >= 26) {
                        if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
                            configuration3.colorMode |= configuration2.colorMode & 3;
                        }
                        if ((configuration.colorMode & 12) != (configuration2.colorMode & 12)) {
                            configuration3.colorMode |= configuration2.colorMode & 12;
                        }
                    }
                    i26 = configuration.uiMode & 15;
                    i27 = configuration2.uiMode & 15;
                    if (i26 != i27) {
                        configuration3.uiMode |= i27;
                    }
                    i28 = configuration.uiMode & 48;
                    i29 = configuration2.uiMode & 48;
                    if (i28 != i29) {
                        configuration3.uiMode |= i29;
                    }
                    i30 = configuration.screenWidthDp;
                    i31 = configuration2.screenWidthDp;
                    if (i30 != i31) {
                        configuration3.screenWidthDp = i31;
                    }
                    i32 = configuration.screenHeightDp;
                    i33 = configuration2.screenHeightDp;
                    if (i32 != i33) {
                        configuration3.screenHeightDp = i33;
                    }
                    i34 = configuration.smallestScreenWidthDp;
                    i35 = configuration2.smallestScreenWidthDp;
                    if (i34 != i35) {
                        configuration3.smallestScreenWidthDp = i35;
                    }
                    i36 = configuration.densityDpi;
                    i37 = configuration2.densityDpi;
                    if (i36 != i37) {
                        configuration3.densityDpi = i37;
                    }
                }
            } else {
                configuration3 = null;
            }
            Configuration configurationV3 = I2.v(context, iE, c0342khR, configuration3, true);
            f7 = new F7(context, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120267);
            f7.a(configurationV3);
            if (context.getTheme() != null) {
                theme = f7.getTheme();
                if (i >= 29) {
                    Q3.d(theme);
                } else {
                    synchronized (X8.l) {
                        if (X8.n) {
                            Method declaredMethod3 = Resources.Theme.class.getDeclaredMethod("rebase", null);
                            X8.m = declaredMethod3;
                            declaredMethod3.setAccessible(true);
                            X8.n = true;
                            method = X8.m;
                            if (method != null) {
                                method.invoke(theme, null);
                            }
                        } else {
                            method = X8.m;
                            if (method != null) {
                                method.invoke(theme, null);
                            }
                        }
                    }
                }
            }
            context = f7;
        }
        super.attachBaseContext(context);
    }

    public final AbstractC0667v2 k() {
        if (this.x == null) {
            O3 o3 = AbstractC0667v2.a;
            this.x = new I2(this, null, this, this);
        }
        return this.x;
    }

    public final AbstractC0744xh l() {
        I2 i2 = (I2) k();
        i2.C();
        return i2.o;
    }

    public final void m() {
        getWindow().getDecorView().setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901fc, this);
        getWindow().getDecorView().setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901ff, this);
        getWindow().getDecorView().setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901fe, this);
        getWindow().getDecorView().setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901fd, this);
    }

    public void o() {
        View decorView;
        View decorView2;
        final Window window = getWindow();
        final Resources.Theme theme = getTheme();
        int i = Build.VERSION.SDK_INT;
        if (i >= 28 && window != null && (decorView2 = window.getDecorView()) != null) {
            final int i2 = 1;
            decorView2.post(new Runnable() { // from class: rikka.shizuku.a2
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            Window window2 = window;
                            WindowInsets rootWindowInsets = window2.getDecorView().getRootWindowInsets();
                            if ((rootWindowInsets != null ? rootWindowInsets.getSystemWindowInsetBottom() : 0) < Resources.getSystem().getDisplayMetrics().density * 40) {
                                window2.setNavigationBarColor(0);
                                if (Build.VERSION.SDK_INT >= 29) {
                                    window2.setNavigationBarContrastEnforced(true);
                                }
                            } else {
                                TypedArray typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(new int[]{R.attr.navigationBarColor});
                                int color = typedArrayObtainStyledAttributes.getColor(0, 0);
                                typedArrayObtainStyledAttributes.recycle();
                                window2.setNavigationBarColor((16777215 & color) | (-536870912));
                                if (Build.VERSION.SDK_INT >= 29) {
                                    window2.setNavigationBarContrastEnforced(false);
                                }
                            }
                            break;
                        default:
                            Window window3 = window;
                            WindowInsets rootWindowInsets2 = window3.getDecorView().getRootWindowInsets();
                            if ((rootWindowInsets2 != null ? rootWindowInsets2.getSystemWindowInsetBottom() : 0) >= Resources.getSystem().getDisplayMetrics().density * 40) {
                                TypedArray typedArrayObtainStyledAttributes2 = theme.obtainStyledAttributes(new int[]{moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04035c});
                                int color2 = typedArrayObtainStyledAttributes2.getColor(0, 0);
                                typedArrayObtainStyledAttributes2.recycle();
                                window3.setNavigationBarDividerColor(color2);
                            }
                            break;
                    }
                }
            });
        }
        final Window window2 = getWindow();
        final Resources.Theme theme2 = getTheme();
        if (i < 26 || window2 == null || (decorView = window2.getDecorView()) == null) {
            return;
        }
        final int i3 = 0;
        decorView.post(new Runnable() { // from class: rikka.shizuku.a2
            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                    case 0:
                        Window window3 = window2;
                        WindowInsets rootWindowInsets = window3.getDecorView().getRootWindowInsets();
                        if ((rootWindowInsets != null ? rootWindowInsets.getSystemWindowInsetBottom() : 0) < Resources.getSystem().getDisplayMetrics().density * 40) {
                            window3.setNavigationBarColor(0);
                            if (Build.VERSION.SDK_INT >= 29) {
                                window3.setNavigationBarContrastEnforced(true);
                            }
                        } else {
                            TypedArray typedArrayObtainStyledAttributes = theme2.obtainStyledAttributes(new int[]{R.attr.navigationBarColor});
                            int color = typedArrayObtainStyledAttributes.getColor(0, 0);
                            typedArrayObtainStyledAttributes.recycle();
                            window3.setNavigationBarColor((16777215 & color) | (-536870912));
                            if (Build.VERSION.SDK_INT >= 29) {
                                window3.setNavigationBarContrastEnforced(false);
                            }
                        }
                        break;
                    default:
                        Window window4 = window2;
                        WindowInsets rootWindowInsets2 = window4.getDecorView().getRootWindowInsets();
                        if ((rootWindowInsets2 != null ? rootWindowInsets2.getSystemWindowInsetBottom() : 0) >= Resources.getSystem().getDisplayMetrics().density * 40) {
                            TypedArray typedArrayObtainStyledAttributes2 = theme2.obtainStyledAttributes(new int[]{moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04035c});
                            int color2 = typedArrayObtainStyledAttributes2.getColor(0, 0);
                            typedArrayObtainStyledAttributes2.recycle();
                            window4.setNavigationBarDividerColor(color2);
                        }
                        break;
                }
            }
        });
    }

    @Override // rikka.shizuku.T6, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        this.s.y();
        super.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        if (getParent() == null) {
            theme.applyStyle(i, true);
        } else {
            try {
                theme.setTo(getParent().getTheme());
            } catch (Exception unused) {
            }
            theme.applyStyle(i, false);
        }
        p(theme, false);
        super.onApplyThemeResource(theme, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f1202ca, z);
    }

    @Override // rikka.shizuku.T6, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        I2 i2 = (I2) k();
        if (i2.E && i2.z) {
            i2.C();
            AbstractC0744xh abstractC0744xh = i2.o;
            if (abstractC0744xh != null) {
                abstractC0744xh.J();
            }
        }
        L2 l2A = L2.a();
        Context context = i2.k;
        synchronized (l2A) {
            Tl tl = l2A.a;
            synchronized (tl) {
                C0620th c0620th = (C0620th) tl.b.get(context);
                if (c0620th != null) {
                    c0620th.a();
                }
            }
        }
        i2.Q = new Configuration(i2.k.getResources().getConfiguration());
        i2.p(false, false);
    }

    @Override // rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public void onCreate(Bundle bundle) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this);
        LayoutInflaterFactory2C0188fg layoutInflaterFactory2C0188fg = new LayoutInflaterFactory2C0188fg(k());
        Collections.addAll(layoutInflaterFactory2C0188fg.c, hu.q, C0290iq.a);
        layoutInflaterFactory2C0188fg.b.add(new Mb());
        layoutInflaterFrom.setFactory2(layoutInflaterFactory2C0188fg);
        r(bundle);
        o();
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView = ((Cc) this.s.b).l.f.onCreateView(view, str, context, attributeSet);
        return viewOnCreateView == null ? super.onCreateView(view, str, context, attributeSet) : viewOnCreateView;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        s();
        k().g();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        Window window;
        if (Build.VERSION.SDK_INT >= 26 || keyEvent.isCtrlPressed() || KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState()) || keyEvent.getRepeatCount() != 0 || KeyEvent.isModifierKey(keyEvent.getKeyCode()) || (window = getWindow()) == null || window.getDecorView() == null || !window.getDecorView().dispatchKeyShortcutEvent(keyEvent)) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // rikka.shizuku.T6, android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (!t(i, menuItem)) {
            AbstractC0744xh abstractC0744xhL = l();
            if (menuItem.getItemId() != 16908332 || abstractC0744xhL == null || (abstractC0744xhL.t() & 4) == 0) {
                return false;
            }
            Intent intentW = AbstractC0744xh.w(this);
            if (intentW == null) {
                finish();
                return true;
            }
            if (shouldUpRecreateTask(intentW)) {
                ArrayList arrayList = new ArrayList();
                Intent intentW2 = AbstractC0744xh.w(this);
                if (intentW2 == null) {
                    intentW2 = AbstractC0744xh.w(this);
                }
                if (intentW2 != null) {
                    ComponentName component = intentW2.getComponent();
                    if (component == null) {
                        component = intentW2.resolveActivity(getPackageManager());
                    }
                    int size = arrayList.size();
                    try {
                        Intent intentX = AbstractC0744xh.x(this, component);
                        while (intentX != null) {
                            arrayList.add(size, intentX);
                            intentX = AbstractC0744xh.x(this, intentX.getComponent());
                        }
                        arrayList.add(intentW2);
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
                        throw new IllegalArgumentException(e);
                    }
                }
                if (arrayList.isEmpty()) {
                    throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
                }
                Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[0]);
                intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
                startActivities(intentArr, null);
                try {
                    finishAffinity();
                } catch (IllegalStateException unused) {
                    finish();
                }
            } else {
                navigateUpTo(intentW);
            }
        }
        return true;
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        this.v = false;
        ((Cc) this.s.b).l.u(5);
        this.t.d(EnumC0557rg.ON_PAUSE);
    }

    @Override // android.app.Activity
    public final void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        ((I2) k()).y();
    }

    @Override // android.app.Activity
    public final void onPostResume() {
        u();
        I2 i2 = (I2) k();
        i2.C();
        AbstractC0744xh abstractC0744xh = i2.o;
        if (abstractC0744xh != null) {
            abstractC0744xh.V(true);
        }
    }

    @Override // rikka.shizuku.T6, android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        this.s.y();
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Override // android.app.Activity
    public void onResume() {
        v();
        if (AbstractC0046as.c(C0281ih.b, ((C0281ih) this.y.getValue()).a)) {
            if (AbstractC0046as.c(this.z, Hn.f(this) + Hn.i())) {
                return;
            }
        }
        recreate();
    }

    @Override // android.app.Activity
    public final void onStart() {
        w();
        ((I2) k()).p(true, false);
    }

    @Override // android.app.Activity
    public final void onStateNotSaved() {
        this.s.y();
    }

    @Override // android.app.Activity
    public final void onStop() {
        x();
        I2 i2 = (I2) k();
        i2.C();
        AbstractC0744xh abstractC0744xh = i2.o;
        if (abstractC0744xh != null) {
            abstractC0744xh.V(false);
        }
    }

    @Override // android.app.Activity
    public final void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        k().o(charSequence);
    }

    @Override // android.app.Activity
    public final void openOptionsMenu() {
        AbstractC0744xh abstractC0744xhL = l();
        if (getWindow().hasFeature(0)) {
            if (abstractC0744xhL == null || !abstractC0744xhL.O()) {
                super.openOptionsMenu();
            }
        }
    }

    public void p(Resources.Theme theme, boolean z) {
        int i;
        if (Hn.i()) {
            if ((getResources().getConfiguration().uiMode & 32) == 32) {
                theme.applyStyle(moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f1202d6, true);
            } else {
                theme.applyStyle(moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f1202d7, true);
            }
        }
        String strF = Hn.f(this);
        int iHashCode = strF.hashCode();
        if (iHashCode != -2032180703) {
            if (iHashCode == 63281119 && strF.equals("BLACK")) {
                i = moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f1202d4;
            }
            theme.applyStyle(i, true);
        }
        strF.equals("DEFAULT");
        i = moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f1202ca;
        theme.applyStyle(i, true);
    }

    public final void q(Bundle bundle) {
        super.onCreate(bundle);
        this.t.d(EnumC0557rg.ON_CREATE);
        Qc qc = ((Cc) this.s.b).l;
        qc.H = false;
        qc.I = false;
        qc.O.g = false;
        qc.u(1);
    }

    public final void r(Bundle bundle) {
        int i;
        View decorView;
        Context context;
        C0281ih c0281ih = (C0281ih) this.y.getValue();
        c0281ih.getClass();
        getWindow().getDecorView().setLayoutDirection(TextUtils.getLayoutDirectionFromLocale(c0281ih.a));
        try {
            i = getPackageManager().getActivityInfo(getComponentName(), 128).labelRes;
        } catch (PackageManager.NameNotFoundException unused) {
            i = 0;
        }
        if (i == 0) {
            i = getApplicationInfo().labelRes;
        }
        if (i != 0) {
            setTitle(i);
        }
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
        int i2 = Build.VERSION.SDK_INT;
        TypedArray typedArrayObtainStyledAttributes = obtainStyledAttributes(new int[]{R.attr.windowLightStatusBar});
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        int i3 = z ? systemUiVisibility | 8192 : systemUiVisibility & (-8193);
        if (i2 >= 27) {
            TypedArray typedArrayObtainStyledAttributes2 = obtainStyledAttributes(new int[]{R.attr.windowLightNavigationBar});
            boolean z2 = typedArrayObtainStyledAttributes2.getBoolean(0, false);
            typedArrayObtainStyledAttributes2.recycle();
            i3 = z2 ? i3 | 16 : i3 & (-17);
        }
        getWindow().getDecorView().setSystemUiVisibility(i3);
        this.z = Hn.f(this) + Hn.i();
        q(bundle);
        Window window = getWindow();
        if (((window == null || (decorView = window.getDecorView()) == null || (context = decorView.getContext()) == null) ? null : context.getTheme()) != null) {
            p(getWindow().getDecorView().getContext().getTheme(), true);
        }
    }

    public final void s() {
        super.onDestroy();
        ((Cc) this.s.b).l.l();
        this.t.d(EnumC0557rg.ON_DESTROY);
    }

    @Override // android.app.Activity
    public void setContentView(int i) {
        m();
        k().j(i);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i) {
        super.setTheme(i);
        ((I2) k()).S = i;
    }

    public final boolean t(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 6) {
            return ((Cc) this.s.b).l.j();
        }
        return false;
    }

    public final void u() {
        super.onPostResume();
        this.t.d(EnumC0557rg.ON_RESUME);
        Qc qc = ((Cc) this.s.b).l;
        qc.H = false;
        qc.I = false;
        qc.O.g = false;
        qc.u(7);
    }

    public final void v() {
        Ys ys = this.s;
        ys.y();
        super.onResume();
        this.v = true;
        ((Cc) ys.b).l.z(true);
    }

    public final void w() {
        Ys ys = this.s;
        ys.y();
        super.onStart();
        this.w = false;
        boolean z = this.u;
        Cc cc = (Cc) ys.b;
        if (!z) {
            this.u = true;
            Qc qc = cc.l;
            qc.H = false;
            qc.I = false;
            qc.O.g = false;
            qc.u(4);
        }
        cc.l.z(true);
        this.t.d(EnumC0557rg.ON_START);
        Qc qc2 = cc.l;
        qc2.H = false;
        qc2.I = false;
        qc2.O.g = false;
        qc2.u(5);
    }

    public final void x() {
        Ys ys;
        super.onStop();
        this.w = true;
        do {
            ys = this.s;
        } while (n(((Cc) ys.b).l));
        Qc qc = ((Cc) ys.b).l;
        qc.I = true;
        qc.O.g = true;
        qc.u(4);
        this.t.d(EnumC0557rg.ON_STOP);
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        m();
        k().k(view);
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView = ((Cc) this.s.b).l.f.onCreateView(null, str, context, attributeSet);
        return viewOnCreateView == null ? super.onCreateView(str, context, attributeSet) : viewOnCreateView;
    }

    @Override // android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        m();
        k().l(view, layoutParams);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onContentChanged() {
    }
}
