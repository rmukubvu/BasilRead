package za.co.thamserios.basilread.models;


/**
 * Created by robson on 2017/02/27.
 */

public class ProductionType {
    public Long _id; // for cupboa

    public int getProductionTypeId() {
        return productionTypeId;
    }

    public void setProductionTypeId(int productionTypeId) {
        this.productionTypeId = productionTypeId;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    int productionTypeId;
    String typeText;

    @Override
    public String toString() {
        return(typeText);
    }

}
