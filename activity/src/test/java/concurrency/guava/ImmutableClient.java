package concurrency.guava;

import com.google.common.collect.ImmutableSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ImmutableClient {


    public static void main(String[] args) {

        Set<String> set = new HashSet<>();
        set.add("Java");
        set.add("JEE");
        set.add("Spring");
        set.add("Hibernate");
//        set = Collections.unmodifiableSet(set);
        set = ImmutableSet.copyOf(set);
       Set  set_back = ImmutableSet.of(set.toArray());
        set_back.add("Ajax"); // not allowed.
    }
}

