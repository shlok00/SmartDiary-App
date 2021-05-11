
package com.example.ml_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.google.firebase.database.*
import java.util.*


var datenow = "wqw"
class SearchFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.searchframe,
            container, false
        )
    }

    @SuppressLint("WrongConstant", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchbar = view.findViewById(R.id.searchbar) as EditText
        var calbut = getView()?.findViewById(R.id.calbut) as Button
        calbut.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val MONTHS = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
            val dpd = (getActivity()?.let { it1 ->
                DatePickerDialog(
                    it1,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        var k = MONTHS[monthOfYear]
                        datenow = "$dayOfMonth $k $year"
                        searchbar.setText(datenow)
                        //Toast.makeText(getActivity(), datenow, Toast.LENGTH_SHORT).show()
                    },
                    year,
                    month,
                    day
                )
            }).also {
                it?.show()
            }



        }


        val recyclerview = view.findViewById(R.id.recyclerViewDiary) as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.setHasFixedSize(true)
        val userlist = arrayListOf<Entry>()
        val root = FirebaseDatabase.getInstance().getReference().child("Textsaving")
        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(Entry::class.java)
                        userlist.add(user!!)
                    }
                    Collections.reverse(userlist)
                    recyclerview.adapter = MyAdapter(userlist)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


        searchbar.addTextChangedListener(object : TextWatcher {

            @SuppressLint("WrongViewCast")
            override fun afterTextChanged(s: Editable) {
                val filteredList = arrayListOf<Entry>()
                //Log.e("lols", filteredList.toString())
                val nsf = getView()?.findViewById(R.id.nsfwr) as TextView
                val root = FirebaseDatabase.getInstance().getReference().child("Textsaving")
                root.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val user = userSnapshot.getValue(Entry::class.java)
                                if (user != null) {
                                    if(user.diaryentry?.toLowerCase()?.contains(s.toString().toLowerCase()) == true)
                                        filteredList.add(user!!)
                                    else if(user.time?.toLowerCase()?.contains(s.toString().toLowerCase()) == true)
                                        filteredList.add(user!!)
                                }
                            }
                            Collections.reverse(filteredList)
                            recyclerview.adapter = MyAdapter(filteredList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }


                })


            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {


            }
        })
    }
    companion object{
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {}
            }}

}


