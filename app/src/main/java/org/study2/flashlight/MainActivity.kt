package org.study2.flashlight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import org.study2.flashlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val torch = Torch(this) 엑티비티 사용할때 사용

        binding.flashSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
//                스위치가 온일때
//                torch.flashOn() 엑티비티 사용할때 사용
                startService(Intent(this, TorchService::class.java).apply {
                    action = "on"
                })
            } else {
//                스위치가 오프일때
//                torch.flashOff() 엑티비티 사용할때 사용
                startService(Intent(this,TorchService::class.java).apply {
                    action = "off"
                })
            }
        }
    }
}