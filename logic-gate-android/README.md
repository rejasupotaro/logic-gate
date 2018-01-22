# Logic AND Gate App

This app contains both TensorFlow Mobile and TensorFlow Lite to show the difference.

## TensorFlow Mobile

```groovy
implementation "org.tensorflow:tensorflow-android:1.4.0"
```

```kotlin
inferenceInterface.feed(inputName, input, inputShape.first.toLong(), inputShape.second.toLong())
inferenceInterface.run(arrayOf(outputName))
inferenceInterface.fetch(outputName, output)
```

See `LogicGate` class.

## TensorFlow Lite

```groovy
implementation "org.tensorflow:tensorflow-lite:0.1.1"
```

```kotlin
interpreter.run(input, output)
```

See `LogicGateLite` class.

## Library Size (Raw File Size)

<img src="https://gist.github.com/rejasupotaro/9ee87744261b5e00bdf520fcbfe2ee2e/raw/91bb7f400dbbe1d46a34bf053541dcc38d5c5ffe/apk_profile_logic_gate.png" width="420">

| | TensorFlow Mobile | TensorFlow Lite |
| -- | -- | -- |
| x86_64 | 4.8 MB | 432.7 KB |
| x86 | 4.6 MB | 378.8 KB |
| arm64-v8a | 4.2 MB | 374.7 KB |
| armeabi-v7a | 3.6 MB | 326.5 KB |

TensorFlow Lite is 92% smaller than TensorFlow Mobile

_as of 2018/01/21_

## How to split APK

```groovy
android {
    ...

    splits {
        abi {
            enable true
            reset()
            include 'x86_64', 'x86', 'arm64-v8a', 'armeabi-v7a', 'arm64-v8a'
        }
    }
    project.ext.abiCodes = ['x86_64': 1, 'x86': 2, 'arm64-v8a': 3, 'armeabi-v7a': 4].withDefault {
        0
    }
}

android.applicationVariants.all { variant ->
    variant.outputs.each { output ->
        def baseAbiVersionCode = project.ext.abiCodes.get(output.getFilter(OutputFile.ABI))
        if (baseAbiVersionCode != null) {
            output.versionCodeOverride = baseAbiVersionCode * 1000 + variant.versionCode
        }
    }
}
```
