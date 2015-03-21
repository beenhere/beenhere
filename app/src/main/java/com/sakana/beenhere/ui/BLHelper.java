package com.sakana.beenhere.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Caesare on 3/23/14.
 */
public class BLHelper {

    private Context c;
    private View view = null;
    public AlertDialog dialog = null;
    private AlertDialog.Builder builder = null;
    private ProgressDialog blProgress;

    public BLHelper(Context context){
        c = context;
    }

    public Context getC(){
        return c;
    }

    public void prompt(String message){
        prompt("", message, null, null, null, null);
    }

    public void prompt(String title, String message){
        prompt(title, message, null, null, null, null);
    }

    public void prompt(String message, final Runnable next){
        prompt("", message, next);
    }

    public void prompt(String title, String message, final Runnable next){
        prompt(title, message, next, null);
    }

    public void prompt(String title, String message, final Runnable positive,final Runnable negative){
        prompt(title, message,
                (positive == null) ? null : new BLButton(positive,"OK"),
                (negative == null) ? null : new BLButton(negative,"Cancel"),
                null, null);
    }
    public void prompt(String title, String message,String pos, String neg, String indi, final Runnable positive,final Runnable negative){
        prompt(title, message,
                (positive == null) ? null : new BLButton(positive,pos),
                (negative == null) ? null : new BLButton(negative,neg),
                null, null);
    }
    public void prompt(String title, String message, final Runnable positive,final Runnable negative, int layout){
        prompt(title, message,
                (positive == null) ? null : new BLButton(positive,"OK"),
                (negative == null) ? null : new BLButton(negative,"Cancel"),
                layout, null);
    }

    public void prompt(Integer layout){
        prompt("","",null, null, layout);
    }

    public void prompt(Integer layout, String message){
        prompt("",message,null, null, layout);
    }

    public void prompt(Integer layout, final Runnable next){
        prompt("","",next, null, layout);
    }

    public void prompt(Integer layout, final Runnable positive, final Runnable negative){
        prompt("","",positive, negative, layout);
    }

    public void prompt(Integer layout, final BLButton positive, final BLButton negative){
        prompt("","",positive, negative, layout, null);
    }

    public void prompt(Integer layout, final BLButton positive, final BLButton negative, final BLButton neutral){
        prompt("","",positive, negative, layout, neutral);
    }

    public void prompt(String title, String message, final BLButton positive,final BLButton negative, Integer layout, final BLButton neutral){
        view = null;
        String dialogtitle = "";

        if(layout != null){
            LayoutInflater li = LayoutInflater.from(c);
            view = li.inflate(layout, null);
        }

        builder = new AlertDialog.Builder(c);

        if(view != null){
            builder.setView(view);
        }

        if(!message.equals("")){
            builder.setMessage(message);
        }

        if(positive != null){
            builder.setPositiveButton(positive.label(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    positive.action().run();
                }
            });
        }

        if(negative != null){
            builder.setNegativeButton(negative.label(), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    negative.action().run();
                }
            });
        }

        //
        if(neutral != null) {
            builder.setNeutralButton(neutral.label(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    neutral.action().run();
                }
            });
        }

        dialogtitle = title.equals("") ? "" : title;

        builder.setTitle(dialogtitle);

        if(negative != null || positive != null){
            builder.setCancelable(false);
        }

        dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.show();

    }

    public View getView(){
        return view;
    }

    protected AlertDialog.Builder builder(){
        return builder;
    }

    public Runnable closeApp = new Runnable() {
        @Override
        public void run() {
            ((Activity) c).finish();
        }
    };

    public Runnable cancelAction = new Runnable() {
        @Override
        public void run() {

        }
    };

    public EditText editInput(int edit){
        return (EditText) view.findViewById(edit);
    }

    public String getInput(Integer edit){
        EditText et = (EditText) view.findViewById(edit);
        return et.getText().toString();
    }

    public void setInput(Integer edit, String value){
        EditText et = (EditText) view.findViewById(edit);
        et.setText(value);
    }

    public TextView getText(Integer text){
        return (TextView) view.findViewById(text);
    }

    public boolean isValidString(String text) {
        if (!text.equals(null) && !text.isEmpty() && text.length() > 0) {
            return true;
        }
        return false;
    }

    public void progress(Boolean show){
        progress(show, null);
    }

    public void progress(Boolean show, String message){
        progress(show, message, null);
    }

    public void progress(Boolean show, String message, final Runnable action){
        if(show){
            String process = "Processing ...";

            if(message != null){
                process = message;
            }

            blProgress = new ProgressDialog(c);
            blProgress.setMessage(process);
            blProgress.setCancelable(false);
            blProgress.setButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    blProgress.dismiss();
                    if(action != null){
                        action.run();
                    }
                }
            });
            blProgress.show();
        }else{
            if(blProgress != null){
                blProgress.dismiss();
            }

            if(action != null){
                action.run();
            }
        }
    }

    /**
     * a helper method to round of a double to two decimal places;
     * @src -> http://stackoverflow.com/questions/19127365/how-to-format-double-variables-to-two-decimal-places
     * @param value -> the value to round off;
     * @param places -> how many decimal places to use;
     * @return -> the rounded off double;
     */
    public static double round(double value, int places) {
        // make sure we get valid arguments:
        if (places < 0)
            throw new IllegalArgumentException();
        // set places to 2 by default:
        if (places == 0 || places < 2)
            places = 2;
        // instantiate a big decimal:
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        // return the rounded off value:
        return bd.doubleValue();
    }

    /**
     * a helper function for formatting a string amount to currency;
     * @param amount -> the amount to format;
     * @return -> the formatted currency;
     */
    public static String formatAmount(String amount){
        double dAmount = round(Double.parseDouble(amount), 2);
        // set it to include only two decimal places;
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(dAmount);
    }

    public static String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append("");
            }
        }
        return sb.toString();
    }
}
