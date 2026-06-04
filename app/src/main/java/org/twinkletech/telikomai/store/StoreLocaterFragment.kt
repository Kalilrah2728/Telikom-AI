package org.twinkletech.telikomai.store

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.databinding.FragmentNewsBinding
import org.twinkletech.telikomai.databinding.FragmentStoreLocaterBinding


class StoreLocaterFragment : Fragment() {

    private var _binding: FragmentStoreLocaterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStoreLocaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true

        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {
                binding.progressBar.visibility = View.VISIBLE
                binding.webView.visibility = View.INVISIBLE
            }

            override fun onPageFinished(
                view: WebView?,
                url: String?
            ) {
                binding.progressBar.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE
            }
        }

        binding.webView.webChromeClient = WebChromeClient()

        binding.webView.loadUrl("https://www.telikom.com.pg/locations/retail/")

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_storeLocaterFragment_to_dashboardFragment)
        }
    }

    override fun onDestroyView() {
        binding.webView.destroy()
        _binding = null
        super.onDestroyView()
    }

}