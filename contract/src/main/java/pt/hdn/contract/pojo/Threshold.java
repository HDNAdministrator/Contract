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

    public boolean hasPositiveThreshold(){
        return positiveThreshold != null;
    }

    public Double getPositiveThreshold() {
        return positiveThreshold;
    }

    public void setPositiveThreshold(Double positiveThreshold) {
        this.positiveThreshold = positiveThreshold;
    }

    public boolean hasNegativeThreshold(){
        return negativeThreshold != null;
    }

    public Double getNegativeThreshold() {
        return negativeThreshold;
    }

    public void setNegativeThreshold(Double negativeThreshold) {
        this.negativeThreshold = negativeThreshold;
    }

    @Override
    public boolean isValid() {
        if(bonus == null) {
            return false;
        } else if(bonus < 0) {
            return false;
        } else if((positiveThreshold == null) == (negativeThreshold == null)) {
            return false;
        } else if(positiveThreshold != null && positiveThreshold < 0) {
            return false;
        } else if(negativeThreshold != null && negativeThreshold < 0) {
            return false;
        } else {
            return true;
        }
    }
}
