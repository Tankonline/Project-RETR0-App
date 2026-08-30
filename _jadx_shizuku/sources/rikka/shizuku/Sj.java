package rikka.shizuku;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
public final class Sj extends AbstractC0750xn implements InterfaceC0812zn {
    public static final ArrayList j = new ArrayList(0);
    public static final String k;
    public static final byte[] l;
    public final ArrayList d = new ArrayList();
    public AbstractList e;
    public ArrayList f;
    public Cr g;
    public InterfaceC0781yn h;
    public Runnable i;

    static {
        String string = UUID.randomUUID().toString();
        k = string;
        l = String.format("__RET=$?;echo %1$s;echo %1$s >&2;echo $__RET;unset __RET\n", string).getBytes(StandardCharsets.UTF_8);
    }

    public Sj() {
        ArrayList arrayList = j;
        this.e = arrayList;
        this.f = arrayList;
    }

    @Override // rikka.shizuku.InterfaceC0812zn
    public final void a(OutputStream outputStream, InputStream inputStream, InputStream inputStream2) {
        ArrayList arrayList = this.d;
        List listSynchronizedList = this.e;
        ArrayList arrayList2 = j;
        int i = 0;
        boolean z = listSynchronizedList == arrayList2;
        boolean z2 = this.f == arrayList2;
        List list = null;
        if (z) {
            listSynchronizedList = this.h == null ? null : new ArrayList();
        }
        if (!z2) {
            list = this.f;
        } else if (Gn.k) {
            list = listSynchronizedList;
        }
        if (listSynchronizedList != null && listSynchronizedList == list) {
            if (AbstractC0046as.a == null) {
                AbstractC0046as.a = Collections.synchronizedCollection(Collections.EMPTY_LIST).getClass();
            }
            if (!AbstractC0046as.a.isInstance(listSynchronizedList)) {
                listSynchronizedList = Collections.synchronizedList(listSynchronizedList);
                list = listSynchronizedList;
            }
        }
        FutureTask futureTask = new FutureTask(new Uo(inputStream, listSynchronizedList, 1));
        FutureTask futureTask2 = new FutureTask(new Uo(inputStream2, list, 0));
        ExecutorService executorService = Gn.j;
        executorService.execute(futureTask);
        executorService.execute(futureTask2);
        C0516q5 c0516q5 = new C0516q5(1);
        try {
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((H6) obj).a(outputStream);
            }
            outputStream.write(l);
            outputStream.flush();
            int iIntValue = ((Integer) futureTask.get()).intValue();
            futureTask2.get();
            c0516q5.a = iIntValue;
        } catch (IOException | InterruptedException | ExecutionException unused) {
        }
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj2 = arrayList.get(i);
            i++;
            ((H6) obj2).getClass();
        }
        InterfaceC0781yn interfaceC0781yn = this.h;
        if (interfaceC0781yn != null) {
            Cr cr = this.g;
            if (cr == null) {
                interfaceC0781yn.a(c0516q5);
            } else {
                cr.execute(new N3(this, 2, c0516q5));
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0812zn
    public final void b() {
        Runnable runnable = this.i;
        if (runnable != null) {
            this.i = null;
            runnable.run();
            return;
        }
        ArrayList arrayList = this.d;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((H6) obj).getClass();
        }
        C0516q5 c0516q5 = new C0516q5(1);
        InterfaceC0781yn interfaceC0781yn = this.h;
        if (interfaceC0781yn != null) {
            Cr cr = this.g;
            if (cr == null) {
                interfaceC0781yn.a(c0516q5);
            } else {
                cr.execute(new N3(this, 2, c0516q5));
            }
        }
    }
}
