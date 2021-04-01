
package com.example.ml_app

import android.annotation.SuppressLint
import android.graphics.Movie
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.searchframe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext

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

/*
        var recyclerView = getView()?.findViewById(R.id.recyclerViewMovies) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<Movies>()

        //adding some dummy data to the list
        users.add(Movies("Belal Khan", "Ranchi Jharkhand"))
        users.add(Movies("Ramiz Khan", "Ranchi Jharkhand"))
        users.add(Movies("Faiz Khan", "Ranchi Jharkhand"))
        users.add(Movies("Yashar Khan", "Ranchi Jharkhand"))

        //creating our adapter
        val adapter = MoviesAdapter(users)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter*/

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

