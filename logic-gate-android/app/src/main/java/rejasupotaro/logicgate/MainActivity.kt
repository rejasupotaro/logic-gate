package rejasupotaro.logicgate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val logicGate: LogicGate by lazy {
        LogicGate(assets, { log ->
            logTextView.text = "${logTextView.text}\n$log"
            logScrollView.post {
                logScrollView.fullScroll(View.FOCUS_DOWN)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val x1 = input1View.selectedItem.toString().toInt()
                val x2 = input2View.selectedItem.toString().toInt()
                val y = logicGate.xor(x1, x2)
                outputTextView.text = y.toString()
            }
        }

        input1View.onItemSelectedListener = listener
        input2View.onItemSelectedListener = listener
    }
}
