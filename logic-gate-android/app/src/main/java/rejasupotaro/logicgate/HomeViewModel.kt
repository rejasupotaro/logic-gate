package rejasupotaro.logicgate

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import rejasupotaro.logicgate.extension.sample
import rejasupotaro.logicgate.inference.InferenceInterface
import rejasupotaro.logicgate.inference.LogicGateLite

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val inputs = listOf(Pair(0, 1), Pair(0, 1), Pair(1, 0), Pair(1, 1))
    private val logicGate: InferenceInterface by lazy {
        LogicGateLite(application.assets, { l -> log.postValue(l) })
    }

    val log = MutableLiveData<String>()
    val input = MutableLiveData<Pair<Int, Int>>()
    val output = MutableLiveData<Int>()

    fun infer(input: Pair<Int, Int>) = async(CommonPool) {
        output.postValue(logicGate.and(input.first, input.second))
    }

    fun next() {
        input.postValue(inputs.sample())
    }
}
