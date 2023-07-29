package aaa.app.android.sqlroomsample.fragments

import aaa.app.android.sqlroomsample.databinding.FragmentHomeFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class HomeFragment : Fragment() {

    var _binding: FragmentHomeFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*    private fun generateBarData(): BarData? {
            val entries1: ArrayList<BarEntry> = ArrayList()
            val entries2: ArrayList<BarEntry> = ArrayList()
            for (index in 0 until count) {
                entries1.add(BarEntry(0, getRandom(25, 25)))

                // stacked
                entries2.add(BarEntry(0, floatArrayOf(getRandom(13, 12), getRandom(13, 12))))
            }
            val set1 = BarDataSet(entries1, "Bar 1")
            set1.setColor(Color.rgb(60, 220, 78))
            set1.setValueTextColor(Color.rgb(60, 220, 78))
            set1.setValueTextSize(10f)
            set1.setAxisDependency(YAxis.AxisDependency.LEFT)
            val set2 = BarDataSet(entries2, "")
            set2.setStackLabels(arrayOf("Stack 1", "Stack 2"))
            set2.setColors(Color.rgb(61, 165, 255), Color.rgb(23, 197, 255))
            set2.setValueTextColor(Color.rgb(61, 165, 255))
            set2.setValueTextSize(10f)
            set2.setAxisDependency(YAxis.AxisDependency.LEFT)
            val groupSpace = 0.06f
            val barSpace = 0.02f // x2 dataset
            val barWidth = 0.45f // x2 dataset
            // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
            val d = BarData(set1, set2)
            d.setBarWidth(barWidth)

            // make this BarData object grouped
            d.groupBars(0, groupSpace, barSpace) // start at x = 0
            return d
        }*/

}