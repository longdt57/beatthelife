/*
 * Created by do thanh long on 1/23/21 9:38 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 9:33 AM
 */

package lee.group.core.base.startup

import android.app.Application
import androidx.annotation.RestrictTo
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseApplication : Application(), BaseAppExecuteTask {

    /**
     * Coroutines extensions stashes the CoroutineScope into this field.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    internal var mInternalScopeRef = AtomicReference<Any>()

    override fun executeInBackground(code: () -> Unit) {
        coroutineScope.launch(Dispatchers.Default) {
            code.invoke()
        }
    }

    override fun executeInMainThread(code: () -> Unit) {
        coroutineScope.launch {
            code.invoke()
        }
    }
}
