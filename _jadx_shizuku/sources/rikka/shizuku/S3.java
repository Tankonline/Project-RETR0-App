package rikka.shizuku;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.materialswitch.MaterialSwitch;
import java.util.ArrayList;
import moe.shizuku.manager.shell.ShellTutorialActivity;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class S3 extends X4 implements View.OnClickListener {
    public final /* synthetic */ int w = 0;
    public final Object x;
    public Object y;
    public static final C0759y1 z = new C0759y1(1);
    public static final C0759y1 A = new C0759y1(9);

    /* JADX WARN: Illegal instructions before constructor call */
    public S3(K2 k2) {
        LinearLayout linearLayout = (LinearLayout) k2.a;
        super(linearLayout);
        this.x = k2;
        linearLayout.setFilterTouchesWhenObscured(true);
        linearLayout.setOnClickListener(this);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0048 A[Catch: SecurityException -> 0x00c4, TRY_ENTER, TryCatch #1 {SecurityException -> 0x00c4, blocks: (B:7:0x001d, B:10:0x0035, B:31:0x0084, B:33:0x0095, B:36:0x009f, B:39:0x00a4, B:40:0x00ad, B:41:0x00ae, B:44:0x00b7, B:45:0x00bc, B:16:0x0048, B:18:0x0059, B:21:0x0064, B:24:0x006a, B:25:0x0073, B:26:0x0074, B:29:0x007e, B:30:0x0083, B:13:0x003d, B:47:0x00be, B:48:0x00c3), top: B:74:0x001d, inners: #0, #2, #3, #5, #7 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x0059 A[Catch: SecurityException -> 0x00c4, TRY_LEAVE, TryCatch #1 {SecurityException -> 0x00c4, blocks: (B:7:0x001d, B:10:0x0035, B:31:0x0084, B:33:0x0095, B:36:0x009f, B:39:0x00a4, B:40:0x00ad, B:41:0x00ae, B:44:0x00b7, B:45:0x00bc, B:16:0x0048, B:18:0x0059, B:21:0x0064, B:24:0x006a, B:25:0x0073, B:26:0x0074, B:29:0x007e, B:30:0x0083, B:13:0x003d, B:47:0x00be, B:48:0x00c3), top: B:74:0x001d, inners: #0, #2, #3, #5, #7 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0084 A[Catch: SecurityException -> 0x00c4, TryCatch #1 {SecurityException -> 0x00c4, blocks: (B:7:0x001d, B:10:0x0035, B:31:0x0084, B:33:0x0095, B:36:0x009f, B:39:0x00a4, B:40:0x00ad, B:41:0x00ae, B:44:0x00b7, B:45:0x00bc, B:16:0x0048, B:18:0x0059, B:21:0x0064, B:24:0x006a, B:25:0x0073, B:26:0x0074, B:29:0x007e, B:30:0x0083, B:13:0x003d, B:47:0x00be, B:48:0x00c3), top: B:74:0x001d, inners: #0, #2, #3, #5, #7 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x0095 A[Catch: SecurityException -> 0x00c4, TRY_LEAVE, TryCatch #1 {SecurityException -> 0x00c4, blocks: (B:7:0x001d, B:10:0x0035, B:31:0x0084, B:33:0x0095, B:36:0x009f, B:39:0x00a4, B:40:0x00ad, B:41:0x00ae, B:44:0x00b7, B:45:0x00bc, B:16:0x0048, B:18:0x0059, B:21:0x0064, B:24:0x006a, B:25:0x0073, B:26:0x0074, B:29:0x007e, B:30:0x0083, B:13:0x003d, B:47:0x00be, B:48:0x00c3), top: B:74:0x001d, inners: #0, #2, #3, #5, #7 }] */
    /* JADX WARN: Code duplicated, block: B:72:0x009f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x0064 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        RecyclerView recyclerView;
        Yk yk;
        int iH;
        String str;
        int i;
        int i2;
        String str2;
        int i3;
        int i4;
        switch (this.w) {
            case 0:
                Context context = view.getContext();
                try {
                    Object obj = this.u;
                    String str3 = ((PackageInfo) obj).packageName;
                    int i5 = ((PackageInfo) obj).applicationInfo.uid;
                    if (!Nn.i) {
                        try {
                            if ((Nn.k().c(i5, 6) & 2) != 2) {
                                Object obj2 = this.u;
                                str2 = ((PackageInfo) obj2).packageName;
                                i3 = ((PackageInfo) obj2).applicationInfo.uid;
                                if (!Nn.i) {
                                    Nn.k().d(i3, 6, 2);
                                } else {
                                    ArrayList arrayList = Vn.a;
                                    i4 = i3 / 100000;
                                    if (Nn.h()) {
                                        X8.G(i4, str2, "moe.shizuku.manager.permission.API_V23");
                                    }
                                }
                            } else {
                                Object obj3 = this.u;
                                str = ((PackageInfo) obj3).packageName;
                                i = ((PackageInfo) obj3).applicationInfo.uid;
                                if (!Nn.i) {
                                    Nn.k().d(i, 6, 0);
                                } else {
                                    ArrayList arrayList2 = Vn.a;
                                    i2 = i / 100000;
                                    if (Nn.h()) {
                                        X8.V(i2, str);
                                    }
                                }
                            }
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (Vn.a(i5 / 100000, str3) == 0) {
                        Object obj4 = this.u;
                        str = ((PackageInfo) obj4).packageName;
                        i = ((PackageInfo) obj4).applicationInfo.uid;
                        if (!Nn.i) {
                            ArrayList arrayList3 = Vn.a;
                            i2 = i / 100000;
                            if (Nn.h()) {
                                try {
                                    X8.V(i2, str);
                                } catch (RemoteException e2) {
                                    throw new RuntimeException(e2.getMessage(), e2);
                                }
                            }
                        } else {
                            try {
                                Nn.k().d(i, 6, 0);
                            } catch (RemoteException e3) {
                                throw new RuntimeException(e3);
                            }
                        }
                    } else {
                        Object obj5 = this.u;
                        str2 = ((PackageInfo) obj5).packageName;
                        i3 = ((PackageInfo) obj5).applicationInfo.uid;
                        if (!Nn.i) {
                            ArrayList arrayList4 = Vn.a;
                            i4 = i3 / 100000;
                            if (Nn.h()) {
                                try {
                                    try {
                                        X8.G(i4, str2, "moe.shizuku.manager.permission.API_V23");
                                    } catch (RemoteException e4) {
                                        throw new RuntimeException(e4.getMessage(), e4);
                                    }
                                } catch (Throwable unused) {
                                    return;
                                }
                            }
                        } else {
                            try {
                                Nn.k().d(i3, 6, 2);
                            } catch (RemoteException e5) {
                                throw new RuntimeException(e5);
                            }
                        }
                    }
                    break;
                } catch (SecurityException unused2) {
                    if (Nn.e() != 0) {
                        Ch ch = new Ch(context);
                        E1 e1 = (E1) ch.b;
                        e1.d = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f110030);
                        e1.f = AbstractC0688vn.A(context.getString(R.string._0_resource_name_obfuscated_res_0x7f11002f, AbstractC0494pe.a.a()), 512);
                        ch.g(android.R.string.ok, null);
                        J1 j1B = ch.b();
                        j1B.setOnShowListener(new R3(0));
                        try {
                            j1B.show();
                            break;
                        } catch (Throwable unused3) {
                        }
                    }
                }
                T4 t4 = this.v;
                int i6 = -1;
                if (this.s != null && (recyclerView = this.r) != null && (yk = recyclerView.m) != null && (iH = recyclerView.H(this)) != -1 && this.s == yk) {
                    i6 = iH;
                }
                t4.a.c(i6, 1, new Object());
                return;
            default:
                view.getContext().startActivity(new Intent(view.getContext(), (Class<?>) ShellTutorialActivity.class));
                return;
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0092  */
    /* JADX WARN: Code duplicated, block: B:27:0x00ff  */
    @Override // rikka.shizuku.X4
    public final void q() {
        CharSequence charSequenceLoadLabel;
        Object obj;
        Ur ur;
        Object obj2 = this.x;
        View view = this.a;
        boolean z2 = true;
        switch (this.w) {
            case 0:
                PackageManager packageManager = view.getContext().getPackageManager();
                ApplicationInfo applicationInfo = ((PackageInfo) this.u).applicationInfo;
                int i = applicationInfo.uid;
                int i2 = Tr.a;
                int i3 = i / 100000;
                K2 k2 = (K2) obj2;
                ((ImageView) k2.b).setImageDrawable(applicationInfo.loadIcon(packageManager));
                TextView textView = (TextView) k2.f;
                if (i3 != Tr.a) {
                    ArrayList arrayListC = Vn.c(true);
                    int size = arrayListC.size();
                    int i4 = 0;
                    do {
                        if (i4 < size) {
                            obj = arrayListC.get(i4);
                            i4++;
                        } else {
                            obj = null;
                        }
                        ur = (Ur) obj;
                        if (ur == null) {
                            ur = new Ur(Tr.a, "Unknown");
                        }
                        charSequenceLoadLabel = ((Object) ((PackageInfo) this.u).applicationInfo.loadLabel(packageManager)) + " - " + ur.b + " (" + i3 + ")";
                    } while (((Ur) obj).a != i3);
                    ur = (Ur) obj;
                    if (ur == null) {
                        ur = new Ur(Tr.a, "Unknown");
                    }
                    charSequenceLoadLabel = ((Object) ((PackageInfo) this.u).applicationInfo.loadLabel(packageManager)) + " - " + ur.b + " (" + i3 + ")";
                } else {
                    charSequenceLoadLabel = ((PackageInfo) this.u).applicationInfo.loadLabel(packageManager);
                }
                textView.setText(charSequenceLoadLabel);
                ((TextView) k2.d).setText(((PackageInfo) this.u).applicationInfo.packageName);
                MaterialSwitch materialSwitch = (MaterialSwitch) k2.e;
                PackageInfo packageInfo = (PackageInfo) this.u;
                String str = packageInfo.packageName;
                int i5 = packageInfo.applicationInfo.uid;
                if (!Nn.i) {
                    try {
                        if ((Nn.k().c(i5, 6) & 2) != 2) {
                            z2 = false;
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } else if (Vn.a(i5 / 100000, str) != 0) {
                    z2 = false;
                }
                materialSwitch.setChecked(z2);
                TextView textView2 = (TextView) k2.c;
                Bundle bundle = ((PackageInfo) this.u).applicationInfo.metaData;
                textView2.setVisibility((bundle == null || !bundle.getBoolean("moe.shizuku.client.V3_REQUIRES_ROOT")) ? 8 : 0);
                Context context = view.getContext();
                ApplicationInfo applicationInfo2 = ((PackageInfo) this.u).applicationInfo;
                this.y = AbstractC0599sr.z(H3.a, new G3((ImageView) k2.b, applicationInfo2, applicationInfo2.uid / 100000, context, null), 3);
                return;
            default:
                Context context2 = view.getContext();
                boolean zA = ((C0103cn) this.u).a();
                C0741xe c0741xe = (C0741xe) obj2;
                MaterialCardView materialCardView = (MaterialCardView) this.y;
                if (zA) {
                    materialCardView.setEnabled(true);
                    c0741xe.a.setText(context2.getString(R.string._0_resource_name_obfuscated_res_0x7f110084));
                    return;
                } else {
                    materialCardView.setEnabled(false);
                    c0741xe.a.setText(context2.getString(R.string._0_resource_name_obfuscated_res_0x7f110081, context2.getString(R.string._0_resource_name_obfuscated_res_0x7f110032)));
                    return;
                }
        }
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0028  */
    @Override // rikka.shizuku.X4
    public void r() {
        switch (this.w) {
            case 0:
                MaterialSwitch materialSwitch = (MaterialSwitch) ((K2) this.x).e;
                PackageInfo packageInfo = (PackageInfo) this.u;
                String str = packageInfo.packageName;
                int i = packageInfo.applicationInfo.uid;
                boolean z2 = false;
                if (!Nn.i) {
                    try {
                        if ((Nn.k().c(i, 6) & 2) == 2) {
                            z2 = true;
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } else if (Vn.a(i / 100000, str) == 0) {
                    z2 = true;
                }
                materialSwitch.setChecked(z2);
                return;
            default:
                return;
        }
    }

    @Override // rikka.shizuku.X4
    public void s() {
        Mo mo;
        switch (this.w) {
            case 0:
                Mo mo2 = (Mo) this.y;
                if (mo2 != null && mo2.a() && (mo = (Mo) this.y) != null) {
                    mo.i(null);
                    break;
                }
                break;
        }
    }

    public S3(C0741xe c0741xe, MaterialCardView materialCardView) {
        super(materialCardView);
        this.x = c0741xe;
        this.y = materialCardView;
        materialCardView.setOnClickListener(this);
    }
}
