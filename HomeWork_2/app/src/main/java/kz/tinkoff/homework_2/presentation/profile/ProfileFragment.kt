package kz.tinkoff.homework_2.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import javax.inject.Inject
import kz.tinkoff.core.utils.lazyUnsafe
import kz.tinkoff.homework_2.databinding.FragmentProfileBinding
import kz.tinkoff.homework_2.di_dagger.people.DaggerPeopleComponent
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileEffect
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileEvent
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileState
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileStoreFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class ProfileFragment : ElmFragment<ProfileEvent, ProfileEffect, ProfileState>() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override val initEvent: ProfileEvent = ProfileEvent.Ui.LoadProfile

    @Inject
    lateinit var storeFactory: ProfileStoreFactory

    override val storeHolder by lazyUnsafe {
        LifecycleAwareStoreHolder(lifecycle) {
            storeFactory.provide()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerPeopleComponent.builder()
            .appComponent(
                requireContext().getAppComponent()
            ).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.reloadRequestBtn.setOnClickListener {
            store.accept(ProfileEvent.Ui.LoadProfile)
        }
        return binding.root
    }

    override fun render(state: ProfileState) {
        hideAll()
        when (state) {
            is ProfileState.Data -> {
                val profileDvo = state.profileDvo
                binding.data.isVisible = profileDvo != null
                if (profileDvo != null) {
                    configureData(profileDvo)
                }
            }
            is ProfileState.Loading -> {
                binding.loadingState.isVisible = true
            }
            is ProfileState.Error -> {
                binding.loadingState.isVisible = true
            }
        }
    }

    private fun hideAll() {
        binding.apply {
            loadingState.isVisible = false
            errorState.isVisible = false
            data.isVisible = false
        }
    }

    private fun configureData(data: ProfileDvo) {
        binding.apply {
            profileNameText.text = data.fullName

            Glide.with(requireContext())
                .load(data.avatarUrl)
                .placeholder(kz.tinkoff.coreui.R.drawable.placeholder)
                .error(kz.tinkoff.coreui.R.drawable.ic_placeholder_error_state)
                .into(profileAvatar)
            profileConnectionStatusText.text = data.presence.status

            val color = ContextCompat.getColor(requireContext(), data.presence.colorResId)
            profileConnectionStatusText.setTextColor(color)
        }
    }
}
