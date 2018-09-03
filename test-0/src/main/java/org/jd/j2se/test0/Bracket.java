package org.jd.j2se.test0;

import java.util.Arrays;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Created by cuijiandong on 2018/3/7.
 */
public class Bracket {
    public static void main(String[] aaaaaaaaaaaaaaaa) {
        String bkt = "(){{}}[()[{({})()}]]";
        TreeMap<Character, Character> map = new TreeMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        Stack<Character> st = new Stack<>();
        for (Character c : bkt.toCharArray()) {
            Character d = map.get(c);
            if (d != null) {//c == 左括号
                st.push(c);
            } else {// c == 右括号
                if (st.size() < 1 || map.get(st.pop()) != c) {//栈顶 != 与 c 不匹配
                    System.out.print(false);
                    return;
                }
            }
        }
        System.out.print(true);
    }
}
