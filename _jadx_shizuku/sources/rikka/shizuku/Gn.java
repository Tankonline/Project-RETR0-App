package rikka.shizuku;

import android.text.TextUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public final class Gn implements Closeable {
    public static final ExecutorService j = Executors.newCachedThreadPool();
    public static boolean k = false;
    public volatile int a;
    public final Process b;
    public final En c;
    public final Dn d;
    public final Dn e;
    public final ReentrantLock f;
    public final Condition g;
    public final ArrayDeque h;
    public boolean i;

    public Gn(C0516q5 c0516q5, Process process) throws IOException {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.f = reentrantLock;
        this.g = reentrantLock.newCondition();
        this.h = new ArrayDeque();
        this.i = false;
        this.a = -1;
        this.b = process;
        OutputStream outputStream = process.getOutputStream();
        this.c = new En(outputStream instanceof BufferedOutputStream ? outputStream : new BufferedOutputStream(outputStream));
        this.d = new Dn(process.getInputStream());
        this.e = new Dn(process.getErrorStream());
        FutureTask futureTask = new FutureTask(new Callable() { // from class: rikka.shizuku.Bn
            @Override // java.util.concurrent.Callable
            public final Object call() throws IOException {
                Gn gn = this.a;
                gn.getClass();
                try {
                    gn.b.exitValue();
                    throw new IOException("Created process has terminated");
                } catch (IllegalThreadStateException unused) {
                    Hn.a(gn.d);
                    Hn.a(gn.e);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gn.d));
                    try {
                        En en = gn.c;
                        Charset charset = StandardCharsets.UTF_8;
                        en.write("echo SHELL_TEST\n".getBytes(charset));
                        gn.c.flush();
                        String line = bufferedReader.readLine();
                        if (TextUtils.isEmpty(line) || !line.contains("SHELL_TEST")) {
                            throw new IOException("Created process is not a shell");
                        }
                        gn.c.write("id\n".getBytes(charset));
                        gn.c.flush();
                        String line2 = bufferedReader.readLine();
                        int i = 0;
                        if (!TextUtils.isEmpty(line2) && line2.contains("uid=0")) {
                            synchronized (AbstractC0046as.class) {
                                String property = System.getProperty("user.dir");
                                StringBuilder sb = new StringBuilder("'");
                                int length = property.length();
                                while (i < length) {
                                    char cCharAt = property.charAt(i);
                                    if (cCharAt == '\'') {
                                        sb.append("'\\''");
                                    } else {
                                        sb.append(cCharAt);
                                    }
                                    i++;
                                }
                                sb.append('\'');
                                String string = sb.toString();
                                gn.c.write(("cd " + string + "\n").getBytes(StandardCharsets.UTF_8));
                                gn.c.flush();
                                i = 1;
                            }
                        }
                        bufferedReader.close();
                        return Integer.valueOf(i);
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
            }
        });
        j.execute(futureTask);
        try {
            try {
                c0516q5.getClass();
                this.a = ((Integer) futureTask.get(20L, TimeUnit.SECONDS)).intValue();
            } catch (InterruptedException e) {
                throw new IOException("Shell check interrupted", e);
            } catch (ExecutionException e2) {
                Throwable cause = e2.getCause();
                if (!(cause instanceof IOException)) {
                    throw new IOException("Unknown ExecutionException", cause);
                }
                throw ((IOException) cause);
            } catch (TimeoutException e3) {
                throw new IOException("Shell check timeout", e3);
            }
        } catch (IOException e4) {
            d();
            throw e4;
        }
    }

    public final synchronized void a(InterfaceC0812zn interfaceC0812zn) {
        if (this.a < 0) {
            interfaceC0812zn.b();
            return;
        }
        Hn.a(this.d);
        Hn.a(this.e);
        try {
            this.c.write(10);
            this.c.flush();
            interfaceC0812zn.a(this.c, this.d, this.e);
        } catch (IOException unused) {
            d();
            interfaceC0812zn.b();
        }
    }

    public final void b(Sj sj) {
        ReentrantLock reentrantLock = this.f;
        reentrantLock.lock();
        try {
            if (this.i) {
                Fn fn = new Fn(reentrantLock.newCondition());
                this.h.offer(fn);
                while (!fn.b) {
                    try {
                        fn.a.await();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            this.i = true;
            reentrantLock.unlock();
            a(sj);
            c(true);
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final InterfaceC0812zn c(boolean z) {
        ReentrantLock reentrantLock = this.f;
        reentrantLock.lock();
        ArrayDeque arrayDeque = this.h;
        try {
            InterfaceC0812zn interfaceC0812zn = (InterfaceC0812zn) arrayDeque.poll();
            if (interfaceC0812zn == null) {
                this.i = false;
                this.g.signalAll();
                reentrantLock.unlock();
                return null;
            }
            if (interfaceC0812zn instanceof Fn) {
                Fn fn = (Fn) interfaceC0812zn;
                fn.b = true;
                fn.a.signal();
                reentrantLock.unlock();
                return null;
            }
            if (!z) {
                reentrantLock.unlock();
                return interfaceC0812zn;
            }
            arrayDeque.offerFirst(interfaceC0812zn);
            reentrantLock.unlock();
            j.execute(new Cn(this, 0));
            return null;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.a < 0) {
            return;
        }
        d();
    }

    public final void d() {
        this.a = -1;
        try {
            this.c.a();
        } catch (IOException unused) {
        }
        try {
            this.e.a();
        } catch (IOException unused2) {
        }
        try {
            this.d.a();
        } catch (IOException unused3) {
        }
        this.b.destroy();
    }
}
