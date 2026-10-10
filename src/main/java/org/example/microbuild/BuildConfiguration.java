package org.example.microbuild;

import java.util.ArrayList;
import java.util.List;

public class BuildConfiguration {
    private String buildName;
    private CPU cpu;
    private Motherboard motherboard;
    private RAM ram;
    private GPU gpu;
    private PSU psu;
    private final List<Storage> storageDrives = new ArrayList<>();
    private CaseComponent pcCase;

    public BuildConfiguration(String buildName) {
        this.buildName = buildName;
    }

    public String getBuildName() { return buildName; }

    public CPU getCpu() { return cpu; }
    public void setCpu(CPU cpu) { this.cpu = cpu; }

    public Motherboard getMotherboard() { return motherboard; }
    public void setMotherboard(Motherboard motherboard) { this.motherboard = motherboard; }

    public RAM getRam() { return ram; }
    public void setRam(RAM ram) { this.ram = ram; }

    public GPU getGpu() { return gpu; }
    public void setGpu(GPU gpu) { this.gpu = gpu; }

    public PSU getPsu() { return psu; }
    public void setPsu(PSU psu) { this.psu = psu; }

    public CaseComponent getPcCase() { return pcCase; }
    public void setPcCase(CaseComponent pcCase) { this.pcCase = pcCase; }

    public void addStorage(Storage storage) { storageDrives.add(storage); }
    public List<Storage> getStorageDrives() { return storageDrives; }

    public List<Component> getAllComponents() {
        List<Component> all = new ArrayList<>();
        if (cpu != null) all.add(cpu);
        if (motherboard != null) all.add(motherboard);
        if (ram != null) all.add(ram);
        if (gpu != null) all.add(gpu);
        if (psu != null) all.add(psu);
        if (pcCase != null) all.add(pcCase);
        all.addAll(storageDrives);
        return all;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Component c : getAllComponents()) {
            total += c.getPrice();
        }
        return total;
    }
}
