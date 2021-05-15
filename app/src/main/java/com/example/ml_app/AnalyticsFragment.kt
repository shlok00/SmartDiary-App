package com.example.ml_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position

import com.anychart.enums.TooltipPositionMode





class AnalyticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.analyticsframe,
            container, false
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anyChartView = view.findViewById(R.id.any_chart_view) as AnyChartView
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar))
        val cc = view.findViewById(R.id.idemo) as TextView
        //cc.text = emotlist.toString()
        var text = emotlist

        val frequencyMap: MutableMap<String, Int> = HashMap()
        for (s in text)
        {
            var count = frequencyMap[s]
            if (count == null) count = 0
            frequencyMap[s] = count + 1
        }


        val cartesian: Cartesian = AnyChart.column()

        val data: MutableList<DataEntry> = ArrayList()
        var maxx = frequencyMap.maxByOrNull(Map.Entry<String, Int>::value)
        for(k in frequencyMap.keys) {
            data.add(ValueDataEntry(k, frequencyMap[k]))
        }
        val column: Column = cartesian.column(data)
        val aa = arrayOf("#47ffd7","#41b0f0","#42cef5","#55fae7","#a2ded7")
        column.fill(aa)
        var im = view.findViewById(R.id.emoim) as ImageView
        if(maxx.toString().contains("happy"))
        {cc.text = "HAPPY!"
         im.setImageResource(R.drawable.ic_happy)
         cc.setBackgroundResource(R.drawable.textborder2)
        }
        else if(maxx.toString().contains("sad"))
        {cc.text = "SAD!"
            im.setImageResource(R.drawable.ic_crying)
            cc.setBackgroundResource(R.drawable.textborder2)
        }
        else if(maxx.toString().contains("angry"))
        {cc.text = "ANGRY!"
            im.setImageResource(R.drawable.ic_angry)
            cc.setBackgroundResource(R.drawable.textborder2)
        }
        else if(maxx.toString().contains("surprised"))
        {cc.text = "SURPRISED!"
            im.setImageResource(R.drawable.ic_surprised)
            cc.setBackgroundResource(R.drawable.textborder2)
        }
        else if(maxx.toString().contains("fear"))
        {cc.text = "FEARFUL!"
            im.setImageResource(R.drawable.ic_fear)
            cc.setBackgroundResource(R.drawable.textborder2)
        }
        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(8.0)
            .format("{%Value}{groupsSeparator: }")


       //column.fill( "#7af4ff")
       //column.fill("#5195f5")
       //column.selected("#ffffff")

        cartesian.animation(true)
        cartesian.title("Emotions in the last 14 entries")
        cartesian.title().fontColor("#fca9e1")

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }").rotation(60)
        cartesian.xAxis(0).labels().rotation(60)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("Emotion")
        cartesian.yAxis(0).title("Frequency")
        cartesian.xAxis(0).title().fontColor("#fca9e1")
        cartesian.yAxis(0).title().fontColor("#fca9e1")
        cartesian.xAxis(0).labels().fontColor("#FFFFFF")
        cartesian.yAxis(0).labels().fontColor("#FFFFFF")
          cartesian.background().stroke("#fca9e1")
        cartesian.background().fill("#2b0659")
        anyChartView.setChart(cartesian)


    }
    companion object {
        @JvmStatic
        fun newInstance() =
                AnalyticsFragment().apply {
                    arguments = Bundle().apply {}
                }
    }
}