package com.example.list_todo.networking


import com.example.list_todo.model.ResponseTodo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoinTodo {
    //menampilkan Data
    @GET("todo")
    fun getData(): Call<ResponseTodo>

    //ngecrite
    @POST("todo")
    fun addTodo(
        @Body jobj:JsonObject): Call<ResponseTodo>

    //hapus
    @DELETE("todo/{id}")
    fun deletTodo(
        @Path("id") id:String
    ): Call<ResponseTodo>

    //edit
    @PUT("todo/{id}")
    fun Update(
        @Path("id") id:String,
        @Body jobj:JsonObject
    ): Call<ResponseTodo>
}