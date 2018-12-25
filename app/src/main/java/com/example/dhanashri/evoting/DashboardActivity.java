package com.example.dhanashri.evoting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class DashboardActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    CardView cardViewvoting, cardViewhelp;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        path= globalClass.getconstr();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        cardViewvoting=(CardView)findViewById(R.id.cvvoting);
        cardViewhelp=(CardView)findViewById(R.id.cvhelp);

        int image[] = {R.drawable.campaining, R.drawable.voting_que, R.drawable.process_voting, R.drawable.win};

        for (int img : image) {

            FlipperImage(img);
        }

        cardViewvoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DashboardActivity.this, VotingActivity.class);
                startActivity(intent);

            }
        });

        cardViewhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(DashboardActivity.this, HelpActivity.class);
                startActivity(intent);

            }
        });
    }


    public void FlipperImage(int img){

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(img);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);


    }
}
