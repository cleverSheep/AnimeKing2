package com.murrayde.retrofittesting.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.murrayde.retrofittesting.R
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    companion object {
        val LOG_TAG = DetailFragment::class.simpleName
    }

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes

        tv_title.text = attributes.titles.en ?: attributes.canonicalTitle
        tv_description.text = attributes.synopsis
        Glide.with(this)
                .load(attributes.posterImage.original)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .into(iv_image)
        Log.d(LOG_TAG, attributes.posterImage.original)

        button_take_quiz.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToLowQuestionCountFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }
}
