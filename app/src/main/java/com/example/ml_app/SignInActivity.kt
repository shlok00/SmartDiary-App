package com.example.ml_app

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ml_app.retrofit.ApiInterface
import com.example.ml_app.retrofit.ApiUtils
import com.example.ml_app.retrofit.UserData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        sign_in_btn.setOnClickListener(){
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()

                }
            }else{
                Log.w("SignInActivity", exception.toString())
                Log.w("SignInActivity", "HII")
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    Log.d("SignInActivity", "signInWithCredential:success")

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }
}

/*class SignInActivity : AppCompatActivity() {
    var mAPIService: ApiInterface? = null

    companion object{
        private const val RC_SIGN_IN = 120
    }
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val githubSecret = "7781afee8a6f51c4fc44f943560cba171b6b006f"
    private val githubId = "c7f03f56110e4196fc4d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title


        mAPIService = ApiUtils.apiService

        getSupportActionBar()?.hide(); // hide the title bar
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_in)
        var loginBtn = findViewById<Button>(R.id.loginbut)
        loginBtn.setOnClickListener {
            userSignIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
            .requestIdToken("914627264845-6vs7i6cgeahf7t24caojbin28u10nivr.apps.googleusercontent.com")
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)

//        mAuth = FirebaseAuth.getInstance()

//        sign_in_btn.setOnClickListener(){
//
//            signIn()
//        }
    }

    private fun userSignIn() {
        val paramObject = JSONObject()
        var emailTxtBox = findViewById<TextView>(R.id.email)
        var passwordTxtBox = findViewById<TextView>(R.id.password)
        paramObject.put("email", emailTxtBox.text)
        paramObject.put("password", passwordTxtBox.text)
        mAPIService!!.loginUser(paramObject).enqueue(object : Callback<UserData> {

            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    var res = response.body().toString()
                    var res2 = response.body()?.email.toString()
                    Log.e("Shab", call.toString())
                    Log.e("Shab", res)
                    if(response.body()?.email == emailTxtBox.text){
                        Log.e("Shaba", "LOGGED IN $response")
                    }
//                    Log.d("Shaba", "post submitted to API.$response")
                    val i = Intent(applicationContext, DashboardActivity::class.java)
                    startActivity(i)
//                    val textbox = findViewById<TextView>(R.id.textView3)
//                    textbox.text = response.body()!!.toString()
//                    Toast.makeText(this, response.body()!!.toString(), Toast.LENGTH_LONG).show()
//                    Log.i("", "post registration to API" + response.body()!!.toString())
//                    Log.i("", "post status to API" + response.body()!!.status)
//                    Log.i("", "post msg to API" + response.body()!!.messages)

//                    val dashboardIntent = Intent(this, DashboardActivity::class.java)
//                    startActivity(dashboardIntent)
//                    finish()

                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.e("Shaba", t.toString())
            }
        })
    }


    // Google Sign In Methods

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()

                }
            }else{
                Log.w("SignInActivity", exception.toString())
                Log.w("SignInActivity", "HII")
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    Log.d("SignInActivity", "signInWithCredential:success")

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }
}*/