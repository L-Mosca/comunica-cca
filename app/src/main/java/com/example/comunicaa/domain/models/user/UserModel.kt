package com.example.comunicaa.domain.models.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    @SerializedName("displayName")
    val displayName: String? = "",
    @SerializedName("isAnonymous")
    val isAnonymous: Boolean? = false,
    @SerializedName("photoUrl")
    val photoUrl: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("isEmailVerified")
    val isEmailVerified: Boolean? = false,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = "",
    @SerializedName("providerId")
    val providerId: String? = "",
    @SerializedName("tenantId")
    val tenantId: String? = "",
    @SerializedName("uid")
    val uid: String? = ""
): Parcelable
