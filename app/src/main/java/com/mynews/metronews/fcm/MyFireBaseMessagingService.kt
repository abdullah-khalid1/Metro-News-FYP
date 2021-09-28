package com.mynews.metronews.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFireBaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        if(remoteMessage.messageId != null){

        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}