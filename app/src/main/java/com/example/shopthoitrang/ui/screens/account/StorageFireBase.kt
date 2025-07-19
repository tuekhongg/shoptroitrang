package com.example.shopthoitrang.ui.screens.account

import com.google.firebase.FirebaseOptions
import android.content.Context
import android.net.Uri
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage

object StorageFireBase{
    fun initSecondaryApp(context: Context): FirebaseApp {
        val existingApp = FirebaseApp.getApps(context).find { it.name == "Project1App" }
        if (existingApp != null) {
            return existingApp
        }

        val options = FirebaseOptions.Builder()
            .setApplicationId("1:247667276125:android:d6f0fa85103e1c733890b4")
            .setApiKey("AIzaSyArTSw55JmdXZsO5A63iPjRmhsvskpJveo")
            .setProjectId("cuahanglaptop-1c2f4")
            .setStorageBucket("cuahanglaptop-1c2f4.appspot.com")
            .build()

        return FirebaseApp.initializeApp(context, options, "Project1App")!!
    }

    fun uploadToProject1Storage(
        context: Context,
        fileUri: Uri,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val secondaryApp = FirebaseApp.getInstance("Project1App")
        val storage = FirebaseStorage.getInstance(secondaryApp)
        val storageRef = storage.reference.child("images/${System.currentTimeMillis()}.jpg")

        val uploadTask = storageRef.putFile(fileUri)
        uploadTask
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }.addOnFailureListener { ex ->
                    onError(ex.message ?: "Lỗi lấy URL")
                }
            }
            .addOnFailureListener { ex ->
                onError(ex.message ?: "Upload lỗi")
            }
    }

}
