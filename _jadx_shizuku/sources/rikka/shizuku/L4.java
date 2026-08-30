package rikka.shizuku;

import android.util.Log;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class L4 implements Nc {
    public final ArrayList a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public boolean g;
    public boolean h;
    public String i;
    public int j;
    public CharSequence k;
    public int l;
    public CharSequence m;
    public ArrayList n;
    public ArrayList o;
    public boolean p;
    public ArrayList q;
    public final Qc r;
    public boolean s;
    public int t;

    public L4(Qc qc) {
        qc.G();
        Cc cc = qc.w;
        if (cc != null) {
            cc.j.getClassLoader();
        }
        this.a = new ArrayList();
        this.h = true;
        this.p = false;
        this.t = -1;
        this.r = qc;
    }

    @Override // rikka.shizuku.Nc
    public final boolean a(ArrayList arrayList, ArrayList arrayList2) {
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(Boolean.FALSE);
        if (!this.g) {
            return true;
        }
        this.r.d.add(this);
        return true;
    }

    public final void b(C0062bd c0062bd) {
        this.a.add(c0062bd);
        c0062bd.d = this.b;
        c0062bd.e = this.c;
        c0062bd.f = this.d;
        c0062bd.g = this.e;
    }

    public final void c(int i) {
        if (this.g) {
            if (Qc.J(2)) {
                Log.v("FragmentManager", "Bump nesting in " + this + " by " + i);
            }
            ArrayList arrayList = this.a;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0062bd c0062bd = (C0062bd) arrayList.get(i2);
                Ac ac = c0062bd.b;
                if (ac != null) {
                    ac.s += i;
                    if (Qc.J(2)) {
                        Log.v("FragmentManager", "Bump nesting of " + c0062bd.b + " to " + c0062bd.b.s);
                    }
                }
            }
        }
    }

    public final void d() {
        ArrayList arrayList = this.a;
        int size = arrayList.size() - 1;
        while (size >= 0) {
            C0062bd c0062bd = (C0062bd) arrayList.get(size);
            if (c0062bd.c) {
                if (c0062bd.a == 8) {
                    c0062bd.c = false;
                    arrayList.remove(size - 1);
                    size--;
                } else {
                    int i = c0062bd.b.y;
                    c0062bd.a = 2;
                    c0062bd.c = false;
                    for (int i2 = size - 1; i2 >= 0; i2--) {
                        C0062bd c0062bd2 = (C0062bd) arrayList.get(i2);
                        if (c0062bd2.c && c0062bd2.b.y == i) {
                            arrayList.remove(i2);
                            size--;
                        }
                    }
                }
            }
            size--;
        }
    }

    public final int e(boolean z, boolean z2) {
        if (this.s) {
            throw new IllegalStateException("commit already called");
        }
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Commit: " + this);
            PrintWriter printWriter = new PrintWriter(new C0558rh());
            g("  ", printWriter, true);
            printWriter.close();
        }
        this.s = true;
        boolean z3 = this.g;
        Qc qc = this.r;
        if (z3) {
            this.t = qc.k.getAndIncrement();
        } else {
            this.t = -1;
        }
        if (z2) {
            qc.x(this, z);
        }
        return this.t;
    }

    public final void f(int i, Ac ac, String str, int i2) {
        String str2 = ac.N;
        if (str2 != null) {
            AbstractC0031ad.c(ac, str2);
        }
        Class<?> cls = ac.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (str != null) {
            String str3 = ac.z;
            if (str3 != null && !str.equals(str3)) {
                throw new IllegalStateException("Can't change tag of fragment " + ac + ": was " + ac.z + " now " + str);
            }
            ac.z = str;
        }
        if (i != 0) {
            if (i == -1) {
                throw new IllegalArgumentException("Can't add fragment " + ac + " with tag " + str + " to container view with no id");
            }
            int i3 = ac.x;
            if (i3 != 0 && i3 != i) {
                throw new IllegalStateException("Can't change container ID of fragment " + ac + ": was " + ac.x + " now " + i);
            }
            ac.x = i;
            ac.y = i;
        }
        b(new C0062bd(i2, ac));
        ac.t = this.r;
    }

    public final void g(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.i);
            printWriter.print(" mIndex=");
            printWriter.print(this.t);
            printWriter.print(" mCommitted=");
            printWriter.println(this.s);
            if (this.f != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.f));
            }
            if (this.b != 0 || this.c != 0) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.b));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.c));
            }
            if (this.d != 0 || this.e != 0) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.d));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.e));
            }
            if (this.j != 0 || this.k != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.j));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.k);
            }
            if (this.l != 0 || this.m != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.l));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.m);
            }
        }
        ArrayList arrayList = this.a;
        if (arrayList.isEmpty()) {
            return;
        }
        printWriter.print(str);
        printWriter.println("Operations:");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            C0062bd c0062bd = (C0062bd) arrayList.get(i);
            switch (c0062bd.a) {
                case 0:
                    str2 = "NULL";
                    break;
                case 1:
                    str2 = "ADD";
                    break;
                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    str2 = "REPLACE";
                    break;
                case 3:
                    str2 = "REMOVE";
                    break;
                case 4:
                    str2 = "HIDE";
                    break;
                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                    str2 = "SHOW";
                    break;
                case 6:
                    str2 = "DETACH";
                    break;
                case 7:
                    str2 = "ATTACH";
                    break;
                case 8:
                    str2 = "SET_PRIMARY_NAV";
                    break;
                case 9:
                    str2 = "UNSET_PRIMARY_NAV";
                    break;
                case 10:
                    str2 = "OP_SET_MAX_LIFECYCLE";
                    break;
                default:
                    str2 = "cmd=" + c0062bd.a;
                    break;
            }
            printWriter.print(str);
            printWriter.print("  Op #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(str2);
            printWriter.print(" ");
            printWriter.println(c0062bd.b);
            if (z) {
                if (c0062bd.d != 0 || c0062bd.e != 0) {
                    printWriter.print(str);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(c0062bd.d));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(c0062bd.e));
                }
                if (c0062bd.f != 0 || c0062bd.g != 0) {
                    printWriter.print(str);
                    printWriter.print("popEnterAnim=#");
                    printWriter.print(Integer.toHexString(c0062bd.f));
                    printWriter.print(" popExitAnim=#");
                    printWriter.println(Integer.toHexString(c0062bd.g));
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.t >= 0) {
            sb.append(" #");
            sb.append(this.t);
        }
        if (this.i != null) {
            sb.append(" ");
            sb.append(this.i);
        }
        sb.append("}");
        return sb.toString();
    }
}
