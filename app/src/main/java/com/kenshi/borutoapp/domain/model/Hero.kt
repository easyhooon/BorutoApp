package com.kenshi.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kenshi.borutoapp.util.Constants.HERO_DATABASE_TABLE
import kotlinx.serialization.Serializable

//each every hero and every hero on our server already has a unique ID

@Serializable
@Entity(tableName = HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val family: List<String>,
    val abilities: List<String>,
    val natureTypes: List<String>
)