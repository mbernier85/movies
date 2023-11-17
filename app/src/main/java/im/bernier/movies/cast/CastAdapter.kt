package im.bernier.movies.cast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import im.bernier.movies.BaseAdapter
import im.bernier.movies.Diff
import im.bernier.movies.R
import im.bernier.movies.databinding.ItemPersonBinding
import im.bernier.movies.util.imageUrl

class CastAdapter(casts: List<Cast>, private val limit: Int = 0, private val onClick: (Cast?) -> Unit) : BaseAdapter<Cast, BaseAdapter.BaseViewHolder<Cast>>(casts) {
    override fun update(list: List<Cast>) {
        val diff: Diff<Cast> = object : Diff<Cast>(this.list, list) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].cast_id == newList[newItemPosition].cast_id
            }
        }
        val result = DiffUtil.calculateDiff(diff)
        result.dispatchUpdatesTo(this)
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Cast> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPersonBinding = DataBindingUtil.inflate(inflater, R.layout.item_person, parent, false)
        val holder = CastViewHolder(binding.root)
        binding.root.setOnClickListener {
            onClick.invoke(list[holder.bindingAdapterPosition])
        }
        return holder
    }

    class CastViewHolder(itemView: View) : BaseViewHolder<Cast>(itemView) {
        override fun bind(item: Cast) {
            itemView.findViewById<TextView>(R.id.textViewPersonName).text = item.name
            itemView.findViewById<TextView>(R.id.textViewPersonCharacter).text = item.character
            val imageView = itemView.findViewById<ImageView>(R.id.imageViewPersonPicture)
            item.profile_path?.let {
                Glide.with(imageView).load(it.imageUrl()).dontTransform().centerCrop().into(imageView)
            }
        }
    }
}