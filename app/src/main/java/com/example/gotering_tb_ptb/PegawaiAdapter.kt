package com.example.gotering_tb_ptb

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.gotering_tb_ptb.data.Pegawai
import com.example.gotering_tb_ptb.databinding.ListItemBinding
import java.util.jar.Attributes.Name

class PegawaiAdapter(private val pegawaiList: ArrayList<Pegawai>) :
    RecyclerView.Adapter<PegawaiAdapter.PegawaiViewHolder>() {

    class PegawaiViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvName :TextView = itemView.findViewById(R.id.nama)
        val tvAlamat :TextView = itemView.findViewById(R.id.alamat)
        val tvTelepon :TextView = itemView.findViewById(R.id.telepon)
        val tvJabatan :TextView = itemView.findViewById(R.id.jabatan)
        val tvDeskripsi :TextView = itemView.findViewById(R.id.deskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PegawaiViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return PegawaiViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: PegawaiViewHolder, position: Int) {
        holder.tvName.text = pegawaiList[position].nama
        holder.tvAlamat.text = pegawaiList[position].alamat
        holder.tvTelepon.text = pegawaiList[position].telepon
        holder.tvJabatan.text = pegawaiList[position].jabatan
        holder.tvDeskripsi.text = pegawaiList[position].deskripsi
    }

    override fun getItemCount(): Int {
        return pegawaiList.size
    }
}