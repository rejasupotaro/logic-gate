package rejasupotaro.logicgate.extension

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, Observer { it!!.let { observer(it) } })
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer { it?.let { observer(it) } })
}

