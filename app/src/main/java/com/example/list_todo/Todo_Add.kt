package com.example.list_todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.list_todo.databinding.ActivityTodoAddBinding
import com.example.list_todo.model.ResponseTodo
import com.example.list_todo.networking.ApiTodo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Todo_Add : AppCompatActivity() {
    private val binding: ActivityTodoAddBinding by lazy {
        ActivityTodoAddBinding.inflate(layoutInflater)
    }
    var id : String? = ""
    var title : String? = ""
    var complete : Boolean = true
    var content : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        addTODO()
        updateTODO()

        val i = intent
        id = i.getStringExtra("id")
        title = i.getStringExtra("title")
        complete = i.getBooleanExtra("complete",true)
        content = i.getStringExtra("content")
        binding.etTitel.setText(title)
        binding.etContent.setText(content)
//        binding.btnEdit.setOnClickListener {
//            Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun updateTODO() {
        binding.btnEdit.setOnClickListener {
            val job = JsonObject()
            job.addProperty("title",binding.etTitel.text.toString())
            job.addProperty("complete",false)
            job.addProperty("content",binding.etContent.text.toString())
            Log.d("JSON","$job")
            ApiTodo.endpoint.Update(id.toString(), job).enqueue(object : Callback<ResponseTodo>{
                override fun onResponse(
                    call: Call<ResponseTodo>,
                    response: Response<ResponseTodo>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(this@Todo_Add, "Add sukses", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@Todo_Add, "Add batal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseTodo>, t: Throwable) {
                    Toast.makeText(this@Todo_Add, "$t", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun addTODO() {
        binding.btnAdd.setOnClickListener {
            var job = JsonObject()
            job.addProperty("title",binding.etTitel.text.toString())
            job.addProperty("complete",true)
            job.addProperty("content",binding.etContent.text.toString())
            Log.d("JSON","$job")
            if (binding.etTitel.text.isEmpty() || binding.etContent.text.isEmpty()){
                Toast.makeText(this, "Maskkan data terlebih dahulu", Toast.LENGTH_SHORT).show()
            }else{
                ApiTodo.endpoint.addTodo(job).enqueue(object :Callback<ResponseTodo>{
                    override fun onResponse(call: Call<ResponseTodo>, response: Response<ResponseTodo>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(this@Todo_Add, "Add sukses", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this@Todo_Add, "Add batal", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseTodo>, t: Throwable) {
                        Toast.makeText(this@Todo_Add, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}