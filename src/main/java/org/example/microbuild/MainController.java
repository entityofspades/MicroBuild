package org.example.microbuild;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.example.microbuild.*;

public class MainController {

    // --- FXML Components ---
    @FXML private ComboBox<CPU> cpuComboBox;
    @FXML private ComboBox<Motherboard> motherboardComboBox;
    @FXML private ComboBox<RAM> ramComboBox;
    @FXML private ComboBox<GPU> gpuComboBox;
    @FXML private ComboBox<PSU> psuComboBox;
    @FXML private ComboBox<Storage> storageComboBox;
    @FXML private ComboBox<CaseComponent> caseComboBox;

    @FXML private Label totalPriceLabel;
    @FXML private Label powerDrawLabel;
    @FXML private Label recommendedPsuLabel;
    @FXML private TextArea quoteOutputTextArea;

    // --- Backend Model Objects ---
    private InventoryManager inventory;
    private BuildConfiguration currentBuild;
    private CompatibilityChecker compatibilityChecker;
    private QuoteGenerator quoteGenerator;

    @FXML
    public void initialize() {
        // 1. Initialize Backend Objects
        inventory = seedInventory();
        currentBuild = new BuildConfiguration("My Custom PC");
        compatibilityChecker = new CompatibilityChecker();
        quoteGenerator = new QuoteGenerator();

        // 2. Populate ComboBoxes with Components
        populateComboBoxes();

        // 3. Add Listeners to update build when user changes selection
        setupSelectionListeners();
    }

    private void populateComboBoxes() {
        for (Component c : inventory.getCatalog().values()) {
            if (c instanceof CPU) {
                cpuComboBox.getItems().add((CPU) c);
            } else if (c instanceof Motherboard) {
                motherboardComboBox.getItems().add((Motherboard) c);
            } else if (c instanceof RAM) {
                ramComboBox.getItems().add((RAM) c);
            } else if (c instanceof GPU) {
                gpuComboBox.getItems().add((GPU) c);
            } else if (c instanceof PSU) {
                psuComboBox.getItems().add((PSU) c);
            } else if (c instanceof Storage) {
                storageComboBox.getItems().add((Storage) c);
            } else if (c instanceof CaseComponent) {
                caseComboBox.getItems().add((CaseComponent) c);
            }
        }
    }

    private void setupSelectionListeners() {
        cpuComboBox.setOnAction(e -> {
            currentBuild.setCpu(cpuComboBox.getValue());
            updateSummary();
        });

        motherboardComboBox.setOnAction(e -> {
            currentBuild.setMotherboard(motherboardComboBox.getValue());
            updateSummary();
        });

        ramComboBox.setOnAction(e -> {
            currentBuild.setRam(ramComboBox.getValue());
            updateSummary();
        });

        gpuComboBox.setOnAction(e -> {
            currentBuild.setGpu(gpuComboBox.getValue());
            updateSummary();
        });

        psuComboBox.setOnAction(e -> {
            currentBuild.setPsu(psuComboBox.getValue());
            updateSummary();
        });

        storageComboBox.setOnAction(e -> {
            if (storageComboBox.getValue() != null) {
                currentBuild.getStorageDrives().clear(); // o pwede ding i-append
                currentBuild.addStorage(storageComboBox.getValue());
            }
            updateSummary();
        });

        caseComboBox.setOnAction(e -> {
            currentBuild.setPcCase(caseComboBox.getValue());
            updateSummary();
        });
    }

    private void updateSummary() {
        // Total Price
        totalPriceLabel.setText(String.format("Total Price: PHP %.2f", currentBuild.getTotalPrice()));

        // Power Calculation
        int totalWatts = PowerCalculator.calculateTotalLoadWatts(currentBuild);
        int recommendedWatts = PowerCalculator.calculateRecommendedPsuWattage(currentBuild);
        powerDrawLabel.setText("Estimated Load: " + totalWatts + " W");
        recommendedPsuLabel.setText("Recommended PSU: " + recommendedWatts + " W");

        // Live Compatibility Check
        var issues = compatibilityChecker.check(currentBuild);
        if (issues.isEmpty()) {
            quoteOutputTextArea.setText("Status: PASSED - All selected parts are compatible!");
        } else {
            StringBuilder sb = new StringBuilder("Status: FAILED - " + issues.size() + " issue(s) found:\n");
            for (String issue : issues) {
                sb.append("• ").append(issue).append("\n");
            }
            quoteOutputTextArea.setText(sb.toString());
        }
    }

    @FXML
    private void handleClearBuild() {
        cpuComboBox.setValue(null);
        motherboardComboBox.setValue(null);
        ramComboBox.setValue(null);
        gpuComboBox.setValue(null);
        psuComboBox.setValue(null);
        storageComboBox.setValue(null);
        caseComboBox.setValue(null);

        currentBuild = new BuildConfiguration("My Custom PC");
        updateSummary();
    }

    @FXML
    private void handleGenerateQuote() {
        // Displays full quotation string from QuoteGenerator
        String fullQuote = quoteGenerator.generateQuote(currentBuild);
        quoteOutputTextArea.setText(fullQuote);
    }

    // Kinopya mula sa Main.java mo para magkaroon ng initial items:
    private InventoryManager seedInventory() {
        InventoryManager inv = new InventoryManager();

        inv.addComponent(new CPU("CPU1", "AMD Ryzen 5 7600", 12500, "AM5", 6, 65), 10);
        inv.addComponent(new CPU("CPU2", "Intel Core i5-13400F", 10500, "LGA1700", 10, 65), 8);

        inv.addComponent(new Motherboard("MB1", "MSI B650M PRO", 7500, "AM5", "DDR5", 128, "mATX"), 5);
        inv.addComponent(new Motherboard("MB2", "ASUS Prime B760M", 6800, "LGA1700", "DDR4", 128, "mATX"), 5);

        inv.addComponent(new RAM("RAM1", "Kingston Fury 16GB DDR5 6000", 3200, "DDR5", 16, 6000), 15);
        inv.addComponent(new RAM("RAM2", "Corsair Vengeance 16GB DDR4 3200", 2400, "DDR4", 16, 3200), 15);

        inv.addComponent(new GPU("GPU1", "NVIDIA RTX 4060", 22000, 200, 285, 550), 6);
        inv.addComponent(new GPU("GPU2", "AMD RX 7600", 19500, 165, 280, 550), 6);

        inv.addComponent(new PSU("PSU1", "Cooler Master 550W Bronze", 3500, 550, "ATX"), 10);
        inv.addComponent(new PSU("PSU2", "Seasonic 650W Gold", 5200, 650, "ATX"), 10);

        inv.addComponent(new Storage("SSD1", "Samsung 980 1TB NVMe", 4200, "SSD", 1000, "NVMe"), 20);
        inv.addComponent(new Storage("HDD1", "Seagate Barracuda 2TB", 2600, "HDD", 2000, "SATA"), 12);

        inv.addComponent(new CaseComponent("CASE1", "NZXT H510", 4800, "ATX,mATX,ITX", 300), 7);
        inv.addComponent(new CaseComponent("CASE2", "Cooler Master MasterBox Q300L", 2900, "mATX,ITX", 360), 7);

        return inv;
    }
}