package edu.psu.ist402.cleanride;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.firebase.client.Firebase;

public class RegisterActivity extends AppCompatActivity {
    private static final String FIREBASE_URL = "https://cleanridedb.firebaseio.com/";
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);

        EditText usernameField = (EditText) findViewById(R.id.registerUserNameField);
        EditText passwordField = (EditText) findViewById(R.id.registerPasswordField);
        EditText emailField = (EditText) findViewById(R.id.registerEmailField);
        EditText firstNameField = (EditText) findViewById(R.id.registerFirstNameField);
        EditText lastNameField = (EditText) findViewById(R.id.registerLastNameField);
        CheckBox isADriver = (CheckBox) findViewById(R.id.registerDriverBox);

        BtnListener listener = new BtnListener();
        ((Button) findViewById(R.id.registerAttemptRegisterButton)).setOnClickListener(listener);
    }
    class BtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText usernameInput = (EditText) findViewById(R.id.registerUserNameField);
            EditText passwordInput = (EditText) findViewById(R.id.registerPasswordField);
            EditText emailInput = (EditText) findViewById(R.id.registerEmailField);
            EditText firstNameInput = (EditText) findViewById(R.id.registerFirstNameField);
            EditText lastNameInput = (EditText) findViewById(R.id.registerLastNameField);
            CheckBox isDriverInput = (CheckBox) findViewById(R.id.registerDriverBox);

            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            String email = emailInput.getText().toString();
            String firstName = firstNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            boolean isDriver = isDriverInput.isChecked();

            if (!username.equals("Username") && !password.equals("Password") && !email.equals("Email")
                    && !firstName.equals("First Name") && !lastName.equals("Last Name")){
                RegisterHelper newUser = new RegisterHelper(username, password, email, firstName, lastName, isDriver);
                firebaseRef.push().setValue(newUser);
                usernameInput.setText("Username");
                passwordInput.setText("Password");
                emailInput.setText("Email");
                firstNameInput.setText("First Name");
                lastNameInput.setText("Last Name");
                isDriverInput.setChecked(true);
            }
            else{
                Snackbar.make(view, "You must enter a value into each field!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    public void navToLogin(View view){
        Intent goToLogin = new Intent(this, LoginActivity.class);
        startActivity(goToLogin);
        this.finish();
    }
    public void navToHome(View view){
        Intent goHome = new Intent(this, MainActivity.class);
        startActivity(goHome);
        this.finish();
    }
}
