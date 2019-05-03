package im.bernier.movies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import im.bernier.movies.cast.Cast

abstract class BaseAdapter<T, V : BaseAdapter.BaseViewHolder<T>?>(var list: List<T>) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    fun update(list: List<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = list.getOrNull(position)
        item?.let {
            holder.bind(it)
        }
    }

    abstract class BaseViewHolder<I>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: I)
    }
}