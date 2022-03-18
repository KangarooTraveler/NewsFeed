package com.example.newsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsfeed.databinding.FragmentDescriptionBinding


class DescriptionFragment : Fragment() {

    private lateinit var binding: FragmentDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    //Инициализация WebView
    private fun initView() {
        binding.progressBarWebView.isVisible = true
        val url = arguments?.getString("url").toString()
        if (!url.isNullOrEmpty()){
            binding.webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, progress: Int) {
                    if (progress == 100) {
                        binding.progressBarWebView.isVisible = false
                    }
                }
            }
            binding.webView.loadUrl(url)
        }
    }


}