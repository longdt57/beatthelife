package lee.group.core.network

import com.google.gson.annotations.SerializedName

open class ObjectItem<T> {

    @SerializedName("data")
    var data: T? = null

    @SerializedName("meta")
    var meta: Any? = null
}
