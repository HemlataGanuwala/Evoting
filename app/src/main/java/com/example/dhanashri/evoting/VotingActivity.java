package com.example.dhanashri.evoting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VotingActivity extends AppCompatActivity {

    TextView textViewvoter1, textViewvoter2, textViewvoter3, textViewvoter4, textViewvoter5, textViewenrollno1, textViewenrollno2, textViewenrollno3, textViewenrollno4, textViewenrollno5;
    ImageButton imageButtonvote1, imageButtonvote2, imageButtonvote3, imageButtonvote4, imageButtonvote5;
    String voter1, voter2, voter3, voter4, voter5, path, enrollno, fname, mname, lname, enroll, enroll1,enroll2, enroll3, enroll4, cdate, candidatenm;
    ProgressDialog progressDialog;
    ServiceHandler shh;
    int Status = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        path= globalClass.getconstr();

        textViewvoter1=(TextView) findViewById(R.id.etvoter1);
        textViewvoter2=(TextView) findViewById(R.id.etvoter2);
        textViewvoter3=(TextView) findViewById(R.id.etvoter3);
        textViewvoter4=(TextView) findViewById(R.id.etvoter4);
        textViewvoter5=(TextView) findViewById(R.id.etvoter5);

        textViewenrollno1=(TextView) findViewById(R.id.etenroll1);
        textViewenrollno2=(TextView) findViewById(R.id.etenroll2);
        textViewenrollno3=(TextView) findViewById(R.id.etenroll3);
        textViewenrollno4=(TextView) findViewById(R.id.etenroll4);
        textViewenrollno5=(TextView) findViewById(R.id.etenroll5);

        imageButtonvote1=(ImageButton)findViewById(R.id.imgvote1);
        imageButtonvote2=(ImageButton)findViewById(R.id.imgvote2);
        imageButtonvote3=(ImageButton)findViewById(R.id.imgvote3);
        imageButtonvote4=(ImageButton)findViewById(R.id.imgvote4);
        imageButtonvote5=(ImageButton)findViewById(R.id.imgvote5);

        Display();

        new GetStudentdata().execute();

        imageButtonvote1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                candidatenm= textViewenrollno1.getText().toString();
                new GetStatusData().execute();

            }
        });

        imageButtonvote2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                candidatenm= textViewenrollno2.getText().toString();
                new GetStatusData().execute();


            }
        });

        imageButtonvote3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                candidatenm=textViewenrollno3.getText().toString();
                new GetStatusData().execute();

            }
        });

        imageButtonvote4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                candidatenm=textViewenrollno4.getText().toString();
                new GetStatusData().execute();

            }
        });

        imageButtonvote5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                candidatenm=textViewenrollno5.getText().toString();
                new GetStatusData().execute();

            }
        });



    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            enrollno=(String)bundle.get("a2");
            cdate=(String)bundle.get("a3");
        }
    }

//code for update status



    // code of getting selection candidate list

    public class GetStudentdata extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(VotingActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/DisplayStudName";

            Log.d("Url: ", "> " + url);

            try {

                List<NameValuePair> para = new ArrayList<>();

                para.add(new BasicNameValuePair("status", "2"));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);

                if (jsonStr != null) {

                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject a1 = jsonArray.getJSONObject(0);
                        fname = a1.getString("fname");
                        mname = a1.getString("mname");
                        lname = a1.getString("lname");
                        voter1=fname+ " " +mname+ " " +lname;
                        enroll= a1.getString("enrollmentno");

                        JSONObject a2 = jsonArray.getJSONObject(1);
                        fname=a2.getString("fname");
                        mname=a2.getString("mname");
                        lname=a2.getString("lname");
                        voter2=fname+ " " +mname+ " " +lname;
                        enroll1= a2.getString("enrollmentno");

                        JSONObject a3 = jsonArray.getJSONObject(2);
                        fname=a3.getString("fname");
                        mname=a3.getString("mname");
                        lname=a3.getString("lname");
                        voter3=fname+ " " +mname+ " " +lname;
                        enroll2= a3.getString("enrollmentno");

                        JSONObject a4 = jsonArray.getJSONObject(3);
                        fname=a4.getString("fname");
                        mname=a4.getString("mname");
                        lname=a4.getString("lname");
                        voter4=fname+ " " +mname+ " " +lname;
                        enroll3= a4.getString("enrollmentno");

                        JSONObject a5 = jsonArray.getJSONObject(4);
                        fname=a5.getString("fname");
                        mname=a5.getString("mname");
                        lname=a5.getString("lname");
                        voter5=fname+ " " +mname+ " " +lname;
                        enroll4= a5.getString("enrollmentno");


                    }

                }


                else {
                    Toast.makeText(VotingActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
            progressDialog.dismiss();

            textViewvoter1.setText(voter1);
            textViewenrollno1.setText(enroll);

            textViewvoter2.setText(voter2);
            textViewenrollno2.setText(enroll1);

            textViewvoter3.setText(voter3);
            textViewenrollno3.setText(enroll2);

            textViewvoter4.setText(voter4);
            textViewenrollno4.setText(enroll3);

            textViewvoter5.setText(voter4);
            textViewenrollno5.setText(enroll4);

//            if (Status == 1)
//            {
//                Toast.makeText(VotingActivity.this, "Voting Successfully", Toast.LENGTH_LONG).show();
//
//            }
//            else {
//                Toast.makeText(VotingActivity.this, "Voting Failed", Toast.LENGTH_LONG).show();
//            }

        }
    }


    public class Updatestatus extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(VotingActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

        }

        @Override
        protected String doInBackground(String... params) {
            shh = new ServiceHandler();

            String url = path + "Registration/StatusUpdate";

            Log.d("Url: ", "> " + url);

            try {

                List<NameValuePair> para = new ArrayList<>();
                para.add(new BasicNameValuePair("EnrollmentNo", enrollno));
                para.add(new BasicNameValuePair("VoteState", "1"));
                para.add(new BasicNameValuePair("VDate", cdate));
                para.add(new BasicNameValuePair("Candidate", candidatenm));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {

                    JSONObject jObj = new JSONObject(jsonStr);
                    Status = jObj.getInt("Status");


                }


                else {
                    Toast.makeText(VotingActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
            progressDialog.dismiss();


        }
    }



    // if woting is already done

    public class GetStatusData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(VotingActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/CheckVotingStatus";

            Log.d("Url: ", "> " + url);

            try {

                List<NameValuePair> para = new ArrayList<>();
                para.add(new BasicNameValuePair("EnrollmentNo", enrollno));
                para.add(new BasicNameValuePair("VoteState", "1"));
                para.add(new BasicNameValuePair("VDate", cdate));
                para.add(new BasicNameValuePair("Candidate", candidatenm));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {

                    JSONObject jObj = new JSONObject(jsonStr);
                    Status = jObj.getInt("Status");


                }


                else {
                    Toast.makeText(VotingActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
            progressDialog.dismiss();

            if (Status == 1)
            {

                Toast.makeText(VotingActivity.this, "Your Voting is Already Done", Toast.LENGTH_LONG).show();

            }
            else {

                new Updatestatus().execute();
                Toast.makeText(VotingActivity.this, "Voting Successfully", Toast.LENGTH_LONG).show();
            }

        }
    }

}
