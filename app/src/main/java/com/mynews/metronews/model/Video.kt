package com.mynews.metronews.model

data class Video(
    val email: String = "",
    val title: String = "",
    val videoDownloadUrl: String = "",
    val id: String = ""
)

////
//if (CheckInternet(requireActivity().applicationContext).isNetworkOnline1()) {
//
//} else {
//
//    AwesomeDialog.build(this)
//        .title("Internet Not Available")
//        .icon(R.drawable.ic_error_24)
//        .onPositive("OK") {
//        }
//}