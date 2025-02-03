package com.example.controlinventariolosolivosmovil.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controlinventariolosolivosmovil.DetallesEditarProducto
import com.example.controlinventariolosolivosmovil.DetallesVentas
import com.example.controlinventariolosolivosmovil.Model.VentasRealizadasModel
import com.example.controlinventariolosolivosmovil.Model.VerProductosModel
import com.example.controlinventariolosolivosmovil.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class VentasRealizadasAdapter (
    private var ventasList: List<VentasRealizadasModel>
) : RecyclerView.Adapter<VentasRealizadasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.ventas_realizadas_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ventas = ventasList[position]

        val id = FirebaseDatabase.getInstance().reference.push().key ?: ""
        holder.TxtFechaVenta.text = ventas.Fecha
        holder.TxtMesaVenta.text = ventas.Mesa
        holder.TxtPrecioVentas.text = ventas.TotalPagar

        holder.seleccionarVenta.setOnClickListener {
            val intent = Intent(holder.seleccionarVenta.context, DetallesVentas::class.java)
            intent.putExtra("id", ventas.id)
            intent.putExtra("Cambio", ventas.Cambio)
            intent.putExtra("Efectivo", ventas.Efectivo)
            intent.putExtra("Fecha", ventas.Fecha)
            intent.putExtra("Mesa", ventas.Mesa)
            intent.putExtra("MetodoPago", ventas.MetodoPago)
            intent.putExtra("NombreMesa", ventas.NombreMesa)
            intent.putExtra("TotalPagar", ventas.TotalPagar)
            holder.seleccionarVenta.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ventasList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seleccionarVenta: LinearLayout = itemView.findViewById(R.id.seleccionarVenta)
        val TxtFechaVenta: TextView = itemView.findViewById(R.id.TxtFechaVenta)
        var TxtMesaVenta: TextView = itemView.findViewById(R.id.TxtMesaVenta)
        var TxtPrecioVentas: TextView = itemView.findViewById(R.id.TxtPrecioVentas)
    }
}