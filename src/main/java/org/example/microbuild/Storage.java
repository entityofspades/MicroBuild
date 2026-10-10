package org.example.microbuild;


public class Storage extends Component {
    private final String type;
    private final int capacityGB;
    private final String interfaceType;

    public Storage(String id, String name, double price, String type,
                   int capacityGB, String interfaceType) {
        super(id, name, price);
        this.type = type;
        this.capacityGB = capacityGB;
        this.interfaceType = interfaceType;
    }

    public String getType() { return type; }
    public int getCapacityGB() { return capacityGB; }
    public String getInterfaceType() { return interfaceType; }

    @Override
    public int getPowerDrawWatts() { return type.equalsIgnoreCase("HDD") ? 8 : 4; }

    @Override
    public String getCategory() { return "Storage"; }
}
