/*
 * Created by do thanh long on 1/23/21 9:36 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 9:33 AM
 */

package lee.group.core.base.startup

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val BaseApplication.coroutineScope: CoroutineScope
    get() {
        while (true) {
            val existing = mInternalScopeRef.get() as CoroutineScope?
            if (existing != null) {
                return existing
            }
            val newScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
            if (mInternalScopeRef.compareAndSet(null, newScope)) {
                return newScope
            }
        }
    }
