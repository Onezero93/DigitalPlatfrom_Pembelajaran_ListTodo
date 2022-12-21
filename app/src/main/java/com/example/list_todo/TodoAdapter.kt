package com.example.list_todo


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.list_todo.model.DataItem
import com.example.list_todo.model.ResponseTodo
import com.example.list_todo.networking.ApiTodo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoAdapter(private var listData:  ArrayList<DataItem>):
    RecyclerView.Adapter<TodoAdapter.myviewHolder>() {

//    private lateinit var mClick: OnItemClickListener

    //    interface OnItemClickListener{
//        fun onItemClick(position: Int)
//    }
//    fun setOnItemClickListener(listener:OnItemClickListener){
//        mClick= listener
//    }
    inner class myviewHolder (item: View) : RecyclerView.ViewHolder(item){
        val tl : TextView = item.findViewById(R.id.tv_titel)
        val ct : TextView = item.findViewById(R.id.tv_conten)
        val dl : ImageView = item.findViewById(R.id.img_delet)
        val up : ImageView = item.findViewById(R.id.img_update)
//        val id : TextView = item.findViewById(R.id.tv_id)

//        val av : ImageView = item.findViewById(R.id.iv_avatar)

//        init {
//            itemView.setOnClickListener{
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return myviewHolder(view)

    }

    override fun onBindViewHolder(holder: myviewHolder, position: Int) {
        val data = listData[position]
//        holder.id.text = data.id.toString()
        holder.tl.text = data.title
        holder.ct.text = data.content
        holder.up.setOnClickListener {
            val i = Intent(holder.itemView.context,Todo_Add::class.java)
            i.putExtra("id",data.id)
            i.putExtra("title",data.title)
            i.putExtra("content",data.content)
            i.putExtra("complete",data.complete)
            holder.itemView.context.startActivity(i)
        }
        holder.dl.setOnClickListener {
            ApiTodo.endpoint.deletTodo("${data.id}").enqueue(object : Callback<ResponseTodo>{
                override fun onResponse(
                    call: Call<ResponseTodo>,
                    response: Response<ResponseTodo>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(holder.itemView.context, "Delet sukses", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(holder.itemView.context, "Delet Gagal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseTodo>, t: Throwable) {
                    Toast.makeText(holder.itemView.context, "$t", Toast.LENGTH_SHORT).show()
                }
            } )
        }
//        holder.jd.text = data.titel
//        holder.dr.text = data.description
//        holder.sc.text = data.stock.toString()
//        holder.rt.text = data.rating.toString()
//        holder.hr.text = data.price.toString()
//        holder.dc.text = data.discountPercentage.toString()
//        Glide.with(holder.itemView)
//            .load("${data.thumbnail}")
//            .apply (RequestOptions.overrideOf(150,150)).into(holder.av)

    }

    override fun getItemCount() = listData.size

    fun getData(data: List<DataItem>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }
}