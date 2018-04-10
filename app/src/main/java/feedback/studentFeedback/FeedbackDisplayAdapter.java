package feedback.studentFeedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.asus.klasseandroid.R;

import java.util.List;

/**
 * Created by harleen on 28/3/18.
 * This just defines the way the feedback looks!
 * it's important for when using
 * recyclerView
 */
public class FeedbackDisplayAdapter extends RecyclerView.Adapter<FeedbackDisplayAdapter.FeedbackViewHolder> {


    private Context mCtx;
    private List<FeedbackLayout> feedbackList;

    public FeedbackDisplayAdapter(Context mCtx, List<FeedbackLayout> feedbackList) {
        this.mCtx = mCtx;
        this.feedbackList = feedbackList;
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_feedback_layout, null);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {
        FeedbackLayout feedback = feedbackList.get(position);
        holder.textViewFileName.setText(String.valueOf(feedback.getPdfFileName()));
        holder.textViewFeedback.setText(String.valueOf(feedback.getFeedback()));
        holder.textViewPgNumber.setText(String.valueOf(feedback.getPgNumber()));
        holder.textViewTime.setText(String.valueOf(feedback.getTime()));
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    class FeedbackViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFileName, textViewFeedback, textViewPgNumber, textViewTime;

        public FeedbackViewHolder(View itemView) {
            super(itemView);

            textViewFileName = itemView.findViewById(R.id.pdfFileName);
            textViewFeedback = itemView.findViewById(R.id.feedbackContent);
            textViewPgNumber = itemView.findViewById(R.id.pgNumber);
            textViewTime = itemView.findViewById(R.id.time);

        }
    }
}