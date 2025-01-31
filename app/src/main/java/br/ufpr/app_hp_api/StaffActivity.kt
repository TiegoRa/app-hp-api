package br.ufpr.app_hp_api

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.com.hvilar.myapplication.StaffApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StaffActivity : AppCompatActivity() {

    private lateinit var textStaff : TextView
    private lateinit var staffApi : StaffApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_staff)

        textStaff = findViewById(R.id.staffTextView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        staffApi = retrofit.create(StaffApi::class.java)

        buscarDados()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buscarDados(){

        lifecycleScope.launch {
            try {
//                    showProgressBar()
                val response = withContext(Dispatchers.IO){
                    staffApi.getStaff()
                }

//                    hideProgressBar()

                if (response.isNotEmpty()){

                    textStaff.text = response.joinToString("\n") { it.name }
                } else{
                    Toast.makeText(this@StaffActivity,"Personagem não encontrado",
                        Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception){
                Log.e("MainActivity","Erro ao buscar persionagem",e)
                Toast.makeText(this@StaffActivity,
                    "Erro ao buscar persionagem",
                    Toast.LENGTH_SHORT).show()
            }
        }//fim da corrotina

    }
}