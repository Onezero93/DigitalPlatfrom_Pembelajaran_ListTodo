package com.example.list_todo.Constrak

import com.example.list_todo.model.ResponseTodo

interface MainConstrak {
    interface Presenter {
        fun presenter(
        )
    }
    interface View{
        fun initActivity()
        fun initlistener()
        fun ResponseList(response: ResponseTodo)
        fun onMassage(message:String)
        fun onLoding(onloding: Boolean)
    }
}