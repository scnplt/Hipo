package dev.sertan.hipoproject.ui.home

import androidx.recyclerview.widget.DiffUtil
import dev.sertan.hipoproject.data.model.Member

internal object MemberDiffUtilCallback : DiffUtil.ItemCallback<Member>() {
    override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean = oldItem == newItem
}
