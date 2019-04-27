package za.co.thamserios.basilread.models;



/**
 * Created by robson on 2017/01/23.
 */

public class CaptureLog {

    private String totalTime;
    private String startTime;
    private String endTime;
    private String holeNumber;
    private double requiredHoleDrill;
    private double actualHoleDrill;

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(String holeNumber) {
        this.holeNumber = holeNumber;
    }

    public double getRequiredHoleDrill() {
        return requiredHoleDrill;
    }

    public void setRequiredHoleDrill(double requiredHoleDrill) {
        this.requiredHoleDrill = requiredHoleDrill;
    }

    public double getActualHoleDrill() {
        return actualHoleDrill;
    }

    public void setActualHoleDrill(double actualHoleDrill) {
        this.actualHoleDrill = actualHoleDrill;
    }
}
