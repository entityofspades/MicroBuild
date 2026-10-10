package org.example.microbuild;

public class CaseComponent extends Component {
    private final String formFactorSupport;
    private final int maxGpuLengthMM;

    public CaseComponent(String id, String name, double price,
                         String formFactorSupport, int maxGpuLengthMM) {
        super(id, name, price);
        this.formFactorSupport = formFactorSupport;
        this.maxGpuLengthMM = maxGpuLengthMM;
    }

    public String getFormFactorSupport() { return formFactorSupport; }
    public int getMaxGpuLengthMM() { return maxGpuLengthMM; }

    @Override
    public int getPowerDrawWatts() { return 0; }

    @Override
    public String getCategory() { return "Case"; }
}
