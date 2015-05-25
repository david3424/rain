package org.david.java.basetype;

import java.util.Random;

/**
 * Created by mac on 15-5-25.
 */
public class RandomClient {


    public static void main(String[] args) {
        Random r = new Random(1000);//能不指定就不指定，指定后每次执行的结果是一样的了

        for (int i = 0; i <4 ; i++) {
            System.out.println(r.nextInt());
        }
                
    }
}
