package com.cain.androidpro.widget.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cain.androidpro.databinding.RvItemTestBinding
import com.orhanobut.logger.Logger

class RVAdapter(val items: MutableList<RvItemEntity> = mutableListOf()) : RecyclerView.Adapter<RVAdapter.ItemVH>() {
    companion object {
        private const val TAG = "RVAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        Logger.t(TAG).d("onCreateViewHolder#")
        return ItemVH(RvItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        Logger.t(TAG).d("getItemCount#")
        return items.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        Logger.t(TAG).d("onBindViewHolder# holder = ${holder.hashCode()}, position = $position")
        holder.viewBinding.tvText.text = items[position].testTxt
    }

    class ItemVH(val viewBinding: RvItemTestBinding) : RecyclerView.ViewHolder(viewBinding.root)
}