package com.example.fav.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.AnimeModel
import com.example.core.ui.ListAnimeAdapter
import com.example.fav.databinding.FragmentFavoriteBinding
import com.example.fav.di.DaggerFavoriteComponent
import com.example.nganim.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("favorite", "onViewCreated: masuk")
        if (activity != null) {
            Log.d("favorite", "onViewCreated: masuk2")
            showListAnime()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showListAnime() {
        Log.d("favorite", "onViewCreated: masuk3")
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvAnime.apply {
            this.layoutManager = layoutManager
            addItemDecoration(itemDecoration)
        }
        Log.d("favorite", "onViewCreated: masuk4")

        favoriteViewModel.getFavListAnime().observe(viewLifecycleOwner) {
            showLoading(true)
            showData(it)
            showLoading(false)
        }
    }

    private fun showData(data: List<AnimeModel>) {
        val adapter = ListAnimeAdapter()
        adapter.setOnItemClickCallback(object : ListAnimeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AnimeModel) {
                val toDetailFragment =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity()
                toDetailFragment.id = data.id
                findNavController().navigate(toDetailFragment)
            }
        })
        adapter.differ.submitList(data.toMutableList())
        binding.rvAnime.adapter = adapter
    }

    private fun showLoading(it: Boolean) {
        binding.apply {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            rvAnime.visibility = if (it) View.INVISIBLE else View.VISIBLE
        }
    }

}