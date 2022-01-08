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
        editor.putString("PREF_USERNAME", user.getUsername());
        editor.apply();
    }

    public static String getUsername(Context context) {
        return getSharedPreference(context).getString("PREF_USERNAME", null);
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
