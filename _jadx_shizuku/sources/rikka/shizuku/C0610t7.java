package rikka.shizuku;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.util.Log;
import androidx.preference.Preference;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.ReentrantLock;
import moe.shizuku.manager.shell.ShellTutorialActivity;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.t7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0610t7 implements InterfaceC0253hj, InterfaceC0100ck, L0, InterfaceC0781yn {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0610t7(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.InterfaceC0781yn
    public void a(C0516q5 c0516q5) {
        if (c0516q5.a != 0) {
            Us us = (Us) this.b;
            StringBuilder sb = us.b;
            sb.append('\n');
            sb.append("Send this to developer may help solve the problem.");
            us.d(null);
        }
    }

    @Override // rikka.shizuku.L0
    public void b(Object obj) throws IOException {
        Uri uri = (Uri) obj;
        int i = ShellTutorialActivity.E;
        if (uri == null) {
            return;
        }
        ShellTutorialActivity shellTutorialActivity = (ShellTutorialActivity) this.b;
        ContentResolver contentResolver = shellTutorialActivity.getContentResolver();
        Uri uriBuildDocumentUriUsingTree = DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
        Cursor cursorQuery = contentResolver.query(DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri)), new String[]{"document_id", "_display_name"}, null, null, null);
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                try {
                    String string = cursorQuery.getString(0);
                    String string2 = cursorQuery.getString(1);
                    if (AbstractC0046as.c(string2, "rish") || AbstractC0046as.c(string2, "rish_shizuku.dex")) {
                        DocumentsContract.deleteDocument(contentResolver, DocumentsContract.buildDocumentUriUsingTree(uri, string));
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        AbstractC0599sr.d(cursorQuery, th);
                        throw th2;
                    }
                }
            }
            cursorQuery.close();
        }
        ShellTutorialActivity.z(shellTutorialActivity, uriBuildDocumentUriUsingTree, contentResolver, "rish");
        ShellTutorialActivity.z(shellTutorialActivity, uriBuildDocumentUriUsingTree, contentResolver, "rish_shizuku.dex");
    }

    @Override // rikka.shizuku.InterfaceC0100ck
    public void c(Preference preference) {
        Context context = (Context) this.b;
        AbstractC0744xh.G(context, context.getString(R.string._0_resource_name_obfuscated_res_0x7f110130));
    }

    @Override // rikka.shizuku.InterfaceC0253hj
    public Object d() {
        Object obj = this.b;
        switch (this.a) {
            case 0:
                Constructor constructor = (Constructor) obj;
                try {
                    return constructor.newInstance(null);
                } catch (IllegalAccessException e) {
                    AbstractC0046as abstractC0046as = AbstractC0717wl.a;
                    throw new RuntimeException("Unexpected IllegalAccessException occurred (Gson 2.13.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.", e);
                } catch (InstantiationException e2) {
                    throw new RuntimeException("Failed to invoke constructor '" + AbstractC0717wl.b(constructor) + "' with no args", e2);
                } catch (InvocationTargetException e3) {
                    throw new RuntimeException("Failed to invoke constructor '" + AbstractC0717wl.b(constructor) + "' with no args", e3.getCause());
                }
            default:
                Class cls = (Class) obj;
                try {
                    return Pr.a.a(cls);
                } catch (Exception e4) {
                    throw new RuntimeException("Unable to create instance of " + cls + ". Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.", e4);
                }
        }
    }

    public boolean e(Ys ys, int i, Bundle bundle) {
        InterfaceC0765y7 ys2;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 25 && (i & 1) != 0) {
            try {
                ((InterfaceC0526qf) ys.b).a();
                Parcelable parcelable = (Parcelable) ((InterfaceC0526qf) ys.b).d();
                bundle = bundle == null ? new Bundle() : new Bundle(bundle);
                bundle.putParcelable("androidx.core.view.extra.INPUT_CONTENT_INFO", parcelable);
            } catch (Exception e) {
                Log.w("InputConnectionCompat", "Can't insert content from IME; requestPermission() failed", e);
                return false;
            }
        }
        ClipDescription clipDescriptionC = ((InterfaceC0526qf) ys.b).c();
        InterfaceC0526qf interfaceC0526qf = (InterfaceC0526qf) ys.b;
        ClipData clipData = new ClipData(clipDescriptionC, new ClipData.Item(interfaceC0526qf.e()));
        if (i2 >= 31) {
            ys2 = new Ys(clipData, 2);
        } else {
            C0796z7 c0796z7 = new C0796z7();
            c0796z7.b = clipData;
            c0796z7.c = 2;
            ys2 = c0796z7;
        }
        ys2.i(interfaceC0526qf.b());
        ys2.h(bundle);
        return Hs.i((M2) this.b, ys2.d()) == null;
    }

    public void f(Gn gn) {
        Sj sj = (Sj) this.b;
        sj.getClass();
        ReentrantLock reentrantLock = gn.f;
        reentrantLock.lock();
        try {
            gn.h.offer(sj);
            if (!gn.i) {
                gn.i = true;
                Gn.j.execute(new Cn(gn, 1));
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}
