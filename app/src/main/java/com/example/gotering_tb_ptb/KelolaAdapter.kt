package com.example.gotering_tb_ptb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gotering_tb_ptb.data.Pengguna
import com.example.gotering_tb_ptb.databinding.ItemKelolaBinding

class KelolaAdapter(private val itemList: List<Pengguna>, private val onClickItem: (Pengguna) -> Unit) :
    RecyclerView.Adapter<KelolaAdapter.KelolaViewHolder>() {

    inner class KelolaViewHolder(private val binding: ItemKelolaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pengguna) {
            binding.tvNama.text = "Nama: ${item.name}"
            binding.tvAlamat.text = "Alamat: ${item.address}"
            binding.tvJenisPesanan.text = "Jenis Pesanan: ${item.orderType}"
            binding.tvNomerhp.text = "Nomer HP: ${item.phoneNumber}"
            binding.root.setOnClickListener { onClickItem.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KelolaViewHolder {
        val binding = ItemKelolaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KelolaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KelolaViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
