
package com.example.ml_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Movie
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.searchframe.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.searchframe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext
import java.util.*

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.searchframe,
            container, false)

}

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var calbut = getView()?.findViewById(R.id.calbut) as Button

        calbut.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = (getActivity()?.let { it1 ->
                DatePickerDialog(
                    it1,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> },
                    year,
                    month,
                    day
                )
            }).also {

                it?.show()
            }
            textsList= mutableListOf()
            listView = findViewById(android.R.id.listView)
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //val p0 = 0
                    if(p0!!.exists()){
                        textsList.clear()
                        for(h in p0.children){
                            val savetext = h.getValue(saveText::class.java)
                            textsList.add(savetext!!)
                        }
                        val adapter = TextAdapter(applicationContext, R.layout.searchframe,textsList)
                        listView.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }


        fun showMovies(movies: List<Movies>) {
            recyclerViewMovies.layoutManager = LinearLayoutManager(activity)
            recyclerViewMovies.adapter = MoviesAdapter(movies)}

        MoviesApi().getAllData().enqueue(object : Callback<List<Movies>> {
            override fun onFailure(call: Call<List<Movies>>, t: Throwable) {
                Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Movies>>, response: Response<List<Movies>>) {
                val movies = response.body()

                movies?.let {
                    showMovies(it)
                }

            }


        })



    }
   /* override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lateinit var manager: RecyclerView.LayoutManager

        manager = LinearLayoutManager(getActivity())
        MoviesApi.retrofitService.getAllData().enqueue(object: Callback<List<Movies>>{
            override fun onResponse(
                call: Call<List<Movies>>,
                response: Response<List<Movies>>
            ) {
                if(response.isSuccessful){
                    var recyclerView = getView()?.findViewById(R.id.recyclerViewMovies) as RecyclerView
                    recyclerView.apply{
                        var myAdapter = MoviesAdapter(response.body()!!)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Movies>>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }*/


companion object{
    @JvmStatic
    fun newInstance() =
        SearchFragment().apply {
            arguments = Bundle().apply {}
        }}

}

