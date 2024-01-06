package com.example.gotering_tb_ptb.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pengguna(
    val userId: String,
    val name: String,
    val address: String,
    val orderType: String,
    val phoneNumber: String
) : Parcelable