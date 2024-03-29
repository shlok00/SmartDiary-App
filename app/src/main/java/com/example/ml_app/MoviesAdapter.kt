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
            val cardhold = view.findViewById(R.id.card) as CardView
            val desc = view.findViewById(R.id.texts) as TextView
            val imageView = view.findViewById(R.id.cards) as RelativeLayout
            //val imageView = view.findViewById(R.id.imageViewc) as ImageView

            var isTextViewClicked = false;
            desc.setEllipsize(TextUtils.TruncateAt.END);

            desc.setOnClickListener {
                if (isTextViewClicked) {
                    desc.setMaxLines(2);

                    isTextViewClicked = false;
                    desc.setEllipsize(TextUtils.TruncateAt.END);
                    /*val layoutParams = cardhold.getLayoutParams()
                    layoutParams.height = 350
                    layoutParams.width = MATCH_PARENT
                    cardhold.setLayoutParams(layoutParams)*/
                } else {
                    desc.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                    desc.setEllipsize(null);

                }
            }
            
            title.text = "20 Feb"
            val descr ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididuntLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididuntLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididuntLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididuntLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididuntLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam"
            desc.text = descr

            val del = view.findViewById(R.id.del) as TextView

            del.setOnClickListener {
                val builder = AlertDialog.Builder(view.context)
                //set title for alert dialog
                builder.setTitle("Delete Entry")
                //set message for alert dialog
                builder.setMessage("Are you sure you want to delete the entry?")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                //performing positive action
                builder.setPositiveButton("Yes"){dialogInterface , which ->
                }
                //performing cancel action
                builder.setNeutralButton("No"){dialogInterface , which ->
                }

                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
            /* val l = listOf(
                 R.drawable.entryim1,
                 R.drawable.entryim2,
                 R.drawable.entryim3,
                 R.drawable.entryim5
             )
             var f = l.random()
             desc.setBackgroundResource(f);*/
        }
    }


}

