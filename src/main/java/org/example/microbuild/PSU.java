package org.example.microbuild;

public class PSU extends Component {
    private final int wattage;
    private final String formFactor;

    public PSU(String id, String name, double price, int wattage, String formFactor) {
        super(id, name, price);
        this.wattage = wattage;
        this.formFactor = formFactor;
    }

    public int getWattage() { return wattage; }
    public String getFormFactor() { return formFactor; }

    @Override
    public int getPowerDrawWatts() { return 0; }

    @Override
    public String getCategory() { return "PSU"; }
}
