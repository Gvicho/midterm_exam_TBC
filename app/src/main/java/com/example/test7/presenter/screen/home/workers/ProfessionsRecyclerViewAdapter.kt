package com.example.test7.presenter.screen.home.workers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test7.databinding.ItemButtonWorkersProfessionOptionBinding

class ProfessionsRecyclerViewAdapter(
    private val data:List<String>,
    private val listener:ItemClickCallBack
): RecyclerView.Adapter<ProfessionsRecyclerViewAdapter.ProfessionButtonViewHolder>() {

    inner class ProfessionButtonViewHolder(private val binding: ItemButtonWorkersProfessionOptionBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            val profession = data[position]
            binding.root.text = profession
            setItemOnClickListener(profession)
        }
        private fun setItemOnClickListener(profession:String){
            binding.root.setOnClickListener {
                listener.onButtonClick(profession)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessionButtonViewHolder {
        Log.d("ItemRecycler","Item was added!!!")
        return ProfessionButtonViewHolder(ItemButtonWorkersProfessionOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProfessionButtonViewHolder, position: Int) {
        holder.bind(position)
    }

}