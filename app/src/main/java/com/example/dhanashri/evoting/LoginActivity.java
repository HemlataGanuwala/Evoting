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
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button login, register;
    EditText editTextusername, editTextpassword;
    ImageButton imageButtonshowpass;
    TextView textViewforgotpass;
    ProgressDialog progressDialog;
    ServiceHandler shh;
    int Status = 1;
    String path, username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        path= globalClass.getconstr();

        login=(Button)findViewById(R.id.btnlogin);
//        register=(Button)findViewById(R.id.btnregister);

        editTextusername=(EditText)findViewById(R.id.etusername);
        editTextpassword=(EditText)findViewById(R.id.etpass);

        textViewforgotpass=(TextView)findViewById(R.id.tvforgotpass);

        imageButtonshowpass=(ImageButton)findViewById(R.id.showpass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertData();


            }
        });

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });

        textViewforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(LoginActivity.this, ForgotpassActivity.class);
                startActivity(intent);
            }
        });

        imageButtonshowpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:
                        editTextpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        editTextpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }

                return true;
            }
        });
    }


    public void InsertData()
    {

        username=editTextusername.getText().toString();
        password=editTextpassword.getText().toString();

        new GetPersonData().execute();
    }


    public class GetPersonData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/UserLogin";

            Log.d("Url: ", "> " + url);

            try {

                List<NameValuePair> para = new ArrayList<>();

                para.add(new BasicNameValuePair("enrollmentno", username));
                para.add(new BasicNameValuePair("studentpassword", password));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    Status = jObj.getInt("Status");


                } else {
                    Toast.makeText(LoginActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
            else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }

            editTextusername.setText("");
            editTextpassword.setText("");

        }
    }
}



