package rejasupotaro.logicgate.inference

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogicGateTest {
    private val logicGate by lazy {
        val assetManager = InstrumentationRegistry.getTargetContext().assets
        LogicGate(assetManager)
    }

    @Test
    fun and() {
        listOf(
                Pair(Pair(0, 0), 0),
                Pair(Pair(0, 1), 0),
                Pair(Pair(1, 0), 0),
                Pair(Pair(1, 1), 1)
        ).forEach { (input, output) ->
            val actual = logicGate.and(input.first, input.second)
            assertEquals(output, actual)
        }
    }
}
