package com.cwclaims.claimapp.models;

/**
 * Created by dharm on 10/30/17.
 */

public class DBItem
{

    String id;
    String name;
    String isselect;


    public String getIsselect() {
        return isselect;
    }

    public void setIsselect(String isselect) {
        this.isselect = isselect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
