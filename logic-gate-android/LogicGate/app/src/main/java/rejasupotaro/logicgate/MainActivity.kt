package rejasupotaro.logicgate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, LogicGate(assets).xor(1, 0).toString(), Toast.LENGTH_SHORT).show()
    }
}
