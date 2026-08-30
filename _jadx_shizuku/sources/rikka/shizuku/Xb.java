package rikka.shizuku;

import java.lang.reflect.Field;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final enum Xb extends EnumC0061bc {
    public Xb() {
        super("UPPER_CASE_WITH_UNDERSCORES", 3);
    }

    @Override // rikka.shizuku.EnumC0061bc
    public final String b(Field field) {
        return EnumC0061bc.a(field.getName(), '_').toUpperCase(Locale.ENGLISH);
    }
}
