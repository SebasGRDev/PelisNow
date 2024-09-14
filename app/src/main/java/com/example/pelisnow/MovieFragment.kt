package com.example.pelisnow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MovieFragment : Fragment() {

    private var title: String? = null
    private var detail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE_BUNDLE)
            detail = it.getString(DETAIL_BUNDLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    companion object {

        const val TITLE_BUNDLE = "title_bundle"
        const val DETAIL_BUNDLE = "detail_bundle"

        @JvmStatic fun newInstance(title: String, detail: String) =
                MovieFragment().apply {
                    arguments = Bundle().apply {
                        putString(TITLE_BUNDLE, title)
                        putString(DETAIL_BUNDLE, detail)
                    }
                }
    }
}