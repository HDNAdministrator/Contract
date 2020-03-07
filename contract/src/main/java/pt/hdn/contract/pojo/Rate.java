package pt.hdn.contract.pojo;

public class Rate extends Shell {

    private Double rate;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public boolean isValid() {
        return rate != null && rate > 0;
    }
}
