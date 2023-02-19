package com.example.hackathon.page

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackathon.core.BaseFragment
import com.example.hackathon.databinding.Fragment1Binding
import com.example.hackathon.utility.ViewUtility
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


open class Fragment1 constructor() : BaseFragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    open fun Fragment1() {}
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        _binding!!.loginButtonGoogle.setOnClickListener { signIn() }
        _binding!!.btnSignOut.setOnClickListener {
            mGoogleSignInClient?.signOut()
            auth.signOut()
            ViewUtility.showToast("You are signed out!", requireContext())
        }
    }

    fun initData() {
        auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener {
            val user: FirebaseUser? = auth.currentUser
            updateUI(user)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1016714489137-dhdse5r7816jk3iir57kadaj19rke7bg.apps.googleusercontent.com")
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
                updateUI(auth.currentUser)
            } catch (e: ApiException) {
                loginFailed()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) updateUI(auth.currentUser)
                else loginFailed()
            }
    }


    private fun loginFailed() {
        ViewUtility.showToast("failed", requireContext())
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        _binding!!.tvLoggedIn.visibility = if (user != null) View.VISIBLE else View.GONE
        if (user != null) _binding!!.tvLoggedIn.text =
            StringBuilder().append("You are signed in with Google!")
                .append("\n\nEmail: " + user.email.toString())
                .append("\nName: " + user.displayName.toString())
        _binding!!.btnSignOut.visibility = if (user != null) View.VISIBLE else View.GONE
        _binding!!.loginButtonGoogle.visibility = if (user != null) View.GONE else View.VISIBLE

    }


    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }
}
