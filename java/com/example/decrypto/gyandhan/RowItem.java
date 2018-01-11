package com.example.decrypto.gyandhan;

/**
 * Created by decrypto on 7/1/18.
 */
public class RowItem {

    private String id;
    private String name;

    public RowItem(String ID, String name) {
        this.id = ID;
        this.name = name;
    }
    public String getid() {
        return id;
    }
    public void setID(String ID) {
        this.id = ID;
    }
    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name + "\n";
    }

}
