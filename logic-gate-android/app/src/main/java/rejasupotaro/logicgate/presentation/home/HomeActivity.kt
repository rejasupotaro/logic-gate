package rejasupotaro.logicgate.presentation.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import rejasupotaro.logicgate.R
import rejasupotaro.logicgate.animation.FadeOutInAnimation
import rejasupotaro.common.extension.observe
import rejasupotaro.common.extension.observeNonNull

class HomeActivity : AppCompatActivity() {
    private val handler = Handler()
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeEvents()
        next()
    }

    private fun subscribeEvents() {
        viewModel.log.observe(this, {
            logTextView.text = "${logTextView.text}\n$it"
            logScrollView.post {
                logScrollView.fullScroll(View.FOCUS_DOWN)
            }
        })

        viewModel.input.observeNonNull(this, {
            input1TextView.setTextWithAnimation(it.first.toString())
            input2TextView.setTextWithAnimation(it.second.toString())
            viewModel.infer(it)
        })

        viewModel.output.observe(this, {
            outputTextView.setTextWithAnimation(it.toString())
            next()
        })
    }

    private fun next() {
        handler.postDelayed({ viewModel.next() }, 500)
    }
}

fun TextView.setTextWithAnimation(value: String) = startAnimation(FadeOutInAnimation { text = value })
