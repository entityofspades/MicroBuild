package org.example.microbuild;

public class CPU extends Component {
    private final String socket;
    private final int coreCount;
    private final int tdpWatts;

    public CPU(String id, String name, double price, String socket, int coreCount, int tdpWatts) {
        super(id, name, price);
        this.socket = socket;
        this.coreCount = coreCount;
        this.tdpWatts = tdpWatts;
    }

    public String getSocket() { return socket; }
    public int getCoreCount() { return coreCount; }

    @Override
    public int getPowerDrawWatts() { return tdpWatts; }

    @Override
    public String getCategory() { return "CPU"; }


}

