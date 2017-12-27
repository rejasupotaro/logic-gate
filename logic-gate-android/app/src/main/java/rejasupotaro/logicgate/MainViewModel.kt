package rejasupotaro.logicgate

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val logicGate: LogicGate by lazy {
        LogicGate(application.assets, { l -> log.postValue(l) })
    }

    val log = MutableLiveData<String>()

    val output = MutableLiveData<Int>()

    fun infer(x1: Int, x2: Int) = async(CommonPool) {
        output.postValue(logicGate.and(x1, x2))
    }
}
