package rejasupotaro.logicgate

import org.tensorflow.Graph
import org.tensorflow.Session
import org.tensorflow.Tensor
import java.nio.FloatBuffer

fun FloatArray.toTensor(): Tensor<Float> = Tensor.create(longArrayOf(1L, size.toLong()), FloatBuffer.wrap(this))

fun createDataSet(): List<Tensor<Float>> = arrayOf(
        floatArrayOf(0f, 0f),
        floatArrayOf(0f, 1f),
        floatArrayOf(1f, 0f),
        floatArrayOf(1f, 1f)
).map { it.toTensor() }

fun importGraphDef(graph: Graph, file: String) {
    val graphDef = Graph::class.java.getResource(file).readBytes()
    graph.importGraphDef(graphDef)
}

fun printOperations(graph: Graph) {
    for (op in graph.operations()) {
        println("name: ${op.name()}, type: ${op.type()}, numOutputs: ${op.numOutputs()}")
    }
}

fun main(args: Array<String>) {
    val dataSet = createDataSet()

    Graph().use { graph ->
        importGraphDef(graph, "/xor_keras.pb")
        printOperations(graph)

        Session(graph).use { session ->
            dataSet.forEach {
                val result = session.runner().feed("dense_1_input_1", it)
                        .fetch("output_0")
                        .run()[0]
                val output = result.copyTo(FloatArray(1))[0]
                println("input: $it | output: $output => ${if (output < 0.5) 0 else 1}")
            }
        }
    }
}
