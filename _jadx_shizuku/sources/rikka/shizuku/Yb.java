package rikka.shizuku;

import java.lang.reflect.Field;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final enum Yb extends EnumC0061bc {
    public Yb() {
        super("LOWER_CASE_WITH_UNDERSCORES", 4);
    }

    @Override // rikka.shizuku.EnumC0061bc
    public final String b(Field field) {
        return EnumC0061bc.a(field.getName(), '_').toLowerCase(Locale.ENGLISH);
    }
}
