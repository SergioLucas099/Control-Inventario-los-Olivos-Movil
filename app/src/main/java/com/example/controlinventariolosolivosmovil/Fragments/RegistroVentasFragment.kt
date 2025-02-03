package com.example.controlinventariolosolivosmovil.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlinventariolosolivosmovil.Adapter.VentasRealizadasAdapter
import com.example.controlinventariolosolivosmovil.Model.VentasRealizadasModel
import com.example.controlinventariolosolivosmovil.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.Calendar

class RegistroVentasFragment : Fragment() {

    private lateinit var ListaVentas : ArrayList<VentasRealizadasModel>
    private lateinit var reference: DatabaseReference
    private lateinit var btnSeleccionarFecha: ImageView
    private lateinit var fechaCompleta: String
    private lateinit var fechaAño: String
    private lateinit var fechaMes: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_registro_ventas, container, false)

        btnSeleccionarFecha = view.findViewById(R.id.btnSeleccionarFecha)

        reference = FirebaseDatabase.getInstance().getReference("CierreCaja")
        val FechaSeleccionada = view.findViewById<TextView>(R.id.FechaSeleccionada)
        val InicioTurno = view.findViewById<TextView>(R.id.InicioTurno)
        val FinTurno = view.findViewById<TextView>(R.id.FinTurno)
        val BaseCaja = view.findViewById<TextView>(R.id.BaseCaja)
        val CobroEfectivo = view.findViewById<TextView>(R.id.CobroEfectivo)
        val EfectivoContado = view.findViewById<TextView>(R.id.EfectivoContado)
        val EfectivoTeorico = view.findViewById<TextView>(R.id.EfectivoTeorico)
        val Descuadre = view.findViewById<TextView>(R.id.Descuadre)
        val TxtBilletesContados = view.findViewById<TextView>(R.id.TxtBilletesContados)
        val Billetes1 = view.findViewById<LinearLayout>(R.id.Billetes1)
        val Billetes2 = view.findViewById<LinearLayout>(R.id.Billetes2)
        val TxtMonedasContadas = view.findViewById<TextView>(R.id.TxtMonedasContadas)
        val Monedas1 = view.findViewById<LinearLayout>(R.id.Monedas1)
        val Monedas2 = view.findViewById<LinearLayout>(R.id.Monedas2)
        val TxtB2000 = view.findViewById<TextView>(R.id.TxtB2000)
        val TxtB5000 = view.findViewById<TextView>(R.id.TxtB5000)
        val TxtB10000 = view.findViewById<TextView>(R.id.TxtB10000)
        val TxtB20000 = view.findViewById<TextView>(R.id.TxtB20000)
        val TxtB50000 = view.findViewById<TextView>(R.id.TxtB50000)
        val TxtB100000 = view.findViewById<TextView>(R.id.TxtB100000)
        val TxtM50 = view.findViewById<TextView>(R.id.TxtM50)
        val TxtM100 = view.findViewById<TextView>(R.id.TxtM100)
        val TxtM200 = view.findViewById<TextView>(R.id.TxtM200)
        val TxtM500 = view.findViewById<TextView>(R.id.TxtM500)
        val TxtM1000 = view.findViewById<TextView>(R.id.TxtM1000)
        val TxtVentasRealizadas = view.findViewById<TextView>(R.id.TxtVentasRealizadas)
        val RevVentasRealizadas = view.findViewById<RecyclerView>(R.id.RevVentasRealizadas)

        btnSeleccionarFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val añoActual = calendario.get(Calendar.YEAR)
            val mesActual = calendario.get(Calendar.MONTH)
            val diaActual = calendario.get(Calendar.DAY_OF_MONTH)

            FechaSeleccionada.visibility = View.VISIBLE
            InicioTurno.visibility = View.VISIBLE
            FinTurno.visibility = View.VISIBLE
            BaseCaja.visibility = View.VISIBLE
            TxtBilletesContados.visibility = View.VISIBLE
            Billetes1.visibility = View.VISIBLE
            Billetes2.visibility = View.VISIBLE
            TxtMonedasContadas.visibility = View.VISIBLE
            Monedas1.visibility = View.VISIBLE
            Monedas2.visibility = View.VISIBLE
            CobroEfectivo.visibility = View.VISIBLE
            EfectivoContado.visibility = View.VISIBLE
            EfectivoTeorico.visibility = View.VISIBLE
            Descuadre.visibility = View.VISIBLE
            TxtVentasRealizadas.visibility = View.VISIBLE
            RevVentasRealizadas.visibility = View.VISIBLE

            DatePickerDialog(requireContext(), { _, año, mes, dia ->
                fechaCompleta = String.format("%02d-%02d-%d", dia, mes + 1, año)
                fechaAño = año.toString()
                fechaMes = String.format("%02d", mes + 1)

                FechaSeleccionada.setText(fechaCompleta)

                RevVentasRealizadas.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                ListaVentas = arrayListOf<VentasRealizadasModel>()
                RevVentasRealizadas.visibility = View.GONE
                reference.child(fechaAño).child(fechaMes).child(fechaCompleta).child("Ventas")
                    .addValueEventListener(object  : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            ListaVentas.clear()
                            if (snapshot.exists()){
                                for (Snap in snapshot.children){
                                    val data = Snap.getValue(VentasRealizadasModel::class.java)
                                    ListaVentas.add(data!!)
                                }
                                val Adapter = VentasRealizadasAdapter(ListaVentas)
                                RevVentasRealizadas.adapter = Adapter
                                RevVentasRealizadas.visibility = View.VISIBLE
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                reference.child(fechaAño).child(fechaMes).child(fechaCompleta)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val IniTurno = snapshot.child("ComienzoTurno").value.toString()
                                val FTurno = snapshot.child("CierreTurno").value.toString()
                                val Bcaja = snapshot.child("BaseCaja").value.toString()
                                val Cefectivo = snapshot.child("CobroEfectivo").value.toString()
                                val Econtado = snapshot.child("EfectivoContado").value.toString()
                                val Fteorico = snapshot.child("EfectivoTeorico").value.toString()
                                val Tdescuadre = snapshot.child("Descuadre").value.toString()
                                val B2Mil = snapshot.child("Billetes2000").value.toString()
                                val B5Mil = snapshot.child("Billetes5000").value.toString()
                                val B10Mil = snapshot.child("Billetes10000").value.toString()
                                val B20Mil = snapshot.child("Billetes20000").value.toString()
                                val B50Mil = snapshot.child("Billetes50000").value.toString()
                                val B100Mil = snapshot.child("Billetes100000").value.toString()
                                val M50 = snapshot.child("Monedas50").value.toString()
                                val M100 = snapshot.child("Monedas100").value.toString()
                                val M200 = snapshot.child("Monedas200").value.toString()
                                val M500 = snapshot.child("Monedas500").value.toString()
                                val M1000 = snapshot.child("Monedas1000").value.toString()

                                InicioTurno.setText("Inicio: ${IniTurno}")
                                FinTurno.setText("Fin: ${FTurno}")
                                BaseCaja.setText("Base Caja: $${Bcaja}")
                                CobroEfectivo.setText("Cobro Efectivo: $${Cefectivo}")
                                EfectivoContado.setText("Efectivo Contado: $${Econtado}")
                                EfectivoTeorico.setText("Efectivo Teorico: $${Fteorico}")
                                Descuadre.setText("Descuadre: $${Tdescuadre}")
                                TxtB2000.setText("X${B2Mil}")
                                TxtB5000.setText("X${B5Mil}")
                                TxtB10000.setText("X${B10Mil}")
                                TxtB20000.setText("X${B20Mil}")
                                TxtB50000.setText("X${B50Mil}")
                                TxtB100000.setText("X${B100Mil}")
                                TxtM50.setText("X${M50}")
                                TxtM100.setText("X${M100}")
                                TxtM200.setText("X${M200}")
                                TxtM500.setText("X${M500}")
                                TxtM1000.setText("X${M1000}")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(requireContext(), "Error en la consulta", Toast.LENGTH_SHORT).show()
                        }
                    })

            }, añoActual, mesActual, diaActual).show()
        }

        return view
    }
}