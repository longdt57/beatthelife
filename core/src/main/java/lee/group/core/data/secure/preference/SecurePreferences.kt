package lee.group.core.data.secure.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

open class SecurePreferences(
    context: Context,
    prefName: String = PREF_NAME,
    isSecure: Boolean = true
) : IPreferences {

    companion object {
        internal const val PREF_NAME: String = "lee.group.core_pref"
    }

    private val securePref: SharedPreferences

    init {
        securePref = if (isSecure) {
            val masterKeyBuilder = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)

            EncryptedSharedPreferences.create(
                context,
                prefName,
                masterKeyBuilder.build(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        }
    }

    override fun getString(key: String): String? {
        return securePref.getString(key, null)
    }

    override fun getString(key: String, default: String): String {
        return securePref.getString(key, null) ?: default
    }

    override fun setString(key: String, value: String) {
        securePref.edit().putString(key, value).apply()
    }

    override fun getBool(key: String, default: Boolean): Boolean {
        return securePref.getBoolean(key, default)
    }

    override fun setBool(key: String, value: Boolean) {
        securePref.edit().putBoolean(key, value).apply()
    }

    override fun getInt(key: String, default: Int): Int {
        return securePref.getInt(key, default)
    }

    override fun setInt(key: String, value: Int) {
        securePref.edit().putInt(key, value).apply()
    }

    override fun getLong(key: String, default: Long): Long {
        return securePref.getLong(key, default)
    }

    override fun setLong(key: String, value: Long) {
        securePref.edit().putLong(key, value).apply()
    }

    override fun remove(key: List<String>) {
        val edit = securePref.edit()
        key.forEach {
            edit.remove(it).apply()
        }
    }

    override fun clear() {
        securePref.edit().clear().apply()
    }
}
