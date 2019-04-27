package za.co.thamserios.basilread.framework.service;

/**
 * Created by robson on 2016/12/26.
 */
public class ApiClient {
    private static ApiClient ourInstance = new ApiClient();
    private IBasilServices apiService;
    public static ApiClient getInstance() {
        return ourInstance;
    }
    public IBasilServices getApiClient(){
        return apiService;
    }
    private ApiClient() {
        apiService = BasilServices.getClient().create(IBasilServices.class);
    }
}
