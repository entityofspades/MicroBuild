package org.example.microbuild;

public class GPU extends Component {
    private final int powerDrawWatts;
    private final int lengthMM;
    private final int recommendedPsuWattage;

    public GPU(String id, String name, double price, int powerDrawWatts,
               int lengthMM, int recommendedPsuWattage) {
        super(id, name, price);
        this.powerDrawWatts = powerDrawWatts;
        this.lengthMM = lengthMM;
        this.recommendedPsuWattage = recommendedPsuWattage;
    }

    public int getLengthMM() { return lengthMM; }
    public int getRecommendedPsuWattage() { return recommendedPsuWattage; }

    @Override
    public int getPowerDrawWatts() { return powerDrawWatts; }

    @Override
    public String getCategory() { return "GPU"; }
}
