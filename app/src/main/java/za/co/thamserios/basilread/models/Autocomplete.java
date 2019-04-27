package za.co.thamserios.basilread.models;

/**
 * Created by robson on 2017/03/10.
 */

public class Autocomplete {
    public Long _id;
    private String textInput;

    public String getText() {
        return textInput;
    }

    public void setText(String text) {
        this.textInput = text;
    }
}
