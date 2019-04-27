package za.co.thamserios.basilread.services;

import za.co.thamserios.basilread.models.CachedIndex;

/**
 * Created by robson on 2017/03/26.
 */

public class CachedIndexService extends PostMan {

    public void saveCache(CachedIndex model){
        deleteAllRecords(CachedIndex.class);
        postLocal(model);
    }

    public CachedIndex getCachedIndexModel(){
        return findOne(CachedIndex.class);
    }

    @Override
    public void postToServer() {

    }
}
