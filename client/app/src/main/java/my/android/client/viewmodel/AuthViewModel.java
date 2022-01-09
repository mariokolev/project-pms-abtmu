package my.android.client.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import my.android.client.model.User;
import my.android.client.repository.UserRepository;
import my.android.client.tcp.TCPClient;
import my.android.client.util.AuthenticationUtils;

public class AuthViewModel extends ViewModel {

    private String username;
    private String password;
    private UserRepository userRepository = new UserRepository();
    private User loggedUser;

    public void connect() {
        ConnectTask connectTask = new ConnectTask();
        connectTask.execute();
    }
    public Boolean login(Context context, String username, String password) throws ExecutionException, InterruptedException {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            this.username = username;
            this.password = password;
            LoginTask loginTask = new LoginTask();
            loggedUser = loginTask.execute().get();

            AuthenticationUtils.setUser(context, loggedUser);
            if (AuthenticationUtils.isLoggedIn(context)) {
                return true;
            } else {
                Toast.makeText(context, "Невалидно потребителско име или парола", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(context, "Попълнете полетата", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private static class ConnectTask extends AsyncTask<String, String, TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            TCPClient tcpClient = TCPClient.getInstance();
            tcpClient.connect();
            return null;
        }

    }

    private class LoginTask extends AsyncTask<String, String, User> {

        @Override
        protected User doInBackground(String... message) {
            try {
                loggedUser = userRepository.login(username, password);
            } catch (JSONException e) {
                Log.e("Login ", "something went wrong", e);
            }
            return loggedUser;
        }
    }
}
