package ru.parma.mirs.loader.model;

public class LoaderFormDto {

    private boolean doLoadAccounting;
    private boolean doLoadGenericIndicators;
    private boolean doLoadHrmIndicators;
    private boolean doLoadHrmIndicatorsCorp;
    private boolean doLoadDaily;
    private boolean doLoadStat;
    private String endPeriod;
    private boolean doLoadHrmIndicatorsAndWorkTime;

    public boolean isDoLoadStat() {
        return doLoadStat;
    }

    public void setDoLoadStat( boolean doLoadStat ) {
        this.doLoadStat = doLoadStat;
    }

    public boolean isDoLoadDaily() {
        return doLoadDaily;
    }

    public void setDoLoadDaily( boolean doLoadDaily ) {
        this.doLoadDaily = doLoadDaily;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public boolean isDoLoadGenericIndicators() {
        return doLoadGenericIndicators;
    }

    public void setDoLoadGenericIndicators(boolean doLoadGenericIndicators) {
        this.doLoadGenericIndicators = doLoadGenericIndicators;
    }

    public boolean isDoLoadAccounting() {
        return doLoadAccounting;
    }

    public void setDoLoadAccounting(boolean doLoadAccounting) {
        this.doLoadAccounting = doLoadAccounting;
    }

    public boolean isDoLoadHrmIndicators() {
        return doLoadHrmIndicators;
    }

    public void setDoLoadHrmIndicators(boolean doLoadHrmIndicators) {
        this.doLoadHrmIndicators = doLoadHrmIndicators;
    }

    public boolean isDoLoadHrmIndicatorsCorp() {
        return doLoadHrmIndicatorsCorp;
    }

    public void setDoLoadHrmIndicatorsCorp(boolean doLoadHrmIndicatorsCorp) {
        this.doLoadHrmIndicatorsCorp = doLoadHrmIndicatorsCorp;
    }

    @Override
    public String toString() {
        return "LoaderFormDto{" +
                "doLoadAccounting=" + doLoadAccounting +
                ", doLoadGenericIndicators=" + doLoadGenericIndicators +
                ", doLoadHrmIndicators=" + doLoadHrmIndicators +
                ", doLoadHrmIndicatorsCorp=" + doLoadHrmIndicatorsCorp +
                ", doLoadDaily=" + doLoadDaily +
                ", endPeriod='" + endPeriod + '\'' +
                '}';
    }

    public boolean isDoLoadHrmIndicatorsAndWorkTime() {
        return doLoadHrmIndicatorsAndWorkTime;
    }

    public void setDoLoadHrmIndicatorsAndWorkTime( boolean doLoadHrmIndicatorsAndWorkTime ) {
        this.doLoadHrmIndicatorsAndWorkTime = doLoadHrmIndicatorsAndWorkTime;
    }
}