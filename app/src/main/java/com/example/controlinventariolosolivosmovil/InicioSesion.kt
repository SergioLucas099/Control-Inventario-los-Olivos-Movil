package com.example.controlinventariolosolivosmovil

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class InicioSesion : AppCompatActivity() {

    private lateinit var Usuario: EditText
    private lateinit var Contraseña: EditText
    private lateinit var BotonInicioSesion: Button
    private lateinit var RecuperarContraseña: TextView
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        Usuario = findViewById(R.id.UsuarioEditText)
        Contraseña = findViewById(R.id.ContaseñaEditText)
        BotonInicioSesion = findViewById(R.id.BotonInicioSesion)
        RecuperarContraseña = findViewById(R.id.texto_recuperar_contraseña)

        // Inicialización de SharedPreferences
        preferences = getSharedPreferences("typeUser", MODE_PRIVATE)
        editor = preferences.edit()

        BotonInicioSesion.setOnClickListener{
            IniciarSesion()
        }

    }

    private fun IniciarSesion(){
        if (!Usuario.text.toString().equals("")){
            if(!Contraseña.text.toString().equals("")){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(Usuario.text.toString(), Contraseña.text.toString()).addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(this, "Bienvenido "+Usuario.text.toString(), Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@InicioSesion, AccionesAdministrador::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            editor.putString("user", "Admin")
                            editor.apply()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Este usuario no esta registrado", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Debe ingresar la contraseña", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, "Debe ingresar el usuario", Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val user: String = preferences.getString("user", "").toString()
            if (user == "Admin") {
                val intent: Intent = Intent(
                    this@InicioSesion,
                    AccionesAdministrador::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}