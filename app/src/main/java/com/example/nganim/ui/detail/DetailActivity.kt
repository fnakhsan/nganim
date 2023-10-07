package com.example.nganim.ui.detail

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
    }

    private fun showData(data: DetailAnimeModel) {
        binding.apply {
            tvTitle.text = data.animeEntity.title
            tvAlterTitle.text = data.otherName
            tvType.text = data.type
            tvDesc.text = data.description
            tvDesc.movementMethod = ScrollingMovementMethod()
            Glide.with(this@DetailActivity)
                .load(data.animeEntity.image)
                .into(ivAnime)
            data.genres.forEach {
                cgGenres.addView(createTagChip(this@DetailActivity, it.genre))
            }

        }
    }

    private fun createTagChip(context: Context, chipName: String): Chip {
        return Chip(context).apply {
            text = chipName
//            setChipBackgroundColorResource(R.color.purple_500)
//            isCloseIconVisible = true
//            setTextColor(ContextCompat.getColor(context, R.color.white))
            setTextAppearance(R.style.label_small)
        }

    }

    private fun showLoading(it: Boolean) {
        binding.apply {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
    }
}