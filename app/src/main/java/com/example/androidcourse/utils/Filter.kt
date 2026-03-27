package com.example.androidcourse.utils

import com.example.androidcourse.data.constants.ApiConstants

fun List<String>.filterUnknown(): List<String> =
    this.filter { it != ApiConstants.UNKNOWN_VALUE }

fun String.orHideUnknown(): String? =
    if (this == ApiConstants.UNKNOWN_VALUE) null else this