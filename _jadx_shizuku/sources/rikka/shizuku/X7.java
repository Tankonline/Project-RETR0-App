package rikka.shizuku;

import java.util.Arrays;
import java.util.List;
import java.util.ServiceConfigurationError;

/* JADX INFO: loaded from: classes.dex */
public abstract class X7 {
    public static final List a;

    static {
        try {
            a = Tm.b0(Tm.a0(Arrays.asList(new P1()).iterator()));
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }
}
