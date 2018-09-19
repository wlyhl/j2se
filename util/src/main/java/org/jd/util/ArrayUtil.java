package org.jd.util;

/**
 * Created by cuijiandong on 2018/9/2.
 */
public class ArrayUtil {
    public static int indexOf(byte[] arr, byte b, int startIndex, int endIndex) {
        for (; startIndex <= endIndex; startIndex++) {
            if (arr[startIndex] == b)
                return startIndex;
        }
        return -1;
    }
//    public static boolean exists(byte[] arr, byte b, int start, int length) {
//        for (int end = start + length; start < end; start++) {
//            if (arr[start] == b)
//                return true;
//        }
//        return false;
//    }
}
