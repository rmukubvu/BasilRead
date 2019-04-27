package za.co.thamserios.basilread.models;



/**
 * Created by robson on 2017/02/27.
 */

public class BreakdownTypes {
    public Long _id; // for cupboard
    int breakdownTypesId;
    String typeText;

    public int getBreakdownTypesId() {
        return breakdownTypesId;
    }

    public void setBreakdownTypesId(int breakdownTypesId) {
        this.breakdownTypesId = breakdownTypesId;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    @Override
    public String toString(){
        return typeText;
    }
}
