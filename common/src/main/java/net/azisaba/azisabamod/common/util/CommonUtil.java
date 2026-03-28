package net.azisaba.azisabamod.common.util;

import org.jetbrains.annotations.NotNull;

public class CommonUtil {
    public static String @NotNull [] dropFirst(String @NotNull [] array) {
        if (array.length == 1) return new String[0];
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, newArray.length);
        return newArray;
    }
}
