package za.co.thamserios.basilread.models;

import java.util.ArrayList;

/**
 * Created by robson on 2017/03/07.
 */

public class DrillingType {

    public DrillingType(){
        list.add(new DrillingType(1,"New"));
        list.add(new DrillingType(2,"Redrill"));
    }

    public DrillingType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DrillingType> getArrayList(){
        return list;
    }

    @Override
    public String toString() {
        return name;
    }

    private int id;
    private String name;
    private ArrayList<DrillingType> list = new ArrayList<>();
}
