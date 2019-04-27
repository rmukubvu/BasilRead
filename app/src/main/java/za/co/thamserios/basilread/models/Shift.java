package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/03/07.
 */

public class Shift {
    public Long _id; // for cupboard
    int shiftId;
    String shiftName;
    String shiftsStartTime;
    String shiftsEndTime;

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getShiftsStartTime() {
        return shiftsStartTime;
    }

    public void setShiftsStartTime(String shiftsStartTime) {
        this.shiftsStartTime = shiftsStartTime;
    }

    public String getShiftsEndTime() {
        return shiftsEndTime;
    }

    public void setShiftsEndTime(String shiftsEndTime) {
        this.shiftsEndTime = shiftsEndTime;
    }

    @Override
    public String toString(){
        return shiftName;
    }


}
