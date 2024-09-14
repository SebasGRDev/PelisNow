package com.example.pelisnow

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.pelisnow.MovieFragment.Companion.DETAIL_BUNDLE
import com.example.pelisnow.MovieFragment.Companion.TITLE_BUNDLE
import com.example.pelisnow.topMovies.data.MovieService
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val service = MovieService.RetrofitServiceFactory.makeRetrofitService()

        lifecycleScope.launch {
            val movies= service.listTopMovies("d741db808360f35a13008f18b828ed3c")
            println(movies)
        }

        val bundle = bundleOf(TITLE_BUNDLE to "Avengers",
            DETAIL_BUNDLE to "Description of movie")
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MovieFragment>(R.id.fragment_container_view, args = bundle)
        }
    }
}