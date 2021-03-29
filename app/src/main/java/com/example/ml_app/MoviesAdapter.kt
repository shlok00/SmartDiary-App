package com.example.ml_app
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.random.Random

import org.w3c.dom.Text

class MoviesAdapter(private val data: List<Movies>) : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardentry, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(property: Movies){
            val title = view.findViewById(R.id.textViewTitle) as TextView
            val imageView = view.findViewById(R.id.cards) as RelativeLayout
            //val imageView = view.findViewById(R.id.imageViewc) as ImageView

            title.text = "20 Feb"

            val l = listOf(R.drawable.entryim1,R.drawable.entryim2,R.drawable.entryim3,R.drawable.entryim4,R.drawable.entryim5)
            var f = l.random()
            imageView.setBackgroundResource(f);
        }
    }


}