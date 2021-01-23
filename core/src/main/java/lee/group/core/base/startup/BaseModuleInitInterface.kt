/*
 * Created by do thanh long on 1/22/21 10:46 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/22/21 10:46 PM
 */

package lee.group.core.base.startup

import android.content.Context

internal interface BaseModuleInitInterface {

    fun init(context: Context)

    fun clean(context: Context)
}
