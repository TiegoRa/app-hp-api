package br.ufpr.app_hp_api

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.com.hvilar.myapplication.StudentApi
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StudentActivity : AppCompatActivity() {

    private lateinit var campoDeBusca: EditText
    //    private lateinit var btGetStudent: Button
    private lateinit var imagemStudent: ImageView
    private lateinit var studentApi: StudentApi
    private lateinit var progressBar: ProgressBar
    private lateinit var nameTextView: TextView
    private lateinit var houseTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student)

        campoDeBusca = findViewById(R.id.editTextText)
        imagemStudent = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)
        nameTextView = findViewById(R.id.nameTextView)
        houseTextView = findViewById(R.id.houseTextView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        studentApi = retrofit.create(StudentApi::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }// fim do oncreate

    fun getStudentIn(view:View){
        val id = campoDeBusca.text.toString().lowercase()
        if (id.isNotEmpty()){
            getStudentApi(id)
        } else {
            Toast.makeText(this,"Insira um id",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStudentApi(id:String){
        lifecycleScope.launch {
            try {
                showProgressBar()
                val response = withContext(Dispatchers.IO){
                    studentApi.getStudent(id)
                }

                hideProgressBar()

                if (response.isNotEmpty()){
                    val student = response[0]
                    Picasso.get().load(student.image).into(imagemStudent)
                    nameTextView.text = student.name
                    houseTextView.text = student.house
                    showDetail()
                } else{
                    Toast.makeText(this@StudentActivity,"Personagem não encontrado",
                        Toast.LENGTH_LONG).show()
                    hideDetail()
                }
            }
            catch (e:Exception){
                Log.e("MainActivity","Erro ao buscar persionagem",e)
                Toast.makeText(this@StudentActivity,
                    "Erro ao buscar persionagem",
                    Toast.LENGTH_SHORT).show()
            }
        }//fim da corrotina
    } // fim do getStudentApi

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.GONE
    }

    private fun showDetail(){
        nameTextView.visibility = View.VISIBLE
        houseTextView.visibility = View.VISIBLE
        imagemStudent.visibility = View.VISIBLE
    }

    private fun hideDetail(){
        nameTextView.visibility = View.GONE
        houseTextView.visibility = View.GONE
        imagemStudent.visibility = View.GONE
    }
}