package com.flasshka.data.items.network

import com.flasshka.data.items.network.entities.BodyItem
import com.flasshka.data.items.network.entities.BodyListItems
import com.flasshka.data.items.network.entities.ItemWithRevision
import com.flasshka.data.items.network.entities.ListItemsWithRevision
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface TodoListService {
    @GET("list")
    suspend fun getItems(): ListItemsWithRevision

    @PATCH("list")
    suspend fun updateItems(@Body body: BodyListItems): ListItemsWithRevision

    @GET("list/{id}")
    suspend fun getItemById(@Path("id") path: String): ItemWithRevision

    @POST("list")
    suspend fun addItem(@Body body: BodyItem): ItemWithRevision

    @PUT("list/{id}")
    suspend fun updateItem(
        @Path("id") path: String,
        @Body body: BodyItem,
    ): ItemWithRevision

    @DELETE("list/{id}")
    suspend fun deleteItem(@Path("id") path: String): ItemWithRevision
}