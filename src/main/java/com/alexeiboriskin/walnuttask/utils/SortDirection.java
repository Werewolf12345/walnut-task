package com.alexeiboriskin.walnuttask.utils;

public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    public final String label;

    SortDirection(String value) {
        this.label = value;
    }

    public static SortDirection fromString(String text) {
        for (SortDirection d : SortDirection.values()) {
            if (d.label.equalsIgnoreCase(text)) {
                return d;
            }
        }
        return null;
    }
}
