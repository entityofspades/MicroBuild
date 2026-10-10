package org.example.microbuild;

public class PowerCalculator {

    private static final double HEADROOM_MARGIN = 1.30; // 30% safety headroom

    public static int calculateTotalLoadWatts(BuildConfiguration build) {
        int total = 0;
        for (Component c : build.getAllComponents()) {
            total += c.getPowerDrawWatts();
        }
        return total;
    }

    public static int calculateRecommendedPsuWattage(BuildConfiguration build) {
        int rawLoad = calculateTotalLoadWatts(build);
        int recommended = (int) Math.ceil(rawLoad * HEADROOM_MARGIN);
        // round up to nearest 50W step, common PSU increment
        return ((recommended + 49) / 50) * 50;
    }
}
