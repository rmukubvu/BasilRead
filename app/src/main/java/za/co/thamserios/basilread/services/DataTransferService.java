package za.co.thamserios.basilread.services;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import za.co.thamserios.basilread.utils.FileUtils;

/**
 * Created by robson on 2017/03/11.
 */

public class DataTransferService {

    private static List<IServices> allServices = new ArrayList<>();
    static {
        allServices.add(new StartShiftService());
        allServices.add(new StandingTimeService());
        allServices.add(new BreakdownService());
        allServices.add(new ProductionService());
        allServices.add(new EndShiftService());
    }

    public String startDataTransfer() {
        String message;
        try {
            File f = FileUtils.getBasilFile();
            for (IServices service : allServices
                    ) {
                String jsonArray = usbDataTransfer(service);
                FileUtils.writeJsonToFile(f, service.getName());
                FileUtils.writeJsonToFile(f, jsonArray);
                FileUtils.writeJsonToFile(f, "%END%");
            }
            return f.getAbsolutePath();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        return message;
    }

    private String usbDataTransfer(IServices services){
        return services.saveToUsb();
    }
}
