package com.github.adizcode.edvorarides

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RidesViewModel : ViewModel() {
    private val repository = RidesRepository()

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
            val userInstance = repository.getUser()
            val listOfRides =
                repository.getRides()

            val listOfUserRides = mapRidesToUserRides(listOfRides, userInstance)

            Log.d("Testing", "List of user rides in ViewModel = $listOfUserRides")
            _user.value = userInstance
            _rides.value = listOfUserRides

            // TODO: This works for a single call
            // Maybe the load is too heavy for a mobile device??
            // computeDistance(userInstance.station_code, listOfUserRides[0].ride.station_path)

            Log.d("Testing", "Value posted!!!!")
        }
    }

    private suspend fun mapRidesToUserRides(listOfRides: List<Ride>, user: User): List<UserRide> {
        return withContext(Dispatchers.Default) {
            listOfRides.map { UserRide.createUserRide(user, it) }
        }
    }
}