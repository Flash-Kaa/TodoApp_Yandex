package com.flasshka.data.network

import com.flasshka.data.network.entities.ItemWithRevision
import com.flasshka.data.network.entities.ListItemsWithRevision
import com.flasshka.todo.data.BuildConfig
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface TodoListService {
    object RuntimeConstants {
        val OAthWithToken: String = "OAuth ${BuildConfig.OATH_TOKEN}"
        var lastKnownRevision: Int = 0
    }

    @GET("list")
    suspend fun getItems(
        @Header("Authorization") auth: String = RuntimeConstants.OAthWithToken
    ): ListItemsWithRevision

    @PATCH("list")
    suspend fun updateItems(
        @Body body: RequestBody,
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
        @Header("Authorization") auth: String = RuntimeConstants.OAthWithToken
    ): ListItemsWithRevision

    @GET("list/{id}")
    suspend fun getItemById(
        @Path("id") path: String,
        @Header("Authorization") auth: String = RuntimeConstants.OAthWithToken,
    ): ItemWithRevision

    @POST("list")
    suspend fun addItem(
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
        @Header("Authorization") auth: String = RuntimeConstants.OAthWithToken,
        @Body body: RequestBody,
    ): ItemWithRevision

    @PUT("list/{id}")
    suspend fun updateItem(
        @Path("id") path: String,
        @Body body: RequestBody,
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
        @Header("Authorization") auth: String = RuntimeConstants.OAthWithToken
    ): ItemWithRevision

    @DELETE("list/{id}")
    suspend fun deleteItem(
        @Path("id") path: String,
        @Header("X-Last-Known-Revision") revision: Int = RuntimeConstants.lastKnownRevision,
        @Header("Authorization") auth: String = RuntimeConstants.OAthWithToken
    ): ItemWithRevision
}