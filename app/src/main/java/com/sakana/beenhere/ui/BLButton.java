package com.sakana.beenhere.ui;

/**
 * Created by caesare on 12/30/13.
 */
public class BLButton {

    private Runnable a = null;
    private String l = null;

    public BLButton(Runnable action){
        a = action;
    }

    public BLButton(Runnable action, String label){
        a = action;
        l = label;
    }

    public Runnable action(){
        return a;
    }

    public String label(){
        return l;
    }

}
