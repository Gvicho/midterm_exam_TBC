package com.example.test7.presenter.screen.employ_details

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test7.databinding.ItemButtonWorkersProfessionOptionBinding
import com.example.test7.databinding.ItemChartBinding
import com.example.test7.databinding.ItemDateBinding
import com.example.test7.databinding.ItemWorkerBinding
import com.example.test7.databinding.ItemWorkersDetailsBinding
import com.example.test7.presenter.model.worker_details.WorkerDetailsListObject
import com.example.test7.presenter.model.worker_details.WorkerDetailsObjectType
import com.example.test7.presenter.screen.home.workers.ProfessionsRecyclerViewAdapter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.math.roundToInt

class DetailsRecyclerViewAdapter(
    private val data:List<WorkerDetailsListObject>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val Item_Type_Info = 1
        const val Item_Type_Date = 2
        const val Item_Type_Chart = 3
    }

    inner class ChartViewHolder(private val binding: ItemChartBinding):
        RecyclerView.ViewHolder(binding.root){

        lateinit var lineList :ArrayList<Entry>
        lateinit var lineDataSet: LineDataSet
        lateinit var lineData: LineData

        fun bind(position: Int) {
            val chartData = data[position]
            lineList = ArrayList()
            chartData.rateData?.monthList?.let {
                putListsInLineData(it,chartData.rateData.ratingList!!)
            }

            lineDataSet = LineDataSet(lineList,"Rate Chart")
            lineData = LineData(lineDataSet)

            binding.lineChart.data =lineData
            lineDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
            lineDataSet.valueTextColor = Color.BLUE
            lineDataSet.valueTextSize = 20f
        }

        private fun putListsInLineData(monthList:List<Float>,rateList:List<Float>){
            val listSize = monthList.size
            for(i in 0..<listSize){
                lineList.add(Entry(monthList[i],rateList[i]))
            }
        }

    }

    inner class InfoViewHolder(private val binding: ItemWorkersDetailsBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            val generalInfo = data[position].workerInfo
            generalInfo?.let {worker->
                binding.apply {
                    worker.avatar?.let {
                        Glide.with(itemView.context)
                            .load(it)
                            .into(binding.workerImage)
                    }


                    tvName.text = worker.name
                    tvProfession.text = worker.profession
                    tvAverageHours.text = worker.averageHoursWorked.toString()

                    worker.rate?.let {
                        showStars(it.roundToInt())
                    }
                }
            }
        }

        private fun showStars(amount:Int){
            when(amount){
                1 ->{
                    binding.apply {
                        oneStar.visibility = View.VISIBLE
                    }
                }
                2 ->{
                    binding.apply {
                        oneStar.visibility = View.VISIBLE
                        twoStar.visibility = View.VISIBLE
                    }
                }
                3 ->{
                    binding.apply {
                        oneStar.visibility = View.VISIBLE
                        twoStar.visibility = View.VISIBLE
                        threeStar.visibility = View.VISIBLE
                    }
                }
                4 ->{
                    binding.apply {
                        oneStar.visibility = View.VISIBLE
                        twoStar.visibility = View.VISIBLE
                        threeStar.visibility = View.VISIBLE
                        fourStar.visibility = View.VISIBLE
                    }
                }
                else ->{
                    binding.apply {
                        oneStar.visibility = View.VISIBLE
                        twoStar.visibility = View.VISIBLE
                        threeStar.visibility = View.VISIBLE
                        fourStar.visibility = View.VISIBLE
                        fiveStar.visibility = View.VISIBLE

                    }
                }
            }
        }
    }

    inner class DateViewHolder(private val binding: ItemDateBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int) {
            val dateInfo = data[position].lastWorkDate
            dateInfo?.let {worker->
                binding.apply {
                    tvMonth.text = worker.lastMonth.toString()
                    tvYear.text = worker.lastYear.toString()
                    tvStatus.text= worker.status
                }

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position].itemType){
            WorkerDetailsObjectType.CHART -> Item_Type_Chart
            WorkerDetailsObjectType.DATE -> Item_Type_Date
            WorkerDetailsObjectType.INFO -> Item_Type_Info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("ItemRecycler","Item was added!!!")
        return when (viewType) {
            Item_Type_Info -> {InfoViewHolder(ItemWorkersDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))}
            Item_Type_Date -> {DateViewHolder(ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))}
            else -> {ChartViewHolder(ItemChartBinding.inflate(LayoutInflater.from(parent.context), parent, false))}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is InfoViewHolder)holder.bind(position)
        else if (holder is DateViewHolder)holder.bind(position)
        else if (holder is ChartViewHolder)holder.bind(position)
    }

    override fun getItemCount(): Int = data.size


}