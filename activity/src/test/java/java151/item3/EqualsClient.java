package java151.item3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class EqualsClient {


    public static void main(String[] args) {
        PersonNew p1 = new PersonNew("张三");
        PersonNew p2 = new PersonNew("张三 ");

        List<PersonNew> l = new ArrayList<>();
        l.add(p1);
        l.add(p2);
        System.out.println("p1/p2 equal?：" + p2.equals(p2));
        System.out.println("列表中是否包含张三：" + l.contains(p1));
        System.out.println("列表中是否包含张三 ：" + l.contains(p2));
    }
}

class PersonNew {
    private String name;

    public PersonNew(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonNew) {
            PersonNew p = (PersonNew) obj;
            return name.equalsIgnoreCase(p.getName().trim());//trim 违反了自反性
        }
        return false;
    }
}


