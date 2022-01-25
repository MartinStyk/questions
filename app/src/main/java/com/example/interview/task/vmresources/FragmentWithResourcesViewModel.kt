package com.example.interview.task.vmresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interview.R
import com.example.interview.repository.ResourcesRepository
import com.example.interview.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Is there any issue with holding resources in viewModel?
 *
 * values
 * <resources>
 *     <color name="odd_name_color">#FFFF0000</color>
 *     <color name="even_name_color">#FF0000FF</color>
 * </resources>
 *
 * values-night
 * <resources>
 *     <color name="odd_name_color">#FFFFFF00</color>
 *     <color name="even_name_color">#FF0FF000</color>
 * </resources>
 *
 */
@HiltViewModel
class FragmentWithResourcesViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val resourcesRepository: ResourcesRepository,
) : ViewModel() {

    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int> = _color

    init {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser()
            _color.value = if (user.name.length % 2 == 0) {
                resourcesRepository.getColor(R.color.even_name_color)
            } else {
                resourcesRepository.getColor(R.color.odd_name_color)
            }
        }
    }
}