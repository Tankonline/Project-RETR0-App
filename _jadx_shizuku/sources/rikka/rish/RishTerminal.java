package rikka.rish;

import android.os.Parcel;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import rikka.shizuku.AbstractC0092cc;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.G0;

/* JADX INFO: loaded from: classes.dex */
public class RishTerminal {
    public final String[] a;
    public final byte b;
    public final FileDescriptor[] c;
    public final FileDescriptor[] d;
    public final FileDescriptor[] e;
    public int f = -1;
    public int g;

    public RishTerminal(String[] strArr) {
        this.a = strArr;
        byte bPrepare = prepare();
        this.b = bPrepare;
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            arrayList.add(entry.getKey() + "=" + entry.getValue());
        }
        String[] strArr2 = (String[]) arrayList.toArray(new String[0]);
        String absolutePath = new File("").getAbsolutePath();
        try {
            parcelObtain.writeInterfaceToken(AbstractC0744xh.n);
            parcelObtain.writeByte(bPrepare);
            FileDescriptor[] fileDescriptorArrPipe = Os.pipe();
            this.c = fileDescriptorArrPipe;
            parcelObtain.writeFileDescriptor(fileDescriptorArrPipe[0]);
            FileDescriptor[] fileDescriptorArrPipe2 = Os.pipe();
            this.d = fileDescriptorArrPipe2;
            parcelObtain.writeFileDescriptor(fileDescriptorArrPipe2[1]);
            if ((bPrepare & 4) == 0) {
                FileDescriptor[] fileDescriptorArrPipe3 = Os.pipe();
                this.e = fileDescriptorArrPipe3;
                parcelObtain.writeFileDescriptor(fileDescriptorArrPipe3[1]);
            }
            parcelObtain.writeStringArray(this.a);
            parcelObtain.writeStringArray(strArr2);
            parcelObtain.writeString(absolutePath);
            AbstractC0744xh.m.transact(AbstractC0744xh.o, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
            b(this.c, 0);
            b(this.d, 1);
            b(this.e, 1);
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x002e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(RishTerminal rishTerminal) {
        rishTerminal.getClass();
        while (true) {
            try {
                long jWaitForWindowSizeChange = waitForWindowSizeChange(rishTerminal.f);
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                parcelObtain.writeInterfaceToken(AbstractC0744xh.n);
                parcelObtain.writeLong(jWaitForWindowSizeChange);
                AbstractC0744xh.m.transact(AbstractC0744xh.o + 1, parcelObtain, null, 0);
                parcelObtain2.readException();
                parcelObtain.recycle();
                parcelObtain2.recycle();
            } catch (Throwable th) {
                Log.w("RishTerminal", Log.getStackTraceString(th));
            }
        }
    }

    public static void b(FileDescriptor[] fileDescriptorArr, int i) {
        if (fileDescriptorArr == null) {
            return;
        }
        FileDescriptor fileDescriptor = fileDescriptorArr[i];
        Method method = AbstractC0092cc.a;
        if (fileDescriptor == null) {
            return;
        }
        try {
            Os.close(fileDescriptor);
        } catch (ErrnoException unused) {
        }
    }

    public static int c(FileDescriptor[] fileDescriptorArr, int i) {
        if (fileDescriptorArr == null) {
            return -1;
        }
        try {
            return ((Integer) AbstractC0092cc.a.invoke(fileDescriptorArr[i], null)).intValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return -1;
        }
    }

    private static native byte prepare();

    private static native int start(byte b, int i, int i2, int i3);

    private static native void waitForProcessExit();

    private static native long waitForWindowSizeChange(int i);

    public final void d() {
        int iStart = start(this.b, c(this.c, 1), c(this.d, 0), c(this.d, 0));
        this.f = iStart;
        if (iStart != -1) {
            new Thread(new G0(15, this)).start();
        }
    }

    public final int e() {
        waitForProcessExit();
        try {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(AbstractC0744xh.n);
                AbstractC0744xh.m.transact(AbstractC0744xh.o + 2, parcelObtain, null, 0);
                parcelObtain2.readException();
                int i = parcelObtain2.readInt();
                parcelObtain.recycle();
                parcelObtain2.recycle();
                this.g = i;
            } catch (Throwable th) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            Log.w("RishTerminal", Log.getStackTraceString(th2));
            this.g = -1;
        }
        return this.g;
    }
}
