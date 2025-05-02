package uqac.dim.bestroommate;

import android.app.Application;
import android.content.Context;

public class BestRoommate extends Application {

    private static Application _applicationInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        _applicationInstance = this;
    }

    public static Context getAppContext(){

        return _applicationInstance.getApplicationContext();
    }
}
