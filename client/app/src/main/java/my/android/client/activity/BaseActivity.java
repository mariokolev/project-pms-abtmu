package my.android.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import my.android.client.LoginActivity;
import my.android.client.R;
import my.android.client.util.AuthenticationUtils;

public class BaseActivity extends AppCompatActivity {

    protected void openActivity(Class activity) {
        Intent i = new Intent(this, activity);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.application_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chats:
                openActivity(ConversationActivity.class);
                return true;
            case R.id.friends:
                Toast.makeText(this, "Friends are selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.findConnections:
                Toast.makeText(this, "Find connections are selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                AuthenticationUtils.unsetUser(getApplicationContext());
                openActivity(LoginActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}