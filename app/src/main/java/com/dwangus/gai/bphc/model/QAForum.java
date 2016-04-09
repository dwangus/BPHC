package com.dwangus.gai.bphc.model;

import java.util.List;

/**
 * Created by David Wang on 4/5/2016.
 */
public class QAForum {
    //
    private List<Questions> mQuestionsList;
    private List<Answers> mAnswersList;

    public List<Questions> getQuestionsList() {
        return mQuestionsList;
    }

    public void setQuestionsList(List<Questions> mQuestionsList) {
        this.mQuestionsList = mQuestionsList;
    }

    public List<Answers> getAnswersList() {
        return mAnswersList;
    }

    public void setAnswersList(List<Answers> mAnswersList) {
        this.mAnswersList = mAnswersList;
    }

}
