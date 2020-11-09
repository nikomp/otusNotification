package com.example.myapplication

import com.google.gson.annotations.SerializedName

class Models {
    class Notification(
        @SerializedName("id") val id: Int=0,
        @SerializedName("notification_id") val notification_id: Int=0,
        @SerializedName("author_id") val author_id: Int=0,
        @SerializedName("recipient_id") val recipient_id: Int=0,
        @SerializedName("guid") val guid: String="",
        @SerializedName("event_guid") val event_guid: String="",
        @SerializedName("record_id") val record_id: Int=0,
        @SerializedName("title") val title: String="",
        @SerializedName("content") val content: String="",
        @SerializedName("shows_popup_message") val shows_popup_message: Boolean=false,
        @SerializedName("email") val email: String?=null,
        @SerializedName("create_date") val create_date: String="",
        @SerializedName("submit_date") val submit_date: String="",
        @SerializedName("read_date") val read_date: String=""
        //@SerializedName("author") val author: List<Author>?=null,
        //@SerializedName("notification") val notification: List<NoticationData>?=null

    ) {
        override fun toString(): String {
            return "Notification(id=$id, notification_id=$notification_id, author_id=$author_id, recipient_id=$recipient_id, guid='$guid', event_guid='$event_guid', record_id=$record_id, title='$title', content='$content', shows_popup_message=$shows_popup_message, email=$email, create_date='$create_date', submit_date='$submit_date', read_date='$read_date')"
        }
    }
}