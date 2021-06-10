package com.example.app_test.util

import androidx.recyclerview.widget.DiffUtil
import com.example.app_test.data.model.RepositoryModel

class UserListDiffCallback(
    private val oldList: List<RepositoryModel>,
    private val newList: List<RepositoryModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].equals(newList[newItemPosition])

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].name.equals(newList[newItemPosition].name)

}