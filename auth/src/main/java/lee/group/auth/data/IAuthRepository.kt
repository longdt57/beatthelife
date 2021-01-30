/*
 * Created by do thanh long on 1/27/21 5:02 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 5:02 PM
 */

package lee.group.auth.data

/**
 * Public this class for outer module using
 */
interface IAuthRepository {

    fun isLogin(): Boolean
}
