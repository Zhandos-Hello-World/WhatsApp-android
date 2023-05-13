package kz.tinkoff.homework_2.presentation.create_topic

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
import kz.tinkoff.homework_2.databinding.FragmentCreateTopicBinding
import kz.tinkoff.homework_2.di_dagger.message.DaggerMessageComponent
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageDataModule
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageNetworkModule
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.presentation.create_stream.CreateStreamMenuProvider
import kz.tinkoff.homework_2.presentation.create_topic.elm.CreateTopicEffects
import kz.tinkoff.homework_2.presentation.create_topic.elm.CreateTopicEvent
import kz.tinkoff.homework_2.presentation.create_topic.elm.CreateTopicState
import kz.tinkoff.homework_2.presentation.create_topic.elm.CreateTopicStoreFactory
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class CreateTopicFragment(private val args: StreamDvo) :
    ElmFragment<CreateTopicEvent, CreateTopicEffects, CreateTopicState>() {
    private var _binding: FragmentCreateTopicBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var storeFactory: CreateTopicStoreFactory

    override val initEvent: CreateTopicEvent = CreateTopicEvent.Ui.Init(args)

    override val storeHolder by lazyUnsafe {
        LifecycleAwareStoreHolder(lifecycle) {
            storeFactory.provide()
        }
    }

    private val menuProvider = CreateStreamMenuProvider()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerMessageComponent.builder()
            .appComponent(requireContext().getAppComponent())
            .messageDataModule(MessageDataModule())
            .messageNetworkModule(MessageNetworkModule())
            .build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)
        configureToolbar()
        return binding.root
    }

    override fun render(state: CreateTopicState) {
        hideAll()
        when (state) {
            CreateTopicState.CreateTopicLoading -> {
                binding.loadingState.isVisible = true
            }
            CreateTopicState.CreateTopicSuccess -> {
                binding.dataState.isVisible = true
            }
            CreateTopicState.NotInit -> {
                binding.dataState.isVisible = true
            }
        }
    }

    override fun handleEffect(effect: CreateTopicEffects) {
        return when (effect) {
            CreateTopicEffects.CreateTopicError -> {
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideAll() {
        binding.apply {
            loadingState.isVisible = false
            dataState.isVisible = false
        }
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
                store.accept(CreateTopicEvent.Ui.BackToStreams)
            }

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.done -> {
                        store.accept(
                            CreateTopicEvent.Ui.CreateTopicRequest(
                                topicName = binding.topicNameInputEdit.text.toString(),
                                firstMessage = binding.topicFirstMessageInputEdit.text.toString()
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
