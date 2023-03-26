package kz.tinkoff.homework_2.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kz.tinkoff.homework_2.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.profileInfo.observe(viewLifecycleOwner) { profile ->
            binding.apply {
                profileNameText.text = profile.fullName
                profileStatusText.text = profile.status
                profileConnectionStatusText.text = profile.connectionStatus
            }
        }

        return binding.root
    }
}