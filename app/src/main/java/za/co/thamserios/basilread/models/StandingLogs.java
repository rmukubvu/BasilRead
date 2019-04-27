package za.co.thamserios.basilread.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by robson on 2017/02/27.
 */
@JsonIgnoreProperties({"_id","flagDown"})
public class StandingLogs {
    public Long _id; // for cupboard
    int standingLogsId;
    Integer operatorSheetId;
    Integer standingTypeId;
    Long startTime;
    Long endTime;
    Long createdDate;
    Integer dateInt;
    Integer rigId;
    String standingReason;
    String flagDown;

    public String getStandingReason() {
        return standingReason;
    }

    public void setStandingReason(String reason) {
        this.standingReason = reason;
    }

    public String getFlagDown() {
        return flagDown;
    }

    public void setFlagDown(String flagDown) {
        this.flagDown = flagDown;
    }

    public int getStandingLogsId() {
        return standingLogsId;
    }

    public void setStandingLogsId(int standingLogsId) {
        this.standingLogsId = standingLogsId;
    }

    public Integer getOperatorSheetId() {
        return operatorSheetId;
    }

    public void setOperatorSheetId(Integer operatorSheetId) {
        this.operatorSheetId = operatorSheetId;
    }

    public Integer getStandingTypeId() {
        return standingTypeId;
    }

    public void setStandingTypeId(Integer standingTypeId) {
        this.standingTypeId = standingTypeId;
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
