package rejasupotaro.logicgate

import android.arch.lifecycle.Observer
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun inferredValue() {
        val viewModel = MainViewModel(activityRule.activity.application)
        val observer: Observer<Int> = mock()
        viewModel.inferredValue.observeForever(observer)
        assert(viewModel.inferredValue.value == 0)
        viewModel.infer(1, 0)
        assert(viewModel.inferredValue.value == 1)
    }
}
