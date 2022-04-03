package com.app.catapi.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.catapi.databinding.ItemCatBinding
import com.app.catapi.model.CatItem
import com.squareup.picasso.Picasso
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CatAdapter(
    private val listener: (CatItem) -> Unit
) : PagingDataAdapter<CatItem, CatAdapter.CatViewHolder>(Differ) {

    private object Differ : DiffUtil.ItemCallback<CatItem>() {
        override fun areItemsTheSame(oldItem: CatItem, newItem: CatItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatItem, newItem: CatItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(inflater, parent, false)
        return CatViewHolder(binding) { pos ->
            getItem(pos)?.let { listener(it) }
        }
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class CatViewHolder(
        private val binding: ItemCatBinding,
        clickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), KoinComponent {

        private val picasso: Picasso by inject()

        init {
            binding.root.setOnClickListener {
                clickListener(absoluteAdapterPosition)
            }
        }

        fun bind(catItem: CatItem) {
            picasso
                .load(catItem.src)
                .fit()
                .centerCrop()
                .into(binding.ivCat)
        }
    }
}