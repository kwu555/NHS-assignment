package nhs;

@CheckRegularAmount
public class RegularAmount {
    private Frequency frequency;
    private String amount;

    public RegularAmount(final Frequency frequency, final String amount){
        this.frequency = frequency;
        this.amount = amount;
    }

    public Frequency getFrequency() {
        return frequency;
    }
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
