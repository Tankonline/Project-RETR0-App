package rikka.shizuku;

import java.lang.reflect.Field;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.ac, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final enum C0030ac extends EnumC0061bc {
    public C0030ac() {
        super("LOWER_CASE_WITH_DOTS", 6);
    }

    @Override // rikka.shizuku.EnumC0061bc
    public final String b(Field field) {
        return EnumC0061bc.a(field.getName(), '.').toLowerCase(Locale.ENGLISH);
    }
}
