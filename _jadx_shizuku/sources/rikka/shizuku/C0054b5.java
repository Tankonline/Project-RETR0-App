package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.b5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0054b5 {
    public static final String b;
    public static final String c;
    public static final C0054b5 d;
    public static final C0054b5 e;
    public final boolean a;

    static {
        C0330k5 c0330k5 = Dp.c;
        b = Character.toString((char) 8206);
        c = Character.toString((char) 8207);
        d = new C0054b5(false);
        e = new C0054b5(true);
    }

    public C0054b5(boolean z) {
        C0330k5 c0330k5 = Dp.a;
        this.a = z;
    }

    public static int a(String str) {
        byte directionality;
        C0023a5 c0023a5 = new C0023a5(str);
        c0023a5.c = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = c0023a5.c;
            if (i4 < c0023a5.b && i == 0) {
                String str2 = c0023a5.a;
                char cCharAt = str2.charAt(i4);
                c0023a5.d = cCharAt;
                if (Character.isHighSurrogate(cCharAt)) {
                    int iCodePointAt = Character.codePointAt(str2, c0023a5.c);
                    c0023a5.c = Character.charCount(iCodePointAt) + c0023a5.c;
                    directionality = Character.getDirectionality(iCodePointAt);
                } else {
                    c0023a5.c++;
                    char c2 = c0023a5.d;
                    directionality = c2 < 1792 ? C0023a5.e[c2] : Character.getDirectionality(c2);
                }
                if (directionality != 0) {
                    if (directionality == 1 || directionality == 2) {
                        if (i3 == 0) {
                            return 1;
                        }
                    } else if (directionality != 9) {
                        switch (directionality) {
                            case 14:
                            case 15:
                                i3++;
                                i2 = -1;
                                continue;
                            case 16:
                            case 17:
                                i3++;
                                i2 = 1;
                                continue;
                            case 18:
                                i3--;
                                i2 = 0;
                                continue;
                        }
                    }
                } else if (i3 == 0) {
                    return -1;
                }
                i = i3;
            }
        }
        if (i != 0) {
            if (i2 == 0) {
                while (c0023a5.c > 0) {
                    switch (c0023a5.a()) {
                        case 14:
                        case 15:
                            if (i == i3) {
                                return -1;
                            }
                            i3--;
                            break;
                        case 16:
                        case 17:
                            if (i == i3) {
                                return 1;
                            }
                            i3--;
                            break;
                        case 18:
                            i3++;
                            break;
                        default:
                            break;
                    }
                }
            } else {
                return i2;
            }
        }
        return 0;
    }

    public static int b(String str) {
        C0023a5 c0023a5 = new C0023a5(str);
        c0023a5.c = c0023a5.b;
        int i = 0;
        while (true) {
            int i2 = i;
            while (c0023a5.c > 0) {
                byte bA = c0023a5.a();
                if (bA == 0) {
                    if (i == 0) {
                        return -1;
                    }
                    if (i2 == 0) {
                    }
                } else if (bA == 1 || bA == 2) {
                    if (i == 0) {
                        return 1;
                    }
                    if (i2 == 0) {
                    }
                } else if (bA != 9) {
                    switch (bA) {
                        case 14:
                        case 15:
                            if (i2 == i) {
                                return -1;
                            }
                            i--;
                            break;
                        case 16:
                        case 17:
                            if (i2 == i) {
                                return 1;
                            }
                            i--;
                            break;
                        case 18:
                            i++;
                            break;
                        default:
                            if (i2 != 0) {
                            }
                            break;
                    }
                } else {
                    continue;
                }
            }
            return 0;
        }
    }
}
