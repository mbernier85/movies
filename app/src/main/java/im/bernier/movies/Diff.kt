package im.bernier.movies

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Michael on 2019-12-30.
 */
abstract class Diff<T>(protected val oldList: List<T>, protected val newList: List<T>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}