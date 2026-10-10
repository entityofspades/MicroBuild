package org.example.microbuild;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private final Map<String, Component> catalog = new HashMap<>();
    private final Map<String, Integer> stock = new HashMap<>();

    public void addComponent(Component component, int quantity) {
        catalog.put(component.getId(), component);
        stock.put(component.getId(), quantity);
    }

    public Component getComponent(String id) {
        return catalog.get(id);
    }

    public int getStock(String id) {
        return stock.getOrDefault(id, 0);
    }

    public boolean isInStock(String id) {
        return getStock(id) > 0;
    }

    public boolean reserve(String id, int quantity) {
        int current = getStock(id);
        if (current < quantity) return false;
        stock.put(id, current - quantity);
        return true;
    }

    public void restock(String id, int quantity) {
        stock.put(id, getStock(id) + quantity);
    }

    public Map<String, Component> getCatalog() {
        return catalog;
    }
}
