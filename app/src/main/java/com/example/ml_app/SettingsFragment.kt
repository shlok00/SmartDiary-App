package com.example.ml_app
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.settingsframe,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var hospital = view.findViewById(R.id.hospnum) as TextView
        var police = view.findViewById(R.id.polnum) as TextView
        var mental = view.findViewById(R.id.mennum) as TextView
        hospital.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:112")
            startActivity(callIntent)
        }

        police.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:100")
            startActivity(callIntent)
        }
        mental.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:+919152987821")
            startActivity(callIntent)
        }
    }

        companion object{
            @JvmStatic
            fun newInstance() =
                    SettingsFragment().apply {
                        arguments = Bundle().apply {}
                    }
    }

}