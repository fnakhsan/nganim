package com.example.nganim.ui.detail

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.core.data.Resource
import com.example.core.domain.model.DetailAnimeModel
import com.example.nganim.R
import com.example.nganim.databinding.ActivityDetailBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailActivityArgs by navArgs()
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.getDetailAnime(args.id).observe(this) {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Error -> {
                    showLoading(false)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    val data = it.data
                    if (data != null) {
                        showData(data)
                    }
                }
            }
        }

        detailViewModel.isFavAnime(args.id).observe(this) {
            Log.d("fav", "onCreate: it $it")
            setFavState(it)
            isFavorite = it
            Log.d("fav", "onCreate: $isFavorite")
        }
    }

    private fun showData(data: DetailAnimeModel) {
        binding.apply {
            data.apply {
                tvTitle.text = animeEntity.title
                tvAlterTitle.text = otherName
                tvType.text = type
                tvDesc.text = description
                tvDesc.movementMethod = ScrollingMovementMethod()

                Glide.with(this@DetailActivity)
                    .load(animeEntity.image)
                    .into(ivAnime)

                genres.forEach {
                    cgGenres.addView(createTagChip(this@DetailActivity, it))
                }

                fabFav.setOnClickListener {
                    showLoading(true)
                    Log.d("fav", "showData: ${!isFavorite}")
                    cgGenres.removeAllViews()
                    detailViewModel.setFavAnime(
                        DetailAnimeModel(
                            animeEntity,
                            genres,
                            totalEpisode,
                            releaseDate,
                            description,
                            type,
                            status,
                            otherName,
                            !isFavorite
                        )
                    )
                    showLoading(false)
                }
            }
        }
    }

    private fun createTagChip(context: Context, chipName: String): Chip {
        return Chip(context).apply {
            text = chipName
//            setChipBackgroundColorResource(R.color.purple_500)
//            isCloseIconVisible = true
//            setTextColor(ContextCompat.getColor(context, R.color.white))
            setTextAppearance(R.style.label_large)
        }
    }

    private fun setFavState(isFav: Boolean) {
        binding.fabFav.apply {
            if (isFav) setImageResource(R.drawable.ic_favorite_selected) else setImageResource(R.drawable.ic_favorite_default)
        }
    }

    private fun showLoading(it: Boolean) {
        binding.apply {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
    }
}