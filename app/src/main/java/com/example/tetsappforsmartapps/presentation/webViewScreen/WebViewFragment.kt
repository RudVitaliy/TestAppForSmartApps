package com.example.tetsappforsmartapps.presentation.webViewScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.tetsappforsmartapps.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding
        get() = _binding ?: throw RuntimeException("FragmentWebViewBinding == null")

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var webView: WebView
    private var webViewState: Bundle? = null
    private var canGoBack: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = binding.webView
        webView.webViewClient = MyWebViewClient()
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            setUpWebView(webView)
            webView.loadUrl("https://www.google.com")
        }
        onBackPressed()
    }



    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (canGoBack) {
                        webView.goBack()
                    }
                    else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
            )
    }


    private fun setUpWebView(webView: WebView) {
        CookieManager.getInstance().setAcceptCookie(true)
        webView.settings.javaScriptEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webViewState = Bundle()
        webViewState.let {
            if (it != null) {
                webView.saveState(it)
            }
        }
        outState.putBundle(WEB_VIEW_STATE, webViewState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            webViewState = savedInstanceState.getBundle(WEB_VIEW_STATE)
            webViewState?.let { webView.restoreState(it) }
            setUpWebView(webView)
        }
    }

    inner class MyWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null) {
                view?.loadUrl(url)
            }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            canGoBack = webView.canGoBack()
        }
    }

    companion object {
        const val WEB_VIEW_STATE = "web_view_state"
    }
}

