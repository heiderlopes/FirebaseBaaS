package br.com.heider.firebasebaas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if(mAuth.currentUser != null) {
            vaiParaTelaPrincipal()
        }

        btLogar.setOnClickListener {
            mAuth.signInWithEmailAndPassword(
                etEmail.text.toString(),
                etSenha.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    vaiParaTelaPrincipal()
                } else {
                    Toast.makeText(this@LoginActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        btNovaConta.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun vaiParaTelaPrincipal(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}