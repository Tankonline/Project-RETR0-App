package rikka.shizuku;

import android.os.Build;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public final class U0 implements Closeable, AutoCloseable {
    public final String a;
    public final int b;
    public final C0235h1 c;
    public Socket d;
    public DataInputStream e;
    public DataOutputStream f;
    public boolean g;
    public SSLSocket h;
    public DataInputStream i;
    public DataOutputStream j;

    public U0(int i, String str, C0235h1 c0235h1) {
        this.a = str;
        this.b = i;
        this.c = c0235h1;
    }

    public static void e(U0 u0, int i, int i2, int i3) throws IOException {
        u0.getClass();
        u0.d(new C0388m1(i, i2, i3, null));
    }

    /* JADX WARN: Type inference failed for: r0v18, types: [java.lang.Object, rikka.shizuku.jg] */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Object, rikka.shizuku.jg] */
    public final void a() throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        String str = this.a;
        int i = this.b;
        Socket socket = new Socket(str, i);
        this.d = socket;
        socket.setTcpNoDelay(true);
        Socket socket2 = this.d;
        if (socket2 == null) {
            socket2 = null;
        }
        this.e = new DataInputStream(socket2.getInputStream());
        Socket socket3 = this.d;
        if (socket3 == null) {
            socket3 = null;
        }
        this.f = new DataOutputStream(socket3.getOutputStream());
        d(new C0388m1(1314410051, 16777216, 4096, "host::".concat("\u0000").getBytes(P5.a)));
        C0388m1 c0388m1B = b();
        C0235h1 c0235h1 = this.c;
        int i2 = c0388m1B.a;
        if (i2 == 1397511251) {
            if (Build.VERSION.SDK_INT < 29) {
                throw new IllegalStateException("Connect to adb with TLS is not supported before Android 9");
            }
            e(this, 1397511251, 16777216, 0);
            SSLSocketFactory socketFactory = ((SSLContext) c0235h1.e.getValue()).getSocketFactory();
            Socket socket4 = this.d;
            if (socket4 == null) {
                socket4 = null;
            }
            SSLSocket sSLSocket = (SSLSocket) socketFactory.createSocket(socket4, str, i, true);
            this.h = sSLSocket;
            if (sSLSocket == null) {
                sSLSocket = null;
            }
            sSLSocket.startHandshake();
            SSLSocket sSLSocket2 = this.h;
            if (sSLSocket2 == null) {
                sSLSocket2 = null;
            }
            this.i = new DataInputStream(sSLSocket2.getInputStream());
            SSLSocket sSLSocket3 = this.h;
            this.j = new DataOutputStream((sSLSocket3 != null ? sSLSocket3 : null).getOutputStream());
            this.g = true;
            c0388m1B = b();
        } else if (i2 == 1213486401) {
            if (i2 != 1213486401 && c0388m1B.b != 1) {
                throw new IllegalStateException("not A_AUTH ADB_AUTH_TOKEN");
            }
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(1, (RSAPrivateKey) c0235h1.a);
            cipher.update(C0235h1.f);
            d(new C0388m1(1213486401, 2, 0, cipher.doFinal(c0388m1B.g)));
            c0388m1B = b();
            if (c0388m1B.a != 1314410051) {
                d(new C0388m1(1213486401, 3, 0, (byte[]) c0235h1.d.getValue()));
                c0388m1B = b();
            }
        }
        if (c0388m1B.a != 1314410051) {
            throw new IllegalStateException("not A_CNXN");
        }
    }

    public final C0388m1 b() throws IOException {
        DataInputStream dataInputStream;
        byte[] bArr;
        DataInputStream dataInputStream2;
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
        DataInputStream dataInputStream3 = null;
        if (!this.g ? (dataInputStream = this.e) == null : (dataInputStream = this.i) == null) {
            dataInputStream = null;
        }
        dataInputStream.readFully(byteBufferOrder.array(), 0, 24);
        int i = byteBufferOrder.getInt();
        int i2 = byteBufferOrder.getInt();
        int i3 = byteBufferOrder.getInt();
        int i4 = byteBufferOrder.getInt();
        int i5 = byteBufferOrder.getInt();
        int i6 = byteBufferOrder.getInt();
        if (i4 >= 0) {
            byte[] bArr2 = new byte[i4];
            if (!this.g ? (dataInputStream2 = this.e) != null : (dataInputStream2 = this.i) != null) {
                dataInputStream3 = dataInputStream2;
            }
            dataInputStream3.readFully(bArr2, 0, i4);
            bArr = bArr2;
        } else {
            bArr = null;
        }
        C0388m1 c0388m1 = new C0388m1(i, i2, i3, i4, i5, i6, bArr);
        if (i == (~i6) && (i4 == 0 || Se.a(bArr) == i5)) {
            c0388m1.a();
            return c0388m1;
        }
        throw new IllegalArgumentException("bad message " + c0388m1.a());
    }

    public final void c(String str, Z0 z0) throws IOException {
        int i;
        int i2;
        d(new C0388m1(1313165391, 1, 0, "shell:".concat(str).concat("\u0000").getBytes(P5.a)));
        C0388m1 c0388m1B = b();
        int i3 = c0388m1B.a;
        if (i3 == 1163086915) {
            e(this, 1163086915, 1, c0388m1B.b);
            return;
        }
        if (i3 != 1497451343) {
            throw new IllegalStateException("not A_OKAY or A_CLSE");
        }
        while (true) {
            C0388m1 c0388m1B2 = b();
            i = c0388m1B2.b;
            i2 = c0388m1B2.a;
            if (i2 != 1163154007) {
                break;
            }
            if (c0388m1B2.d > 0 && z0 != null) {
                z0.g(c0388m1B2.g);
            }
            e(this, 1497451343, 1, i);
        }
        if (i2 != 1163086915) {
            throw new IllegalStateException("not A_WRTE or A_CLSE");
        }
        e(this, 1163086915, 1, i);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        SSLSocket sSLSocket = null;
        try {
            DataInputStream dataInputStream = this.e;
            if (dataInputStream == null) {
                dataInputStream = null;
            }
            dataInputStream.close();
        } catch (Throwable unused) {
        }
        try {
            DataOutputStream dataOutputStream = this.f;
            if (dataOutputStream == null) {
                dataOutputStream = null;
            }
            dataOutputStream.close();
        } catch (Throwable unused2) {
        }
        try {
            Socket socket = this.d;
            if (socket == null) {
                socket = null;
            }
            socket.close();
        } catch (Exception unused3) {
        }
        if (this.g) {
            try {
                DataInputStream dataInputStream2 = this.i;
                if (dataInputStream2 == null) {
                    dataInputStream2 = null;
                }
                dataInputStream2.close();
            } catch (Throwable unused4) {
            }
            try {
                DataOutputStream dataOutputStream2 = this.j;
                if (dataOutputStream2 == null) {
                    dataOutputStream2 = null;
                }
                dataOutputStream2.close();
            } catch (Throwable unused5) {
            }
            try {
                SSLSocket sSLSocket2 = this.h;
                if (sSLSocket2 != null) {
                    sSLSocket = sSLSocket2;
                }
                sSLSocket.close();
            } catch (Exception unused6) {
            }
        }
    }

    public final void d(C0388m1 c0388m1) throws IOException {
        DataOutputStream dataOutputStream;
        DataOutputStream dataOutputStream2;
        DataOutputStream dataOutputStream3 = null;
        if (!this.g ? (dataOutputStream = this.f) == null : (dataOutputStream = this.j) == null) {
            dataOutputStream = null;
        }
        byte[] bArr = c0388m1.g;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((bArr != null ? bArr.length : 0) + 24);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(c0388m1.a);
        byteBufferAllocate.putInt(c0388m1.b);
        byteBufferAllocate.putInt(c0388m1.c);
        byteBufferAllocate.putInt(c0388m1.d);
        byteBufferAllocate.putInt(c0388m1.e);
        byteBufferAllocate.putInt(c0388m1.f);
        if (bArr != null) {
            byteBufferAllocate.put(bArr);
        }
        dataOutputStream.write(byteBufferAllocate.array());
        if (!this.g ? (dataOutputStream2 = this.f) != null : (dataOutputStream2 = this.j) != null) {
            dataOutputStream3 = dataOutputStream2;
        }
        dataOutputStream3.flush();
        c0388m1.a();
    }
}
