package rikka.shizuku;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class Jj extends Thread {
    public final InputStream a;
    public final OutputStream b;

    public Jj(InputStream inputStream, OutputStream outputStream) {
        super("ParcelFileDescriptor Transfer Thread");
        this.a = inputStream;
        this.b = outputStream;
        setDaemon(true);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        InputStream inputStream = this.a;
        OutputStream outputStream = this.b;
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                try {
                    try {
                        int i = inputStream.read(bArr);
                        if (i > 0) {
                            outputStream.write(bArr, 0, i);
                            outputStream.flush();
                        } else {
                            try {
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e2) {
                        Log.e("TransferThread", Log.getStackTraceString(e2));
                        try {
                            inputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                        outputStream.close();
                        return;
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return;
                }
            } catch (Throwable th) {
                inputStream.close();
                outputStream.close();
                throw th;
            }
            try {
                inputStream.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            try {
                outputStream.close();
                throw th;
            } catch (IOException e6) {
                e6.printStackTrace();
                throw th;
            }
        }
        inputStream.close();
        outputStream.close();
    }
}
