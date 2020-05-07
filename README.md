# MALOS Vision Android/Java Bridge

Implementation sample of a Android or JAVA client for interfaced it to Admobilize Malos Vision.

## Compiling and installing sample

After clone this project, please first update `protos` submodule and then compile and install it:

```bash
git submodule update --init
./gradlew installDebug
```

---

## Implementation

### gradle file

Set the path of protos (in this sample, they are in `app/src/main/proto`):


```gradle
android {
    ...
    sourceSets {
        main {
            proto {
            }
        }
    }
}
```

Add dependencies for `protobuf` and `GRPC`:

```gradle
dependencies {
    ...
    implementation 'org.zeromq:jeromq:0.3.5'
    implementation 'io.grpc:grpc-okhttp:1.10.0'
    implementation 'io.grpc:grpc-protobuf-lite:1.10.0'
    implementation 'io.grpc:grpc-stub:1.10.0'
    implementation 'javax.annotation:javax.annotation-api:1.2'

    protobuf 'com.google.protobuf:protobuf-java:3.4.0'
}
```

In the main project gradle file:

```gradle
buildscript {
    
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.1'
        classpath "com.google.protobuf:protobuf-gradle-plugin:0.8.8"
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

#### note: proto git submodule

If you want replicate this project in your project, you keep in mind first add the `matrix-io protos submodule`, with something like:

```bash
git submodule add https://github.com/matrix-io/protocol-buffers.git app/src/main/proto
```

### Malos Driver

#### Connection to MALOS VISION device

If you have a malos vision device provisioned and running, only put the IP for have a connection and subscribe to Vision events:

```java
MalosDrive malosVision = new MalosDrive(MalosTarget.VISION, "192.168.178.53");
malosVision.subscribe(new MalosDrive.OnSubscriptionCallBack() {
    @Override
    public void onReceiveData(String host, byte[] data) {

    }
});
```

#### Vision Result proto

Processing the proto Vision Events:

```java
VisionResult visionResult = VisionResult.parseFrom(data);
Iterator<VisionEvent> it = visionResult.getVisionEventList().iterator();
while (it.hasNext()) {
    Log.d(TAG, "=== V I S I O N  E V E N T ===");
    VisionEvent event = it.next();
    Log.d(TAG, "Dwelltime:"   + event.getDwellTime());
    Log.d(TAG, "= " + event.getTag().name() + " =");
    printFaceRecognitionData(visionResult);
}
```

#### Vision Data

From `VisionResult` proto, you have many information, for example this:

```java
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
```

