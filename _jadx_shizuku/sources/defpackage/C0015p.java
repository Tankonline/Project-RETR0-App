package defpackage;

import android.content.res.AssetManager;
import android.os.Build;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.concurrent.Executor;

/* JADX INFO: renamed from: p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0015p {
    public final Executor a;
    public final y b;
    public final byte[] c;
    public final File d;
    public final String e;
    public boolean f = false;
    public C0016q[] g;
    public byte[] h;

    public C0015p(AssetManager assetManager, Executor executor, y yVar, String str, File file) {
        this.a = executor;
        this.b = yVar;
        this.e = str;
        this.d = file;
        int i = Build.VERSION.SDK_INT;
        byte[] bArr = null;
        if (i <= 33) {
            switch (i) {
                case 24:
                case 25:
                    bArr = t.j;
                    break;
                case 26:
                    bArr = t.i;
                    break;
                case 27:
                    bArr = t.h;
                    break;
                case 28:
                case 29:
                case 30:
                    bArr = t.g;
                    break;
                case 31:
                case 32:
                case 33:
                    bArr = t.f;
                    break;
            }
        }
        this.c = bArr;
    }

    public final FileInputStream a(AssetManager assetManager, String str) {
        try {
            return assetManager.openFd(str).createInputStream();
        } catch (FileNotFoundException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("compressed")) {
                return null;
            }
            this.b.a();
            return null;
        }
    }

    public final void b(final int i, final Serializable serializable) {
        this.a.execute(new Runnable() { // from class: o
            @Override // java.lang.Runnable
            public final void run() {
                this.a.b.b(i, serializable);
            }
        });
    }
}
