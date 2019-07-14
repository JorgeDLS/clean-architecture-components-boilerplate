package org.buffer.android.boilerplate.cache.db.constants

/**
 * Defines constants for the Bufferoos Table
 */
object BufferooConstants {

    const val TABLE_NAME = "bufferoos"

    const val QUERY_BUFFEROOS = "SELECT * FROM" + " " + TABLE_NAME

    const val GET_BUFFEROO_BY_ID = "SELECT * FROM" + " " + TABLE_NAME + " WHERE id = "

    const val DELETE_ALL_BUFFEROOS = "DELETE FROM" + " " + TABLE_NAME

}