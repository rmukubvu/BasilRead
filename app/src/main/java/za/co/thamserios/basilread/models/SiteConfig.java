package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/02/27.
 */

public class SiteConfig {
    public Long _id; // for cupboard
    int siteConfigId;
    Integer countryId;
    Integer regionId;
    String siteName;

    public int getSiteConfigId() {
        return siteConfigId;
    }

    public void setSiteConfigId(int siteConfigId) {
        this.siteConfigId = siteConfigId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public String toString(){
        return siteName;
    }
}
