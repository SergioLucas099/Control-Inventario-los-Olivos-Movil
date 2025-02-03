package com.example.controlinventariolosolivosmovil.Fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlinventariolosolivosmovil.Adapter.VerProductosAdapter
import com.example.controlinventariolosolivosmovil.Model.VerProductosModel
import com.example.controlinventariolosolivosmovil.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class EditarProductosFragment : Fragment() {

    private lateinit var ListaProductos : ArrayList<VerProductosModel>
    private lateinit var RevProductos : RecyclerView
    private var textoBusqueda: String = ""

    private var botonActivo: LinearLayout? = null
    private var categoriaSeleccionada: String? = null

    private lateinit var BtnCervezaBotella: LinearLayout
    private lateinit var BtnCervezaLata: LinearLayout
    private lateinit var BtnGaseosas: LinearLayout
    private lateinit var BtnEnergizantes: LinearLayout
    private lateinit var BtnProductosPostobon: LinearLayout
    private lateinit var BtnProductosAlpina: LinearLayout
    private lateinit var BtnPaquetes: LinearLayout
    private lateinit var BtnDulces: LinearLayout
    private lateinit var BtnChicles: LinearLayout
    private lateinit var BtnCigarrillos: LinearLayout
    private lateinit var BtnTrago: LinearLayout
    private lateinit var BtnGalletas: LinearLayout
    private lateinit var BtnChocorramo: LinearLayout
    private lateinit var BtnBimbo: LinearLayout
    private lateinit var BtnCafeteria: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_editar_productos, container, false)

        val Buscar = view.findViewById<SearchView>(R.id.BuscadorProductos)
        RevProductos = view.findViewById<RecyclerView>(R.id.RevProductos)

        BtnCervezaBotella = view.findViewById(R.id.BtnCervezaBotella)
        BtnCervezaLata = view.findViewById(R.id.BtnCervezaLata)
        BtnGaseosas = view.findViewById(R.id.BtnGaseosas)
        BtnEnergizantes = view.findViewById(R.id.BtnEnergizantes)
        BtnProductosPostobon = view.findViewById(R.id.BtnProductosPostobon)
        BtnProductosAlpina = view.findViewById(R.id.BtnProductosAlpina)
        BtnPaquetes = view.findViewById(R.id.BtnPaquetes)
        BtnDulces = view.findViewById(R.id.BtnDulces)
        BtnChicles = view.findViewById(R.id.BtnChicles)
        BtnCigarrillos = view.findViewById(R.id.BtnCigarrillos)
        BtnTrago = view.findViewById(R.id.BtnTrago)
        BtnGalletas = view.findViewById(R.id.BtnGalletas)
        BtnChocorramo = view.findViewById(R.id.BtnChocorramo)
        BtnBimbo = view.findViewById(R.id.BtnBimbo)
        BtnCafeteria = view.findViewById(R.id.BtnCafeteria)

        BtnCervezaBotella.setOnClickListener {
            alternarFiltro("Cerveza en Botella", BtnCervezaBotella)
        }

        BtnCervezaLata.setOnClickListener {
            alternarFiltro("Cerveza en Lata", BtnCervezaLata)
        }

        BtnGaseosas.setOnClickListener {
            alternarFiltro("Gaseosas", BtnGaseosas)
        }

        BtnEnergizantes.setOnClickListener {
            alternarFiltro("Energizantes", BtnEnergizantes)
        }

        BtnProductosPostobon.setOnClickListener {
            alternarFiltro("Productos Postobon", BtnProductosPostobon)
        }

        BtnProductosAlpina.setOnClickListener {
            alternarFiltro("Productos Alpina", BtnProductosAlpina)
        }

        BtnPaquetes.setOnClickListener {
            alternarFiltro("Paquetes", BtnPaquetes)
        }

        BtnDulces.setOnClickListener {
            alternarFiltro("Dulces", BtnDulces)
        }

        BtnChicles.setOnClickListener {
            alternarFiltro("Chicles", BtnChicles)
        }

        BtnCigarrillos.setOnClickListener {
            alternarFiltro("Cigarrillos", BtnCigarrillos)
        }

        BtnTrago.setOnClickListener {
            alternarFiltro("Trago", BtnTrago)
        }

        BtnGalletas.setOnClickListener {
            alternarFiltro("Galletas", BtnGalletas)
        }

        BtnChocorramo.setOnClickListener {
            alternarFiltro("Chocorramo", BtnChocorramo)
        }

        BtnBimbo.setOnClickListener {
            alternarFiltro("Bimbo", BtnBimbo)
        }

        BtnCafeteria.setOnClickListener {
            alternarFiltro("Cafeteria", BtnCafeteria)
        }

        ///////////////////Productos/////////////////////////
        RevProductos.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        ListaProductos = arrayListOf<VerProductosModel>()
        RevProductos.visibility = View.GONE
        FirebaseDatabase.getInstance().reference.child("Productos")
            .orderByChild("nombre")
            .addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaProductos.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(VerProductosModel::class.java)
                            ListaProductos.add(data!!)
                        }
                        val Adapter = VerProductosAdapter(ListaProductos)
                        RevProductos.adapter = Adapter
                        RevProductos.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        ///////////////////Buscador Productos/////////////////////////
        Buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // No es necesario manejar el submit para búsqueda en tiempo real
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Llamar a la función buscar cada vez que cambie el texto
                buscar(newText ?: "")
                return true
            }
        })

        return view
    }

    private fun buscar(s: String) {
        val milista = ArrayList<VerProductosModel>()
        for (obj in ListaProductos) {
            if (obj.nombre?.lowercase()?.contains(s.lowercase()) == true){
                milista.add(obj)
            }
        }
        val adapter = VerProductosAdapter(milista)
        RevProductos.setAdapter(adapter)
    }

    private fun actualizarRecyclerView() {
        val productosFiltrados = ListaProductos.filter { producto ->
            val cumpleCategoria = categoriaSeleccionada?.let { producto.categoria == it } ?: true
            val cumpleBusqueda = textoBusqueda.isEmpty() || producto.nombre?.contains(textoBusqueda, true) == true
            true // Opcionalmente: filtra por mesa si es necesario agregar lógica aquí
            cumpleCategoria && cumpleBusqueda
        }

        val adapter = VerProductosAdapter(productosFiltrados)
        RevProductos.adapter = adapter
    }

    private fun alternarFiltro(categoria: String, boton: LinearLayout) {
        val nightModeFlags = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val colorBlancoONegro = if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            R.color.black_android // Si está en modo oscuro, cambia a negro
        } else {
            R.color.white_android // Si está en modo claro, cambia a blanco
        }

        if (botonActivo == boton) {
            categoriaSeleccionada = null
            boton.setBackgroundColor(ContextCompat.getColor(requireContext(), colorBlancoONegro))
            botonActivo = null
        } else {
            categoriaSeleccionada = categoria
            botonActivo?.setBackgroundColor(ContextCompat.getColor(requireContext(), colorBlancoONegro))
            boton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.cafe_transparente_50))
            botonActivo = boton
        }
        actualizarRecyclerView()
    }

}