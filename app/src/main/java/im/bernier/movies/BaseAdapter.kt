package im.bernier.movies

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, V : BaseAdapter.BaseViewHolder<T>?>(var list: List<T>) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    abstract fun update(list: List<T>)

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = list.getOrNull(position)
        item?.let {
            holder.bind(it)
        }
    }

    abstract class BaseViewHolder<I>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: I)
    }
}