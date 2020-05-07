package admobilize.hpsaturn.malosvision;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Iterator;

import admobilize.hpsaturn.malosvision.malos.MalosDrive;
import admobilize.hpsaturn.malosvision.malos.MalosTarget;
import one.matrixio.proto.vision.v1.FacialRecognition;
import one.matrixio.proto.vision.v1.RectangularDetection;
import one.matrixio.proto.vision.v1.VisionEvent;
import one.matrixio.proto.vision.v1.VisionResult;


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
                try {
                    VisionResult visionResult = VisionResult.parseFrom(data);
                    Iterator<VisionEvent> it = visionResult.getVisionEventList().iterator();
                    while (it.hasNext()) {
                        Log.d(TAG, "=== V I S I O N  E V E N T ===");
                        VisionEvent event = it.next();
                        Log.d(TAG, "Dwelltime:"   + event.getDwellTime());
                        Log.d(TAG, "= " + event.getTag().name() + " =");
                        printFaceRecognitionData(visionResult);
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void printFaceRecognitionData(VisionResult visionResult) {
        Iterator<RectangularDetection> rd_it = visionResult.getRectDetectionList().iterator();
        while (rd_it.hasNext()) {
            RectangularDetection rd = rd_it.next();
            Iterator<FacialRecognition> fr_it = rd.getFacialRecognitionList().iterator();
            while (fr_it.hasNext()) {
                FacialRecognition fr = fr_it.next();
                Log.d(TAG, "age:" + fr.getAge());
                Log.d(TAG, "gender:" + fr.getGender().name());
                Log.d(TAG, "emotion:" + fr.getEmotion().name());
                Log.d(TAG, "pose pitch:" + fr.getPosePitch());
                Log.d(TAG, "beard:" + fr.getBeard());
            }
        }
    }

}
