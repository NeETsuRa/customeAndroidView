package sandi.kelenc.zoga;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ZogaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load custome view
        View zv = new ZogaView(this);
        setContentView(zv);
    }
}