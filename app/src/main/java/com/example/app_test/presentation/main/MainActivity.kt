package com.example.app_test.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_test.R
import com.example.app_test.data.model.RepositoryModel
import com.example.app_test.databinding.ActivityMainBinding
import com.example.app_test.util.UserListDiffCallback

import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var adapter: MainAdapter

    private val listRepositories: MutableList<RepositoryModel> = ArrayList()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mainViewModel)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupRecycler()
        setupObserver()
    }

    private fun setupObserver() {
        mainViewModel.loading.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        mainViewModel.updateList.observe(this, Observer {
            val newList = ArrayList<RepositoryModel>()
            newList.addAll(listRepositories)
            newList.addAll(it)
            val result = fetchDiff(listRepositories, newList)
            listRepositories.addAll(it)
            result.dispatchUpdatesTo(adapter)
        })

        mainViewModel.error.observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE

            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT)
                .show()
        })
    }


    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        adapter = MainAdapter(listRepositories)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val pastVisiblesItems: Int
                val visibleItemCount: Int
                val totalItemCount: Int
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount;
                    totalItemCount = layoutManager.itemCount;
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (mainViewModel.loading.value == false)
                            mainViewModel.fetchRepositories()
                    }
                }
            }
        })
    }

    private fun fetchDiff(listUser: MutableList<RepositoryModel>, newList: List<RepositoryModel>) =
        DiffUtil.calculateDiff(UserListDiffCallback(listUser, newList))

}
