package rikka.shizuku;

import java.lang.reflect.Field;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final enum Zb extends EnumC0061bc {
    public Zb() {
        super("LOWER_CASE_WITH_DASHES", 5);
    }

    @Override // rikka.shizuku.EnumC0061bc
    public final String b(Field field) {
        return EnumC0061bc.a(field.getName(), '-').toLowerCase(Locale.ENGLISH);
    }
}
