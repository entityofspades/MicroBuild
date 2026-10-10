package org.example.microbuild;

import java.util.ArrayList;
import java.util.List;

public class CompatibilityChecker {

    public List<String> check(BuildConfiguration build) {
        List<String> issues = new ArrayList<>();

        CPU cpu = build.getCpu();
        Motherboard mobo = build.getMotherboard();
        RAM ram = build.getRam();
        GPU gpu = build.getGpu();
        PSU psu = build.getPsu();
        CaseComponent pcCase = build.getPcCase();

        if (cpu != null && mobo != null && !cpu.getSocket().equalsIgnoreCase(mobo.getSocket())) {
            issues.add("CPU socket (" + cpu.getSocket() + ") does not match motherboard socket ("
                    + mobo.getSocket() + ").");
        }

        if (ram != null && mobo != null && !ram.getType().equalsIgnoreCase(mobo.getRamType())) {
            issues.add("RAM type (" + ram.getType() + ") is not supported by motherboard ("
                    + mobo.getRamType() + " required).");
        }

        if (ram != null && mobo != null && ram.getCapacityGB() > mobo.getMaxRamGB()) {
            issues.add("RAM capacity (" + ram.getCapacityGB() + "GB) exceeds motherboard max ("
                    + mobo.getMaxRamGB() + "GB).");
        }

        if (gpu != null && pcCase != null && gpu.getLengthMM() > pcCase.getMaxGpuLengthMM()) {
            issues.add("GPU length (" + gpu.getLengthMM() + "mm) exceeds case clearance ("
                    + pcCase.getMaxGpuLengthMM() + "mm).");
        }

        if (mobo != null && pcCase != null
                && !pcCase.getFormFactorSupport().toUpperCase().contains(mobo.getFormFactor().toUpperCase())) {
            issues.add("Motherboard form factor (" + mobo.getFormFactor()
                    + ") is not supported by case (" + pcCase.getFormFactorSupport() + ").");
        }

        if (psu != null) {
            int totalDraw = PowerCalculator.calculateTotalLoadWatts(build);
            int recommended = PowerCalculator.calculateRecommendedPsuWattage(build);
            if (psu.getWattage() < recommended) {
                issues.add("PSU wattage (" + psu.getWattage() + "W) is below the recommended "
                        + recommended + "W for this build (raw load ~" + totalDraw + "W).");
            }
        }

        return issues;
    }

    public boolean isCompatible(BuildConfiguration build) {
        return check(build).isEmpty();
    }
}
