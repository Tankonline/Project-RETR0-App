package rikka.shizuku;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public class Ys implements InterfaceC0067bi, InterfaceC0344kj, InterfaceC0765y7, A7, La, W9 {
    public static final Ee c = new Ee();
    public final /* synthetic */ int a;
    public final Object b;

    public /* synthetic */ Ys(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public static int u(int i) {
        if (i < 128) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 8;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    public static int v(int i, boolean z) {
        return u(i) + (z ? 1 : 0) + i;
    }

    public static int w(int i) {
        if (i < 31) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 7;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    public static Ys x(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View viewInflate = layoutInflater.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c003d, viewGroup, false);
        if (viewInflate == null) {
            throw new NullPointerException("rootView");
        }
        return new Ys(19, (MaterialCardView) viewInflate);
    }

    public static Ys z(boolean z, int i, int i2, int i3, int i4) {
        return new Ys(2, AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, false, z));
    }

    public boolean A(int i, int i2, Bundle bundle) {
        return false;
    }

    public void D(int i) throws IOException {
        ((OutputStream) this.b).write(i);
    }

    public void E(byte[] bArr, int i, int i2) throws IOException {
        ((OutputStream) this.b).write(bArr, i, i2);
    }

    public void F(int i) throws IOException {
        if (i < 128) {
            D(i);
            return;
        }
        int i2 = 5;
        byte[] bArr = new byte[5];
        while (true) {
            int i3 = i2 - 1;
            bArr[i3] = (byte) i;
            i >>>= 8;
            if (i == 0) {
                int i4 = i2 - 2;
                bArr[i4] = (byte) ((5 - i3) | 128);
                E(bArr, i4, 6 - i3);
                return;
            }
            i2 = i3;
        }
    }

    public void G(InterfaceC0202g[] interfaceC0202gArr) {
        for (InterfaceC0202g interfaceC0202g : interfaceC0202gArr) {
            interfaceC0202g.d().j(this, true);
        }
    }

    public void H(boolean z, int i, byte[] bArr) throws IOException {
        K(i, z);
        F(bArr.length);
        E(bArr, 0, bArr.length);
    }

    public void I(boolean z, int i, InterfaceC0202g[] interfaceC0202gArr) throws IOException {
        K(i, z);
        D(128);
        G(interfaceC0202gArr);
        D(0);
        D(0);
    }

    public void J(int i, int i2) throws IOException {
        if (i2 < 31) {
            D(i | i2);
            return;
        }
        byte[] bArr = new byte[6];
        int i3 = 5;
        bArr[5] = (byte) (i2 & 127);
        while (i2 > 127) {
            i2 >>>= 7;
            i3--;
            bArr[i3] = (byte) ((i2 & 127) | 128);
        }
        int i4 = i3 - 1;
        bArr[i4] = (byte) (i | 31);
        E(bArr, i4, 6 - i4);
    }

    public void K(int i, boolean z) throws IOException {
        if (z) {
            D(i);
        }
    }

    public void L(AbstractC0633u abstractC0633u) {
        abstractC0633u.j(this, true);
    }

    public void M(AbstractC0633u[] abstractC0633uArr) {
        for (AbstractC0633u abstractC0633u : abstractC0633uArr) {
            abstractC0633u.j(this, true);
        }
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public void a(MenuC0129di menuC0129di) {
        InterfaceC0067bi interfaceC0067bi = ((ActionMenuView) this.b).v;
        if (interfaceC0067bi != null) {
            interfaceC0067bi.a(menuC0129di);
        }
    }

    @Override // rikka.shizuku.W9
    public boolean b(float f) {
        if (f == 0.0f) {
            return false;
        }
        m();
        ((NestedScrollView) this.b).j((int) f);
        return true;
    }

    @Override // rikka.shizuku.A7
    public ClipData c() {
        return ((ContentInfo) this.b).getClip();
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public B7 d() {
        return new B7(new Ys(((ContentInfo.Builder) this.b).build()));
    }

    @Override // rikka.shizuku.A7
    public int e() {
        return ((ContentInfo) this.b).getFlags();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // rikka.shizuku.InterfaceC0344kj
    public au f(View view, au auVar) {
        int i = 0;
        Object obj = this.b;
        switch (this.a) {
            case 6:
                AppBarLayout appBarLayout = (AppBarLayout) obj;
                WeakHashMap weakHashMap = Hs.a;
                au auVar2 = appBarLayout.getFitsSystemWindows() ? auVar : null;
                if (!Objects.equals(appBarLayout.g, auVar2)) {
                    appBarLayout.g = auVar2;
                    if (appBarLayout.t != null && appBarLayout.e() > 0) {
                        i = 1;
                    }
                    appBarLayout.setWillNotDraw(i ^ 1);
                    appBarLayout.requestLayout();
                }
                break;
            default:
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) obj;
                if (!Objects.equals(coordinatorLayout.n, auVar)) {
                    coordinatorLayout.n = auVar;
                    boolean z = auVar.b() > 0;
                    coordinatorLayout.o = z;
                    coordinatorLayout.setWillNotDraw(!z && coordinatorLayout.getBackground() == null);
                    Wt wt = auVar.a;
                    if (!wt.m()) {
                        int childCount = coordinatorLayout.getChildCount();
                        while (i < childCount) {
                            View childAt = coordinatorLayout.getChildAt(i);
                            WeakHashMap weakHashMap2 = Hs.a;
                            if (!childAt.getFitsSystemWindows() || ((N7) childAt.getLayoutParams()).a == null || !wt.m()) {
                                i++;
                            }
                        }
                    }
                    coordinatorLayout.requestLayout();
                }
                break;
        }
        return auVar;
    }

    @Override // rikka.shizuku.A7
    public ContentInfo g() {
        return (ContentInfo) this.b;
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public void h(Bundle bundle) {
        ((ContentInfo.Builder) this.b).setExtras(bundle);
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public void i(Uri uri) {
        ((ContentInfo.Builder) this.b).setLinkUri(uri);
    }

    @Override // rikka.shizuku.A7
    public int j() {
        return ((ContentInfo) this.b).getSource();
    }

    @Override // rikka.shizuku.La
    public void k(AbstractC0599sr abstractC0599sr) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new V6("EmojiCompatInitializer"));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        threadPoolExecutor.execute(new RunnableC0674v9(this, abstractC0599sr, threadPoolExecutor, 1));
    }

    @Override // rikka.shizuku.W9
    public float l() {
        return -((NestedScrollView) this.b).m();
    }

    @Override // rikka.shizuku.W9
    public void m() {
        ((NestedScrollView) this.b).d.abortAnimation();
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public boolean n(MenuC0129di menuC0129di, MenuItem menuItem) {
        boolean z;
        boolean zOnMenuItemSelected;
        C0075bq c0075bq = ((ActionMenuView) this.b).A;
        if (c0075bq != null) {
            Toolbar toolbar = c0075bq.a;
            Iterator it = ((CopyOnWriteArrayList) toolbar.G.c).iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (((Jc) it.next()).a.p()) {
                    z = true;
                    break;
                }
            }
            if (z) {
                zOnMenuItemSelected = true;
            } else {
                C0229gq c0229gq = toolbar.I;
                zOnMenuItemSelected = c0229gq != null ? c0229gq.a.r.a.onMenuItemSelected(0, menuItem) : false;
            }
            if (zOnMenuItemSelected) {
                return true;
            }
        }
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public void o(int i) {
        ((ContentInfo.Builder) this.b).setFlags(i);
    }

    public C0141e0 p(int i) {
        return null;
    }

    public C0141e0 q(int i) {
        return null;
    }

    public Vs r(Class cls) {
        String str;
        C0178f6 c0178f6A = AbstractC0593sl.a(cls);
        Class cls2 = c0178f6A.a;
        String canonicalName = null;
        if (!cls2.isAnonymousClass() && !cls2.isLocalClass()) {
            boolean zIsArray = cls2.isArray();
            HashMap map = C0178f6.c;
            if (zIsArray) {
                Class<?> componentType = cls2.getComponentType();
                if (componentType.isPrimitive() && (str = (String) map.get(componentType.getName())) != null) {
                    canonicalName = str.concat("Array");
                }
                if (canonicalName == null) {
                    canonicalName = "kotlin.Array";
                }
            } else {
                canonicalName = (String) map.get(cls2.getName());
                if (canonicalName == null) {
                    canonicalName = cls2.getCanonicalName();
                }
            }
        }
        if (canonicalName != null) {
            return ((Xn) this.b).p(c0178f6A, "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName));
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    public B8 s() {
        return new B8(1, (OutputStream) this.b);
    }

    public P8 t() {
        return new P8(1, (OutputStream) this.b);
    }

    public String toString() {
        switch (this.a) {
            case 13:
                return "ContentInfoCompat{" + ((ContentInfo) this.b) + "}";
            default:
                return super.toString();
        }
    }

    public void y() {
        ((Cc) this.b).l.Q();
    }

    public Ys(Zs zs, Xs xs) {
        this.a = 0;
        this.b = new Xn(zs, xs, C0149e8.b);
    }

    public Ys(TextView textView) {
        this.a = 17;
        this.b = new Ya(textView);
    }

    public Ys(EditText editText) {
        this.a = 16;
        this.b = new R2(editText, 11);
    }

    public Ys(int i) {
        this.a = i;
        switch (i) {
            case 21:
                this.b = new LinkedHashMap(0, 0.75f, true);
                break;
            default:
                if (Build.VERSION.SDK_INT >= 26) {
                    this.b = new C0203g0(this);
                } else {
                    this.b = new C0172f0(this);
                }
                break;
        }
    }

    public Ys(Uri uri, ClipDescription clipDescription, Uri uri2) {
        this.a = 20;
        if (Build.VERSION.SDK_INT >= 25) {
            this.b = new C0495pf(uri, clipDescription, uri2);
        } else {
            this.b = new K3(uri, clipDescription, uri2);
        }
    }

    public Ys(Context context) {
        this.a = 15;
        this.b = context.getApplicationContext();
    }

    public Ys(ContentInfo contentInfo) {
        this.a = 13;
        contentInfo.getClass();
        this.b = AbstractC0604t1.j(contentInfo);
    }

    public Ys(ClipData clipData, int i) {
        this.a = 12;
        this.b = AbstractC0604t1.h(clipData, i);
    }

    public void B(int i) {
    }

    public void C(int i) {
    }
}
