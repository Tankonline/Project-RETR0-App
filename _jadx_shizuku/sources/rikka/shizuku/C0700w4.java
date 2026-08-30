package rikka.shizuku;

import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: renamed from: rikka.shizuku.w4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0700w4 extends AbstractC0744xh {
    public static final Hashtable A;
    public static final C0700w4 B;
    public static final C0510q s;
    public static final C0510q t;
    public static final C0510q u;
    public static final C0510q v;
    public static final C0510q w;
    public static final C0510q x;
    public static final C0510q y;
    public static final Hashtable z;
    public final Hashtable q;
    public final Hashtable r;

    static {
        C0510q c0510qF = AbstractC0535qo.f("2.5.4.6");
        s = c0510qF;
        C0510q c0510qF2 = AbstractC0535qo.f("2.5.4.10");
        C0510q c0510qF3 = AbstractC0535qo.f("2.5.4.11");
        C0510q c0510qF4 = AbstractC0535qo.f("2.5.4.12");
        C0510q c0510qF5 = AbstractC0535qo.f("2.5.4.3");
        t = AbstractC0535qo.f("2.5.4.5");
        C0510q c0510qF6 = AbstractC0535qo.f("2.5.4.9");
        C0510q c0510qF7 = AbstractC0535qo.f("2.5.4.5");
        C0510q c0510qF8 = AbstractC0535qo.f("2.5.4.7");
        C0510q c0510qF9 = AbstractC0535qo.f("2.5.4.8");
        C0510q c0510qF10 = AbstractC0535qo.f("2.5.4.4");
        C0510q c0510qF11 = AbstractC0535qo.f("2.5.4.42");
        C0510q c0510qF12 = AbstractC0535qo.f("2.5.4.43");
        C0510q c0510qF13 = AbstractC0535qo.f("2.5.4.44");
        C0510q c0510qF14 = AbstractC0535qo.f("2.5.4.45");
        C0510q c0510qF15 = AbstractC0535qo.f("2.5.4.13");
        C0510q c0510qF16 = AbstractC0535qo.f("2.5.4.15");
        C0510q c0510qF17 = AbstractC0535qo.f("2.5.4.17");
        C0510q c0510qF18 = AbstractC0535qo.f("2.5.4.46");
        u = c0510qF18;
        C0510q c0510qF19 = AbstractC0535qo.f("2.5.4.65");
        C0510q c0510qF20 = AbstractC0535qo.f("2.5.4.72");
        C0510q c0510qF21 = AbstractC0535qo.f("1.3.6.1.5.5.7.9.1");
        v = c0510qF21;
        C0510q c0510qF22 = AbstractC0535qo.f("1.3.6.1.5.5.7.9.2");
        C0510q c0510qF23 = AbstractC0535qo.f("1.3.6.1.5.5.7.9.3");
        C0510q c0510qF24 = AbstractC0535qo.f("1.3.6.1.5.5.7.9.4");
        C0510q c0510qF25 = AbstractC0535qo.f("1.3.6.1.5.5.7.9.5");
        C0510q c0510qF26 = AbstractC0535qo.f("1.3.36.8.3.14");
        C0510q c0510qF27 = AbstractC0535qo.f("2.5.4.16");
        new C0510q("2.5.4.54").u();
        C0510q c0510q = nu.a;
        w = c0510q;
        C0510q c0510q2 = nu.b;
        C0510q c0510q3 = nu.c;
        C0510q c0510q4 = Ej.s;
        x = c0510q4;
        C0510q c0510q5 = Ej.t;
        C0510q c0510q6 = Ej.u;
        C0510q c0510q7 = new C0510q("0.9.2342.19200300.100.1.25");
        y = c0510q7;
        C0510q c0510q8 = new C0510q("0.9.2342.19200300.100.1.1");
        Hashtable hashtable = new Hashtable();
        z = hashtable;
        Hashtable hashtable2 = new Hashtable();
        A = hashtable2;
        hashtable.put(c0510qF, "C");
        hashtable.put(c0510qF2, "O");
        hashtable.put(c0510qF4, "T");
        hashtable.put(c0510qF3, "OU");
        hashtable.put(c0510qF5, "CN");
        hashtable.put(c0510qF8, "L");
        hashtable.put(c0510qF9, "ST");
        hashtable.put(c0510qF7, "SERIALNUMBER");
        hashtable.put(c0510q4, "E");
        hashtable.put(c0510q7, "DC");
        hashtable.put(c0510q8, "UID");
        hashtable.put(c0510qF6, "STREET");
        hashtable.put(c0510qF10, "SURNAME");
        hashtable.put(c0510qF11, "GIVENNAME");
        hashtable.put(c0510qF12, "INITIALS");
        hashtable.put(c0510qF13, "GENERATION");
        hashtable.put(c0510qF15, "DESCRIPTION");
        hashtable.put(c0510qF20, "ROLE");
        hashtable.put(c0510q6, "unstructuredAddress");
        hashtable.put(c0510q5, "unstructuredName");
        hashtable.put(c0510qF14, "UniqueIdentifier");
        hashtable.put(c0510qF18, "DN");
        hashtable.put(c0510qF19, "Pseudonym");
        hashtable.put(c0510qF27, "PostalAddress");
        hashtable.put(c0510qF26, "NameAtBirth");
        hashtable.put(c0510qF24, "CountryOfCitizenship");
        hashtable.put(c0510qF25, "CountryOfResidence");
        hashtable.put(c0510qF23, "Gender");
        hashtable.put(c0510qF22, "PlaceOfBirth");
        hashtable.put(c0510qF21, "DateOfBirth");
        hashtable.put(c0510qF17, "PostalCode");
        hashtable.put(c0510qF16, "BusinessCategory");
        hashtable.put(c0510q, "TelephoneNumber");
        hashtable.put(c0510q2, "Name");
        hashtable.put(c0510q3, "organizationIdentifier");
        hashtable2.put("c", c0510qF);
        hashtable2.put("o", c0510qF2);
        hashtable2.put("t", c0510qF4);
        hashtable2.put("ou", c0510qF3);
        hashtable2.put("cn", c0510qF5);
        hashtable2.put("l", c0510qF8);
        hashtable2.put("st", c0510qF9);
        hashtable2.put("sn", c0510qF10);
        hashtable2.put("serialnumber", c0510qF7);
        hashtable2.put("street", c0510qF6);
        hashtable2.put("emailaddress", c0510q4);
        hashtable2.put("dc", c0510q7);
        hashtable2.put("e", c0510q4);
        hashtable2.put("uid", c0510q8);
        hashtable2.put("surname", c0510qF10);
        hashtable2.put("givenname", c0510qF11);
        hashtable2.put("initials", c0510qF12);
        hashtable2.put("generation", c0510qF13);
        hashtable2.put("description", c0510qF15);
        hashtable2.put("role", c0510qF20);
        hashtable2.put("unstructuredaddress", c0510q6);
        hashtable2.put("unstructuredname", c0510q5);
        hashtable2.put("uniqueidentifier", c0510qF14);
        hashtable2.put("dn", c0510qF18);
        hashtable2.put("pseudonym", c0510qF19);
        hashtable2.put("postaladdress", c0510qF27);
        hashtable2.put("nameatbirth", c0510qF26);
        hashtable2.put("countryofcitizenship", c0510qF24);
        hashtable2.put("countryofresidence", c0510qF25);
        hashtable2.put("gender", c0510qF23);
        hashtable2.put("placeofbirth", c0510qF22);
        hashtable2.put("dateofbirth", c0510qF21);
        hashtable2.put("postalcode", c0510qF17);
        hashtable2.put("businesscategory", c0510qF16);
        hashtable2.put("telephonenumber", c0510q);
        hashtable2.put("name", c0510q2);
        hashtable2.put("organizationidentifier", c0510q3);
        B = new C0700w4();
    }

    public C0700w4() {
        Hashtable hashtable = z;
        Hashtable hashtable2 = new Hashtable();
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            hashtable2.put(objNextElement, hashtable.get(objNextElement));
        }
        this.r = hashtable2;
        Hashtable hashtable3 = A;
        Hashtable hashtable4 = new Hashtable();
        Enumeration enumerationKeys2 = hashtable3.keys();
        while (enumerationKeys2.hasMoreElements()) {
            Object objNextElement2 = enumerationKeys2.nextElement();
            hashtable4.put(objNextElement2, hashtable3.get(objNextElement2));
        }
        this.q = hashtable4;
    }

    public final C0510q d0(String str) {
        Hashtable hashtable = this.q;
        if (Wo.e(str).startsWith("OID.")) {
            return new C0510q(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new C0510q(str);
        }
        C0510q c0510q = (C0510q) hashtable.get(Wo.c(str));
        if (c0510q != null) {
            return c0510q;
        }
        throw new IllegalArgumentException(AbstractC0535qo.d("Unknown object id - ", str, " - passed to distinguished name"));
    }
}
