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
import androidx.core.view.isVisible
import coil.load
import com.facebook.shimmer.ShimmerFrameLayout
import kz.tinkoff.homework_2.databinding.FragmentProfileBinding
import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileEffect
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileEvent
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileState
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileStoreFactory
import org.koin.android.ext.android.inject
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class ProfileFragment : ElmFragment<ProfileEvent, ProfileEffect, ProfileState>() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override val initEvent: ProfileEvent = ProfileEvent.Ui.LoadProfile

    private val loadingView: ShimmerFrameLayout get() = binding.loadingState
    private val dataView: ConstraintLayout get() = binding.data
    private val errorView: ConstraintLayout get() = binding.errorState
    private val reloadView: Button get() = binding.reloadRequestBtn
    private val avatar: ImageView get() = binding.profileAvatar
    private val fullName: TextView get() = binding.profileNameText
    private val connectionStatus: TextView get() = binding.profileConnectionStatusText

    private val profileStoreFactory: ProfileStoreFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        reloadView.setOnClickListener {
            store.accept(ProfileEvent.Ui.LoadProfile)
        }
        return binding.root
    }

    override fun createStore() = profileStoreFactory.provide()

    override fun render(state: ProfileState) {
        loadingView.isVisible = state.isLoading
        errorView.isVisible = state.error

        val profileDvo = state.profileDvo
        dataView.isVisible = profileDvo != null

        if (profileDvo != null) {
            configureData(profileDvo)
        }
    }

    private fun configureData(data: ProfileDvo) {
        fullName.text = data.fullName
        avatar.load(data.avatarUrl)
        connectionStatus.text = data.presence.status

        val color = ContextCompat.getColor(requireContext(), data.presence.colorResId)
        connectionStatus.setTextColor(color)
    }
}