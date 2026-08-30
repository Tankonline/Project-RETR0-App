package rikka.shizuku;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public final class Uf extends Pf {
    public final Serializable a;

    public Uf(Boolean bool) {
        this.a = bool;
    }

    public static boolean d(Uf uf) {
        Serializable serializable = uf.a;
        if (!(serializable instanceof Number)) {
            return false;
        }
        Number number = (Number) serializable;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    public final BigInteger a() {
        Serializable serializable = this.a;
        if (serializable instanceof BigInteger) {
            return (BigInteger) serializable;
        }
        if (d(this)) {
            return BigInteger.valueOf(b().longValue());
        }
        String strC = c();
        X8.l(strC);
        return new BigInteger(strC);
    }

    public final Number b() {
        Serializable serializable = this.a;
        if (serializable instanceof Number) {
            return (Number) serializable;
        }
        if (serializable instanceof String) {
            return new C0280ig((String) serializable);
        }
        throw new UnsupportedOperationException("Primitive is neither a number nor a string");
    }

    public final String c() {
        Serializable serializable = this.a;
        if (serializable instanceof String) {
            return (String) serializable;
        }
        if (serializable instanceof Number) {
            return b().toString();
        }
        if (serializable instanceof Boolean) {
            return ((Boolean) serializable).toString();
        }
        throw new AssertionError("Unexpected value type: " + serializable.getClass());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Uf.class != obj.getClass()) {
            return false;
        }
        Uf uf = (Uf) obj;
        Serializable serializable = this.a;
        Serializable serializable2 = uf.a;
        if (serializable == null) {
            return serializable2 == null;
        }
        if (d(this) && d(uf)) {
            if ((serializable instanceof BigInteger) || (serializable2 instanceof BigInteger)) {
                return a().equals(uf.a());
            }
            return b().longValue() == uf.b().longValue();
        }
        if (!(serializable instanceof Number) || !(serializable2 instanceof Number)) {
            return serializable.equals(serializable2);
        }
        if ((serializable instanceof BigDecimal) && (serializable2 instanceof BigDecimal)) {
            return (serializable instanceof BigDecimal ? (BigDecimal) serializable : X8.N(c())).compareTo(serializable2 instanceof BigDecimal ? (BigDecimal) serializable2 : X8.N(uf.c())) == 0;
        }
        double dDoubleValue = serializable instanceof Number ? b().doubleValue() : Double.parseDouble(c());
        double dDoubleValue2 = serializable2 instanceof Number ? uf.b().doubleValue() : Double.parseDouble(uf.c());
        if (dDoubleValue != dDoubleValue2) {
            return Double.isNaN(dDoubleValue) && Double.isNaN(dDoubleValue2);
        }
        return true;
    }

    public final int hashCode() {
        long jDoubleToLongBits;
        Serializable serializable = this.a;
        if (serializable == null) {
            return 31;
        }
        if (d(this)) {
            jDoubleToLongBits = b().longValue();
        } else {
            if (!(serializable instanceof Number)) {
                return serializable.hashCode();
            }
            jDoubleToLongBits = Double.doubleToLongBits(b().doubleValue());
        }
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    public Uf(Number number) {
        this.a = number;
    }

    public Uf(String str) {
        this.a = str;
    }
}
