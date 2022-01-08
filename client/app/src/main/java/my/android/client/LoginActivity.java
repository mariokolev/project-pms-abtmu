package my.android.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import my.android.client.activity.ConversationActivity;
import my.android.client.tcp.TCPClient;
import my.android.client.util.AuthenticationUtils;
import my.android.client.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnLogin;
    private AuthViewModel authViewModel;
    private TCPClient tcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);


        authViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(AuthViewModel.class);

        new connectTask().execute();
        btnLogin.setOnClickListener(view -> {

            String message = username.getText().toString();
            Log.e("Stava"," username: " + username);
            //add the text in the arrayList

            //sends the message to the server
            if (tcpClient != null) {
                tcpClient.sendMessage(message);
            }
        });

        Boolean isLoggedIn = AuthenticationUtils.isLoggedIn(getApplicationContext());
        if (!isLoggedIn) {
            //btnLogin.setOnClickListener(view -> login());
            btnLogin.setOnClickListener(view -> {
                if (authViewModel.login(this, username.getText().toString(), password.getText().toString())) {
                    openActivity();
                }
            });
        } else {
            openActivity();
        }
    }

//    private void login() {
//        if (!TextUtils.isEmpty(username.getText()) && !TextUtils.isEmpty(password.getText())) {
//            User user = new User(username.getText().toString(), password.getText().toString());
//            AuthenticationUtils.setUser(getApplicationContext(), user);
//            if (AuthenticationUtils.isLoggedIn(getApplicationContext())) {
//                openActivity();
//            } else {
//                Toast.makeText(this, "Невалидно потребителско име или парола", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Попълнете полетата", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void openActivity() {
        Intent i = new Intent(this, ConversationActivity.class);
        startActivity(i);
    }


    // test
    public class connectTask extends AsyncTask<String,String,TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a TCPClient object and
            tcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            tcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
//            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
//            mAdapter.notifyDataSetChanged();
            System.out.println("kek kek");
        }
    }
}
