package com.alexeiboriskin.walnuttask.utils;

public enum SortField {
    ID("id"),
    READS("reads"),
    LIKES("likes"),
    POPULARITY("popularity");

    public final String label;

    SortField(String value) {
        this.label = value;
    }

    public static SortField fromString(String text) {
        for (SortField f : SortField.values()) {
            if (f.label.equalsIgnoreCase(text)) {
                return f;
            }
        }
        return null;
    }
}
