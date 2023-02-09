package com.example.list_todo.presenter

import com.example.list_todo.Constrak.MainConstrak
import com.example.list_todo.model.ResponseTodo
import com.example.list_todo.networking.ApiTodo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(val view:MainConstrak.View):MainConstrak.Presenter {
    init {
        view.initActivity()
        view.initlistener()
    }
    override fun presenter() {
        view.onLoding(false)
        ApiTodo.endpoint.getData().enqueue(object : Callback<ResponseTodo> {
            override fun onResponse(call: Call<ResponseTodo>, response: Response<ResponseTodo>) {
                if(response.isSuccessful){
                    val responseGet: ResponseTodo? = response.body()
                    view.ResponseList(responseGet!!)
                    view.onLoding(false)
                }
            }

            override fun onFailure(call: Call<ResponseTodo>, t: Throwable) {
                view.onMassage("$t")
            }

        })
    }
}