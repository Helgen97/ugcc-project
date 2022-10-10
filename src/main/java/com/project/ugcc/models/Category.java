package com.project.ugcc.models;

public enum Category {
    NEWS("NEWS"),
    DOCUMENTS("DOCUMENTS"),
    MEDIA("MEDIA"),
    ARTICLES("ARTICLES");

    Category(String news) {
    }

    @Override
    public String toString() {
        return this.name();
    }
}
