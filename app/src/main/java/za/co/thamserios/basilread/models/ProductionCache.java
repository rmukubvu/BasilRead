package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/03/12.
 */

public class ProductionCache {
    private String drillType;
    private String bitSize;
    private String rodSize;
    private String hammer;
    private String benchNumber;
    private String holeNumber;
    private String holeDepthRequired;

    public String getDrillType() {
        return drillType;
    }

    public void setDrillType(String drillType) {
        this.drillType = drillType;
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

    public String getHammer() {
        return hammer;
    }

    public void setHammer(String hammer) {
        this.hammer = hammer;
    }
}
