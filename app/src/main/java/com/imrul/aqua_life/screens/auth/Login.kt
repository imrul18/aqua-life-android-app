package com.imrul.aqua_life.screens.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.imrul.aqua_life.R
import com.imrul.aqua_life.components.BasicInputFieldWithIcon
import com.imrul.aqua_life.components.BubbleAnimation
import com.imrul.aqua_life.components.CustomButton
import com.imrul.aqua_life.ui.theme.Primary
import com.imrul.aqua_life.ui.theme.blue200
import com.imrul.aqua_life.ui.theme.water100
import com.imrul.aqua_life.ui.theme.water200

import com.imrul.aqua_life.navigation.AuthScreens
import com.imrul.aqua_life.dataset.ProfileData
import com.imrul.aqua_life.API.ApiClient
import com.imrul.aqua_life.API.LoginRequest
import com.imrul.aqua_life.API.LoginResponse
import com.imrul.aqua_life.dataset.ProfileDataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

@Composable
fun Login(
    navController: NavController
) {

    val profileModel: ProfileDataModel = viewModel()

    val context = LocalContext.current
    val url = "https://www1.aqua-global.com/?mode=anmeldung_kunde"

    var data by remember { mutableStateOf(LoginRequest("jkoenig", "ala1234")) }
    var showPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    fun onLogin(){
        isLoading = true
        val call: Call<LoginResponse> =
            ApiClient.authService.login(data)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse: LoginResponse = response.body()!!
                    if (loginResponse.status == "success") {
                        val data = loginResponse.data as Map<*, *>
                        val user = data["user"] as Map<*, *>

                        val username = user["username"] as String
                        val email = user["email"] as String
                        val sex = user["sex"] as String
//                                    val height = user["height"].toString()
//                                    val weight = user["weight"].toString()
//                                    val surname = data["surname"] as String
//                                    val firstname = data["firstname"] as String
//                                    val jobActivity = data["jobActivity"] as String
//                                    val sportsActivity = data["sportsActivity"] as String
//                                    val bottleWaterType = data["bottleWaterType"] as String
//                                    val filteredWaterType = data["filteredWaterType"] as String
//                                    val rewardPointsEarned = data["rewardPointsEarned"] as Int
                        val accessToken = data["accessToken"].toString()
                        val refreshToken = data["refreshToken"].toString()

                        profileModel.addProfileData(
                            ProfileData(
                                status = 1,
                                username = "imrulafnan",
                                email = email,
//                                            surname = surname,
//                                            firstname = firstname,
                                sex = sex,
//                                            height = height,
//                                            jobActivity = jobActivity,
//                                            sportsActivity = sportsActivity,
//                                            bottleWaterType = bottleWaterType,
//                                            filteredWaterType = filteredWaterType,
//                                            rewardPointsEarned = rewardPointsEarned,
                                accessToken = accessToken,
                                refreshToken = refreshToken
                            )
                        )
                        navController.navigate(AuthScreens.ProfileSetup.route) {
                            launchSingleTop = true
                        }
                    } else {
                        Toast.makeText(
                            context,
                            loginResponse.data.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                        isLoading = false
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                    isLoading = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
                isLoading = false
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to water200.copy(alpha = 0.3f),
                        0.1f to water200.copy(alpha = 0.6f),
                        0.3f to water100.copy(alpha = 0.8f),
                        0.6f to blue200.copy(alpha = 0.46f),
                        1.0f to blue200.copy(alpha = 0.7f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        // Water bubbles background animation
        BubbleAnimation()

        // Background Image taking half of the screen
        Image(
            painter = painterResource(id = R.drawable.male_mask), // Replace with your image resource
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
                .fillMaxHeight(0.5f),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color(0xFFB9DDEF))
        )

        // Centered Company Name "Aqua Life"
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp) // Adjust padding based on image height
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_logo), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp),
                contentScale = ContentScale.Fit,
            )
        }

        // Bottom 50% containing text and input fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // Occupies the bottom half of the screen
                .align(Alignment.BottomCenter)
                .verticalScroll(rememberScrollState()) // Enables scrolling
                .padding(horizontal = 8.dp), // Adjust padding as necessary
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .padding(vertical = 20.dp)
                ,
                text = "Herzlich Willkommen",
                fontSize = 28.sp,
                lineHeight = 35.sp,
                textAlign = TextAlign.Center,
                color = Primary
            )

            BasicInputFieldWithIcon(
                backgroundColor = Color(0xFFBAD0DE),
                leftIcon = R.drawable.login_person,
                value = data.username,
                onValueChange = { data = data.copy(username = it) },
                placeholderText = "Benuztername"
            )

            BasicInputFieldWithIcon(
                backgroundColor = Color(0xFFBAD0DE),
                leftIcon = R.drawable.login_lock,
                rightIcon = if(showPassword) R.drawable.eye else R.drawable.eye_close,
                showText = showPassword,
                rightIconClick = { showPassword = !showPassword },
                value = data.password,
                onValueChange = { data = data.copy(password = it) },
                placeholderText = "Passwort"
            )

            CustomButton(
                modifier = Modifier,
                text = "Anmelden",
                loadingText = "Anmelden...",
                isLoading = isLoading,
                onClick = {
                    onLogin()
                }
            )

            Text(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            }
                        )
                    },
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                text = "Registrieren"
            )
        }
    }
}

