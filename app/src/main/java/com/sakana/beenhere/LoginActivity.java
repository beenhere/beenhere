package com.sakana.beenhere;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sakana.beenhere.ui.BLHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends Activity implements  View.OnClickListener {

    public SQLiteDatabase dbBWD = null;
//    public SqlLiteDbHelper sql= null;

    private static final String URL_AGENT = "http://"+ Class_Declarations.ipAddress+"/beenhere/beenhere_api/login.php?token=asdasd&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
//
//        openDB();

//        Typeface typeFace= Typeface.createFromAsset(getAssets(), "fonts/futura_normal.ttf");
        //      Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");

        //TextView myTextView=(TextView)findViewById(R.id.textGlobe);
        //TextView profileName=(TextView)findViewById(R.id.textSalesForce);
        //myTextView.setTypeface(typeFaceBold);
        //profileName.setTypeface(typeFace);

        findViewById(R.id.btnLogin).setOnClickListener(this);

    }

    @Override
    public void onAttachedToWindow(){

    }
    private Runnable agentsData = new Runnable() {
        @Override
        public void run() {
            System.out.println(BLHttp.apiResult);
            if(BLHttp.apiResult.equals("URL not available")){
                Toast.makeText(LoginActivity.this, "Unable to fetch data! Try login again", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    JSONArray agents = new JSONObject(BLHttp.apiResult).getJSONArray("Account");
                    for (int i = 0; i < agents.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = agents.getJSONObject(i);

                        Class_Declarations.userID=e.getString("id");
                        Class_Declarations.complete_name_u=e.getString("complete_name") ;
                        Class_Declarations.userName=e.getString("username");

                        //Toast.makeText(DashboardActivity.this,e.getString("agent"), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(DashboardActivity.this,e.getString("mobileno"), Toast.LENGTH_SHORT).show();
                    }
                    Intent ii = new Intent(new Intent(LoginActivity.this, DashboardActivity.class));

                    finish();
                    startActivity(ii);
                } catch (JSONException e) {
                    e.printStackTrace();
                    BLHelper helper;
                    helper = new BLHelper(LoginActivity.this);
                    helper.prompt(R.layout.prompt_unableto,
                            new Runnable() {
                                @Override
                                public void run() {

                                }
                            }
                    );
                }
            }
        }
    };


    @Override
    public void onClick(View view){
        final EditText edituser = (EditText) findViewById(R.id.txtUsername);
        final EditText edituser2 = (EditText) findViewById(R.id.txtName);


        if(edituser.getText().length() > 0 && edituser2.getText().length() > 0 ) {
//            Cursor c=null;
//            c = dbBWD.rawQuery("select  * from tbl_users where username='"+  edituser.getText().toString() +"' and password='"+  edituser2.getText().toString() +"' ", null);
//            System.out.println(c.getCount());
//            c.moveToFirst();
//            if(c.getCount() > 0){
//
//                Class_Declarations.userID= c.getString(0);
//                Class_Declarations.complete_name=c.getString(3) ;
//                Class_Declarations.userName=c.getString(1);
//                Intent ii = new Intent(new Intent(LoginActivity.this, HomeActivity.class));
//                finish();
//                startActivity(ii);
//
//            }
//            else
//            {
//                BLHelper helper;
//                helper = new BLHelper(LoginActivity.this);
//                helper.prompt(R.layout.prompt_unableto,
//                        new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        }
//                );
//
//            }
            BLHttp retailers = new BLHttp(agentsData);
            retailers.execute(URL_AGENT+"username="+edituser.getText().toString()+"&name="+edituser2.getText().toString() );


        }
        else
        {
            BLHelper helper;
            helper = new BLHelper(LoginActivity.this);
            helper.prompt(R.layout.prompt_wrong,
                    new Runnable() {
                        @Override
                        public void run() {

                            edituser2.requestFocus();
                        }
                    }
            );
//            startActivity(new Intent(LoginActivity.this, User_Empty.class));
        }

    }



    @Override
    public void onBackPressed() {


        finish();


    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }



}