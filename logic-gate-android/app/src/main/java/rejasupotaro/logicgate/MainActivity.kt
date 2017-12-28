package rejasupotaro.logicgate

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import rejasupotaro.logicgate.anim.FadeOutInAnimation

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private val onItemSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val x1 = input1View.selectedItem.toString().toInt()
            val x2 = input2View.selectedItem.toString().toInt()
            viewModel.infer(x1, x2)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupViews()
        subscribeEvents()
    }

    private fun setupViews() {
        input1View.onItemSelectedListener = onItemSelectListener
        input2View.onItemSelectedListener = onItemSelectListener
    }

    private fun subscribeEvents() {
        viewModel.log.observe(this, Observer { log ->
            logTextView.text = "${logTextView.text}\n$log"
            logScrollView.post {
                logScrollView.fullScroll(View.FOCUS_DOWN)
            }
        })

        viewModel.output.observe(this, Observer { value ->
            outputTextView.startAnimation(FadeOutInAnimation({
                outputTextView.text = value.toString()
            }))
        })
    }
}
