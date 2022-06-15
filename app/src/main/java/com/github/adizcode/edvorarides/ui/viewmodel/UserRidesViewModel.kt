package com.github.adizcode.edvorarides.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adizcode.edvorarides.data.model.UserRide
import com.github.adizcode.edvorarides.data.model.Ride
import com.github.adizcode.edvorarides.data.repositories.RidesRepository
import com.github.adizcode.edvorarides.data.model.User
import com.github.adizcode.edvorarides.data.repositories.UserRepository
import com.github.adizcode.edvorarides.util.isFutureDate
import com.github.adizcode.edvorarides.util.isPastDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRidesViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val ridesRepository = RidesRepository()

    private val _rides: MutableLiveData<List<UserRide>> = MutableLiveData(emptyList())
    val rides: LiveData<List<UserRide>>
        get() = _rides

    val nearestRides = MediatorLiveData<List<UserRide>>().apply {
        addSource(rides) { ridesList ->
            this.value = ridesList.sortedBy { it.distance }
        }
    }

    val upcomingRides = MediatorLiveData<List<UserRide>>().apply {
        addSource(rides) { ridesList ->
            this.value = ridesList
                .filter { isFutureDate(it.date) }
        }
    }

    val pastRides = MediatorLiveData<List<UserRide>>().apply {
        addSource(rides) { ridesList ->
            this.value = ridesList
                .filter { isPastDate(it.date) }
        }
    }

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        initUserAndRides()
    }

    private fun initUserAndRides() {

        viewModelScope.launch {
            val userInstance = userRepository.getUser()
            val listOfRides =
                ridesRepository.getRides()

            val listOfUserRides = mapRidesToUserRides(listOfRides, userInstance)

            _user.value = userInstance
            _rides.value = listOfUserRides
        }
    }

    private suspend fun mapRidesToUserRides(listOfRides: List<Ride>, user: User): List<UserRide> {
        return withContext(Dispatchers.Default) {
            listOfRides.map { UserRide.createUserRide(user.station_code, it) }
        }
    }
}