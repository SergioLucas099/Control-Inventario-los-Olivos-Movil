package com.example.controlinventariolosolivosmovil

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imagen = findViewById<ImageView>(R.id.img_logo)
        val texto1 = findViewById<TextView>(R.id.textViewTitulo)
        val texto2 = findViewById<TextView>(R.id.textViewDescripcion)

        val animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        val animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)

        imagen.animation = animacion1
        texto1.animation = animacion1
        texto2.animation = animacion2

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, InicioSesion::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}