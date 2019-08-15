package com.recyclerview.sample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(@PrimaryKey val id: Long, val name: String, val image: String) {
    constructor() : this(0, "", "")
}