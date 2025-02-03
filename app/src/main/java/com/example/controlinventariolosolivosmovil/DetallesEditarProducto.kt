package com.example.controlinventariolosolivosmovil

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class DetallesEditarProducto : AppCompatActivity() {

    private lateinit var reference: DatabaseReference
    var id = ""
    var imagen = ""
    var nombre = ""
    var precio = ""
    var categoria = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_editar_producto)

        reference = FirebaseDatabase.getInstance().getReference("Productos")
        val BorrarProductoBD = findViewById<ImageView>(R.id.BorrarProductoBD)
        val CerrarDetallesEditarProductos = findViewById<ImageView>(R.id.CerrarDetallesEditarProductos)
        val EditarImagenProducto = findViewById<ImageView>(R.id.EditarImagenProducto)
        val EditarNombreProducto = findViewById<EditText>(R.id.EditarNombreProducto)
        val EditarPrecioProducto = findViewById<EditText>(R.id.EditarPrecioProducto)
        val CategoriaSaleccionada = findViewById<TextView>(R.id.CategoriaSaleccionada)
        val SpinnerEditarCategoria = findViewById<Spinner>(R.id.SpinnerEditarCategoria)
        val BtnActualizarProducto = findViewById<Button>(R.id.BtnActualizarProducto)

        CerrarDetallesEditarProductos.setOnClickListener { finish() }

        // Obtener valores pasados por Intent
        id = intent.getStringExtra("id").toString()
        imagen = intent.getStringExtra("imagen").toString()
        nombre = intent.getStringExtra("nombre").toString()
        precio = intent.getStringExtra("precio").toString()
        categoria = intent.getStringExtra("categoria").toString()

        // Configurar los valores iniciales
        Picasso.get().load(imagen).into(EditarImagenProducto)
        EditarNombreProducto.setText(nombre)
        EditarPrecioProducto.setText(precio)
        CategoriaSaleccionada.text = "Categoria Seleccionada: $categoria"

        BorrarProductoBD.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Eliminar producto")
            builder.setMessage("¿Estás seguro de que deseas eliminar este producto?")

            builder.setPositiveButton("Eliminar") { dialog, _ ->
                reference.child(id).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Producto Eliminado Exitosamente", Toast.LENGTH_SHORT).show()
                    }
                dialog.dismiss()
                finish()
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }

        // Lista para el Spinner
        val lista = listOf(
            "Escoger una categoria", "Cerveza en Botella", "Cerveza en Lata", "Gaseosas",
            "Energizantes", "Productos Postobon", "Productos Alpina", "Paquetes",
            "Dulces", "Chicles", "Cigarrillos", "Trago", "Galletas",
            "Chocorramo", "Bimbo", "Cafeteria"
        )

        // Adaptador para el Spinner
        val adaptador = ArrayAdapter(this, R.layout.spinner_item_olivos, lista)
        SpinnerEditarCategoria.adapter = adaptador

        // Establecer la posición inicial del Spinner según la categoría actual
        val posicionInicial = lista.indexOf(categoria)
        if (posicionInicial != -1) {
            SpinnerEditarCategoria.setSelection(posicionInicial)
        }

        // Listener del Spinner para actualizar la categoría seleccionada
        SpinnerEditarCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val seleccion = SpinnerEditarCategoria.selectedItem.toString()
                if (seleccion != "Escoger una categoria") {
                    categoria = seleccion // Actualizar la variable "categoria"
                    CategoriaSaleccionada.text = "Categoria Seleccionada: $categoria"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada si no se selecciona nada
            }
        }

        BtnActualizarProducto.setOnClickListener{
            val map: MutableMap<String, Any> = HashMap()
            map["id"] = id
            map["imagen"] = imagen
            map["nombre"] = EditarNombreProducto.text.toString()
            map["precio"] = EditarPrecioProducto.text.toString()
            map["categoria"] = categoria
            reference.child(id).updateChildren(map).addOnCompleteListener{
                Toast.makeText(this, "Producto Actualizado", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
