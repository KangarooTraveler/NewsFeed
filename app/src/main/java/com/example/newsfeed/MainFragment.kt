package com.example.newsfeed

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.ViewModel.MainViewModel
import com.example.newsfeed.ViewModel.MainViewModelFactory
import com.example.newsfeed.databinding.FragmentMainBinding
import com.example.newsfeed.repository.Repository
import java.util.*


class MainFragment : Fragment() {

    private val myAdapter by lazy { AdapterRecyclerView(context as MainActivity) }
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        showLoading()

        //Проверка на подключение пользователя к интернету
        if (isNetworkConnected(context as MainActivity)) initView()
        else Toast.makeText(requireContext(), R.string.connection_message, Toast.LENGTH_SHORT).show()
        return binding.root
    }

    //Инициализация объектов
    private fun initView() {

        //Автоматический запрос в главном окне
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getNewsPost(Locale.getDefault().country)
        showLoading()
        viewModel.myResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                hideLoading()
                if (!it.body()?.articles.isNullOrEmpty())
                    myAdapter.setData(it.body()!!.articles)
                else showLoading()
            }
        }

        //Реализация поиска новостей по упоминанию в заголовке
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            binding.rbGroup.clearCheck()
            viewModel.searchNewsByPopularity(text.toString(), Locale.getDefault().language)
            showLoading()
            viewModel.myResponseBySearch.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    hideLoading()
                    if (!it.body()?.articles.isNullOrEmpty())
                        myAdapter.setData(it.body()!!.articles)
                    else showLoading()
                }
            }
        }

        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.rbBusiness.setOnClickListener { getCategoryPost("business") }
        binding.rbEntertainment.setOnClickListener { getCategoryPost("entertainment") }
        binding.rbGeneral.setOnClickListener { getCategoryPost("general") }
        binding.rbHealth.setOnClickListener { getCategoryPost("health") }
        binding.rbScience.setOnClickListener { getCategoryPost("science") }
        binding.rbSports.setOnClickListener { getCategoryPost("sports") }
        binding.rbTechnology.setOnClickListener { getCategoryPost("technology") }

    }

    //Запрос по категориям
    private fun getCategoryPost(category: String) {
        viewModel.getNewsPostByCategory(Locale.getDefault().country, category)
        showLoading()
        viewModel.myResponseByCategory.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                hideLoading()
                if (!it.body()?.articles.isNullOrEmpty())
                    myAdapter.setData(it.body()!!.articles)
                else showLoading()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun isNetworkConnected(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = true
    }

}