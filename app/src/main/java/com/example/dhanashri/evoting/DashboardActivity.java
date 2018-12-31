package com.example.dhanashri.evoting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class DashboardActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    CardView cardViewvoting, cardViewhelp;
    String path, cdate, tdate, sdate, enrollno;
    ProgressDialog progressDialog;
    ServiceHandler shh;
    int Status = 1;

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

        Date d = new Date();
        CharSequence g = android.text.format.DateFormat.format("yyyy-MM-dd",d.getTime());
        tdate = g.toString();
        Display();

        new GetDate().execute();

        cardViewvoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(tdate.equals(sdate)) {

                    Intent intent=new Intent(DashboardActivity.this, VotingActivity.class);
                    intent.putExtra("a2", enrollno);
                    intent.putExtra("a3", sdate);
                    startActivity(intent);

                }

                else{

                    Toast.makeText(DashboardActivity.this, "Today is not Voting", Toast.LENGTH_LONG).show();

                }


            }
        });

        cardViewhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, HelpActivity.class);
                startActivity(intent);

            }
        });


    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            enrollno=(String)bundle.get("a1");


        }
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

    public class GetDate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/GetVotingDate";

            Log.d("Url: ", "> " + url);

            try {

                List<NameValuePair> para = new ArrayList<>();

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET, para);
                if (jsonStr != null) {

                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for(int i = 0; i<jsonArray.length(); i++){

                        JSONObject a1=jsonArray.getJSONObject(i);
                        cdate= a1.getString("bdate");

                        sdate= cdate;
                        sdate= sdate.substring(0, 10);


                    }

                } else {
                    Toast.makeText(DashboardActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }


        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);



        }
    }
}
