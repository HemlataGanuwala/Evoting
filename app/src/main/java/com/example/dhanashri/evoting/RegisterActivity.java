package com.example.dhanashri.evoting;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextfirstnm, editTextmidlenm, editTextlastnm, editTextenrollno, editTextemailid, editTextmobileno, editTextpassword, editTextaddress, editTextdob;
    String path,firstnm,middlenm,lastnm, enrollno, emailid, mobileno, password, address, dob, semister, branch, gender;

    RadioButton radioButtonfemale, radioButtonmale;

    ImageButton imageButtonshow;

    Calendar myCalendar = Calendar.getInstance();

    Spinner spinnersemister, spinnerbranch;

    Button register;

    ServiceHandler shh;
    ProgressDialog progressDialog;

    int Status = 1;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String spsemisters[] = {"Semister I", "Semister II", "Semister III", "Semister IV", "Semister V", "semister VI"};
    private String spbranch[] = {"CS", "EE", "EE", "ETC", "ME"};

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        path= globalClass.getconstr();

        editTextfirstnm = (EditText) findViewById(R.id.etfirstname);
        editTextmidlenm = (EditText)findViewById(R.id.etmiddlename);
        editTextlastnm = (EditText)findViewById(R.id.etlastname);
        editTextenrollno = (EditText)findViewById(R.id.etenrollno);
        editTextmobileno = (EditText) findViewById(R.id.etmobileno);
        editTextpassword = (EditText) findViewById(R.id.etpassword);
        editTextaddress = (EditText) findViewById(R.id.etaddress);
        editTextdob = (EditText) findViewById(R.id.etdob);

        radioButtonfemale = (RadioButton) findViewById(R.id.rbfemale);
        radioButtonmale = (RadioButton) findViewById(R.id.rbmale);

        register = (Button)findViewById(R.id.btnregister);

        spinnersemister = (Spinner) findViewById(R.id.spsemister);
        spinnerbranch = (Spinner) findViewById(R.id.spbranch);

        imageButtonshow=(ImageButton) findViewById(R.id.imgbtnshow);

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

                InsertData();

            }

        });

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String formate= "dd/mm/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate, Locale.UK);

                editTextdob.setText(simpleDateFormat.format(myCalendar.getTime()));

            }
        };

        editTextdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, dateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageButtonshow.setOnTouchListener(new View.OnTouchListener() {
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

        if(validation())
        {
            enrollno=editTextenrollno.getText().toString();
            firstnm = editTextfirstnm.getText().toString();
            middlenm = editTextmidlenm.getText().toString();
            lastnm = editTextlastnm.getText().toString();
            mobileno = editTextmobileno.getText().toString();
            password = editTextpassword.getText().toString();
            address = editTextaddress.getText().toString();
            dob = editTextdob.getText().toString();
            branch = spinnerbranch.getSelectedItem().toString();
            semister = spinnersemister.getSelectedItem().toString();

            if (radioButtonmale.isChecked()==true)
            {
                gender="Male";
            }
            else {
                gender="Female";
            }

            new GetPersonData().execute();
        }

        else{

            Toast.makeText(RegisterActivity.this, "Enter Valid Data", Toast.LENGTH_LONG).show();
        }

    }

    public boolean validation()
    {
        boolean valid = true;

        if(firstnm.isEmpty()||firstnm.length()>20)
        {
            editTextfirstnm.setError("Please Enter Your First Name");
            valid = false;
        }

        if(middlenm.isEmpty()||middlenm.length()>20)
        {
            editTextmidlenm.setError("Please Enter Your Middle Name");
            valid = false;
        }

        if(lastnm.isEmpty()||lastnm.length()>20)
        {
            editTextlastnm.setError("Please Enter Your Last Name");
            valid = false;
        }

        if(password.isEmpty()||password.length()>=8)
        {
            editTextpassword.setError("Please Enter atleast 8-character");
            valid = false;
        }
        else{
            editTextpassword.setError("Please Enter your Password");
        }

        if(address.isEmpty())
        {
            editTextaddress.setError("Please Enter Your Address");
            valid = false;
        }

        if(enrollno.isEmpty())
        {
            editTextaddress.setError("Please Enter Your Enrollment No");
            valid = false;
        }

        if(mobileno.isEmpty()||mobileno.length()==10)
        {
            editTextmobileno.setError("Please Enter Valid Mobile No");
            valid = false;
        }

        if(enrollno.isEmpty()||mobileno.length()==12)
        {
            editTextenrollno.setError("Please Enter Valid Enrollment No");
            valid = false;
        }

        return valid;
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

            String url = path + "Registration/StudentRegister";

            Log.d("Url: ", "> " + url);

            try {

                List<NameValuePair> para = new ArrayList<>();

                para.add(new BasicNameValuePair("enrollmentno", enrollno));
                para.add(new BasicNameValuePair("fname", firstnm));
                para.add(new BasicNameValuePair("mname", middlenm));
                para.add(new BasicNameValuePair("lname", lastnm));
                para.add(new BasicNameValuePair("contactno", mobileno));
                para.add(new BasicNameValuePair("studentpassword", password));
                para.add(new BasicNameValuePair("address", address));
                para.add(new BasicNameValuePair("birthdate", dob));
                para.add(new BasicNameValuePair("semester", semister));
                para.add(new BasicNameValuePair("branch", branch));
                para.add(new BasicNameValuePair("sex", gender));

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

           editTextenrollno.setText("");
           editTextfirstnm.setText("");
           editTextmidlenm.setText("");
           editTextlastnm.setText("");
           editTextmobileno.setText("");
           editTextpassword.setText("");
           editTextaddress.setText("");
           editTextdob.setText("");

        }
    }
}
