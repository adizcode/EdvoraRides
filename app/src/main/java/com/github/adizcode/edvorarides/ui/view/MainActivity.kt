package com.github.adizcode.edvorarides.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.adizcode.edvorarides.data.model.User
import com.github.adizcode.edvorarides.data.model.UserRide
import com.github.adizcode.edvorarides.ui.theme.AppBarBlack
import com.github.adizcode.edvorarides.ui.theme.ButtonGray
import com.github.adizcode.edvorarides.ui.theme.EdvoraRidesTheme
import com.github.adizcode.edvorarides.ui.theme.Gray
import com.github.adizcode.edvorarides.ui.theme.LightBlack
import com.github.adizcode.edvorarides.ui.theme.LightGray
import com.github.adizcode.edvorarides.ui.viewmodel.UserRidesViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<UserRidesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EdvoraRidesTheme {
                val user by viewModel.user.observeAsState(User())
                val allRides by viewModel.rides.observeAsState(emptyList())
                val upcomingRides by viewModel.upcomingRides.observeAsState(emptyList())
                val pastRides by viewModel.pastRides.observeAsState(emptyList())
                val nearestRides by viewModel.nearestRides.observeAsState(emptyList())

                var selectedCategory by remember { mutableStateOf("All") }
                val currentList = when (selectedCategory) {
                    "All" -> allRides
                    "Upcoming" -> upcomingRides
                    "Past" -> pastRides
                    "Nearest" -> nearestRides
                    else -> emptyList()
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp),
                            backgroundColor = AppBarBlack,
                            contentColor = Color.White
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Edvora",
                                    style = MaterialTheme.typography.h4.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                                UserDetails(user = user, modifier = Modifier.padding(8.dp))
                            }
                        }
                    },
                    backgroundColor = Gray,
                    contentColor = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.fillMaxHeight()) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(0.75f)
                            ) {
                                MyTextButton(
                                    onClick = {
                                        selectedCategory = "All"
                                    }, text = "Rides:",
                                    isSelected = selectedCategory == "All"
                                )
                                MyTextButton(
                                    onClick = {
                                        selectedCategory = "Nearest"
                                    }, text = "Nearest",
                                    isSelected = selectedCategory == "Nearest"
                                )
                                MyTextButton(
                                    onClick = {
                                        selectedCategory = "Upcoming"
                                    },
                                    text = "Upcoming (${upcomingRides.size})",
                                    isSelected = selectedCategory == "Upcoming",
                                )
                                MyTextButton(
                                    onClick = {
                                        selectedCategory = "Past"
                                    },
                                    text = "Past (${pastRides.size})",
                                    isSelected = selectedCategory == "Past"
                                )
                            }
                            TextButton(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                            ) {
                                Icon(Icons.Filled.Sort, null)
                                Text("Filter")
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        RidesList(currentList)
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetails(user: User?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .clickable {}
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            user?.name ?: "User",
            style = MaterialTheme.typography.body1.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.5.sp,
            )
        )
        Spacer(Modifier.width(10.dp))
        AsyncImage(
            model = user?.url,
            contentDescription = "User's image",
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp),
        )
    }
}

@Composable
fun MyTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    isSelected: Boolean,
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
        modifier = modifier,
    ) {
        Text(
            text,
            style = MaterialTheme.typography.button.copy(
                color = if (isSelected) Color.White else ButtonGray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}

@Composable
fun RidesList(listOfRides: List<UserRide>) {
    LazyColumn {
        items(listOfRides) { RideCard(it) }
    }
}

@Composable
fun RideCard(userRide: UserRide) {

    val roundedShape = RoundedCornerShape(10.dp)

    Column(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp, bottom = 18.dp)
            .clip(roundedShape)
            .background(LightBlack)
            .clickable {}
            .padding(28.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = userRide.ride.map_url,
            contentDescription = "Image of the ride's route",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(roundedShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Chip(textContent = userRide.ride.city)
            Chip(textContent = userRide.ride.state)
        }
        Spacer(Modifier.height(15.dp))
        RideCardRow(attribute = "Ride ID", value = "${userRide.ride.id}")
        Spacer(Modifier.height(10.dp))
        RideCardRow(attribute = "Origin Station", value = "${userRide.ride.origin_station_code}")
        Spacer(Modifier.height(10.dp))
        RideCardRow(attribute = "Station Path", value = "${userRide.ride.station_path}")
        Spacer(Modifier.height(10.dp))
        RideCardRow(attribute = "Date", value = userRide.date)
        Spacer(Modifier.height(10.dp))
        RideCardRow(attribute = "Distance", value = "${userRide.distance}")
    }
}

@Composable
fun Chip(textContent: String) {
    Box(
        Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(textContent)
    }
}

@Composable
fun RideCardRow(attribute: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("$attribute: ", style = MaterialTheme.typography.h6.copy(color = LightGray))
        Text(value, style = MaterialTheme.typography.h6.copy(color = Color.White))
    }
}