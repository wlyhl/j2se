package org.jd.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cuijiandong on 2018/5/10.
 */
public class JCollection {
    public static <E> Set<E> asSet(E... sth) {
        Set<E> set = new HashSet<>(sth.length);
        set.addAll(Arrays.asList(sth));
        return set;
    }
}
