package com.example.dhanashri.evoting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextname, editTextemailid, editTextmobileno, editTextpassword, editTextretypepass, editTextaddress, editTextdob, editTextclass;
    RadioButton radioButtonfemale, radioButtonmale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextname=(EditText)findViewById(R.id.etname);
        editTextemailid=(EditText)findViewById(R.id.etemailid);
        editTextmobileno=(EditText)findViewById(R.id.etmobileno);
        editTextpassword=(EditText)findViewById(R.id.etpassword);
        editTextretypepass=(EditText)findViewById(R.id.etretypepass);
        editTextaddress=(EditText)findViewById(R.id.etaddress);
        editTextdob=(EditText)findViewById(R.id.etdob);
        editTextclass=(EditText)findViewById(R.id.etclass);

        radioButtonfemale=(RadioButton)findViewById(R.id.rbfemale);
        radioButtonmale=(RadioButton)findViewById(R.id.rbmale);
    }
}
