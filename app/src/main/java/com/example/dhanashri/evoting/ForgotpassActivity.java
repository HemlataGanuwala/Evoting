package com.example.dhanashri.evoting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForgotpassActivity extends AppCompatActivity {

    Button changepass, back;

    ImageButton imageButtonpass, imageButtonconformpass;

    EditText editTextenrollno, editTextpass, editTextconformpass;
    String enrollno, password, conformpass, path, pwd;

    ProgressDialog progressDialog;
    ServiceHandler shh;

    int Status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        path= globalClass.getconstr();

        changepass=(Button)findViewById(R.id.btnchangepass);
        back =(Button)findViewById(R.id.btnback);

        editTextenrollno=(EditText)findViewById(R.id.etuser);
        editTextpass=(EditText)findViewById(R.id.etpass);
        editTextconformpass= (EditText)findViewById(R.id.etconformpass);

        imageButtonpass=(ImageButton)findViewById(R.id.imgpass);
        imageButtonconformpass=(ImageButton)findViewById(R.id.imgconformpass);


        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               InsertData();

            }

        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(ForgotpassActivity.this, LoginActivity.class);
                startActivity(intent);

            }

        });

        imageButtonpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:
                        editTextpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        editTextpass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }

                return true;
            }
        });

        imageButtonconformpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:
                        editTextconformpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        editTextconformpass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }

                return true;
            }
        });
    }

    public void InsertData(){
        enrollno = editTextenrollno.getText().toString();
        password = editTextpass.getText().toString();
        conformpass=editTextconformpass.getText().toString();

        if(!password.equals(conformpass))
        {
            Toast.makeText(ForgotpassActivity.this, "Password not Matche", Toast.LENGTH_LONG).show();
        }
        else
        {
            new GetPersonData().execute();
        }

    }



    public class GetPersonData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForgotpassActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/ForgotPassword";

            Log.d("Url: ", "> " + url);

            try {


                List<NameValuePair> para = new ArrayList<>();

                para.add(new BasicNameValuePair("enrollmentno", enrollno));
                para.add(new BasicNameValuePair("studentpassword", conformpass));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    Status = jObj.getInt("Status");


                } else {
                    Toast.makeText(ForgotpassActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ForgotpassActivity.this, "Password Successfully Changed", Toast.LENGTH_LONG).show();

            }

            else
                {


                Toast.makeText(ForgotpassActivity.this, "Password Dosenot Match", Toast.LENGTH_LONG).show();
            }

            editTextenrollno.setText("");
            editTextpass.setText("");
            editTextconformpass.setText("");


        }
    }
}
