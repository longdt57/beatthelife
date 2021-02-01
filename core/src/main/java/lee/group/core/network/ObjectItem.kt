package lee.group.core.network

import com.google.gson.annotations.SerializedName

open class ObjectItem<T>(

    @SerializedName("data")
    val data: T
)
