/*
 * Created by do thanh long on 1/25/21 4:58 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/25/21 4:58 PM
 */

package lee.group.core.ext

import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(
    target: Fragment,
    addToBackStack: Boolean = false,
    containerId: Int = android.R.id.content,
    tag: String? = null
) {
    parentFragmentManager.beginTransaction()
        .replace(containerId, target, tag)
        .apply {
            if (addToBackStack) addToBackStack(null)
        }
        .commit()
}
