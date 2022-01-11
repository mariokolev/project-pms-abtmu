package my.android.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutionException;

import my.android.client.activity.ConversationActivity;
import my.android.client.tcp.TCPClient;
import my.android.client.util.AuthenticationUtils;
import my.android.client.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnLogin;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        authViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(AuthViewModel.class);
        authViewModel.connect();

        Boolean isLoggedIn = AuthenticationUtils.isLoggedIn(getApplicationContext());
        if (!isLoggedIn) {
            btnLogin.setOnClickListener(view -> {
                try {
                    if (authViewModel.login(this, username.getText().toString(), password.getText().toString())) {
                        openActivity();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            openActivity();
        }
    }

    private void openActivity() {
        Intent i = new Intent(this, ConversationActivity.class);
        startActivity(i);
    }
}
