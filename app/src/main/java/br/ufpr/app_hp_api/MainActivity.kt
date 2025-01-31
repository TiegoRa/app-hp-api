package br.ufpr.app_hp_api

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btPersonagem = findViewById<Button>(R.id.btPersonagem)
        val btStaff = findViewById<Button>(R.id.btStaff)
        val btHouse = findViewById<Button>(R.id.btHouse)
        val btExit = findViewById<Button>(R.id.btExit)

        // Ações dos botões
        btPersonagem.setOnClickListener {
            val intent = Intent(this, StudentActivity::class.java)
            startActivity(intent)
        }

        btStaff.setOnClickListener {
            val intent = Intent(this, StaffActivity::class.java)
            startActivity(intent)
        }

        btHouse.setOnClickListener {
            val intent = Intent(this, HouseActivity::class.java)
            startActivity(intent)
        }

        btExit.setOnClickListener {
            finishAffinity()
        }
    }
}