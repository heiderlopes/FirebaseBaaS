package br.com.heider.firebasebaas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import br.com.heider.firebasebaas.model.CoisaDigital
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        escutarMudancaNoFirebase()

        btLigar.setOnClickListener {
            onOff(1)
        }

        btDesligar.setOnClickListener {
            onOff(0)
        }
    }

    private fun escutarMudancaNoFirebase() {
        // Read from the database
        FirebaseDatabase.getInstance().getReference("Coisas")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val coisa = dataSnapshot.getValue(CoisaDigital::class.java)
                    when (coisa?.valor) {
                        0 -> {
                            ivStatus.visibility = View.VISIBLE
                            ivStatus.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@MainActivity,
                                    R.drawable.desligada
                                )
                            )
                        }
                        1 -> {
                            ivStatus.visibility = View.VISIBLE
                            ivStatus.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ligada))
                        }
                        else -> {
                            ivStatus.visibility = View.GONE
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun onOff(valor: Int) {
        FirebaseDatabase.getInstance().getReference("Coisas")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(CoisaDigital("LED", valor))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Comando executado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao criar o usuário", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
