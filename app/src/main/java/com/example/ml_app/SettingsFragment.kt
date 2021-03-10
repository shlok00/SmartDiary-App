package com.example.ml_app
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.victor.loading.book.BookLoading


class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.settingsframe,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookLoading: BookLoading = getView()?.findViewById(R.id.bookloading) as BookLoading
        if(!bookLoading.isStart()){
            bookLoading.start();
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