package com.zy.demo.rocketmq.common.constant;

public class PermName {
    private static final int PERM_PRIORITY = 0x1 << 3;
    private static final int PERM_READ = 0x1 << 2;
    private static final int PERM_WRITE = 0x1 << 1;
    private static final int PERM_INHERIT = 0x1 << 0;

    public static String perm2String(final int perm){
        //线程安全的
        final StringBuffer stringBuffer = new StringBuffer("---");
        if (isReadable(perm)){
            stringBuffer.replace(0, 1, "R");
        }

        if (isWriteable(perm)){
            stringBuffer.replace(1, 2, "W");
        }

        if (isInherited(perm)){
            stringBuffer.replace(2, 3, "X");
        }
        return stringBuffer.toString();
    }

    public static boolean isReadable(final int perm){
        return (perm & PERM_READ) == PERM_READ;
    }

    public static boolean isWriteable(final int perm){
        return (perm & PERM_WRITE) == PERM_WRITE;
    }

    public static boolean isInherited(final int perm){
        return (perm & PERM_INHERIT) == PERM_INHERIT;
    }
}
