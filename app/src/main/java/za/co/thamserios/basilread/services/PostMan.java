package za.co.thamserios.basilread.services;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.qbusict.cupboard.DatabaseCompartment;
import nl.qbusict.cupboard.QueryResultIterable;
import za.co.thamserios.basilread.helper.DatabaseHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by robson on 2017/03/07.
 */

public abstract class PostMan {

    public <T> void postLocal(T data){
        cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).put(data);
    }

    public abstract void postToServer();

    public void save(Collection<?> entities){
        cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).put(entities);
    }

    public <T> void update(T data){
        cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).put(data);
    }

    public <T> void replace(List<T> entities,Class<T> tClass){
        if (entities==null)
            return;
        cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).delete(tClass, null);
        save(entities);
    }

    public <T> T findOne(Class<T> clazz){
        DatabaseCompartment.QueryBuilder<T> query = cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).query(clazz);
        return query.get();
    }

    public <T> List<T> findAll(Class<T> clazz){
        final QueryResultIterable<T> results = cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).query(clazz).query();
        List<T> arrayList = getListFromQueryResultIterator(results);
        return arrayList;
    }

    public <T> List<T> findSpecific(String criteria,Class<T> clazz){
        QueryResultIterable<T> iterable =
                cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).query(clazz).withSelection(criteria).query();
        List<T> list = getListFromQueryResultIterator(iterable);
        return list;
    }

    public <T> void deleteRecord(Class<T> clazz,long id){
        cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).delete(clazz,id);
    }

    public <T> void deleteSpecificRecords(Class<T> clazz){
        //criteria ,"title = ?", "android"
        cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).delete(clazz, "flagDown = ?", "yes");
    }

    public <T> void deleteAllRecords(Class<T> tClass){
        cupboard().withDatabase(DatabaseHelper.getInstance().getDb()).delete(tClass, null);
    }

    private <T> List<T> getListFromQueryResultIterator(QueryResultIterable<T> iter) {
        final List<T> records = new ArrayList<>();
        for (T record : iter) {
            records.add(record);
        }
        iter.close();
        return records;
    }

}
