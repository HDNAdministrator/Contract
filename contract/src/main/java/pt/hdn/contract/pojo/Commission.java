package pt.hdn.contract.pojo;

public class Commission extends Shell {

    private Double cut;
    private Double lowerBound;
    private Double upperBound;

    public Double getCut() {
        return cut;
    }

    public void setCut(Double cut) {
        this.cut = cut;
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
        if(cut == null) {
            return false;
        } else if(cut <= 0 || cut > 0) {
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
