package com.example.hackathon.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.hackathon.databinding.ItemGraphBinding
import com.example.hackathon.databinding.ItemRankingBinding
import com.example.hackathon.gson.RankingListModelSealed


class GraphAdapter(
    data: ArrayList<String>
) : BaseQuickAdapter<String, GraphAdapter.VH>(data) {

    override fun onCreateViewHolder(
        context: Context, parent: ViewGroup, viewType: Int
    ): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(
        holder: VH, position: Int, item: String?
    ) {
        Glide.with(context).load("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png?20210701224649").into( holder.binding.ivGraph)
    }

    class VH(
        parent: ViewGroup,
        val binding: ItemGraphBinding = ItemGraphBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)


}
