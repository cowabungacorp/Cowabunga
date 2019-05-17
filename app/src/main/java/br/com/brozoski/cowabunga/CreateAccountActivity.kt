package br.com.brozoski.cowabunga

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {


    //Interface
    private var etFirstName:EditText? = null
    private var etLastName:EditText? = null
    private var etEmail:EditText? = null
    private var etSenha:EditText? = null
    private var btnRegister:Button? = null
    private var mProgressBar:ProgressDialog?  = null


    //Referencias do banco de dados
    private var mDataBaseReference: DatabaseReference? = null
    private var mDataBase: FirebaseDatabase
    ? = null
    private var mAuth: FirebaseAuth? = null

    //Global
    private val TAG = "CreateAccountActivity"
    private var firstName:String? = null
    private var lastName:String? = null
    private var email:String? = null
    private var senha:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialize()
    }

    private fun initialize() {
        etFirstName = et_first_name as EditText
        etLastName = et_last_name as EditText
        etEmail = et_email as EditText
        etSenha = et_senha as EditText
        btnRegister = btn_register as Button
        mProgressBar = ProgressDialog(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        val database = FirebaseDatabase.getInstance()
        mDataBase = FirebaseDatabase.getInstance()
        mDataBaseReference = mDataBase!!.reference!!.child("Users")

        mAuth = FirebaseAuth.getInstance()


        btnRegister!!.setOnClickListener(){
            firstName = etFirstName?.text.toString()
            lastName = etLastName?.text.toString()
            email = etEmail?.text.toString()
            senha = etSenha?.text.toString()

            if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha) ){
                Toast.makeText(this, "Entre", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this, "Entre com os dados", Toast.LENGTH_SHORT).show()
            }

            mProgressBar!!.setMessage("carregabndo")
            mProgressBar!!.show()

            mAuth!!.createUserWithEmailAndPassword(email!!, senha!!).addOnCompleteListener(this){ task ->
                mProgressBar!!.hide()

                if (task.isSuccessful){

                    Log.d(TAG, "CreateUserWithEmail::Success")
                    val USER_ID = mAuth!!.currentUser!!.uid

                    verifyEmail()

                    val CURRENT_USER_DB = mDataBaseReference!!.child(USER_ID)
                    CURRENT_USER_DB.child("firstName").setValue(firstName)
                    CURRENT_USER_DB.child("lastName").setValue(lastName)

                    updateUserInfoAndUi()

                }else{
                    Log.w(TAG, "CreateUserWithEmail::Failure", task.exception)
                    Toast.makeText(this@CreateAccountActivity, "fp´sadjfasd", Toast.LENGTH_SHORT ).show()


                }
            }


        }
    }

    private fun updateUserInfoAndUi() {
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener(){task ->
            if (task.isSuccessful){
                Toast.makeText(this@CreateAccountActivity, "deu bom", Toast.LENGTH_SHORT).show()
            }else{
                Log.e(TAG, "SendEmailVerification::Failure", task.exception)
                Toast.makeText(this@CreateAccountActivity, "deu ruim", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
