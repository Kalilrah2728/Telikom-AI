package org.twinkletech.telikomai.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            validateLoginCredentials()
        }

    }
    fun validateLoginCredentials() {
        val number = binding.edtxtMsisdnNo.text.toString().trim()
        val password = binding.edtxtPassword.text.toString().trim()

        if (number.isEmpty() && password.isEmpty()) {
            showError("Please enter Subscription Number and Password")
            return
        }

        if (number.isEmpty()) {
            showError("Please enter Subscription Number")
            return
        }

        if (password.isEmpty()) {
            showError("Please enter Password")
            return
        }

        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)

    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Warning")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .setCancelable(false)
            .show()
    }

}