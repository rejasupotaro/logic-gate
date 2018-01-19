package rejasupotaro.logicgate.inference

import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class LogicGateLite(assets: AssetManager, private val logger: (String) -> Unit = {}) : InferenceInterface {
    private val modelName = "logic_and_gate.tflite"
    private val interpreter: Interpreter
    private val input: FloatArray = FloatArray(2)
    private val output: FloatArray = FloatArray(1)
    private val threshold = 0.5

    init {
        interpreter = Interpreter(loadModelFile(assets))
    }

    private fun loadModelFile(assetManager: AssetManager): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    override fun and(x1: Int, x2: Int): Int {
        input[0] = x1.toFloat()
        input[0] = x2.toFloat()

        interpreter.run(input, output)

        logger("input: [$x1, $x2] => output (probability): ${output[0]}")
        return if (output[0] >= threshold) 1 else 0
    }
}



