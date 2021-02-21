@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingmobile.screen.game_over


import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.util.QuestionUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_view_results.*
import timber.log.Timber


@AndroidEntryPoint
class GameOverResultsList : Fragment() {

    private val resultsArgs: GameOverResultsListArgs by navArgs()
    private lateinit var gameOverViewModel: GameOverViewModel
    private lateinit var media: MediaPlayer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var animeTitle: String
    private lateinit var auth: FirebaseAuth
    private lateinit var handler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_results, container, false)
        gameOverViewModel = ViewModelProvider(requireActivity()).get(GameOverViewModel::class.java)
        animeTitle = if (resultsArgs.animeAttributes.titles.en != null) resultsArgs.animeAttributes.titles.en else resultsArgs.animeAttributes.canonicalTitle
        auth = FirebaseAuth.getInstance()
        handler = Handler()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
        gameOverViewModel.getGameTotalCorrectLD.observe(viewLifecycleOwner, Observer {
            questions_correct.text = "$it question(s) correct!"
        })
        gameOverViewModel.getGameQuizScoreLD.observe(viewLifecycleOwner, Observer {
            quiz_score.text = "$it pts"
        })
        gameOverViewModel.getHighScoreBonusLD.observe(viewLifecycleOwner, Observer {
            high_score_bonus.text = "$it pts"
        })
        gameOverViewModel.getGameBonusTimeLD.observe(viewLifecycleOwner, Observer {
            time_bonus.text = "$it pts"
        })
        gameOverViewModel.getPreviousPlayerXPLiveData().observe(viewLifecycleOwner, Observer{ progress ->
            progressBarSimpleCustom.setAnimationEnabled(false)
            progressBarSimpleCustom.setProgress(progress)
        })
        gameOverViewModel.getUpdatedPlayerXPLiveData().observe(viewLifecycleOwner, Observer{ xp ->
            current_xp.text = "XP $xp"
        })
        updateCustomPrimaryProgressBar(gameOverViewModel.getTotalXP(), 0)
        gameOverViewModel.getPlayerRequiredXP().observe(viewLifecycleOwner, Observer {required_experience ->
            level_up_xp.text = "/${required_experience}"
            if (QuestionUtil.XP_TRACK == required_experience.toFloat()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBarSimpleCustom.max = required_experience.toFloat()
                }, 0)
            } else  {
                handler.postDelayed({
                    progressBarSimpleCustom.max = required_experience.toFloat()
                    QuestionUtil.XP_TRACK = required_experience.toFloat()
                }, 1500)
            }
        })
        gameOverViewModel.getPlayerXPToLevelUp().observe(viewLifecycleOwner, Observer {
            required_xp.text = "$it XP "
        })
        gameOverViewModel.getPlayerCurrentLevel().observe(viewLifecycleOwner, Observer {
            current_level.text = "$it"
            next_level.text = "${it + 1}"
        })
        btn_results_exit_game.setOnClickListener {
            val action = GameOverResultsListDirections.actionViewResultsToDetailFragment(resultsArgs.animeAttributes)
            Navigation.findNavController(view).navigate(action)

        }
        btn_results_play_again.setOnClickListener {
            val action = GameOverResultsListDirections.actionViewResultsToAnswerQuestionFragment(resultsArgs.animeAttributes)
            Navigation.findNavController(view).navigate(action)
        }
        if(gameOverViewModel.getTotalCorrect() > gameOverViewModel.getHighScore()) {
            gameOverViewModel.updateBackendHighScore(auth.uid!!, animeTitle, gameOverViewModel.getTotalCorrect())
        }
        gameOverViewModel.getRequiredExperience(auth.uid!!)
    }

    private fun updateCustomPrimaryProgressBar(incrementProgress: Int, progress: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            progressBarSimpleCustom.enableAnimation()
            val newProgressIncrement = progressBarSimpleCustom.progress
            progressBarSimpleCustom.progress = progressBarSimpleCustom.progress + incrementProgress
            Timber.d("progress: ${progressBarSimpleCustom.progress}")
            Timber.d("prog max: ${progressBarSimpleCustom.max}")
            if (progressBarSimpleCustom.progress >= progressBarSimpleCustom.max) {
                resetPrimaryProgressBar((incrementProgress + newProgressIncrement) - QuestionUtil.XP_TRACK)
            }
            updateCustomSecondaryProgress()
        }, 625)
    }

    private fun resetPrimaryProgressBar(newProgress: Float) {
        Handler(Looper.getMainLooper()).postDelayed({
            activity?.runOnUiThread {
                progressBarSimpleCustom.setAnimationEnabled(false)
                progressBarSimpleCustom.progress = 0.toFloat()
                updateCustomSecondaryProgress()
                startAnimationReset(newProgress)
            }
        }, 685)
    }

    private fun startAnimationReset(newProgress: Float) {
        Handler(Looper.getMainLooper()).postDelayed({
            activity?.runOnUiThread {
                progressBarSimpleCustom.enableAnimation()
                progressBarSimpleCustom.progress = progressBarSimpleCustom.progress + newProgress
                updateCustomSecondaryProgress()
            }
        }, 75)
    }

    private fun updateCustomSecondaryProgress() {
        progressBarSimpleCustom.secondaryProgress = progressBarSimpleCustom.progress + 35
    }

    override fun onStop() {
        super.onStop()
        Timber.e("fragment stopped")
        handler.removeCallbacksAndMessages(null)
    }
}
