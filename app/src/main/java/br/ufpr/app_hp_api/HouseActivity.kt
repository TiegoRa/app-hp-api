package br.ufpr.app_hp_api

import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.com.hvilar.myapplication.HouseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HouseActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var rBuHouse : RadioButton
    private lateinit var resultTextView : TextView
    private lateinit var houseApi: HouseApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_house)

        radioGroup = findViewById(R.id.radioGroup)
        resultTextView = findViewById(R.id.rTextView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        houseApi = retrofit.create(HouseApi::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                rBuHouse = findViewById(checkedId) // Pega o RadioButton selecionado
                val selectedText = rBuHouse.text.toString() // Pega o texto do selecionado

                // Exibe o resultado no TextView (caso tenha)
//                resultTextView.text = "Casa selecionada: $selectedText"
                buscarDados(selectedText)

                // Exemplo de armazenamento (você pode salvar isso em um banco de dados ou SharedPreferences)
                Log.d("HouseActivity", "Casa selecionada: $selectedText")
            }
        }
    }

    fun buscarDados(casa:String){

        lifecycleScope.launch {
            try {
//                    showProgressBar()
                val response = withContext(Dispatchers.IO){
                    houseApi.getHouseMembers(casa)
                }

//                    hideProgressBar()

                if (response.isNotEmpty()){

                    resultTextView.text = response.joinToString("\n") { it.name }
                } else{
                    Toast.makeText(this@HouseActivity,"Personagem não encontrado",
                        Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception){
                Log.e("MainActivity","Erro ao buscar persionagem",e)
                Toast.makeText(this@HouseActivity,
                    "Erro ao buscar persionagem",
                    Toast.LENGTH_SHORT).show()
            }
        }//fim da corrotina

    }
}