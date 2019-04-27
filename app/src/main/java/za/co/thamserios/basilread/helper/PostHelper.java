package za.co.thamserios.basilread.helper;

import za.co.thamserios.basilread.ApplicationCache;
import za.co.thamserios.basilread.services.OperatorStatusUpdateService;

/**
 * Created by robson on 2017/05/11.
 */

public class PostHelper implements Runnable {
    private OperatorStatusUpdateService operatorStatusUpdateService;

    public PostHelper() {
        operatorStatusUpdateService = new OperatorStatusUpdateService();
    }

    @Override
    public void run() {
        ApplicationCache cache = ApplicationCache.getInstance();
        operatorStatusUpdateService.postStatusToServer(cache.getSetUserId(), cache.getColor(), cache.getStatus());
    }
}

