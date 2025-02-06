package com.example.catan_studentinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText txtIdno, txtName;
    Spinner cboCourse;
    RadioGroup radioGroup;
    Button btnSave, btnCancel;

    String selected_course, gender;
    String[] courses;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        courses = this.getResources().getStringArray(R.array.courses);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Student Information");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setNeutralButton("OKAY", null);

        txtIdno = findViewById(R.id.editText1);
        txtName = findViewById(R.id.editText2);
        cboCourse = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radioGroup1);
        btnSave = findViewById(R.id.button);
        btnCancel = findViewById(R.id.button2);

        cboCourse.setOnItemSelectedListener(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_gender = radioGroup.getCheckedRadioButtonId();
                RadioButton selected_button = findViewById(selected_gender);
                gender = selected_button.getText().toString();

                String idno = txtIdno.getText().toString();
                String name = txtName.getText().toString();
                String studentInfo = "IDNO:  " + idno +"\n" +
                                     "NAME:  " + name + "\n" +
                                     "COURSE:  " + selected_course + "\n" +
                                     "GENDER:  " + gender + "\n";
                builder.setMessage(studentInfo);
                AlertDialog dialog = builder.create();
                dialog.show();

                txtIdno.setText("");
                txtName.setText("");
                radioGroup.clearCheck();
                txtIdno.requestFocus();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selected_course = courses[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}