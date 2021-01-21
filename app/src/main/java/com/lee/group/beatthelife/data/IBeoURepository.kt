package com.lee.group.beatthelife.data

import kotlinx.coroutines.flow.Flow

interface IBeoURepository {

    suspend fun signOut(): Flow<Boolean>
}
