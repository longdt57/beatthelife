package lee.group.core.base.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import lee.group.core.R
import lee.group.core.base.viewmodel.BaseViewModel
import lee.group.core.ext.afterMeasured
import lee.group.core.ext.initBottomSheetBehavior

abstract class BaseBottomSheetDialogFragment<V : BaseViewModel> :
    BottomSheetDialogFragment(), ViewInterface<V> {

    private var bottomSheetBehavior: BottomSheetBehavior<View?>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.afterMeasured {
            dialog?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.let { frameLayout ->
                bottomSheetBehavior = BottomSheetBehavior.from(frameLayout)
                initBottomSheetBehavior(bottomSheetBehavior)
            }
        }
        setupUI()
        setupViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
}
