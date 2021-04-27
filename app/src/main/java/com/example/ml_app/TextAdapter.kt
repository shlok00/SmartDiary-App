package com.example.ml_app

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


//import androidx.core.graphics.drawable.DrawableCompat.inflate
import com.google.firebase.database.core.Context
//import org.w3c.dom.Text


class TextAdapter(private val mCtx: Context, val layoutResId:Int, val textList: List<saveText>)
    :ArrayAdapter<saveText>(mCtx,layoutResId,textList)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx);
        val view : View = layoutInflater.inflate(layoutResId,  null)

        val textViewName = view.findViewById<TextView>(R.id.textViewTitle);
        val Texts = textList[position]
        textViewName.text = saveText.name

        return view;

    }
}