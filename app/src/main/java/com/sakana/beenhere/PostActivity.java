package com.sakana.beenhere;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sakana.beenhere.ui.BLHttp;

public class PostActivity extends Activity implements View.OnClickListener {
    TextView txtComplete_Name;
    EditText txtDesc;
    Spinner txtType;
    BLHelper helper;
    GPSTracker gps;
    String post_type="1";
    private static final String URL_POST = "http://"+ Class_Declarations.ipAddress+"/beenhere/beenhere_api/set_post.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_post);

        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.btnPostStory).setOnClickListener(this);

        txtType  = (Spinner) findViewById(R.id.txtType);
        txtComplete_Name  = (TextView) findViewById(R.id.txtComplete_Name);
        txtDesc  = (EditText) findViewById(R.id.txtStory);
        txtComplete_Name.setText(Class_Declarations.complete_name_u);

        helper = new BLHelper(PostActivity.this);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {



        switch(v.getId())
        {
            case R.id.imgBack:

                finish();

                break;

          case R.id.btnPostStory:


              if (txtType.getSelectedItem().toString().contains("Not"))

              {

                  post_type="0";

              }
              String subid=Class_Declarations.userName;

              Log.wtf("asd", "addd");

              if(txtDesc.getText().toString().trim().replace("'","").equals("")==true )
              {


                  helper.prompt(R.layout.prompt_complete,
                          new Runnable() {
                              @Override
                              public void run() {

                                  txtDesc.requestFocus();
                              }

                          }
                  );





              }
              else
              {
                  String lng;String latt;

                  gps = new GPSTracker(PostActivity.this);
                  if (gps.canGetLocation()) {
                      latt = String.valueOf(gps.getLatitude());
                      lng =String.valueOf( gps.getLongitude());
                      BLHttp feedBack = new BLHttp(setPost,PostActivity.this);
                      feedBack.execute(URL_POST +"?"+ "&username="+subid +"&post_type="+post_type+"&longg="+lng+"&lat="+latt+
                              "&desc="+txtDesc.getText().toString().replace(" ","%20")+"&token=atleast" );
                      //   test();

                      Log.wtf("asd", "ddd");

                      helper.prompt(R.layout.prompt_successfeedback,
                              new Runnable() {
                                  @Override
                                  public void run() {

                                     finish();
                                  }

                              }
                      );
                  } else {
                      gps.showSettingsAlert();
                      return;
                  }



              }


              break;




        }

    }



    private Runnable setPost = new Runnable() {
        @Override
        public void run() {
            System.out.println(BLHttp.apiResult);
            if (BLHttp.apiResult.equals("URL not available")) {
                helper.prompt(R.layout.prompt_connect,new Runnable() {
                    @Override
                    public void run() {
                        //finish();
                        Log.wtf("asd", "ddssd");

                    }
                });
            } else {


                helper.prompt(R.layout.prompt_successfeedback,
                        new Runnable() {
                            @Override
                            public void run() {
                                finish();

                            }

                        }
                );
            }
        }
    };
}
