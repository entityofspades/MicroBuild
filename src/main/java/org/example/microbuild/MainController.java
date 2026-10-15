package org.example.microbuild;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    // --- FXML Components ---
    @FXML private TextField searchBarTextField;
    @FXML private ListView<CPU> cpuListView;
    @FXML private ListView<Motherboard> motherboardListView;
    @FXML private ListView<RAM> ramListView;
    @FXML private ListView<GPU> gpuListView;
    @FXML private ListView<PSU> psuListView;
    @FXML private ListView<Storage> storageListView;
    @FXML private ListView<CaseComponent> caseListView;

    @FXML private Label totalPriceLabel;
    @FXML private Label powerDrawLabel;
    @FXML private Label recommendedPsuLabel;
    @FXML private Label quoteOutputTextArea; // Status Label sa ibaba

    // --- Backend Model Objects ---
    private InventoryManager inventory;
    private BuildConfiguration currentBuild;
    private CompatibilityChecker compatibilityChecker;
    private QuoteGenerator quoteGenerator;

    // Master lists para sa search filtering
    private ObservableList<CPU> allCpus = FXCollections.observableArrayList();
    private ObservableList<Motherboard> allMotherboards = FXCollections.observableArrayList();
    private ObservableList<RAM> allRams = FXCollections.observableArrayList();
    private ObservableList<GPU> allGpus = FXCollections.observableArrayList();
    private ObservableList<PSU> allPsus = FXCollections.observableArrayList();
    private ObservableList<Storage> allStorages = FXCollections.observableArrayList();
    private ObservableList<CaseComponent> allCases = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        inventory = seedInventory();
        currentBuild = new BuildConfiguration("My Custom PC");
        compatibilityChecker = new CompatibilityChecker();
        quoteGenerator = new QuoteGenerator();

        loadInventoryToLists();
        setupSelectionListeners();
        setupSearchFilter();
    }

    private void loadInventoryToLists() {
        for (Component c : inventory.getCatalog().values()) {
            if (c instanceof CPU) allCpus.add((CPU) c);
            else if (c instanceof Motherboard) allMotherboards.add((Motherboard) c);
            else if (c instanceof RAM) allRams.add((RAM) c);
            else if (c instanceof GPU) allGpus.add((GPU) c);
            else if (c instanceof PSU) allPsus.add((PSU) c);
            else if (c instanceof Storage) allStorages.add((Storage) c);
            else if (c instanceof CaseComponent) allCases.add((CaseComponent) c);
        }

        cpuListView.setItems(allCpus);
        motherboardListView.setItems(allMotherboards);
        ramListView.setItems(allRams);
        gpuListView.setItems(allGpus);
        psuListView.setItems(allPsus);
        storageListView.setItems(allStorages);
        caseListView.setItems(allCases);
    }

    private void setupSelectionListeners() {
        cpuListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            currentBuild.setCpu(newVal);
            updateSummary();
        });

        motherboardListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            currentBuild.setMotherboard(newVal);
            updateSummary();
        });

        ramListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            currentBuild.setRam(newVal);
            updateSummary();
        });

        gpuListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            currentBuild.setGpu(newVal);
            updateSummary();
        });

        psuListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            currentBuild.setPsu(newVal);
            updateSummary();
        });

        storageListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentBuild.getStorageDrives().clear();
                currentBuild.addStorage(newVal);
                updateSummary();
            }
        });

        caseListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            currentBuild.setPcCase(newVal);
            updateSummary();
        });
    }

    private void setupSearchFilter() {
        searchBarTextField.textProperty().addListener((obs, oldText, newText) -> {
            String query = newText == null ? "" : newText.toLowerCase();

            cpuListView.setItems(allCpus.filtered(c -> c.getName().toLowerCase().contains(query) || c.getId().toLowerCase().contains(query)));
            motherboardListView.setItems(allMotherboards.filtered(m -> m.getName().toLowerCase().contains(query) || m.getId().toLowerCase().contains(query)));
            ramListView.setItems(allRams.filtered(r -> r.getName().toLowerCase().contains(query) || r.getId().toLowerCase().contains(query)));
            gpuListView.setItems(allGpus.filtered(g -> g.getName().toLowerCase().contains(query) || g.getId().toLowerCase().contains(query)));
            psuListView.setItems(allPsus.filtered(p -> p.getName().toLowerCase().contains(query) || p.getId().toLowerCase().contains(query)));
            storageListView.setItems(allStorages.filtered(s -> s.getName().toLowerCase().contains(query) || s.getId().toLowerCase().contains(query)));
            caseListView.setItems(allCases.filtered(cs -> cs.getName().toLowerCase().contains(query) || cs.getId().toLowerCase().contains(query)));
        });
    }

    private void updateSummary() {
        totalPriceLabel.setText(String.format("Total Price: PHP %.2f", currentBuild.getTotalPrice()));

        int totalWatts = PowerCalculator.calculateTotalLoadWatts(currentBuild);
        int recommendedWatts = PowerCalculator.calculateRecommendedPsuWattage(currentBuild);
        powerDrawLabel.setText("Estimated Load: " + totalWatts + " W");
        recommendedPsuLabel.setText("Recommended PSU: " + recommendedWatts + " W");

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
        cpuListView.getSelectionModel().clearSelection();
        motherboardListView.getSelectionModel().clearSelection();
        ramListView.getSelectionModel().clearSelection();
        gpuListView.getSelectionModel().clearSelection();
        psuListView.getSelectionModel().clearSelection();
        storageListView.getSelectionModel().clearSelection();
        caseListView.getSelectionModel().clearSelection();

        currentBuild = new BuildConfiguration("My Custom PC");
        updateSummary();
    }

    @FXML
    private void handleGenerateQuote() {
        String fullQuote = quoteGenerator.generateQuote(currentBuild);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quote-view.fxml"));
            Parent root = loader.load();

            QuoteController quoteController = loader.getController();
            quoteController.setQuoteText(fullQuote);

            Stage quoteStage = new Stage();
            quoteStage.setTitle("MicroBuild PC - Generated Quote");
            quoteStage.setScene(new Scene(root, 600, 500));
            quoteStage.initModality(Modality.APPLICATION_MODAL);
            quoteStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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