package my.android.client.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import my.android.client.model.User;

public class AuthenticationUtils {

    public static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUser(Context context, User user) {
        Editor editor = getSharedPreference(context).edit();
        editor.putLong("sender_id", user.getId());
        editor.putString("username", user.getUsername());
        editor.apply();
    }

    public static String getUsername(Context context) {
        return getSharedPreference(context).getString("username", null);
    }

    public static Long getUserId(Context context) {
        return getSharedPreference(context).getLong("sender_id", 0L);
    }

    public static void unsetUser(Context context) {
        Editor editor = getSharedPreference(context).edit();
        editor.clear();
        editor.apply();
    }

    public static Boolean isLoggedIn(Context context) {
        return getUsername(context) != null;
    }

}
