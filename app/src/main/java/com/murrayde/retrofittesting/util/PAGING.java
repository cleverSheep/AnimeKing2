package com.murrayde.retrofittesting.util;

public class PAGING {
    public static int PAGING_LIMIT = 20;
    public static int PAGING_OFFSET = 0;
    public static int PAGING_PREFETCH = 10;

    public static int PAGING_OFFSET() {
        PAGING_OFFSET += PAGING_LIMIT;
        return PAGING_OFFSET;
    }
}
