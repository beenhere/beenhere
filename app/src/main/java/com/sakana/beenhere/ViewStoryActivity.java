package com.sakana.beenhere;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewStoryActivity extends Activity implements View.OnClickListener{

    ImageView imgPost_Type;
    TextView txtComplete_Name,txtPost_Description,txtPostDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_story);
    findViewById(R.id.imgBack).setOnClickListener(this);
        txtComplete_Name  = (TextView) findViewById(R.id.txtComplete_Name);
        txtPost_Description  = (TextView) findViewById(R.id.txtPost_Desc);
        txtPostDate  = (TextView) findViewById(R.id.txtPostDate);
        imgPost_Type=(ImageView) findViewById(R.id.imgPost_type);
        txtComplete_Name.setText(Class_Declarations.complete_name);
        txtPostDate.setText("Posted on: " + Class_Declarations.post_date);
          txtPost_Description.setText("\t" + Class_Declarations.post_description);

        Resources res = getResources(); // need this to fetch the drawable
        Drawable draw = res.getDrawable( R.drawable.dangerpin);

if (Class_Declarations.post_type.equals("1"))
 {
     draw= res.getDrawable( R.drawable.safepin);

}
        imgPost_Type.setImageDrawable(draw);


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




        }

    }
}
