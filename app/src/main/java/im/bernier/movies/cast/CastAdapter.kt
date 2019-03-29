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
import im.bernier.movies.imageUrl

class CastAdapter(casts: List<Cast>) : BaseAdapter<Cast, CastAdapter.CastViewHolder>(casts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: im.bernier.movies.databinding.ItemPersonBinding = DataBindingUtil.inflate(inflater, R.layout.item_person, parent, false)
        return CastViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return Math.min(list.size, 5)
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