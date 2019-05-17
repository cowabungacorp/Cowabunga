package br.com.brozoski.cowabunga

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_passowod.*

class ForgotPassowodActivity : AppCompatActivity() {

    private val TAG = "ForgotPasswordActivity"
    private var etEmail:EditText? = null
    private var btnSubmit: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passowod)

        init()
    }

    private fun init() {
        etEmail = et_email
        btnSubmit = btn_forgot

        mAuth = FirebaseAuth.getInstance()

        btnSubmit!!.setOnClickListener(){
            val email = etEmail?.text.toString()

            if(!TextUtils.isEmpty(email)){
                mAuth!!
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val message = "Email enviado"
                            Log.d(TAG, message)
                            Toast.makeText(this@ForgotPassowodActivity, message, Toast.LENGTH_SHORT).show()
                            updateUI()
                        }else{
                            Log.w(TAG, task.exception!!.message)
                            Toast.makeText(this@ForgotPassowodActivity, "Nenhum email", Toast.LENGTH_SHORT).show()
                        }
                    }

            }else{
                Toast.makeText(this@ForgotPassowodActivity, "entre com um email", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun updateUI() {
        val intent = Intent(this@ForgotPassowodActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }
}
