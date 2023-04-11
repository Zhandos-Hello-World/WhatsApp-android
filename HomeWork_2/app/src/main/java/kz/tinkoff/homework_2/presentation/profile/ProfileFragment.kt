package kz.tinkoff.homework_2.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.coreui.ext.hide
import kz.tinkoff.coreui.ext.show
import kz.tinkoff.homework_2.databinding.FragmentProfileBinding
import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val loadingView: ShimmerFrameLayout get() = binding.loadingState
    private val dataView: ConstraintLayout get() = binding.data
    private val errorView: ConstraintLayout get() = binding.errorState
    private val reloadView: Button get() = binding.reloadRequestBtn
    private val avatar: ImageView get() = binding.profileAvatar
    private val fullName: TextView get() = binding.profileNameText
    private val connectionStatus: TextView get() = binding.profileConnectionStatusText

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.profileInfo.flowWithLifecycle(lifecycle).onEach(::render).launchIn(lifecycleScope)

        reloadView.setOnClickListener {
            viewModel.getProfile()
        }
        return binding.root
    }

    private fun render(state: ScreenState<ProfileDvo>) {
        when (state) {
            is ScreenState.Loading -> {
                hideAll()
                loadingView.show()
            }
            is ScreenState.Error -> {
                hideAll()
                errorView.show()
            }
            is ScreenState.Data -> {
                hideAll()
                dataView.show()

                state.data.let { data ->
                    fullName.text = data.fullName
                    avatar.load(data.avatarUrl)
                    connectionStatus.text = data.presence.status

                    val color = ContextCompat.getColor(requireContext(), data.presence.colorResId)
                    connectionStatus.setTextColor(color)
                }
            }
        }
    }


    private fun hideAll() {
        loadingView.hide()
        dataView.hide()
        errorView.hide()
    }


}