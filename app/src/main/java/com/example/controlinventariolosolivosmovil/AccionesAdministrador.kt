package com.example.controlinventariolosolivosmovil

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class AccionesAdministrador : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acciones_administrador)

        auth = FirebaseAuth.getInstance()

        val BotonCrearProducto = findViewById<LinearLayout>(R.id.BotonCrearProducto)
        val BotonSalir = findViewById<ImageView>(R.id.BotonSalir)

        BotonCrearProducto.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, CrearProducto::class.java)
            startActivity(intent)
        }

        BotonSalir.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cerrar Sesión")
            builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")

            builder.setPositiveButton("Cerrar sesión") { dialog, _ ->
                cerrarSesion()
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    fun cerrarSesion() {
        auth.signOut()
        val intent = Intent(this, InicioSesion::class.java)
        startActivity(intent)
        finish()
    }
}