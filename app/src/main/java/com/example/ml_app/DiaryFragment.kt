package com.example.ml_app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.text.SimpleDateFormat
import java.util.*

public lateinit var ref: DatabaseReference
public lateinit var textsList:MutableList<saveText>
@SuppressLint("StaticFieldLeak")
public lateinit var listView : ListView
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DiaryFragment : Fragment() {



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
                R.layout.diaryframe,
                container, false
        )
    }

    private val ttsc: TextToSpeech by lazy{
        TextToSpeech(context!!.applicationContext, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsc.language = Locale.forLanguageTag("en")
            }

        })}

    @SuppressLint("WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentTime: String = SimpleDateFormat(
                "EEE, d MMM yyyy, HH:mm aaa",
                Locale.getDefault()
        ).format(Date())
        val datehead : TextView = getView()?.findViewById(R.id.datehead) as TextView
        datehead.setText(currentTime)
        val speechv : EditText = getView()?.findViewById(R.id.voiceInput) as EditText
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking!")
        var micbutton = getView()?.findViewById(R.id.btnSpeak) as ImageButton
        micbutton.setOnClickListener {
            try{
                startActivityForResult(intent, 100)
            }
            catch (e: ActivityNotFoundException){
                Toast.makeText(getActivity(), "Does not support mic!", Toast.LENGTH_LONG).show()
            }

        }

        var isSpeakerClicked = false;
        var speakerbutton = getView()?.findViewById(R.id.btntext) as ImageButton
        speakerbutton.setOnClickListener {
            val textsp = speechv.text.toString().trim()
            //Toast.makeText(getActivity(), textsp, Toast.LENGTH_LONG).show()
            if (textsp.isNotEmpty())
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {   if (isSpeakerClicked)
                {   isSpeakerClicked = false
                    ttsc.speak(textsp, TextToSpeech.QUEUE_FLUSH, null, "a")
                    speakerbutton.setBackgroundResource(R.drawable.gradhin)

                }
                else
                {   isSpeakerClicked = true
                    ttsc.stop()
                    speakerbutton.setBackgroundResource(R.drawable.gradients)
                }
                }

                else
                {ttsc.speak(textsp, TextToSpeech.QUEUE_FLUSH, null)

                }
            }

            else{
                Toast.makeText(getActivity(), "No Text found", Toast.LENGTH_SHORT).show()
            }
        }

       var a = 0

        val source = speechv.text.toString()
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        val enhi = Translation.getClient(options)
        getLifecycle().addObserver(enhi)
        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        enhi.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                           }
            .addOnFailureListener { exception ->
                           }
        val options2 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.HINDI)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        val hien = Translation.getClient(options2)
        getLifecycle().addObserver(hien)
        hien.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                           }
            .addOnFailureListener { exception ->
                            }
        var transbutton = getView()?.findViewById(R.id.btntran) as ImageButton
        transbutton.setOnClickListener {


            if(a==0 || a%2==0)
            {   a+=1
                transbutton.setBackgroundResource(R.drawable.gradhin)
                if(speechv.text.toString() != null){
                    //Toast.makeText(getActivity(),speechv.text.toString(), Toast.LENGTH_SHORT).show()
                    enhi.translate(speechv.text.toString())
                        .addOnSuccessListener { translatedText ->
                            speechv.setText(translatedText)
                            //Toast.makeText(getActivity(), translatedText, Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show()
                        }}

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi")
                ttsc.language = Locale.forLanguageTag("hi")

            }
            else
            {   a+=1
                transbutton.setBackgroundResource(R.drawable.gradients)
                if(speechv.text.toString() != null){
                    // Toast.makeText(getActivity(),speechv.text.toString(), Toast.LENGTH_SHORT).show()
                    hien.translate(speechv.text.toString())
                        .addOnSuccessListener { translatedText ->
                            speechv.setText(translatedText)
                            //Toast.makeText(getActivity(), translatedText, Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(getActivity(), "Translation error", Toast.LENGTH_SHORT).show()
                        }}
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en")
                ttsc.language = Locale.forLanguageTag("en")

            }

        }

        ref = FirebaseDatabase.getInstance().getReference("Textsaving")
        var clearbut = getView()?.findViewById(R.id.button2) as Button
        clearbut.setOnClickListener {
            var s = speechv.text.toString()
            speechv.setText("")
        }



        fun savingText() {

            val currentTime: String = SimpleDateFormat(
                "EEE, d MMM yyyy, HH:mm aaa",
                Locale.getDefault()
            ).format(Date())

            val textst = speechv.text.toString()
            if (textst.isEmpty()) (
                    return
                    )

            val textId = ref.push().key

            val savedText = saveText(currentTime,textst)

           if (textId != null) {
                ref.child(textId).child("diaryentry").setValue(textst)
                ref.child(textId).child("time").setValue(currentTime)
            }
        }


        var savebut = getView()?.findViewById(R.id.button) as Button
        savebut.setOnClickListener {
            savingText()
            var s = speechv.text.toString()
            speechv.setText("")
        }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val speechv : EditText = getView()?.findViewById(R.id.voiceInput) as EditText
        when (requestCode)
        {
            100 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    var result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    var s = speechv.text.toString()
                    speechv.setText(s + " " + result?.get(0))

                }
            }
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