package java151.item11;

import java.util.HashSet;

/**
 * Created by xdw9486 on 2017/7/26.
 * 不可变对象 内容不可变 final修饰
 */
public class StringHolder {

    private String string;

    public StringHolder(String s) {
        this.string = s;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o == null || !(o instanceof StringHolder))
            return false;
        else {
            final StringHolder other = (StringHolder) o;
            if (string == null)
                return (other.string == null);
            else
                return string.equals(other.string);
        }
    }

    public int hashCode() {
        return (string != null ? string.hashCode() : 0);
    }

    public String toString() {
        return string;
    }

    public static void main(String[] args) {
        StringHolder sh = new StringHolder("blert");
        HashSet<StringHolder> h = new HashSet<>();
        h.add(sh);
        System.out.println(h.contains(sh));
        sh.setString("moo");
        System.out.println(h.contains(sh));
        System.out.println(h.size());
        System.out.println(h.iterator().next());
    }
}
