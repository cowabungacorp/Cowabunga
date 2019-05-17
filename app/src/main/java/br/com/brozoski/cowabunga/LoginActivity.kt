package br.com.brozoski.cowabunga

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private var tvForgotPassword:TextView? = null
    private var etEmail:EditText? = null
    private var etSenha:EditText? = null
    private var btnLogin:Button? = null
    private var btnCreateAccount:Button? = null
    private var mProgressBar:ProgressDialog? = null

    private var mAuth: FirebaseAuth? = null

    private var email:String? = null
    private var senha:String? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            window.setStatusBarColor(R.color.colorPrimary)
        }

        init()

    }

    private fun init() {
       tvForgotPassword = tv_forgot_password as TextView
       etEmail = et_username as EditText
       etSenha = et_senha as EditText
       btnLogin = btn_enttrar as Button
       btnCreateAccount = btn_register as Button
       mProgressBar = ProgressDialog(this)

       mAuth = FirebaseAuth.getInstance()

       tvForgotPassword!!.setOnClickListener( ){startActivity(Intent(this@LoginActivity, ForgotPassowodActivity::class.java))}

        btnCreateAccount!!.setOnClickListener(){startActivity(Intent(this@LoginActivity, CreateAccountActivity::class.java))}


        btnLogin!!.setOnClickListener(){
            email = etEmail?.text.toString()
            senha = etSenha?.text.toString()

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)){
                mProgressBar!!.setMessage("kkkkkkkkkkkkkkkk")
                mProgressBar!!.show()
                Log.d(TAG, "Login")
                mAuth!!.signInWithEmailAndPassword(email!!, senha!!).addOnCompleteListener(this){
                        task ->
                            mProgressBar!!.hide()

                            if(task.isSuccessful){
                                Log.d(TAG, "DEU BOM")
                                updateUI()
                            }else{
                                Log.d(TAG, "DEU RUIM")
                                Toast.makeText(this@LoginActivity, "fsdaçfs", Toast.LENGTH_SHORT).show()
                            }

                }
            }else{
                Toast.makeText(this@LoginActivity, "EFPDSFASD", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColor(color:Int){
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

}
