package com.murrayde.retrofittesting.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.murrayde.retrofittesting.R
import kotlinx.android.synthetic.main.fragment_ask_question.*
import kotlinx.android.synthetic.main.fragment_ask_question.tv_ask_question
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class AskQuestionFragment : Fragment() {

    companion object {
        val LOG_TAG = AskQuestionFragment::class.simpleName
    }

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ask_question, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes

        tv_ask_question.text = attributes.titles.en ?: attributes.canonicalTitle
        Glide.with(this)
                .load(attributes.posterImage.original)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .into(iv_ask_question)
        button_ask_question.setOnClickListener {
            // TODO: SUBMIT QUESTION TO FIREBASE DATABASE
        }
    }

}
