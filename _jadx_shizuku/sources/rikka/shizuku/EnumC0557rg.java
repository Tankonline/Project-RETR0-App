package rikka.shizuku;

import rikka.html.text.style.BulletSpan;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: rikka.shizuku.rg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class EnumC0557rg {
    private static final /* synthetic */ InterfaceC0522qb $ENTRIES;
    private static final /* synthetic */ EnumC0557rg[] $VALUES;
    public static final C0496pg Companion;
    public static final EnumC0557rg ON_ANY;
    public static final EnumC0557rg ON_CREATE;
    public static final EnumC0557rg ON_DESTROY;
    public static final EnumC0557rg ON_PAUSE;
    public static final EnumC0557rg ON_RESUME;
    public static final EnumC0557rg ON_START;
    public static final EnumC0557rg ON_STOP;

    static {
        EnumC0557rg enumC0557rg = new EnumC0557rg("ON_CREATE", 0);
        ON_CREATE = enumC0557rg;
        EnumC0557rg enumC0557rg2 = new EnumC0557rg("ON_START", 1);
        ON_START = enumC0557rg2;
        EnumC0557rg enumC0557rg3 = new EnumC0557rg("ON_RESUME", 2);
        ON_RESUME = enumC0557rg3;
        EnumC0557rg enumC0557rg4 = new EnumC0557rg("ON_PAUSE", 3);
        ON_PAUSE = enumC0557rg4;
        EnumC0557rg enumC0557rg5 = new EnumC0557rg("ON_STOP", 4);
        ON_STOP = enumC0557rg5;
        EnumC0557rg enumC0557rg6 = new EnumC0557rg("ON_DESTROY", 5);
        ON_DESTROY = enumC0557rg6;
        EnumC0557rg enumC0557rg7 = new EnumC0557rg("ON_ANY", 6);
        ON_ANY = enumC0557rg7;
        EnumC0557rg[] enumC0557rgArr = {enumC0557rg, enumC0557rg2, enumC0557rg3, enumC0557rg4, enumC0557rg5, enumC0557rg6, enumC0557rg7};
        $VALUES = enumC0557rgArr;
        $ENTRIES = new C0552rb(enumC0557rgArr);
        Companion = new C0496pg();
    }

    public static EnumC0557rg[] b() {
        return (EnumC0557rg[]) $VALUES.clone();
    }

    public final EnumC0588sg a() {
        switch (AbstractC0527qg.a[ordinal()]) {
            case 1:
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return EnumC0588sg.c;
            case 3:
            case 4:
                return EnumC0588sg.d;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return EnumC0588sg.e;
            case 6:
                return EnumC0588sg.a;
            case 7:
                throw new IllegalArgumentException(this + " has no target state");
            default:
                throw new K6();
        }
    }
}
