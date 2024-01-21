package com.example.test7.presenter.screen.home.workers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test7.databinding.ItemProfessionsListRecyclerBinding
import com.example.test7.databinding.ItemWorkerBinding
import com.example.test7.presenter.model.workers_list.ItemType
import com.example.test7.presenter.model.workers_list.WorkersListObjectPresenter


class WorkersListRecyclerViewAdapter(
    private val listener: ItemClickCallBack
) : ListAdapter<WorkersListObjectPresenter, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {
    lateinit var myAdaper: ProfessionsRecyclerViewAdapter

    companion object {

        const val Item_Type_Recycler = 1
        const val Item_Type_Worker = 2



        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WorkersListObjectPresenter>() {
            override fun areItemsTheSame(oldItem: WorkersListObjectPresenter, newItem: WorkersListObjectPresenter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WorkersListObjectPresenter, newItem: WorkersListObjectPresenter): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class WorkerItemViewHolder(private val binding: ItemWorkerBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val listObject = currentList[position]
            val worker = listObject.worker


            binding.apply {
                worker?.let {worker->
                    worker.avatar?.let {
                        Glide.with(itemView.context)
                            .load(it)
                            .into(binding.workerImage)
                    }


                    tvName.text = worker.name
                    tvProfession.text = worker.profession
                    tvRate.text = worker.rate.toString()
                    tvAverageHours.text = worker.averageHoursWorked.toString()
                }
            }

            bindClickListener(listObject.id)
        }

        private fun bindClickListener(itemId:Int){
            binding.root.setOnClickListener{
                listener.onItemClick(itemId)
            }
        }

    }

    inner class ProfessionsRecyclerViewHolder(private val binding: ItemProfessionsListRecyclerBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val professionsList = currentList[position].professionsList?: emptyList()
            myAdaper = ProfessionsRecyclerViewAdapter(professionsList,listener)
            binding.professionsRecycler.adapter = myAdaper
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            ItemType.PROFESSIONS_RECYCLER->{
                Item_Type_Recycler
            }
            ItemType.WORKER_ITEM->{
                Item_Type_Worker
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("tag123","onCreate")
        return when (viewType) {
            Item_Type_Worker -> WorkerItemViewHolder(ItemWorkerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            Item_Type_Recycler -> ProfessionsRecyclerViewHolder(ItemProfessionsListRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalStateException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("tag123","onBind")
        if(holder is WorkerItemViewHolder) holder.bind(position)
        else if(holder is ProfessionsRecyclerViewHolder)holder.bind(position)
    }
}

interface ItemClickCallBack{
    fun onItemClick(id:Int)
    fun onButtonClick(profession:String)
}