package com.example.controlinventariolosolivosmovil

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.controlinventariolosolivosmovil.Fragments.CrearProductosFragment
import com.example.controlinventariolosolivosmovil.Fragments.EditarProductosFragment
import com.example.controlinventariolosolivosmovil.Fragments.RegistroVentasFragment
import com.google.firebase.auth.FirebaseAuth
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class BottomNavigationBar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_bar)

        auth = FirebaseAuth.getInstance()
        val BotonSalir = findViewById<ImageView>(R.id.BotonSalir)

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Crear", R.drawable.baseline_create_new_folder_24)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Editar", R.drawable.baseline_edit_document_24)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Ventas", R.drawable.baseline_attach_money_24)
        )

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

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> {
                    replaceFragment(CrearProductosFragment())
                }
                2 -> {
                    replaceFragment(EditarProductosFragment())
                }
                3 -> {
                    replaceFragment(RegistroVentasFragment())
                }
            }
        }
        // Default Bottom Tab Selected
        replaceFragment(EditarProductosFragment())
        bottomNavigation.show(2)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

    fun cerrarSesion() {
        auth.signOut()
        val intent = Intent(this, InicioSesion::class.java)
        startActivity(intent)
        finish()
    }
}