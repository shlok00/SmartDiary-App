package com.example.ml_app

import android.R.id.edit
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


//import com.bumptech.glide.Glide
//import kotlin.random.Random
//
//import org.w3c.dom.Text

class MyAdapter(private val data: List<Entry>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardentry, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val d = data[position]
        holder.title.text = d.time
        holder.desc.text = d.diaryentry
        var isTextViewClicked = false
        holder.desc.setEllipsize(TextUtils.TruncateAt.END);

        holder.desc.setOnClickListener {
            if (isTextViewClicked) {
                holder.desc.setMaxLines(2);

                isTextViewClicked = false;
                holder.desc.setEllipsize(TextUtils.TruncateAt.END);

            } else {
                holder.desc.setMaxLines(Integer.MAX_VALUE);
                isTextViewClicked = true;
                holder.desc.setEllipsize(null);

            }
        }




        holder.del.setOnClickListener {
            holder.builder.setTitle("Delete Entry")
            holder.builder.setMessage("Are you sure you want to delete the entry?")
            holder.builder.setIcon(android.R.drawable.ic_dialog_alert)
            holder.builder.setPositiveButton("Yes") { dialogInterface, which ->
            }
            holder.builder.setNeutralButton("No") { dialogInterface, which ->
            }

            val alertDialog: AlertDialog = holder.builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()


        }
    }
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

            val title = view.findViewById(R.id.textViewTitle) as TextView
            val desc = view.findViewById(R.id.texts) as TextView
        val del = view.findViewById(R.id.del) as TextView
        val builder = AlertDialog.Builder(view.context)


    }


}

