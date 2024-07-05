package com.flasshka.data.network

import com.flasshka.data.network.entities.ItemWithRevision
import com.flasshka.data.network.entities.ListItemsWithRevision
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoListService {
    @GET("list")
    suspend fun getItems(
        @Header("Authorization") auth: String = ServiceConstants.OAthWithToken.getFullTokenValue()
    ): ListItemsWithRevision

    @PATCH("list")
    suspend fun updateItems(
        @Body body: RequestBody,
        @Header("X-Last-Known-Revision") revision: Int = ServiceConstants.lastKnownRevision,
        @Header("Authorization") auth: String = ServiceConstants.OAthWithToken.getFullTokenValue()
    ): ListItemsWithRevision

    @GET("list/{id}")
    suspend fun getItemById(
        @Path("id") path: String,
        @Header("Authorization") auth: String = ServiceConstants.OAthWithToken.getFullTokenValue(),
    ): ItemWithRevision

    @POST("list")
    suspend fun addItem(
        @Header("X-Last-Known-Revision") revision: Int = ServiceConstants.lastKnownRevision,
        @Header("Authorization") auth: String = ServiceConstants.OAthWithToken.getFullTokenValue(),
        @Body body: RequestBody,
    ): ItemWithRevision

    @PUT("list/{id}")
    suspend fun updateItem(
        @Path("id") path: String,
        @Body body: RequestBody,
        @Header("X-Last-Known-Revision") revision: Int = ServiceConstants.lastKnownRevision,
        @Header("Authorization") auth: String = ServiceConstants.OAthWithToken.getFullTokenValue()
    ): ItemWithRevision

    @DELETE("list/{id}")
    suspend fun deleteItem(
        @Path("id") path: String,
        @Header("X-Last-Known-Revision") revision: Int = ServiceConstants.lastKnownRevision,
        @Header("Authorization") auth: String = ServiceConstants.OAthWithToken.getFullTokenValue()
    ): ItemWithRevision

    @GET("authorize?response_type=token")
    suspend fun get(): Call<ResponseBody>
}