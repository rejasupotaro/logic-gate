package rejasupotaro.logicgate

import android.content.res.AssetManager
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

class LogicGate(assets: AssetManager, private val logger: (String) -> Unit = {}) {
    private val inferenceInterface = TensorFlowInferenceInterface(assets, "optimized_logic_and_gate.pb")
    private val inputShape = Pair(1, 2)
    private val inputName = "x"
    private val outputShape = Pair(1, 1)
    private val outputName = "y_pred"
    private val output = FloatArray(outputShape.first * outputShape.second)
    private val threshold = 0.5

    init {
        for (op in inferenceInterface.graph().operations()) {
            logger("name: ${op.name()}, type: ${op.type()}, numOutputs: ${op.numOutputs()}")
        }
    }

    fun and(x1: Int, x2: Int): Int {
        val input = floatArrayOf(x1.toFloat(), x2.toFloat())

        inferenceInterface.feed(inputName, input, inputShape.first.toLong(), inputShape.second.toLong())
        inferenceInterface.run(arrayOf(outputName))
        inferenceInterface.fetch(outputName, output)

        logger("input: [$x1, $x2] => output (probability): ${output[0]}")

        return if (output[0] >= threshold) 1 else 0
    }
}

