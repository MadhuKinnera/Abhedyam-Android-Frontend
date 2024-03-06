package com.madhu.projectkapp1.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressDto(
    var street: String? = "",
    var landmark: String? = "",
    var description: String? = "",
    var villageName: String = ""
) : Parcelable
