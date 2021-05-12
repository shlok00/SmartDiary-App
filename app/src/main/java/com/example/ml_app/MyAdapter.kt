
package com.example.ml_app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.os.Build
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.net.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.beust.klaxon.PathMatcher
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.*


import java.util.*
import java.util.regex.Pattern
import kotlin.coroutines.coroutineContext


//import com.bumptech.glide.Glide
//import kotlin.random.Random
//
//import org.w3c.dom.Text
class MyAdapter(private var data: List<Entry>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()  {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardentry, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }




    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val d = data[position]
        holder.title.text = d.time
        holder.desc.text = d.diaryentry

        //https://ssv-sentiment.herokuapp.com/getEmotion  secret a1Bz0
        val retrofit = Retrofit.Builder()
                .baseUrl("https://ssv-sentiment.herokuapp.com")
                .build()
        val api = retrofit.create(Sentiment::class.java)

        val json = JSONObject()
        json.put("text", d.diaryentry.toString())
        json.put("secret", "a1Bz0")
        val requestBody: RequestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        api.createentry(requestBody).enqueue(
                object : Callback<ResponseBody> {
                    @SuppressLint("ResourceAsColor")
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        if (response!!.isSuccessful) {
                            val resp = response.body()?.string()
                            //holder.desc.text = resp
                            val diaryobj = JSONObject(resp)
                            val nsf0 = diaryobj.getBoolean("nsfw").toString()
                            val emot: JSONObject = diaryobj.getJSONObject("overall_emotion")
                            val happy = emot.getDouble("Happy")
                            val angry = emot.getDouble("Angry")
                            val sad = emot.getDouble("Sad")
                            val surprise = emot.getDouble("Surprise")
                            val fear = emot.getDouble("Fear")
                            val emo = mapOf("happy" to happy,"angry" to angry,"sad" to sad,"surprise" to surprise,"fear" to fear)
                            val maxValue = emo.values.max() // or max() depending on Kotlin version
                            val maxe = emo.filterValues { it == maxValue }.keys.first()
                            val m = emo.filterValues { it == maxValue }.keys
                            //holder.desc.text = m.toString()
                            if (nsf0 == "true")
                            {holder.nsfw.text = "#NSFW"
                                holder.nsfw.setBackgroundResource(R.drawable.nsfw)
                            }
                            else{
                                holder.nsfw.text = "#SFW"
                                holder.nsfw.setBackgroundResource(R.drawable.neutral)
                            }
                            if (m.toString() == "[happy, angry, sad, surprise, fear]")
                            {holder.emote.text = "#NEUTRAL"
                                holder.emote.setBackgroundResource(R.drawable.neutral)
                            }
                            else if (maxe == "fear")
                            {holder.emote.text = "#FEAR"
                                holder.emote.setBackgroundResource(R.drawable.fear)}
                            else if (maxe == "angry")
                            {holder.emote.text = "#ANGRY"
                                holder.emote.setBackgroundResource(R.drawable.angry)}
                            else if (maxe == "surprise")
                            {holder.emote.text = "#SURPRISE"
                                holder.emote.setBackgroundResource(R.drawable.surprise)}
                            else if (maxe == "happy")
                            {holder.emote.text = "#HAPPY"
                                holder.emote.setBackgroundResource(R.drawable.happy)}
                            else if (maxe == "sad")
                            {holder.emote.text = "#SAD"
                                holder.emote.setBackgroundResource(R.drawable.sad)}
                            //holder.desc.text = emot.toString()
                            // get employee name and salary


                            holder.pieChart.addPieSlice(
                                    PieModel(
                                            "Angry", angry.toFloat(),
                                            Color.parseColor("#23C2D1")
                                    )
                            )
                            holder.pieChart.addPieSlice(
                                    PieModel(
                                            "Fear", fear.toFloat(),
                                            Color.parseColor("#1847BF")
                                    )
                            )
                            holder.pieChart.addPieSlice(
                                    PieModel(
                                            "Happy", happy.toFloat(),
                                            Color.parseColor("#7F30CC")
                                    )
                            )
                            holder.pieChart.addPieSlice(
                                    PieModel(
                                            "Sad", sad.toFloat(),
                                            Color.parseColor("#C318CF")
                                    )
                            )
                            holder.pieChart.addPieSlice(
                                    PieModel(
                                            "Surprise", surprise.toFloat(),
                                            Color.parseColor("#CA154E")
                                    )
                            )
                            holder.pieChart.addPieSlice(
                                    PieModel(
                                            "Neutral", surprise.toFloat(),
                                            Color.parseColor("#ffffff")
                                    )
                            )


                        } else {

                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Log.d("postreqerr", "failure--" + t.toString())
                    }
                }
        )


        var isTextViewClicked = false
        holder.desc.setEllipsize(TextUtils.TruncateAt.END);

        var statcheck = false
        holder.stats.setOnClickListener{
            if(statcheck == false)
            {   holder.piecard.setVisibility(View.VISIBLE)
                holder.pieChart.startAnimation()
                statcheck = true}
            else
            {   holder.piecard.setVisibility(View.GONE)
                statcheck = false}
        }

        // To animate the pie chart

        // To animate the pie chart

        holder.desc.setOnClickListener {
            if (isTextViewClicked) {
                holder.desc.setMaxLines(2);

                isTextViewClicked = false;
                holder.desc.setEllipsize(TextUtils.TruncateAt.END)

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
                val ref = FirebaseDatabase.getInstance().reference
                val applesQuery: Query =
                        ref.child("Textsaving").orderByChild("diaryentry").equalTo(d.diaryentry)
                applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (appleSnapshot in dataSnapshot.children) {
                            appleSnapshot.ref.removeValue()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException())
                    }
                })
            }
            holder.builder.setNeutralButton("No") { dialogInterface, which ->
            }

            val alertDialog: AlertDialog = holder.builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()


        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

        val title = view.findViewById(R.id.textViewTitle) as TextView
        val desc = view.findViewById(R.id.texts) as TextView
        val emote = view.findViewById(R.id.emote) as TextView
        val nsfw = view.findViewById(R.id.nsfwr) as TextView
        val del = view.findViewById(R.id.del) as TextView
        val pieChart = view.findViewById(R.id.piechart) as PieChart
        val stats = view.findViewById(R.id.stats) as TextView
        val piecard = view.findViewById(R.id.cardViewGraph) as CardView
        val builder = AlertDialog.Builder(view.context)

    }



}