package com.example.dhanashri.evoting;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextfirstnm, editTextmidlenm, editTextlastnm, editTextenrollno, editTextemailid, editTextmobileno, editTextpassword, editTextaddress, editTextdob;
    String path,firstnm,middlenm,lastnm, enrollno, emailid, mobileno, password, address, dob, semister, branch;

    RadioButton radioButtonfemale, radioButtonmale;

    Spinner spinnersemister, spinnerbranch;

    Button register;

    ServiceHandler shh;
    ProgressDialog progressDialog;

    int Status = 1;

    int year, month, day;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    String spsemisters[] = {"Semister I", "Semister II", "Semister III", "Semister IV", "Semister V", "semister VI"};
    String spbranch[] = {"CS", "EE", "EE", "ETC", "ME"};

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextfirstnm = (EditText) findViewById(R.id.etfirstname);
        editTextmidlenm = (EditText)findViewById(R.id.etmiddlename);
        editTextlastnm = (EditText)findViewById(R.id.etlastname);
        editTextenrollno = (EditText)findViewById(R.id.etenrollno);
        editTextemailid = (EditText) findViewById(R.id.etemailid);
        editTextmobileno = (EditText) findViewById(R.id.etmobileno);
        editTextpassword = (EditText) findViewById(R.id.etpassword);
        editTextaddress = (EditText) findViewById(R.id.etaddress);
        editTextdob = (EditText) findViewById(R.id.etdob);

        radioButtonfemale = (RadioButton) findViewById(R.id.rbfemale);
        radioButtonmale = (RadioButton) findViewById(R.id.rbmale);

        register = (Button)findViewById(R.id.btnregister);

        spinnersemister = (Spinner) findViewById(R.id.spsemister);
        spinnerbranch = (Spinner) findViewById(R.id.spbranch);

//        spinnersemister.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spsemisters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersemister.setAdapter(adapter);

//        spinnerbranch.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spbranch);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerbranch.setAdapter(adapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validation();
                InsertData();

            }
        });

        editTextdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

    }

    public void InsertData()
    {
        enrollno=editTextenrollno.getText().toString();
        firstnm = editTextfirstnm.getText().toString();
        middlenm = editTextmidlenm.getText().toString();
        lastnm = editTextlastnm.getText().toString();
        emailid = editTextemailid.getText().toString();
        mobileno = editTextmobileno.getText().toString();
        password = editTextpassword.getText().toString();
        address = editTextaddress.getText().toString();
        dob = editTextdob.getText().toString();
        branch = spinnerbranch.getSelectedItem().toString();
        semister = spinnersemister.getSelectedItem().toString();
        new GetPersonData().execute();
    }

    public void Validation()
    {
        String MobilePattern = "[0-9]{10}";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    }

//    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
//    {
//        Toast.makeText(getApplicationContext(), spsemisters[position], Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), spbranch[position], Toast.LENGTH_LONG).show();
//    }
//
//    public void onNothingSelected(AdapterView<?> arg0) { }

    public class GetPersonData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "InformationApi/PersonalInfo";

            Log.d("Url: ", "> " + url);

            try {

                List<NameValuePair> para = new ArrayList<>();

                para.add(new BasicNameValuePair("EnrollmentNo", enrollno));
                para.add(new BasicNameValuePair("FirstName", firstnm));
                para.add(new BasicNameValuePair("MiddleName", middlenm));
                para.add(new BasicNameValuePair("LastName", lastnm));
                para.add(new BasicNameValuePair("EmailId", emailid));
                para.add(new BasicNameValuePair("MobileNo", mobileno));
                para.add(new BasicNameValuePair("Password", password));
                para.add(new BasicNameValuePair("Address", address));
                para.add(new BasicNameValuePair("Date of Birth", dob));
                para.add(new BasicNameValuePair("Semister", semister));
                para.add(new BasicNameValuePair("Branch", branch));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    Status = jObj.getInt("Status");


                } else {
                    Toast.makeText(RegisterActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
               Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
           }
            else {
               Toast.makeText(RegisterActivity.this, "Registered Failed", Toast.LENGTH_LONG).show();
           }

        }
    }
}
