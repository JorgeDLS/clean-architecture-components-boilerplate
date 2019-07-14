package org.buffer.android.boilerplate.data.repository

import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.model.BufferooEntity

/**
 * Interface defining methods for the caching of Bufferoos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface BufferooRemote {

    /**
     * Retrieve a list of Bufferoos, from the cache
     */
    fun getBufferoos(): Flowable<List<BufferooEntity>>

    /**
     * Retrieve a Bufferoo by ID, from the cache.
     */
    fun getBufferooByID(id: Long): Flowable<BufferooEntity>

}