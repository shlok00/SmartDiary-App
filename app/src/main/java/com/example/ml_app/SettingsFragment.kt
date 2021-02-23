package com.example.ml_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.settingsframe,
                container, false)
    }
        companion object{
            @JvmStatic
            fun newInstance() =
                    SettingsFragment().apply {
                        arguments = Bundle().apply {}
                    }
    }

}