package com.makeathon.handyapp.ui.settings

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.makeathon.handyapp.databinding.ListitemSettingsBinding
import com.makeathon.handyapp.models.SettingsItem

class SettingsAdapter(private val context: Context, private val itemList: List<SettingsItem>) :
    RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val viewBinding = ListitemSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class SettingsViewHolder(private val viewBinding: ListitemSettingsBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: SettingsItem){
            viewBinding.apply {
                settingsIcon.setImageResource(item.icon)
                settingsTitle.text = item.title
            }
        }
    }
}
