/*
 * Created by do thanh long on 1/25/21 4:58 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/25/21 4:58 PM
 */

package lee.group.core.ext

import android.view.View
import android.view.ViewTreeObserver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.initBottomSheetBehavior(
    bottomSheetBehavior: BottomSheetBehavior<View?>? = null
) {
    bottomSheetBehavior?.let {
        it.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED,
                    BottomSheetBehavior.STATE_HIDDEN,
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        activity?.onBackPressed()
                    }
                    else -> Unit
                }
            }
        })
        it.peekHeight = 0
        it.state = BottomSheetBehavior.STATE_EXPANDED
    }
}

fun <T : View> T.afterMeasured(f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}
