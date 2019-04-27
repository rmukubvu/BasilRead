package za.co.thamserios.basilread.services;

import java.util.ArrayList;
import java.util.List;

import za.co.thamserios.basilread.models.Autocomplete;

/**
 * Created by robson on 2017/03/10.
 */

public class AutocompleteService extends PostMan {

    public void saveCommonWords(String text){
        Autocomplete autocomplete = new Autocomplete();
        autocomplete.setText(text);
        postLocal(autocomplete);
    }

    public List<String> getAutoCompleteList(){
        List<String> list = new ArrayList<>();
        List<Autocomplete> items = findAll(Autocomplete.class);
        for (Autocomplete item : items
             ) {
            list.add(item.getText());
        }
        return list;
    }

    public List<Autocomplete> autoCompleteText(String searchText){
        return findSpecific("textInput like '%" + searchText + "%'",Autocomplete.class);
    }

    @Override
    public void postToServer() {

    }
}
