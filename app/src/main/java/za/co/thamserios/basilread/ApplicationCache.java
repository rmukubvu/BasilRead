package za.co.thamserios.basilread;

import android.content.Context;

import java.util.Date;

import za.co.thamserios.basilread.models.CachedIndex;
import za.co.thamserios.basilread.models.ProductionCache;
import za.co.thamserios.basilread.services.CachedIndexService;

/**
 * Created by Robson.Mukubvu on 1/8/2016.
 */
public class ApplicationCache {
    private Context context;
    private static ApplicationCache cacheInstance = new ApplicationCache();
    private String breakdownOthersReason;
    private String currentStatus;
    private String currentActivity;
    private String currentOperator;
    private String previousActivity;
    private String currentAssistant;
    private Date startTimeProd;
    private CachedIndex cachedIndex;
    private int selectedStandingTypeId;
    private int selectedProductionTypeId;
    private int selectedBreakdownTypeId;
    private String benchNumber;
    private String holeNumber;
    private String holeRequiredDepth;
    private Integer bitSize;
    private Integer rodSize;
    private ProductionCache productionCache;
    private CachedIndexService cachedIndexService = new CachedIndexService();

    private String status;
    private String color;
    private int setUserId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSelectedStandingTypeId() {
        return selectedStandingTypeId;
    }

    public void setSelectedStandingTypeId(int selectedStandingTypeId) {
        this.selectedStandingTypeId = selectedStandingTypeId;
    }

    public int getSelectedProductionTypeId() {
        return selectedProductionTypeId;
    }

    public void setSelectedProductionTypeId(int selectedProductionTypeId) {
        this.selectedProductionTypeId = selectedProductionTypeId;
    }

    public int getSelectedBreakdownTypeId() {
        return selectedBreakdownTypeId;
    }

    public void setSelectedBreakdownTypeId(int selectedBreakdownTypeId) {
        this.selectedBreakdownTypeId = selectedBreakdownTypeId;
    }

    private ApplicationCache() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static ApplicationCache getInstance() {
        if (cacheInstance == null) {
            cacheInstance = new ApplicationCache();
        }
        return cacheInstance;
    }

    public void flushCache() {
        currentStatus = null;
    }


    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCurrentOperator() {
        return currentOperator;
    }

    public void setCurrentOperator(String currentOperator) {
        this.currentOperator = currentOperator;
    }

    public String getCurrentAssistant() {
        return currentAssistant;
    }

    public void setCurrentAssistant(String currentAssistant) {
        this.currentAssistant = currentAssistant;
    }

    public String getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Date getStartTimeProd() {
        return startTimeProd;
    }

    public void setStartTimeProd(Date startTimeProd) {
        this.startTimeProd = startTimeProd;
    }

    public CachedIndex getCachedIndex() {
        return cachedIndexService.getCachedIndexModel();
    }

    /*public void setCachedIndex(CachedIndex cachedIndex) {
        this.cachedIndex = cachedIndex;
    }*/

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

    public String getHoleRequiredDepth() {
        return holeRequiredDepth;
    }

    public void setHoleRequiredDepth(String holeRequiredDepth) {
        this.holeRequiredDepth = holeRequiredDepth;
    }

    public Integer getBitSize() {
        return bitSize;
    }

    public void setBitSize(Integer bitSize) {
        this.bitSize = bitSize;
    }

    public Integer getRodSize() {
        return rodSize;
    }

    public void setRodSize(Integer rodSize) {
        this.rodSize = rodSize;
    }

    public ProductionCache getProductionCache() {
        return productionCache;
    }

    public void setProductionCache(ProductionCache productionCache) {
        this.productionCache = productionCache;
    }

    public String getBreakdownOthersReason() {
        return breakdownOthersReason;
    }

    public void setBreakdownOthersReason(String breakdownOthersReason) {
        this.breakdownOthersReason = breakdownOthersReason;
    }

    public String getPreviousActivity() {
        return previousActivity;
    }

    public void setPreviousActivity(String previousActivity) {
        this.previousActivity = previousActivity;
    }

    public int getSetUserId() {
        return setUserId;
    }

    public void setSetUserId(int setUserId) {
        this.setUserId = setUserId;
    }
}
