package lee.group.core.data.secure.preference

interface IPreferences {

    fun getString(key: String): String?

    fun getString(key: String, default: String): String

    fun setString(key: String, value: String)

    fun getBool(key: String, default: Boolean = false): Boolean

    fun setBool(key: String, value: Boolean)

    fun getInt(key: String, default: Int = 0): Int

    fun setInt(key: String, value: Int)

    fun getLong(key: String, default: Long = 0): Long

    fun setLong(key: String, value: Long)

    fun remove(key: List<String>)

    fun clear()
}
