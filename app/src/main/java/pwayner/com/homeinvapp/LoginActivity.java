package pwayner.com.homeinvapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LoginActivity extends AppCompatActivity {
    EditText loginEmails,loginPasswords;
    Button loginButtons,registerButtons,newPassButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        loginEmails = (EditText) findViewById(R.id.loginEmail);
        loginPasswords = (EditText) findViewById(R.id.loginPass);
        loginButtons = (Button) findViewById(R.id.loginButton);
        registerButtons = (Button) findViewById(R.id.registerButton);
        newPassButton = (Button) findViewById(R.id.forgotButton);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        registerButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin() {
        String email = loginEmails.getText().toString().trim();
        String password = loginPasswords.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please insert an Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please insert a Password", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //intent.putExtra("USER", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Please review your credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}