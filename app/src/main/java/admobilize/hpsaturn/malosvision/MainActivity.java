package admobilize.hpsaturn.malosvision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import admobilize.hpsaturn.malosvision.malos.MalosDrive;
import admobilize.hpsaturn.malosvision.malos.MalosTarget;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MalosDrive malosVision = new MalosDrive(MalosTarget.VISION, "192.168.178.53");
        malosVision.subscribe(new MalosDrive.OnSubscriptionCallBack() {
            @Override
            public void onReceiveData(String host, byte[] data) {

                Log.d(TAG,"onReceiveData on "+host+":");
                Log.d(TAG,"data size:"+data.length);

            }
        });

    }



}
