package effectivejava.ch3i8;

/**
 * Created by mac on 15-4-12.
 */

public final class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        if (s == null)
            throw new NullPointerException();
        this.s = s;
    }

    // Broken - violates symmetry!
    @Override public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(
                    ((CaseInsensitiveString) o).s);
        if (o instanceof String)  // One-way interoperability! 不能把String类型的带上
            return s.equalsIgnoreCase((String) o);
        return false;
    }

    //This version is correct.
//   @Override public boolean equals(Object o) {
//       return o instanceof CaseInsensitiveString &&
//           ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
//   }

    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        System.out.println(cis.equals(s) + "  " + s.equals(cis));
    }
}
