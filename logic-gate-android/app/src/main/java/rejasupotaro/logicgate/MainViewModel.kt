package rejasupotaro.logicgate

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val logicGate: LogicGate by lazy {
        LogicGate(application.assets, { l -> log.postValue(l) })
    }

    val log = MutableLiveData<String>()

    val inferredValue = MutableLiveData<Int>()

    fun infer(x1: Int, x2: Int) {
        inferredValue.postValue(logicGate.xor(x1, x2))
    }
}
