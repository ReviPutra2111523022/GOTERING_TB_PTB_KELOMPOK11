package com.example.gotering_tb_ptb.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val orderID: String,
    val orderName: String,
    val orderDate: String,
    val ordersArray: String,
    var orderStatus: Int
) : Parcelable
