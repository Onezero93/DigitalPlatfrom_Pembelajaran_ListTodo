package com.example.list_todo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.list_todo.Adapter.TodoAdapter
import com.example.list_todo.databinding.ActivityMainBinding
import com.example.list_todo.model.DataItem
import com.example.list_todo.model.ResponseTodo
import com.example.list_todo.networking.ApiTodo
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val mainAdapter = TodoAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fbTODadd.setOnClickListener{
            startActivity(Intent(this@MainActivity, Todo_Add::class.java))
        }
//        mainAdapter.setOnItemClickListener(object : ProdukAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//
//            }
//        })
        supportActionBar?.hide()
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = mainAdapter
        sf_content.setOnRefreshListener {
            getData()
        }
        getData()
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData() {
        binding.sfContent.isRefreshing = false
        ApiTodo.endpoint.getData().enqueue(object : Callback<ResponseTodo> {
            override fun onResponse(call: Call<ResponseTodo>, response: Response<ResponseTodo>) {
                if(response.isSuccessful){
                    val responseGet:ResponseTodo? = response.body()
                    onResultData(responseGet!!)
                    binding.sfContent.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ResponseTodo>, t: Throwable) {

            }

        })
    }
    private fun onResultData(responseGet: ResponseTodo) {
        val vertical = responseGet.data
        mainAdapter.getData(vertical as List<DataItem>)
        binding.sfContent.isRefreshing = true
    }
}