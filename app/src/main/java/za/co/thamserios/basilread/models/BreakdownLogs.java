package za.co.thamserios.basilread.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by robson on 2017/02/27.
 */
@JsonIgnoreProperties({"_id","flagDown"})
public class BreakdownLogs {
    public Long _id;
    int breakdownLogsId;
    Integer operatorSheetId;
    Integer breakdownTypeId;
    Integer lookUpDataId;
    String optionalText;
    Long startTime;
    Long endTime;
    Long createdDate;
    Integer dateInt;
    Integer rigId;
    String breakdownText;

    public String getBreakdownText() {
        return breakdownText;
    }

    public void setBreakdownText(String breakdownText) {
        this.breakdownText = breakdownText;
    }

    public String getFlagDown() {
        return flagDown;
    }

    public void setFlagDown(String flagDown) {
        this.flagDown = flagDown;
    }

    String flagDown;

    public int getBreakdownLogsId() {
        return breakdownLogsId;
    }

    public void setBreakdownLogsId(int breakdownLogsId) {
        this.breakdownLogsId = breakdownLogsId;
    }

    public Integer getOperatorSheetId() {
        return operatorSheetId;
    }

    public void setOperatorSheetId(Integer operatorSheetId) {
        this.operatorSheetId = operatorSheetId;
    }

    public Integer getBreakdownTypeId() {
        return breakdownTypeId;
    }

    public void setBreakdownTypeId(Integer breakdownTypeId) {
        this.breakdownTypeId = breakdownTypeId;
    }

    public Integer getLookUpDataId() {
        return lookUpDataId;
    }

    public void setLookUpDataId(Integer lookUpDataId) {
        this.lookUpDataId = lookUpDataId;
    }

    public String getOptionalText() {
        return optionalText;
    }

    public void setOptionalText(String optionalText) {
        this.optionalText = optionalText;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getDateInt() {
        return dateInt;
    }

    public void setDateInt(Integer dateInt) {
        this.dateInt = dateInt;
    }

    public Integer getRigId() {
        return rigId;
    }

    public void setRigId(Integer rigId) {
        this.rigId = rigId;
    }

}
