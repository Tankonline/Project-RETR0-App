package rikka.shizuku;

import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public final enum Vb extends EnumC0061bc {
    public Vb() {
        super("UPPER_CAMEL_CASE", 1);
    }

    @Override // rikka.shizuku.EnumC0061bc
    public final String b(Field field) {
        return EnumC0061bc.c(field.getName());
    }
}
