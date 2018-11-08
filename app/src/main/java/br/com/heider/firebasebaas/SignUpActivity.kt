package br.com.heider.firebasebaas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.heider.firebasebaas.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        btCriar.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(
                etEmail.text.toString(),
                etSenha.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    salvarNoRealtimeDatabase()
                } else {
                    Toast.makeText(this@SignUpActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun salvarNoRealtimeDatabase() {
        val user = User(etNome.text.toString(), etEmail.text.toString(), etTelefone.text.toString())
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao criar o usuário", Toast.LENGTH_SHORT).show()
                }
            }
    }
}