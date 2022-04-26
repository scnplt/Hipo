package dev.sertan.hipoproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.databinding.ItemMemberBinding

internal class MemberAdapter(private val listener: MemberListener) :
    ListAdapter<Member, MemberViewHolder>(MemberDiffUtilCallback), Filterable {

    var data: List<Member> = arrayListOf()
        set(value) {
            field = value
            submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMemberBinding.inflate(inflater, parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(queryText: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = when {
                queryText.isNullOrBlank() -> data
                else -> data.filter { it.name.contains(queryText, true) }
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(queryText: CharSequence?, filterResults: FilterResults) {
            val filteredList = filterResults.values as List<Member>
            submitList(filteredList)
        }
    }

    fun interface MemberListener {
        fun onMemberClicked(member: Member)
    }
}
