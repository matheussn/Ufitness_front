package com.example.ufitness.core.client

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ufitness.R
import com.example.ufitness.databinding.FragmentClientRegisterBinding
import com.example.ufitness.databinding.FragmentLoginBinding
import com.example.ufitness.utils.Encrypt

class ClientRegister : Fragment() {
    private lateinit var encrypt: Encrypt
    private var _binding: FragmentClientRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        encrypt = Encrypt()
        print("CADE?")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sigUpButton.setOnClickListener {
            submitButtonHandler()
        }
    }

    private fun submitButtonHandler() {
        //Decide what happens when the user clicks the submit button

        val fullName = binding.name.text.toString()
        val email = binding.email.text.toString()
        val password = encrypt.encrypt(binding.password.text.toString())
        val repeatPassword = encrypt.encrypt(binding.repeatPassword.text.toString())

        if (fullName.isBlank()) {
            Toast.makeText(context, "Nome inválido", Toast.LENGTH_LONG).show()
            return
        }
        if (email.isBlank() || !isEmailValid(email)) {
            Toast.makeText(context, "Email inválido", Toast.LENGTH_LONG).show()
            return
        }

        if (password.isBlank()) {
            Toast.makeText(context, "Senha inválida", Toast.LENGTH_LONG).show()
            return
        }

        if (repeatPassword.isBlank()) {
            Toast.makeText(context, "Repita a senha", Toast.LENGTH_LONG).show()
            return
        }

        if (password.compareTo(repeatPassword) != 0) {
            Toast.makeText(context, "As senhas não batem", Toast.LENGTH_LONG).show()
            return
        }

        println(fullName)
        println(email)
        println(password)
        println(repeatPassword)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://ufitness-api.herokuapp.com/api/v1/client"

        // Request a string response from the provided URL.

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener {
                cleanFields()
                Toast.makeText(context, "Usuário criado com sucesso", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                Toast.makeText(
                    context,
                    "Ocorreu um erro ao conectar ao servidor. Tente novamente mais tarde",
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return "{\"nome\": \"$fullName\", \"email\":\"$email\", \"senha\":\"$password\"}".toByteArray()
            }
        }

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun cleanFields() {
        binding.name.setText("")
        binding.email.setText("")
        binding.password.setText("")
        binding.repeatPassword.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}