package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/05/11.
 */

public class OperatorStatus {
    private int operatorStatusId;
    private Integer lookUpId;
    private Integer operatorId;
    private String plantNumber;
    private Long statusDate;
    private String actualStatus;

    public int getOperatorStatusId() {
        return operatorStatusId;
    }

    public void setOperatorStatusId(int operatorStatusId) {
        this.operatorStatusId = operatorStatusId;
    }

    public Integer getLookUpId() {
        return lookUpId;
    }

    public void setLookUpId(Integer lookUpId) {
        this.lookUpId = lookUpId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getPlantNumber() {
        return plantNumber;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public Long getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Long statusDate) {
        this.statusDate = statusDate;
    }

    public String getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(String actualStatus) {
        this.actualStatus = actualStatus;
    }
}
