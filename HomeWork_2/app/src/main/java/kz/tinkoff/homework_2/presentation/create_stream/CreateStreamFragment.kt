package kz.tinkoff.homework_2.presentation.create_stream

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.appbar.MaterialToolbar
import javax.inject.Inject
import kz.tinkoff.core.utils.lazyUnsafe
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.FragmentCreateStreamBinding
import kz.tinkoff.homework_2.di_dagger.stream.DaggerStreamComponent
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamDataModule
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamNetworkModule
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.presentation.create_stream.elm.CreateStreamEffect
import kz.tinkoff.homework_2.presentation.create_stream.elm.CreateStreamEvent
import kz.tinkoff.homework_2.presentation.create_stream.elm.CreateStreamState
import kz.tinkoff.homework_2.presentation.create_stream.elm.CreateStreamStoreFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder


class CreateStreamFragment :
    ElmFragment<CreateStreamEvent, CreateStreamEffect, CreateStreamState>() {
    private var _binding: FragmentCreateStreamBinding? = null
    private val binding get() = _binding!!

    private val menuProvider = CreateStreamMenuProvider()

    override val initEvent: CreateStreamEvent = CreateStreamEvent.Ui.NotInit

    @Inject
    lateinit var storeFactory: CreateStreamStoreFactory

    override val storeHolder by lazyUnsafe {
        LifecycleAwareStoreHolder(lifecycle) {
            storeFactory.provide()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerStreamComponent.builder()
            .appComponent(requireContext().getAppComponent())
            .streamNetworkModule(StreamNetworkModule())
            .streamDataModule(StreamDataModule())
            .build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateStreamBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        configureToolbar()

        return binding.root
    }

    override fun render(state: CreateStreamState) {
        hideAll()
        when (state) {
            CreateStreamState.Error -> {
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                binding.dataState.isVisible = true
            }
            CreateStreamState.Loading -> {
                binding.loadingState.isVisible = true
            }
            CreateStreamState.NotInit -> {
                binding.dataState.isVisible = true
            }
            CreateStreamState.Success -> {
                store.accept(CreateStreamEvent.Ui.BackToStreams)
            }
        }
    }

    private fun hideAll() {
        binding.dataState.isVisible = false
        binding.loadingState.isVisible = false
    }


    private fun configureToolbar() {
        val activity = activity as? AppCompatActivity
        activity?.setSupportActionBar(binding.toolbar)
        activity?.supportActionBar?.let { supportActionBar ->
            supportActionBar.setDisplayShowTitleEnabled(false)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }

        activity?.addMenuProvider(menuProvider, viewLifecycleOwner)
        binding.toolbar.showOverflowMenu()
        menuClickListeners(binding.toolbar)
    }

    private fun menuClickListeners(toolbar: MaterialToolbar) {
        toolbar.apply {
            setNavigationOnClickListener {
                store.accept(CreateStreamEvent.Ui.BackToStreams)
            }

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.done -> {
                        store.accept(
                            CreateStreamEvent.Ui.CreateStreamRequest(
                                name = binding.streamNameInputEdit.text.toString(),
                                desc = binding.streamDescriptionInputEdit.text.toString()
                            )
                        )
                        true
                    }
                    else -> false
                }
            }
        }

    }
}
