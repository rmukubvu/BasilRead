package za.co.thamserios.basilread.models;



/**
 * Created by robson on 2017/02/27.
 */

public class StandingTypes {
    public Long _id; // for cupboard
    int standingTypesId;
    String typeName;

    public int getStandingTypesId() {
        return standingTypesId;
    }

    public void setStandingTypesId(int standingTypesId) {
        this.standingTypesId = standingTypesId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString(){
        return typeName;
    }
}
