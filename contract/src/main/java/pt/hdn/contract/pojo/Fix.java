package pt.hdn.contract.pojo;

public class Fix extends Shell {

    private Double fix;

    public Double getFix() {
        return fix;
    }

    public void setFix(Double fix) {
        this.fix = fix;
    }

    @Override
    public boolean isValid(){
        return fix != null && fix > 0;
    }
}
