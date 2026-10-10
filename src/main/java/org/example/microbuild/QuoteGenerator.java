package org.example.microbuild;

import java.util.List;

public class QuoteGenerator {
    private final CompatibilityChecker checker = new CompatibilityChecker();

    public String generateQuote(BuildConfiguration build) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append(" MicroBuild Quotation - ").append(build.getBuildName()).append("\n");
        sb.append("=========================================\n");

        for (Component c : build.getAllComponents()) {
            sb.append(String.format("%-14s %-30s PHP %10.2f%n",
                    c.getCategory(), c.getName(), c.getPrice()));
        }

        sb.append("-----------------------------------------\n");
        sb.append(String.format("%-45s PHP %10.2f%n", "TOTAL", build.getTotalPrice()));
        sb.append("-----------------------------------------\n");

        int load = PowerCalculator.calculateTotalLoadWatts(build);
        int recommended = PowerCalculator.calculateRecommendedPsuWattage(build);
        sb.append("Estimated power draw: ").append(load).append(" W\n");
        sb.append("Recommended PSU wattage: ").append(recommended).append(" W\n");

        List<String> issues = checker.check(build);
        sb.append("-----------------------------------------\n");
        if (issues.isEmpty()) {
            sb.append("Compatibility check: PASSED - all parts are compatible.\n");
        } else {
            sb.append("Compatibility check: FAILED - ").append(issues.size()).append(" issue(s):\n");
            for (String issue : issues) {
                sb.append("  - ").append(issue).append("\n");
            }
        }
        sb.append("=========================================\n");
        return sb.toString();
    }
}
