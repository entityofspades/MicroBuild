package org.example.microbuild;

public abstract class Component {
    private final String id;
    private final String name;
    private final double price;

    public Component(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public abstract int getPowerDrawWatts();
    public abstract String getCategory();

    @Override
    public String toString() {
        return String.format("[%s] %s - PHP %.2f", getCategory(), name, price);
    }
}
