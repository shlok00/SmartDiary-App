package com.example.ml_app

import android.app.AlertDialog
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


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

        // Set the data and color to the pie chart

        // Set the data and color to the pie chart
        var statcheck = false
        holder.stats.setOnClickListener{
            if(statcheck == false)
            {   holder.piecard.setVisibility(View.VISIBLE)
                statcheck = true}
            else
            {   holder.piecard.setVisibility(View.GONE)
                statcheck = false}
        }
        holder.pieChart.addPieSlice(
            PieModel(
                "A", 40F,
                Color.parseColor("#23C2D1")
            )
        )
        holder.pieChart.addPieSlice(
            PieModel(
                "B", 30F,
                Color.parseColor("#1847BF")
            )
        )
        holder.pieChart.addPieSlice(
            PieModel(
                "C", 15F,
                Color.parseColor("#7F30CC")
            )
        )
        holder.pieChart.addPieSlice(
            PieModel(
                "D", 15F,
                Color.parseColor("#C318CF")
            )
        )

        // To animate the pie chart

        // To animate the pie chart
        holder.pieChart.startAnimation()

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
        val pieChart = view.findViewById(R.id.piechart) as PieChart
        val stats = view.findViewById(R.id.stats) as TextView
        val piecard = view.findViewById(R.id.cardViewGraph) as CardView

        val builder = AlertDialog.Builder(view.context)


    }


}
