package com.sygic.tracker.screen.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sygic.tracker.databinding.FragmentTrackingBinding
import com.sygic.tracker.screen.map.MapDataModel
import com.sygic.tracker.util.components.SnackBarComponent
import com.sygic.tracker.util.components.createIn
import com.sygic.tracker.util.components.showIn
import com.sygic.tracker.util.provideViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TrackingFragment : Fragment() {

    @Inject
    lateinit var factory: StoresFragmentViewModel.Factory

    private val mapDataModel by viewModels<MapDataModel>()

    private lateinit var viewModel: StoresFragmentViewModel
    private lateinit var binding: FragmentTrackingBinding
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel { factory.create(mapDataModel) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        with(viewModel) {
            showToast.observe(viewLifecycleOwner) { it.showIn(requireContext()) }
            showIndefiniteSnackbar.observe(viewLifecycleOwner) { handleSnackbar(it) }
        }
    }

    private fun handleSnackbar(component: SnackBarComponent?) {
        snackbar?.dismiss()
        snackbar = null
        if (component != null) {
            snackbar = component.createIn(binding.root)
            snackbar?.show()
        }
    }

}