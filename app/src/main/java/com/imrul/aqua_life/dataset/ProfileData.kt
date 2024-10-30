package com.imrul.aqua_life.dataset

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "ProfileData")
data class ProfileData(
    @PrimaryKey val id : UUID = UUID.randomUUID(),
    val objectId: Int = 1,
    val username : String = "jkoenig",
    val email : String = "",
    val surname : String = "König",
    val firstname : String = "Jennifer",
    val sex : String = "male",
    val height : String = "165",
    val weight : String = "69",
    val jobActivity : String = "mostlySeated",
    val sportsActivity : String = "light",
    val bottleWaterType : String = "none",
    val filteredWaterType : String = "none",
    val drinkGoalBurdenInLiters : Float = 0.0f,
    val rewardPointsEarned : Int = 0,
    val status : Int = 0, // 0 = logged in but not verified, 1 = verified, 2 = complete,
    val accessToken: String = "",
    val refreshToken: String = "",
)


