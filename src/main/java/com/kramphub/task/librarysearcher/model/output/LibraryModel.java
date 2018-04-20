package com.kramphub.task.librarysearcher.model.output;

import java.util.ArrayList;
import java.util.List;

public class LibraryModel {

    private String infoMsg;

    public List<Item> getItemList() {
        if(null == itemList){
            itemList = new ArrayList<>();
        }
        return itemList;
    }

    private List<Item> itemList;

    public String getInfoMsg() {
        if(null == infoMsg){
            infoMsg = "";
        }
        return infoMsg;
    }

    public void setInfoMsg(String infoMsg) {
        this.infoMsg = infoMsg;
    }


}
