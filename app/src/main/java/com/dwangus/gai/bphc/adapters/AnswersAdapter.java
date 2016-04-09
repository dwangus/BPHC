package com.dwangus.gai.bphc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwangus.gai.bphc.R;
import com.dwangus.gai.bphc.model.Answers;
import com.dwangus.gai.bphc.model.Questions;
import com.dwangus.gai.bphc.ui.Forum;
import com.dwangus.gai.bphc.ui.tappedQuestion;

import java.util.List;

/**
 * Created by David Wang on 4/6/2016.
 */
public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder> {
    List<Answers> mAnswers;
    private Context mContext;

    public AnswersAdapter(Context context, List<Answers> answers){
        this.mAnswers = answers;
        this.mContext = context;
    }

    @Override
    public AnswersAdapter.AnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_list_item, parent, false);
        AnswersViewHolder viewHolder = new AnswersViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AnswersAdapter.AnswersViewHolder holder, int position) {
        holder.bindAnswers(mAnswers.get(position));
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    public class AnswersViewHolder extends RecyclerView.ViewHolder {
        public TextView mtheHeader;
        public TextView mtheTime;
        public TextView mtheAnswer;

        public AnswersViewHolder(View itemView){
            super(itemView);
            mtheHeader = (TextView) itemView.findViewById(R.id.answerHeader);
            mtheTime = (TextView) itemView.findViewById(R.id.timeAnswered);
            mtheAnswer = (TextView) itemView.findViewById(R.id.answerDetails);
        }

        public void bindAnswers(Answers answer){
            mtheHeader.setText(answer.getHeader());
            mtheTime.setText(answer.getformattedTime());
            mtheAnswer.setText(answer.getAnswer());
        }
    }
}
