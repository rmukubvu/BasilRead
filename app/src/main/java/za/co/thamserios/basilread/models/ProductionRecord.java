package za.co.thamserios.basilread.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by robson on 2017/02/27.
 */
@JsonIgnoreProperties({"_id","flagDown"})
public class ProductionRecord {
    public Long _id; // for cupboa
    int productionRecordId;
    Integer operatorId;
    Integer rigId;
    String drillType;
    Integer statusId;
    String bitSize;
    String rodSize;
    String benchNumber;
    String benchEndNumber;
    String holeNumber;
    String holeDepthRequired;
    Integer holes;
    BigDecimal meters;
    Long startTime;
    Long endTime;

    public String getFlagDown() {
        return flagDown;
    }

    public void setFlagDown(String flagDown) {
        this.flagDown = flagDown;
    }

    String flagDown;


    public Integer getHoles() {
        return holes;
    }

    public void setHoles(Integer holes) {
        this.holes = holes;
    }


    public BigDecimal getMeters() {
        return meters;
    }

    public void setMeters(BigDecimal meters) {
        this.meters = meters;
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

    private Long createdDate;
    private Integer dateInt;


    public int getProductionRecordId() {
        return productionRecordId;
    }

    public void setProductionRecordId(int productionRecordId) {
        this.productionRecordId = productionRecordId;
    }


    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }


    public Integer getRigId() {
        return rigId;
    }

    public void setRigId(Integer rigId) {
        this.rigId = rigId;
    }


    public String getDrillType() {
        return drillType;
    }

    public void setDrillType(String drillType) {
        this.drillType = drillType;
    }


    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }


    public String getBitSize() {
        return bitSize;
    }

    public void setBitSize(String bitSize) {
        this.bitSize = bitSize;
    }


    public String getRodSize() {
        return rodSize;
    }

    public void setRodSize(String rodSize) {
        this.rodSize = rodSize;
    }


    public String getBenchNumber() {
        return benchNumber;
    }

    public void setBenchNumber(String benchNumber) {
        this.benchNumber = benchNumber;
    }


    public String getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(String holeNumber) {
        this.holeNumber = holeNumber;
    }


    public String getHoleDepthRequired() {
        return holeDepthRequired;
    }

    public void setHoleDepthRequired(String holeDepthRequired) {
        this.holeDepthRequired = holeDepthRequired;
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

    public String getBenchEndNumber() {
        return benchEndNumber;
    }

    public void setBenchEndNumber(String benchEndNumber) {
        this.benchEndNumber = benchEndNumber;
    }
}
