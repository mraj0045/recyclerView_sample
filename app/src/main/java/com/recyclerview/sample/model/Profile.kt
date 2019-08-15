package com.recyclerview.sample.model

data class Profile(val id: Long, val name: String, val image: String) {
    constructor() : this(0, "", "")
}