package org.example.microbuild;

public class RAM extends Component {
    private final String type;
    private final int capacityGB;
    private final int speedMHz;

    public RAM(String id, String name, double price, String type, int capacityGB, int speedMHz) {
        super(id, name, price);
        this.type = type;
        this.capacityGB = capacityGB;
        this.speedMHz = speedMHz;
    }

    public String getType() { return type; }
    public int getCapacityGB() { return capacityGB; }
    public int getSpeedMHz() { return speedMHz; }

    @Override
    public int getPowerDrawWatts() { return 5; }

    @Override
    public String getCategory() { return "RAM"; }
}
