package rejasupotaro.logicgate.home

import android.arch.lifecycle.Observer
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rejasupotaro.logicgate.HomeViewModel
import rejasupotaro.logicgate.MainActivity

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun inferredValue() {
        val viewModel = HomeViewModel(activityRule.activity.application)
        val observer: Observer<Int> = mock()
        viewModel.output.observeForever(observer)
        assertEquals(viewModel.output.value, 0)
        viewModel.infer(Pair(1, 0))
        assertEquals(viewModel.output.value, 1)
    }
}
