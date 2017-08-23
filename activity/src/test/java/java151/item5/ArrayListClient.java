package java151.item5;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ArrayListClient {


    public static void main(String[] args) {


        List<Integer> sizeList = new ArrayList<>();
        sizeList.toArray();
        sizeList.add(12);
        System.out.println(sizeList.size());
    }
}

