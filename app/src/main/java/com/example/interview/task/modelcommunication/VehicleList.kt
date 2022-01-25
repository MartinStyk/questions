package com.example.interview.task.modelcommunication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.interview.R
import com.example.interview.databinding.FragmentVehicleDetailBinding
import com.example.interview.model.Vehicle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleBrowsingModel {
    var currentVehicle: Vehicle? = null
}

/**
 * We need to send information about currently selected vehicle between these fragments.
 * What are pros and const of this solution?
 */
@AndroidEntryPoint
class FragmentVehicleList : Fragment() {

    @Inject
    lateinit var vehicleBrowsingModel: VehicleBrowsingModel

    fun onVehicleSelected(vehicle: Vehicle) {
        vehicleBrowsingModel.currentVehicle = vehicle
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, FragmentVehicleDetail())
            .commit()
    }

}

@AndroidEntryPoint
class FragmentVehicleDetail : Fragment() {

    @Inject
    lateinit var vehicleBrowsingModel: VehicleBrowsingModel
    lateinit var binding: FragmentVehicleDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model.text = vehicleBrowsingModel.currentVehicle!!.model
    }

}