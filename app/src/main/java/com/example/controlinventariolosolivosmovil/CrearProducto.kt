package com.example.controlinventariolosolivosmovil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class CrearProducto : AppCompatActivity() {

    private lateinit var EditTextNombreProducto: EditText
    private lateinit var EditTextPrecioProducto: EditText
    private lateinit var SpinnerCategoria: Spinner
    private lateinit var TextoCategoriaProducto: TextView
    private lateinit var Camara: LinearLayout
    private lateinit var Galeria: LinearLayout
    private lateinit var ImagenSeleccionada: ImageView
    private lateinit var AvisoSelImg: TextView
    private lateinit var SubirInformacion: Button
    private lateinit var LinkImagen : TextView
    private lateinit var reference: DatabaseReference
    private val TAG = "FirebaseStorageManager"
    private val mStorageRef = FirebaseStorage.getInstance().reference

    companion object{
        const val REQUEST_FROM_CAMERA = 1001
        const val REQUEST_FROM_GALERY = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)

        val BotonSalir = findViewById<ImageView>(R.id.BotonSalir)

        reference = FirebaseDatabase.getInstance().getReference("Productos")
        EditTextNombreProducto = findViewById(R.id.EditTextNombreProducto)
        EditTextPrecioProducto = findViewById(R.id.EditTextPrecioProducto)
        SpinnerCategoria = findViewById(R.id.SpinnerCategoria)
        TextoCategoriaProducto = findViewById(R.id.TextoCategoriaProducto)
        Camara = findViewById(R.id.Camara)
        Galeria = findViewById(R.id.Galeria)
        ImagenSeleccionada = findViewById(R.id.ImagenSeleccionada)
        AvisoSelImg = findViewById(R.id.AvisoSeleccionImagen)
        SubirInformacion = findViewById(R.id.SubirInformacion)
        LinkImagen = findViewById(R.id.LinkImg)

        val lista = listOf(
            "Escoger una categoria", "Cerveza en Botella", "Cerveza en Lata", "Gaseosas",
            "Energizantes", "Productos Postobon", "Productos Alpina", "Paquetes",
            "Dulces", "Chicles", "Galletas", "Cafeteria"
        )

        val adaptador = ArrayAdapter(this, R.layout.spinner_item_olivos, lista)
        SpinnerCategoria.adapter=adaptador
        SpinnerCategoria.onItemSelectedListener=object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TextoCategoriaProducto.setText(SpinnerCategoria.selectedItem.toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        Camara.setOnClickListener {
            TomarFoto()
        }

        Galeria.setOnClickListener {
            AbrirGaleria()
        }

        SubirInformacion.setOnClickListener{
            SubirInformacion()
        }

        BotonSalir.setOnClickListener {
            finish()
        }
    }

    private fun AbrirGaleria(){
        ImagePicker.with(this).galleryOnly()
            .crop()
            .start(REQUEST_FROM_GALERY)
    }

    private fun TomarFoto(){
        ImagePicker.with(this).cameraOnly()
            .crop()
            .start(REQUEST_FROM_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                REQUEST_FROM_CAMERA -> {
                    ImagenSeleccionada.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                    AvisoSelImg.visibility = View.GONE
                    AvisoSelImg.visibility = View.INVISIBLE
                }
                REQUEST_FROM_GALERY -> {
                    ImagenSeleccionada.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                    AvisoSelImg.visibility = View.GONE
                    AvisoSelImg.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun uploadImage(mContext: Context, imageURI: Uri){
        val ImagenFileName = "ImagenesProductos ${System.currentTimeMillis()}"
        val uploadTask = mStorageRef.child(ImagenFileName).putFile(imageURI)
        uploadTask.addOnSuccessListener {
            Log.e(TAG, "Imagen cargada con éxito")
            val DescargarUrlImagen = mStorageRef.child(ImagenFileName).downloadUrl
            DescargarUrlImagen.addOnSuccessListener {
                LinkImagen.setText("$it")
            }.addOnFailureListener{
                Toast.makeText(this, "No se pudo cargar la Url de la imagen", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Log.e(TAG, "Carga de imagen fallida ${it.printStackTrace()}")

        }
    }

    private fun SubirInformacion () {
        val Nombre = EditTextNombreProducto.text.toString()
        val Imagen = LinkImagen.text.toString()
        val Precio = EditTextPrecioProducto.text.toString()
        val Categoria = TextoCategoriaProducto.text.toString()
        val Id = reference.push().key!!

        if (!Nombre.isEmpty()){
            if (!Imagen.isEmpty()){
                if (!Precio.isEmpty()){
                    if (!Categoria.equals("Escoger una categoria")){
                        val datonumerico = Precio.toInt()
                        if (datonumerico >= 0 && datonumerico<=49){
                            Toast.makeText(this, "Precio ingresado no valido", Toast.LENGTH_SHORT).show()
                        }else{
                            val map: MutableMap<String, Any> = HashMap()
                            map["id"] = Id
                            map["nombre"] = Nombre
                            map["imagen"] = Imagen
                            map["precio"] = Precio
                            map["categoria"] = Categoria
                            reference.child(Id).setValue(map).addOnCompleteListener{
                                Toast.makeText(this, "Se ha creado un nuevo producto", Toast.LENGTH_SHORT).show()
                                finish()
                            }.addOnFailureListener{
                                Toast.makeText(this, "Error al subir el nuevo producto", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this, "Debe ingresar la categoria del producto", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Debe ingresar el precio del producto", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Debe ingresar la imagen del producto", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Debe ingresar el nombre del producto", Toast.LENGTH_SHORT).show()
        }
    }
}