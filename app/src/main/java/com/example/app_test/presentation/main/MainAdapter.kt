package com.example.app_test.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_test.R
import com.example.app_test.data.model.RepositoryModel
import com.example.app_test.databinding.ListItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class MainAdapter(val list: MutableList<RepositoryModel>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list.getOrNull(position)?.let {
            ViewHolder(holder.binding).bind(it)
        }
    }

    class ViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepositoryModel) {
            binding.repoName?.text = item.name
            binding.name?.text = item.user.name
            binding.stars.text = item.stars.toString()
            binding.forks.text = item.forks.toString()
            binding.progressBar?.visibility = View.VISIBLE
            Picasso.get()
                .load(item.user.photo)
                .error(R.drawable.ic_round_account_circle)
                .into(binding.picture, object : Callback {
                    override fun onSuccess() {
                        binding.progressBar?.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        binding.progressBar?.visibility = View.GONE
                    }
                })
        }

    }
}