# malos vision android bridge

Implementation sample of a Android client  interfaced to Admobilize Malos Vision

## compiling and installing sample

After clone this project, please firts update submodule protos and then conpile it:

```bash
git submodule update --init
./gradlew installDebug
```

## Implementation

### Gradle file

Set the source path of protos (in this sample, they are in `app/src/main/proto`):


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

Add dependencies for protobuf and GRPC:

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

### Note: Protos

If you want replicate this project in your project, you keep in mind first add the matrix-io protos submodule, with something like:

```bash
git submodule add https://github.com/matrix-io/protocol-buffers.git app/src/main/proto
```

### Malos Driver

```java
    MalosDrive malosVision = new MalosDrive(MalosTarget.VISION, "192.168.178.53");
        malosVision.subscribe(new MalosDrive.OnSubscriptionCallBack() {
            @Override
            public void onReceiveData(String host, byte[] data) {
                Log.d(TAG,"onReceiveData on "+host+":");
                Log.d(TAG,"data size:"+data.length);
            }
        });
```
