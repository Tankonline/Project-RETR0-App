package rikka.shizuku;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class Pg extends AbstractMap implements Serializable {
    public static final Q7 i = new Q7(2);
    public final Comparator a;
    public final boolean b;
    public Og c;
    public int d;
    public int e;
    public final Og f;
    public Ng g;
    public Ng h;

    public Pg(boolean z) {
        Q7 q7 = i;
        this.d = 0;
        this.e = 0;
        this.a = q7;
        this.b = z;
        this.f = new Og(z);
    }

    public final Og a(Object obj, boolean z) {
        int iCompareTo;
        Og og;
        Og og2 = this.c;
        Q7 q7 = i;
        Comparator comparator = this.a;
        if (og2 != null) {
            Comparable comparable = comparator == q7 ? (Comparable) obj : null;
            while (true) {
                Object obj2 = og2.f;
                iCompareTo = comparable != null ? comparable.compareTo(obj2) : comparator.compare(obj, obj2);
                if (iCompareTo == 0) {
                    return og2;
                }
                Og og3 = iCompareTo < 0 ? og2.b : og2.c;
                if (og3 == null) {
                    break;
                }
                og2 = og3;
            }
        } else {
            iCompareTo = 0;
        }
        Og og4 = og2;
        if (!z) {
            return null;
        }
        Og og5 = this.f;
        if (og4 != null) {
            og = new Og(this.b, og4, obj, og5, og5.e);
            if (iCompareTo < 0) {
                og4.b = og;
            } else {
                og4.c = og;
            }
            b(og4, true);
        } else {
            if (comparator == q7 && !(obj instanceof Comparable)) {
                throw new ClassCastException(obj.getClass().getName().concat(" is not Comparable"));
            }
            og = new Og(this.b, og4, obj, og5, og5.e);
            this.c = og;
        }
        this.d++;
        this.e++;
        return og;
    }

    public final void b(Og og, boolean z) {
        while (og != null) {
            Og og2 = og.b;
            Og og3 = og.c;
            int i2 = og2 != null ? og2.i : 0;
            int i3 = og3 != null ? og3.i : 0;
            int i4 = i2 - i3;
            if (i4 == -2) {
                Og og4 = og3.b;
                Og og5 = og3.c;
                int i5 = (og4 != null ? og4.i : 0) - (og5 != null ? og5.i : 0);
                if (i5 == -1 || (i5 == 0 && !z)) {
                    e(og);
                } else {
                    f(og3);
                    e(og);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 2) {
                Og og6 = og2.b;
                Og og7 = og2.c;
                int i6 = (og6 != null ? og6.i : 0) - (og7 != null ? og7.i : 0);
                if (i6 == 1 || (i6 == 0 && !z)) {
                    f(og);
                } else {
                    e(og2);
                    f(og);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 0) {
                og.i = i2 + 1;
                if (z) {
                    return;
                }
            } else {
                og.i = Math.max(i2, i3) + 1;
                if (!z) {
                    return;
                }
            }
            og = og.a;
        }
    }

    public final void c(Og og, boolean z) {
        Og og2;
        Og og3;
        int i2;
        if (z) {
            Og og4 = og.e;
            og4.d = og.d;
            og.d.e = og4;
        }
        Og og5 = og.b;
        Og og6 = og.c;
        Og og7 = og.a;
        int i3 = 0;
        if (og5 == null || og6 == null) {
            if (og5 != null) {
                d(og, og5);
                og.b = null;
            } else if (og6 != null) {
                d(og, og6);
                og.c = null;
            } else {
                d(og, null);
            }
            b(og7, false);
            this.d--;
            this.e++;
            return;
        }
        if (og5.i > og6.i) {
            Og og8 = og5.c;
            while (true) {
                Og og9 = og8;
                og3 = og5;
                og5 = og9;
                if (og5 == null) {
                    break;
                } else {
                    og8 = og5.c;
                }
            }
        } else {
            Og og10 = og6.b;
            while (true) {
                og2 = og6;
                og6 = og10;
                if (og6 == null) {
                    break;
                } else {
                    og10 = og6.b;
                }
            }
            og3 = og2;
        }
        c(og3, false);
        Og og11 = og.b;
        if (og11 != null) {
            i2 = og11.i;
            og3.b = og11;
            og11.a = og3;
            og.b = null;
        } else {
            i2 = 0;
        }
        Og og12 = og.c;
        if (og12 != null) {
            i3 = og12.i;
            og3.c = og12;
            og12.a = og3;
            og.c = null;
        }
        og3.i = Math.max(i2, i3) + 1;
        d(og, og3);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.c = null;
        this.d = 0;
        this.e++;
        Og og = this.f;
        og.e = og;
        og.d = og;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        Og ogA = null;
        if (obj != null) {
            try {
                ogA = a(obj, false);
            } catch (ClassCastException unused) {
            }
        }
        return ogA != null;
    }

    public final void d(Og og, Og og2) {
        Og og3 = og.a;
        og.a = null;
        if (og2 != null) {
            og2.a = og3;
        }
        if (og3 == null) {
            this.c = og2;
        } else if (og3.b == og) {
            og3.b = og2;
        } else {
            og3.c = og2;
        }
    }

    public final void e(Og og) {
        Og og2 = og.b;
        Og og3 = og.c;
        Og og4 = og3.b;
        Og og5 = og3.c;
        og.c = og4;
        if (og4 != null) {
            og4.a = og;
        }
        d(og, og3);
        og3.b = og;
        og.a = og3;
        int iMax = Math.max(og2 != null ? og2.i : 0, og4 != null ? og4.i : 0) + 1;
        og.i = iMax;
        og3.i = Math.max(iMax, og5 != null ? og5.i : 0) + 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        Ng ng = this.g;
        if (ng != null) {
            return ng;
        }
        Ng ng2 = new Ng(this, 0);
        this.g = ng2;
        return ng2;
    }

    public final void f(Og og) {
        Og og2 = og.b;
        Og og3 = og.c;
        Og og4 = og2.b;
        Og og5 = og2.c;
        og.b = og5;
        if (og5 != null) {
            og5.a = og;
        }
        d(og, og2);
        og2.c = og;
        og.a = og2;
        int iMax = Math.max(og3 != null ? og3.i : 0, og5 != null ? og5.i : 0) + 1;
        og.i = iMax;
        og2.i = Math.max(iMax, og4 != null ? og4.i : 0) + 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        Og ogA;
        if (obj != null) {
            try {
                ogA = a(obj, false);
            } catch (ClassCastException unused) {
                ogA = null;
            }
        } else {
            ogA = null;
        }
        if (ogA != null) {
            return ogA.h;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        Ng ng = this.h;
        if (ng != null) {
            return ng;
        }
        Ng ng2 = new Ng(this, 1);
        this.h = ng2;
        return ng2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        if (obj2 == null && !this.b) {
            throw new NullPointerException("value == null");
        }
        Og ogA = a(obj, true);
        Object obj3 = ogA.h;
        ogA.h = obj2;
        return obj3;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        Og ogA;
        if (obj != null) {
            try {
                ogA = a(obj, false);
            } catch (ClassCastException unused) {
                ogA = null;
            }
        } else {
            ogA = null;
        }
        if (ogA != null) {
            c(ogA, true);
        }
        if (ogA != null) {
            return ogA.h;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.d;
    }
}
