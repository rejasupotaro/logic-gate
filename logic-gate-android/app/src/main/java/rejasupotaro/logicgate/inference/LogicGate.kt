package rejasupotaro.logicgate.inference

import android.content.res.AssetManager
import org.tensorflow.contrib.android.TensorFlowInferenceInterface
import java.io.File

class LogicGate : InferenceInterface {
    private val logger: (String) -> Unit
    private val inferenceInterface: TensorFlowInferenceInterface
    private val inputShape = Pair(1, 2)
    private val inputName = "x"
    private val input = FloatArray(inputShape.first * inputShape.second)
    private val outputShape = Pair(1, 1)
    private val outputName = "y_pred"
    private val output = FloatArray(outputShape.first * outputShape.second)
    private val threshold = 0.5

    constructor(assets: AssetManager, logger: (String) -> Unit = {}) {
        this.logger = logger
        this.inferenceInterface = TensorFlowInferenceInterface(assets, "optimized_logic_and_gate.pb")
        printOperations()
    }

    constructor(file: File, logger: (String) -> Unit = {}) {
        this.logger = logger
        this.inferenceInterface = file.inputStream().use { TensorFlowInferenceInterface(it) }
        printOperations()
    }

    private fun printOperations() {
        for (op in inferenceInterface.graph().operations()) {
            logger("name: ${op.name()}, type: ${op.type()}, numOutputs: ${op.numOutputs()}")
        }
    }

    override fun and(x1: Int, x2: Int): Int {
        input[0] = x1.toFloat()
        input[1] = x2.toFloat()

        inferenceInterface.feed(inputName, input, inputShape.first.toLong(), inputShape.second.toLong())
        inferenceInterface.run(arrayOf(outputName))
        inferenceInterface.fetch(outputName, output)

        logger("input: [$x1, $x2] => output (probability): ${output[0]}")

        return if (output[0] >= threshold) 1 else 0
    }
}

