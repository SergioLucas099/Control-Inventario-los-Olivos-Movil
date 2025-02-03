package com.example.controlinventariolosolivosmovil.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controlinventariolosolivosmovil.DetallesVentas
import com.example.controlinventariolosolivosmovil.Model.ProductosVendidosModel
import com.example.controlinventariolosolivosmovil.Model.VentasRealizadasModel
import com.example.controlinventariolosolivosmovil.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ProductosVendidosAdapter (
    private var productosVendidosList: List<ProductosVendidosModel>
) : RecyclerView.Adapter<ProductosVendidosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.productos_vendidos_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ventas = productosVendidosList[position]

        Picasso.get().load(ventas.imagen).into(holder.imgProducto)
        holder.NombreProductoVendido.text = "Nombre: ${ventas.nombre}"
        holder.PrecioNormalProductoVendido.text = "Precio: $${ventas.precioFijo.toString()}"
        holder.CantidadProductoVendido.text = "Cantidad: ${ventas.cantidad.toString()}"
        holder.TotalProductoVendido.text = "Total: $${ventas.precio.toString()}"
    }

    override fun getItemCount(): Int {
        return productosVendidosList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.ImagenProductoVendido)
        val NombreProductoVendido: TextView = itemView.findViewById(R.id.NombreProductoVendido)
        var PrecioNormalProductoVendido: TextView = itemView.findViewById(R.id.PrecioNormalProductoVendido)
        var CantidadProductoVendido: TextView = itemView.findViewById(R.id.CantidadProductoVendido)
        var TotalProductoVendido: TextView = itemView.findViewById(R.id.TotalProductoVendido)
    }
}