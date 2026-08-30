package rikka.shizuku;

import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public final enum Ub extends EnumC0061bc {
    public Ub() {
        super("IDENTITY", 0);
    }

    @Override // rikka.shizuku.EnumC0061bc
    public final String b(Field field) {
        return field.getName();
    }
}
