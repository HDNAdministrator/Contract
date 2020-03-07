package pt.hdn.contract.pojo;

public class Objective extends Shell {

    private Double bonus;
    private Double lowerBound;
    private Double upperBound;

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public boolean hasLowerBound(){
        return lowerBound != null;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public boolean hasUpperBound(){
        return upperBound != null;
    }

    public Double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Double upperBound) {
        this.upperBound = upperBound;
    }

    @Override
    boolean isValid() {
        if(bonus == null) {
            return false;
        } else if(bonus <= 0 || bonus > 0) {
            return false;
        } else if(lowerBound != null && lowerBound < 0) {
            return false;
        } else if(upperBound != null && upperBound < 0) {
            return false;
        } else if(lowerBound != null && upperBound != null && upperBound <= lowerBound) {
            return false;
        } else {
            return true;
        }
    }
}
