package com.example.controlinventariolosolivosmovil

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlinventariolosolivosmovil.Adapter.ProductosVendidosAdapter
import com.example.controlinventariolosolivosmovil.Model.ProductosVendidosModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class DetallesVentas : AppCompatActivity() {

    private lateinit var ListaProductosVendidos : ArrayList<ProductosVendidosModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_ventas)

        val CerrarDetallesVentas = findViewById<ImageView>(R.id.CerrarDetallesVentas)
        val TxtDFechaVentas = findViewById<TextView>(R.id.TxtDFechaVentas)
        val TxtDMesaVentas = findViewById<TextView>(R.id.TxtDMesaVentas)
        val TxtDNombreMesaVentas = findViewById<TextView>(R.id.TxtDNombreMesaVentas)
        val TxtDMetodoPagoVentas = findViewById<TextView>(R.id.TxtDMetodoPagoVentas)
        val TxtDTotalPagarVentas = findViewById<TextView>(R.id.TxtDTotalPagarVentas)
        val TxtDEfectivoVentas = findViewById<TextView>(R.id.TxtDEfectivoVentas)
        val TxtDCambioVentas = findViewById<TextView>(R.id.TxtDCambioVentas)
        val RevDetallesVenta = findViewById<RecyclerView>(R.id.RevDetallesVenta)

        // Obtener valores pasados por Intent
        val id = intent.getStringExtra("id").toString()
        val Cambio = intent.getStringExtra("Cambio").toString()
        val Efectivo = intent.getStringExtra("Efectivo").toString()
        val Fecha = intent.getStringExtra("Fecha").toString()
        val Mesa = intent.getStringExtra("Mesa").toString()
        val MetodoPago = intent.getStringExtra("MetodoPago").toString()
        val NombreMesa = intent.getStringExtra("NombreMesa")?.takeIf { it.isNotEmpty() } ?: "Ninguno"
        val TotalPagar = intent.getStringExtra("TotalPagar").toString()

        // Extraer solo la fecha antes del guion
        val fecha = Fecha.split(" - ")[0]

// Separar día, mes y año
        val partesFecha = fecha.split("/")
        val dia = partesFecha[0]
        val mes = partesFecha[1]
        val año = partesFecha[2]

// Formatear la fecha en el formato requerido
        val fechaCompleta = "$dia-$mes-$año"

        TxtDFechaVentas.setText(Fecha)
        TxtDMesaVentas.setText(Mesa)
        TxtDNombreMesaVentas.setText("Nombre Mesa: $NombreMesa")
        TxtDMetodoPagoVentas.setText("Metodo pago: $MetodoPago")
        TxtDTotalPagarVentas.setText("Total pagar: $TotalPagar")
        TxtDEfectivoVentas.setText(Efectivo)
        TxtDCambioVentas.setText(Cambio)

        CerrarDetallesVentas.setOnClickListener {
            finish()
        }

        RevDetallesVenta.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        ListaProductosVendidos = arrayListOf<ProductosVendidosModel>()
        RevDetallesVenta.visibility = View.GONE
        FirebaseDatabase.getInstance().reference.child("CierreCaja")
            .child(año).child(mes).child(fechaCompleta)
            .child("Ventas").child(id).child("Productos")
            .addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaProductosVendidos.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(ProductosVendidosModel::class.java)
                            ListaProductosVendidos.add(data!!)
                        }
                        val Adapter = ProductosVendidosAdapter(ListaProductosVendidos)
                        RevDetallesVenta.adapter = Adapter
                        RevDetallesVenta.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}