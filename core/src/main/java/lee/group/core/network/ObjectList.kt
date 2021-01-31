package lee.group.core.network

import com.google.gson.annotations.SerializedName

open class ObjectList<T> {

    @SerializedName("data")
    open val data: List<T>? = null
}
