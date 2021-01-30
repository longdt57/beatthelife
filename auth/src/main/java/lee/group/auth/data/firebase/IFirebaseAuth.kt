/*
 * Created by do thanh long on 1/28/21 11:49 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 11:49 AM
 */

package lee.group.auth.data.firebase

import kotlinx.coroutines.flow.Flow

internal interface IFirebaseAuth {

    suspend fun signOut(): Flow<Boolean>
}
