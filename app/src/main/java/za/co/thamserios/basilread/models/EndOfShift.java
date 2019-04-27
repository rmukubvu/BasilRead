package za.co.thamserios.basilread.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by robson on 2017/03/02.
 */
@JsonIgnoreProperties({"_id"})
public class EndOfShift {
    public Long _id; // for cupboa
    int endOfShiftId;
    int operatorId;
    int rigId;
    double machineClosing;
    double trammingClosing;
    Long endTime;
    String reason;
    int dateInt;
    
    public double getMachineClosing() {
        return machineClosing;
    }

    public void setMachineClosing(double machineClosing) {
        this.machineClosing = machineClosing;
    }

    public double getTrammingClosing() {
        return trammingClosing;
    }

    public void setTrammingClosing(double trammingClosing) {
        this.trammingClosing = trammingClosing;
    }

    public int getEndOfShiftId() {
        return endOfShiftId;
    }

    public void setEndOfShiftId(int endOfShiftId) {
        this.endOfShiftId = endOfShiftId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getRigId() {
        return rigId;
    }

    public void setRigId(int rigId) {
        this.rigId = rigId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getDateInt() {
        return dateInt;
    }

    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }

}
