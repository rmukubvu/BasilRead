package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/03/07.
 */

public class Crews {
    public Long _id; // for cupboard

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    int crewId;

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    String crewName;

    @Override
    public String toString(){
        return crewName;
    }
}
