package org.example.microbuild;

public class Motherboard extends Component {
    private final String socket;
    private final String ramType;
    private final int maxRamGB;
    private final String formFactor;

    public Motherboard(String id, String name, double price, String socket,
                       String ramType, int maxRamGB, String formFactor) {
        super(id, name, price);
        this.socket = socket;
        this.ramType = ramType;
        this.maxRamGB = maxRamGB;
        this.formFactor = formFactor;
    }

    public String getSocket() { return socket; }
    public String getRamType() { return ramType; }
    public int getMaxRamGB() { return maxRamGB; }
    public String getFormFactor() { return formFactor; }

    @Override
    public int getPowerDrawWatts() { return 40; }

    @Override
    public String getCategory() { return "Motherboard"; }
}
