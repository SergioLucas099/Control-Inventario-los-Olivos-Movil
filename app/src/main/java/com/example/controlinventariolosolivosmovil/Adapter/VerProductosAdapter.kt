package com.example.controlinventariolosolivosmovil.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.controlinventariolosolivosmovil.DetallesEditarProducto
import com.example.controlinventariolosolivosmovil.Model.VerProductosModel
import com.example.controlinventariolosolivosmovil.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class VerProductosAdapter (
    private var productosList: List<VerProductosModel>
) : RecyclerView.Adapter<VerProductosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.producto_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productosList[position]

        Picasso.get().load(producto.imagen).into(holder.imgProducto)
        val id = FirebaseDatabase.getInstance().reference.push().key ?: ""
        holder.nombreProducto.text = producto.nombre
        holder.precioProducto.text = producto.precio ?: "0"

        holder.selectItem.setOnClickListener {
            val intent = Intent(holder.selectItem.context, DetallesEditarProducto::class.java)
            intent.putExtra("id", producto.id)
            intent.putExtra("categoria", producto.categoria)
            intent.putExtra("imagen", producto.imagen)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("precio", producto.precio)
            holder.selectItem.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val selectItem: LinearLayout = itemView.findViewById(R.id.selectItem)
        val imgProducto: ImageView = itemView.findViewById(R.id.ImagenProducto)
        val nombreProducto: TextView = itemView.findViewById(R.id.TextViewNombreProducto)
        var precioProducto: TextView = itemView.findViewById(R.id.TextViewPrecioProducto)
    }
}