package moe.shizuku.manager.adb;

import android.util.Log;
import com.android.org.conscrypt.Conscrypt;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import rikka.shizuku.AbstractC0484p4;
import rikka.shizuku.C0112d1;
import rikka.shizuku.C0235h1;
import rikka.shizuku.Ij;
import rikka.shizuku.P5;
import rikka.shizuku.Qj;

/* JADX INFO: loaded from: classes.dex */
public final class AdbPairingClient implements Closeable, AutoCloseable {
    public final int a;
    public final String b;
    public final C0235h1 c;
    public Socket d;
    public DataInputStream e;
    public DataOutputStream f;
    public final Qj g;
    public PairingContext h;
    public int i = 1;

    static {
        System.loadLibrary("adb");
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.Object, rikka.shizuku.jg] */
    public AdbPairingClient(int i, String str, C0235h1 c0235h1) {
        this.a = i;
        this.b = str;
        this.c = c0235h1;
        this.g = new Qj((byte) 0, (byte[]) c0235h1.d.getValue());
    }

    public final Ij a() throws IOException {
        byte[] bArr = new byte[6];
        DataInputStream dataInputStream = this.e;
        if (dataInputStream == null) {
            dataInputStream = null;
        }
        dataInputStream.readFully(bArr);
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN);
        byte b = byteBufferOrder.get();
        byte b2 = byteBufferOrder.get();
        int i = byteBufferOrder.getInt();
        if (b < 1 || b > 1) {
            Log.e("AdbPairClient", "PairingPacketHeader version mismatch (us=1 them=" + ((int) b) + ")");
            return null;
        }
        if (b2 != 0 && b2 != 1) {
            Log.e("AdbPairClient", "Unknown PairingPacket type=" + ((int) b2));
            return null;
        }
        if (i > 0 && i <= 16384) {
            Ij ij = new Ij(b, b2, i);
            ij.a();
            return ij;
        }
        Log.e("AdbPairClient", "header payload not within a safe payload size (size=" + i + ")");
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Object, rikka.shizuku.jg] */
    public final boolean b() throws C0112d1, IOException {
        int i = this.a;
        Socket socket = new Socket("127.0.0.1", i);
        this.d = socket;
        socket.setTcpNoDelay(true);
        SSLSocketFactory socketFactory = ((SSLContext) this.c.e.getValue()).getSocketFactory();
        Socket socket2 = this.d;
        if (socket2 == null) {
            socket2 = null;
        }
        SSLSocket sSLSocket = (SSLSocket) socketFactory.createSocket(socket2, "127.0.0.1", i, true);
        sSLSocket.startHandshake();
        this.e = new DataInputStream(sSLSocket.getInputStream());
        this.f = new DataOutputStream(sSLSocket.getOutputStream());
        Charset charset = P5.a;
        String str = this.b;
        byte[] bytes = str.getBytes(charset);
        byte[] bArrExportKeyingMaterial = Conscrypt.exportKeyingMaterial(sSLSocket, "adb-label\u0000", (byte[]) null, 64);
        byte[] bArr = new byte[str.length() + bArrExportKeyingMaterial.length];
        AbstractC0484p4.g0(bytes, bArr, 0, 14);
        AbstractC0484p4.g0(bArrExportKeyingMaterial, bArr, bytes.length, 12);
        long jNativeConstructor = PairingContext.nativeConstructor(true, bArr);
        PairingContext pairingContext = jNativeConstructor != 0 ? new PairingContext(jNativeConstructor) : null;
        if (pairingContext == null) {
            throw new IllegalStateException("Unable to create PairingContext.");
        }
        this.h = pairingContext;
        this.i = 2;
        byte[] bArr2 = pairingContext.b;
        c(new Ij((byte) 1, (byte) 0, bArr2.length), bArr2);
        Ij ijA = a();
        if (ijA != null && ijA.b == 0) {
            byte[] bArr3 = new byte[ijA.c];
            DataInputStream dataInputStream = this.e;
            if (dataInputStream == null) {
                dataInputStream = null;
            }
            dataInputStream.readFully(bArr3);
            PairingContext pairingContext2 = this.h;
            if (pairingContext2 == null) {
                pairingContext2 = null;
            }
            if (pairingContext2.e(bArr3)) {
                this.i = 3;
                ByteBuffer byteBufferOrder = ByteBuffer.allocate(8192).order(ByteOrder.BIG_ENDIAN);
                Qj qj = this.g;
                byteBufferOrder.put(qj.a);
                byteBufferOrder.put(qj.b);
                qj.a();
                PairingContext pairingContext3 = this.h;
                if (pairingContext3 == null) {
                    pairingContext3 = null;
                }
                byte[] bArrD = pairingContext3.d(byteBufferOrder.array());
                if (bArrD != null) {
                    c(new Ij((byte) 1, (byte) 1, bArrD.length), bArrD);
                    Ij ijA2 = a();
                    if (ijA2 != null && ijA2.b == 1) {
                        byte[] bArr4 = new byte[ijA2.c];
                        DataInputStream dataInputStream2 = this.e;
                        if (dataInputStream2 == null) {
                            dataInputStream2 = null;
                        }
                        dataInputStream2.readFully(bArr4);
                        PairingContext pairingContext4 = this.h;
                        byte[] bArrB = (pairingContext4 != null ? pairingContext4 : null).b(bArr4);
                        if (bArrB == null) {
                            throw new C0112d1();
                        }
                        if (bArrB.length == 8192) {
                            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArrB);
                            byteBufferWrap.get();
                            byte[] bArr5 = new byte[8191];
                            byteBufferWrap.get(bArr5);
                            byte[] bArr6 = new byte[8191];
                            System.arraycopy(bArr5, 0, bArr6, 0, 8191);
                            Arrays.toString(bArr6);
                            this.i = 4;
                            return true;
                        }
                        Log.e("AdbPairClient", "Got size=" + bArrB.length + " PeerInfo.size=8192");
                    }
                }
                this.i = 4;
                return false;
            }
        }
        this.i = 4;
        return false;
    }

    public final void c(Ij ij, byte[] bArr) throws IOException {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put(ij.a);
        byteBufferOrder.put(ij.b);
        byteBufferOrder.putInt(ij.c);
        ij.a();
        DataOutputStream dataOutputStream = this.f;
        if (dataOutputStream == null) {
            dataOutputStream = null;
        }
        dataOutputStream.write(byteBufferOrder.array());
        DataOutputStream dataOutputStream2 = this.f;
        (dataOutputStream2 != null ? dataOutputStream2 : null).write(bArr);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
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
        if (this.i != 1) {
            PairingContext pairingContext = this.h;
            (pairingContext != null ? pairingContext : null).c();
        }
    }
}
