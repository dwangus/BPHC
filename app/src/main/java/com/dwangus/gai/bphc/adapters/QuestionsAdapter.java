package com.dwangus.gai.bphc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwangus.gai.bphc.R;
import com.dwangus.gai.bphc.model.Questions;
import com.dwangus.gai.bphc.ui.Forum;
import com.dwangus.gai.bphc.ui.tappedQuestion;

import java.util.List;

/**
 * Created by David Wang on 4/4/2016.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    List<Questions> mQuestions;
    private Context mContext;

    public QuestionsAdapter(Context context, List<Questions> questions){
        this.mQuestions = questions;
        this.mContext = context;
    }

    @Override
    public QuestionsAdapter.QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_item, parent, false);
        QuestionsViewHolder viewHolder = new QuestionsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionsAdapter.QuestionsViewHolder holder, int position) {
        holder.bindQuestions(mQuestions.get(position));
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mtheTitle;
        public TextView mtheDate;
        public TextView mtheNumAnswers;
        private String qID;

        public QuestionsViewHolder(View itemView){
            super(itemView);

            mtheTitle = (TextView) itemView.findViewById(R.id.questionTitle);
            mtheDate = (TextView) itemView.findViewById(R.id.timeSubmitted);
            mtheNumAnswers = (TextView) itemView.findViewById(R.id.numAnswers);

            itemView.setOnClickListener(this);
        }

        public void bindQuestions(Questions question){
            this.qID = question.getID();
            mtheTitle.setText(question.getTitle());
            mtheDate.setText(question.getformattedDate());
            mtheNumAnswers.setText(question.getStringNumAnswers());
        }

        public void onClick(View v){
            Intent intent = new Intent(mContext, tappedQuestion.class);
            intent.putExtra("URL", Forum.FIREBASE_URL);
            intent.putExtra("QUESTION_ID", qID);
            mContext.startActivity(intent);
        }
    }
}
