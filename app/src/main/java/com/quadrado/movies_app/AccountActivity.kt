package com.quadrado.movies_app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.quadrado.movies_app.database.Database
import com.quadrado.movies_app.database.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.EditText

class AccountActivity : AppCompatActivity() {

    private lateinit var txtUser: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtFilmes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        supportActionBar?.hide()

        val db = Database.getInstance(this)
        val dao = db.userDAO()
        val dao_filmes = db.favoriteMovieDAO()

        txtUser = findViewById(R.id.tv_user_account)
        txtEmail = findViewById(R.id.tv_email_account)
        txtFilmes = findViewById(R.id.tv_favorite_movies)

        val btnEditUser = findViewById<Button>(R.id.btn_edit_username)
        val btnEditEmail = findViewById<Button>(R.id.btn_edit_email)
        val btnApagarConta = findViewById<Button>(R.id.btn_delete_account)

        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                dao.getAllUsers().firstOrNull()
            }
            user?.let {
                txtUser.text = it.username
                txtEmail.text = it.email
            }

            val filmesFavoritos = dao_filmes.getAllFavorites()

            txtFilmes.text = "FILMES FAVORITOS: " + filmesFavoritos.size

        }

        btnEditUser.setOnClickListener {
            showEditDialog("Editar Nome de Usuário", txtUser.text.toString()) { newUsername ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val user = dao.getAllUsers().firstOrNull()
                        user?.let {
                            dao.insertUser(it.copy(username = newUsername))
                        }
                    }
                    txtUser.text = newUsername
                }
            }
        }

        btnEditEmail.setOnClickListener {
            showEditDialog("Editar Email", txtEmail.text.toString()) { newEmail ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val user = dao.getAllUsers().firstOrNull()
                        user?.let {
                            dao.insertUser(it.copy(email = newEmail))
                        }
                    }
                    txtEmail.text = newEmail
                }
            }
        }

        btnApagarConta.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("Tem certeza que deseja apagar a conta?")
                .setPositiveButton("Sim") { _, _ ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            dao.deleteAllUsers()
                            dao_filmes.deleteAllFavorites()
                            finish()
                        }
                        txtUser.text = ""
                        txtEmail.text = ""
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    private fun showEditDialog(title: String, currentValue: String, onSave: (String) -> Unit) {
        val editText = EditText(this)
        editText.setText(currentValue)

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val newValue = editText.text.toString()
                if (newValue.isNotBlank()) {
                    onSave(newValue)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
