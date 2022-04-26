package dev.sertan.hipoproject.ui.home

import androidx.recyclerview.widget.RecyclerView
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.databinding.ItemMemberBinding

internal class MemberViewHolder(private val binding: ItemMemberBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(member: Member, listener: MemberAdapter.MemberListener) {
        binding.textViewMemberName.text = member.name
        binding.itemMember.setOnClickListener { listener.onMemberClicked(member) }
    }
}
