package com.store.lotto

import android.os.*
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.store.lotto.databinding.ActivityMainBinding
import java.security.SecureRandom
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

	val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	var recycle = 1
	var started = false

	val handler = object : Handler(Looper.getMainLooper()) {
		override fun handleMessage(msg: Message) {
			super.handleMessage(msg)
			val ball1 = String.format("%d", recycle * 1)
			val ball2 = String.format("%d", recycle * 2)
			val ball3 = String.format("%d", recycle * 3)
			val ball4 = String.format("%d", recycle * 4)
			val ball5 = String.format("%d", recycle * 5)
			val ball6 = String.format("%d", recycle * 6)
			binding.ballnum1.text = ball1
			binding.ballnum2.text = ball2
			binding.ballnum3.text = ball3
			binding.ballnum4.text = ball4
			binding.ballnum5.text = ball5
			binding.ballnum6.text = ball6
		}
	}

	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


		var num = ArrayList<Int>()
		val rand = SecureRandom.getInstanceStrong()
		for (i in 0..5) {
			var tmp = rand.nextInt(45) + 1
			while (tmp in num) {
				tmp = rand.nextInt(45) + 1
			}
			num.add(tmp)
		}
		var sortNum = num.sorted()

		started = true
		thread(start = true) {
			while (started) {
				Thread.sleep(70)
				if (started) {
					recycle += 1
					handler.sendEmptyMessage(0)
					if (recycle > 7) {
						recycle = 1
					}
				}
			}
		}
		binding.choiceNum.setOnClickListener {
			if (started) {
				started = false
				binding.ballnum1.text = sortNum[0].toString()
				binding.ballnum2.text = sortNum[1].toString()
				binding.ballnum3.text = sortNum[2].toString()
				binding.ballnum4.text = sortNum[3].toString()
				binding.ballnum5.text = sortNum[4].toString()
				binding.ballnum6.text = sortNum[5].toString()
			}
		}
	}
}