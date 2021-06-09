package com.granson.protopoint.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class Utils {

    @SuppressLint("SimpleDateFormat")
    fun showDate(dateString: String?): String {
        return if (dateString !== "null" && !dateString.isNullOrEmpty()) {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
            formatter.format(parser.parse(dateString))
        } else {
            "-/-"
        }
    }
}