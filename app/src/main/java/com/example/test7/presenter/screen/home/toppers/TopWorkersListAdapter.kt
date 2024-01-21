package com.example.test7.presenter.screen.home.toppers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test7.databinding.ItemTopWorkerBinding
import com.example.test7.presenter.model.WorkerPresenter

class TopWorkersListAdapter(
    private val listener: TopperItemClickCallBack
) :
    ListAdapter<WorkerPresenter, TopWorkersListAdapter.TopWorkerViewHolder>(TopWorkerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopWorkerViewHolder {
        val binding = ItemTopWorkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopWorkerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopWorkerViewHolder, position: Int) {
        getItem(position)
        holder.bind(position)
    }

    inner class TopWorkerViewHolder(private val binding: ItemTopWorkerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val worker = currentList[position]

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
            bindItemClickListener(worker.profession)
        }

        private fun bindItemClickListener(professin:String){
            val onlyProfession = professin.substring(4)
            binding.root.setOnClickListener{
                listener.onItemClicked(onlyProfession)
            }
        }
    }

    class TopWorkerDiffCallback : DiffUtil.ItemCallback<WorkerPresenter>() {
        override fun areItemsTheSame(oldItem: WorkerPresenter, newItem: WorkerPresenter): Boolean {
            return oldItem.profession == newItem.profession // there will be only one from each profession
        }

        override fun areContentsTheSame(oldItem: WorkerPresenter, newItem: WorkerPresenter): Boolean {
            return oldItem == newItem
        }
    }
}

interface TopperItemClickCallBack{
    fun onItemClicked(profession:String)
}