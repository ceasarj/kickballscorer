package ceasar.com.myapplication;

import com.firebase.client.Firebase;

/**
 * @author Ceasar Jimenez
 * Initialize firebase with the application when the process for the application/package is created
 **/
public class KickballApplication extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
