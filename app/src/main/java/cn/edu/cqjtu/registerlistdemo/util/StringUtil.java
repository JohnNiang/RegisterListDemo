package cn.edu.cqjtu.registerlistdemo.util;

import java.util.List;

/**
 * Created by JohnNiang on 2016/11/2.
 */

public class StringUtil {

    private StringUtil() {

    }

    public static String[] listToArray(List<String> list) {
        String[] arrays = null;
        if (list != null) {
            arrays = new String[list.size()];
            for (int i = 0; i < arrays.length; i++) {
                arrays[i] = list.get(i);
            }
        }
        return arrays;
    }
}
