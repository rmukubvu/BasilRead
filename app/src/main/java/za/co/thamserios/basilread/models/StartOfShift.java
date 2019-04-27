package za.co.thamserios.basilread.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by robson on 2017/02/27.
 */
@JsonIgnoreProperties({"_id"})
public class StartOfShift {
    public Long _id;
    @JsonProperty("operatorSignId")
    int operatorSignId;
    @JsonProperty("operatorId")
    Integer operatorId;
    @JsonProperty("assistantId")
    Integer assistantId;
    @JsonProperty("shiftCrew")
    String shiftCrew;
    @JsonProperty("shiftId")
    Integer shiftId;
    @JsonProperty("rigId")
    Integer rigId;
    @JsonProperty("benchSupervisorId")
    Integer benchSupervisorId;
    @JsonProperty("foremanId")
    Integer foremanId;
    @JsonProperty("machineOpenHours")
    double machineOpenHours;
    @JsonProperty("trammingOpenHours")
    double trammingOpenHours;
    @JsonProperty("createdDate")
    Long createdDate;
    @JsonProperty("dateInt")
    int dateInt;


    public int getOperatorSignId() {
        return operatorSignId;
    }

    public void setOperatorSignId(int operatorSignId) {
        this.operatorSignId = operatorSignId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Integer assistantId) {
        this.assistantId = assistantId;
    }

    public String getShiftCrew() {
        return shiftCrew;
    }

    public void setShiftCrew(String shiftCrew) {
        this.shiftCrew = shiftCrew;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getRigId() {
        return rigId;
    }

    public void setRigId(Integer rigId) {
        this.rigId = rigId;
    }

    public Integer getBenchSupervisorId() {
        return benchSupervisorId;
    }

    public void setBenchSupervisorId(Integer benchSupervisorId) {
        this.benchSupervisorId = benchSupervisorId;
    }

    public Integer getForemanId() {
        return foremanId;
    }

    public void setForemanId(Integer foremanId) {
        this.foremanId = foremanId;
    }

    public double getMachineOpenHours() {
        return machineOpenHours;
    }

    public void setMachineOpenHours(double machineOpenHours) {
        this.machineOpenHours = machineOpenHours;
    }

    public double getTrammingOpenHours() {
        return trammingOpenHours;
    }

    public void setTrammingOpenHours(double trammingOpenHours) {
        this.trammingOpenHours = trammingOpenHours;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public int getDateInt() {
        return dateInt;
    }

    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }

}
