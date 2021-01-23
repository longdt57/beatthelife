/*
 * Created by do thanh long on 1/23/21 9:38 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 9:36 AM
 */

package lee.group.core.base.startup

interface BaseAppExecuteTask {

    fun executeInBackground(code: () -> Unit)

    fun executeInMainThread(code: () -> Unit)
}
