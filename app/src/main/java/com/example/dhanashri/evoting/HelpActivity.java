package com.example.dhanashri.evoting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    String path, cdate, ctime, sdate;
    EditText editTextdate, editTexttime;
    ProgressDialog progressDialog;
    ServiceHandler shh;
    int Status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        path= globalClass.getconstr();

        editTextdate=(EditText)findViewById(R.id.etcalender);
        editTexttime=(EditText)findViewById(R.id.ettime);

        new GetDate().execute();

    }

    public class GetDate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(HelpActivity.this);
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
                        ctime= a1.getString("vtime");

                        sdate= cdate;
                        sdate= sdate.substring(0, 10);

                    }

                } else {
                    Toast.makeText(HelpActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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

            editTextdate.setText(sdate);
            editTexttime.setText(ctime);

        }
    }
}
