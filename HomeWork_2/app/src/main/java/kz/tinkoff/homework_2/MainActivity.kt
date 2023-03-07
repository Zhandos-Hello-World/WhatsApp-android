package kz.tinkoff.homework_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kz.tinkoff.homework_2.custom.item.ReactionViewItem
import kz.tinkoff.homework_2.custom.view.ReactionView
import kz.tinkoff.homework_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.message.setEmojiClickListener {
            it.isSelected = !it.isSelected
        }



        binding.message.addReaction()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}