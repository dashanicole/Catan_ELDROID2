package com.example.catan_mylogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtUsername, pwdPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        this.txtUsername = findViewById(R.id.editText1);
        this.pwdPassword = findViewById(R.id.editText2);
        this.btnLogin = findViewById(R.id.button);

        this.btnLogin.setOnClickListener(this);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        String username = this.txtUsername.getText().toString();
        String password = this.pwdPassword.getText().toString();
        String message = (username.equals("admin")) && (password.equals("123")) ? "LOGIN ACCEPTED" : "LOGIN FAILED";
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        this.txtUsername.setText("");
        this.pwdPassword.setText("");
        this.txtUsername.requestFocus();
    }
}