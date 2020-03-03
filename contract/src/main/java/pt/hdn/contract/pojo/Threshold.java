package pt.hdn.contract.pojo;

public class Threshold extends Shell {

    private Double bonus;
    private Double positiveThreshold;
    private Double negativeThreshold;

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getPositiveThreshold() {
        return positiveThreshold;
    }

    public void setPositiveThreshold(Double positiveThreshold) {
        this.positiveThreshold = positiveThreshold;
    }

    public Double getNegativeThreshold() {
        return negativeThreshold;
    }

    public void setNegativeThreshold(Double negativeThreshold) {
        this.negativeThreshold = negativeThreshold;
    }
}
