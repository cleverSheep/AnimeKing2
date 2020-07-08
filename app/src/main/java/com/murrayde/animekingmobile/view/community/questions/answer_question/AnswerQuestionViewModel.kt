package com.murrayde.animekingmobile.view.community.questions.answer_question

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.CommunityQuestion

class AnswerQuestionViewModel : ViewModel() {

    private var listQuestions = MutableLiveData<CommunityQuestion>()

    private val firebaseDB = FirebaseFirestore.getInstance()

}