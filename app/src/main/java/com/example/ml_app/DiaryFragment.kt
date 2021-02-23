package com.example.ml_app

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.content.Context
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import org.w3c.dom.Text
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DiaryFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.diaryframe,
                container, false)
    }

    private val ttsc: TextToSpeech by lazy{
    TextToSpeech(context!!.applicationContext , TextToSpeech.OnInitListener { status ->
        if (status == TextToSpeech.SUCCESS) {
            ttsc.language = Locale.forLanguageTag("en")
        }

    })}

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val speechv : TextView = getView()?.findViewById(R.id.voiceInput) as TextView
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking!")
        var micbutton = getView()?.findViewById(R.id.btnSpeak) as ImageButton
        micbutton.setOnClickListener {
            try{
                startActivityForResult(intent,100)
            }
            catch(e: ActivityNotFoundException){
                Toast.makeText(getActivity(), "Does not support mic", Toast.LENGTH_LONG).show()
            }

        }


        var speakerbutton = getView()?.findViewById(R.id.btntext) as ImageButton
        speakerbutton.setOnClickListener {
            val textsp = speechv.text.toString().trim()
            //Toast.makeText(getActivity(), textsp, Toast.LENGTH_LONG).show()
            if (textsp.isNotEmpty())
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {ttsc.speak(textsp, TextToSpeech.QUEUE_FLUSH, null, "a")}
                else
                {ttsc.speak(textsp, TextToSpeech.QUEUE_FLUSH, null)}
            }
            else{
                Toast.makeText(getActivity(), "No Text found", Toast.LENGTH_SHORT).show()
            }
        }

        var a = 0
        var transbutton = getView()?.findViewById(R.id.btntran) as ImageButton
        transbutton.setOnClickListener {
            if(a%2==0)
            {   a+=1
                transbutton.setBackgroundResource(R.drawable.gradhin)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi")
            }
            else
            {   a+=1
                transbutton.setBackgroundResource(R.drawable.gradients)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en")}

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val speechv : TextView = getView()?.findViewById(R.id.voiceInput) as TextView
        when (requestCode)
        {
            100 -> {if(resultCode== Activity.RESULT_OK && data!=null){
                var result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                speechv.setText(result?.get(0))
            }}
        }
    }

    companion object{
        @JvmStatic
        fun newInstance() =
                DiaryFragment().apply {
                    arguments = Bundle().apply {}
                }
    }

}