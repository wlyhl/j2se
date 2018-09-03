package org.jd.j2se.test0;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by cuijiandong on 2018/3/29.
 */
public class Java {
    public static void main(String []a){
        ArrayList<Integer> list=new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> i = list.iterator();
        Integer next = i.next();
        i.remove();
        Integer next1 = i.next();
        System.out.println(list);
    }
}
