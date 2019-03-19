package com.zy;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int claories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int claories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.claories = claories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getClaories() {
        return claories;
    }

    public Type getType() {
        return type;
    }

    public enum Type{
        MEAT,
        FISH,
        OTHER;
    }
}
