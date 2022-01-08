package my.android.client.viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import my.android.client.model.User;
import my.android.client.util.AuthenticationUtils;

public class AuthViewModel extends ViewModel {

    public Boolean login(Context context, String username, String password) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            User user = new User(username, password);
            AuthenticationUtils.setUser(context, user);
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
}
