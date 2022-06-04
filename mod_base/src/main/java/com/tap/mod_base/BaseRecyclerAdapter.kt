package com.tap.mod_base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tap.utils.Inflate

@Suppress("UNCHECKED_CAST")
abstract class BaseRecyclerAdapter<DATA, VB : ViewBinding> constructor(
    private val inflate: Inflate<VB>,
    private val onBind: ((VB, DATA, Int) -> Unit)? = null,
    private val isAnimated: Boolean? = false
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<DATA?> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(inflate.invoke(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list.size > 0 && list.size > position && list[position] != null)
            (holder as BaseRecyclerAdapter<DATA, VB>.ViewHolder).bind(list[position]!!)
    }

    override fun getItemCount() = list.size

    fun getItem(position: Int): DATA? {
        return list[position]
    }

    fun addItem(item: DATA?) {
        list.add(item)
        notifyItemInserted(list.size)
    }

    fun addItems(items: List<DATA>?) {
        items?.forEach {
            addItem(it)
        }
    }

    fun getList() = list

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DATA) {
            if (isAnimated == true) {
                binding.root.animation =
                    AnimationUtils.loadAnimation(
                        binding.root.context,
                        R.anim.recycler_view_vertical_2
                    )
            }
            onBind?.let { it(binding, data, bindingAdapterPosition) }
        }
    }
}

