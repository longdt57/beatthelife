package one.module.chat.sdk.data.firestore.utils

import java.util.Arrays

internal class Path(path: List<String>) {

    private var components: MutableList<String> = ArrayList()

    /**
     * The remainder type used to fix an issue which arises with Firestore. In Firestore
     * there are documents and collections. But sometimes we want to reference information
     * that type at a path within a document for example:
     * chats/id/meta
     * Here the id, type a document but if we generated a path from this, it would point to a
     * collection. Therefore if the path we pass in to the ref doesn't point to the correct
     * reference type, we truncate it by one and set the remainder
     */
    var remainder: String? = null
        private set

    constructor(path: String) : this(path.split("/".toRegex()).toList())

    fun first(): String {
        return components[0]
    }

    fun last(): String {
        return components[size() - 1]
    }

    fun size(): Int {
        return components.size
    }

    operator fun get(index: Int): String? {
        return if (components.size > index) {
            components[index]
        } else null
    }

    override fun toString(): String {
        val path = StringBuilder()
        for (component in components) {
            path.append(component).append("/")
        }
        path.deleteCharAt(path.length - 1)
        return path.toString()
    }

    fun child(child: String): Path {
        components.add(child)
        return this
    }

    fun children(vararg children: String?): Path {
        components.addAll(Arrays.asList<String>(*children))
        return this
    }

    fun removeLast(): Path {
        if (components.size > 0) {
            components.removeAt(components.size - 1)
        }
        return this
    }

    val isDocument: Boolean
        get() = size() % 2 == 0

    fun getComponents(): List<String> {
        return components
    }

    fun normalizeForDocument() {
        if (!isDocument) {
            remainder = last()
            removeLast()
        }
    }

    fun normalizeForCollection() {
        if (isDocument) {
            remainder = last()
            removeLast()
        }
    }

    /**
     * For Firestore to update nested fields on a document, you need to use a
     * dot notation. This method returns the remainder if it exists plus a
     * dotted path component
     * @param component path to extend
     * @return dotted components
     */
    fun dotPath(component: String): String {
        return if (remainder == null) {
            component
        } else {
            "$remainder.$component"
        }
    }

    init {
        for (s in path) {
            components.add(s)
        }
    }
}
