package com.example.nganim.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.AnimeModel
import com.example.core.ui.ListAnimeAdapter
import com.example.nganim.R
import com.example.nganim.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModels()
//    private val adapter by lazy { ListAnimeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            showListAnime()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showListAnime() {
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding?.rvAnime?.layoutManager = layoutManager

        binding?.actionSearch?.apply {
            queryHint = resources.getString(R.string.search)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.searchAnime(query, null).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                showLoading(false)
                            }

                            is Resource.Empty -> {
                                showLoading(false)
                                binding?.rvAnime?.visibility = View.INVISIBLE
                            }

                            is Resource.Success -> {
                                showLoading(false)
                                showData(it.data)
                            }
                        }
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        binding?.rvAnime?.visibility = View.INVISIBLE
                    }
                    return true
                }
            })
        }
    }

    private fun showData(data: List<AnimeModel>) {
        val adapter = ListAnimeAdapter()
        adapter.setOnItemClickCallback(object : ListAnimeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AnimeModel) {
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                toDetailFragment.id = data.id
                findNavController().navigate(toDetailFragment)
            }
        })
        adapter.differ.submitList(data.toMutableList())
        binding?.rvAnime?.adapter = adapter
    }

    private fun showLoading(it: Boolean) {
        binding?.apply {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            rvAnime.visibility = if (it) View.INVISIBLE else View.VISIBLE
        }
    }
}