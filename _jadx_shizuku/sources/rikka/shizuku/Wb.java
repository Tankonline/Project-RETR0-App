package rikka.shizuku;

import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public final enum Wb extends EnumC0061bc {
    public Wb() {
        super("UPPER_CAMEL_CASE_WITH_SPACES", 2);
    }

    @Override // rikka.shizuku.EnumC0061bc
    public final String b(Field field) {
        return EnumC0061bc.c(EnumC0061bc.a(field.getName(), ' '));
    }
}
