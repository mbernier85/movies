package im.bernier.movies.cast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import im.bernier.movies.BaseAdapter
import im.bernier.movies.GlideApp
import im.bernier.movies.R
import im.bernier.movies.databinding.ItemPersonBinding
import im.bernier.movies.imageUrl

class CastAdapter(casts: List<Cast>, private val limit: Int = 0, private val onClick: (Cast?) -> Unit) : BaseAdapter<Cast, BaseAdapter.BaseViewHolder<Cast>>(casts) {

    override fun getItemViewType(position: Int): Int {
        return if (limit > 0 && limit == position) {
            R.layout.item_button
        } else {
            R.layout.item_person
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Cast> {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == R.layout.item_person) {
            val binding: ItemPersonBinding = DataBindingUtil.inflate(inflater, R.layout.item_person, parent, false)
            val holder = CastViewHolder(binding.root)
            binding.root.setOnClickListener {
                onClick.invoke(list[holder.adapterPosition])
            }
            return holder
        } else {
            val holder = CastLoadMore(inflater.inflate(R.layout.item_button, parent, false))
            holder.itemView.setOnClickListener {
                onClick.invoke(null)
            }
            return holder
        }
    }

    override fun getItemCount(): Int {
        return if (limit == 0) {
            list.size
        } else {
            Math.min(list.size, limit) + 1
        }
    }

    class CastLoadMore(itemView: View): BaseViewHolder<Cast>(itemView) {
        override fun bind(item: Cast) {

        }
    }

    class CastViewHolder(itemView: View) : BaseViewHolder<Cast>(itemView) {
        override fun bind(item: Cast) {
            itemView.findViewById<TextView>(R.id.textViewPersonName).text = item.name
            itemView.findViewById<TextView>(R.id.textViewPersonCharacter).text = item.character
            val imageView = itemView.findViewById<ImageView>(R.id.imageViewPersonPicture)
            item.profile_path?.let {
                GlideApp.with(imageView).load(imageUrl(it)).dontTransform().centerCrop().into(imageView)
            }
        }
    }
}