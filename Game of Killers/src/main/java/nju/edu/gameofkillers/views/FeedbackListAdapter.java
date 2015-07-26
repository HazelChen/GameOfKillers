package nju.edu.gameofkillers.views;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.umeng.fb.adapter.a;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Reply;
import nju.edu.gameofkillers.R;

import java.util.List;

/**
 * Created by hazel on 2015-07-26.
 */
public class FeedbackListAdapter extends a {
    private List<Reply> replies;

    public FeedbackListAdapter(Context context, Conversation conversation) {
        super(context, conversation);
        this.replies = conversation.getReplyList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View superView = super.getView(position, convertView, parent);

        Log.d("right?", "right!");
        RelativeLayout replyContentLayout =
                (RelativeLayout) superView.findViewById(
                        R.id.relativelayout_feedback_item);
        Reply reply = replies.get(position);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) replyContentLayout.getLayoutParams();
        if ("dev_reply".equals(reply.type)){
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            replyContentLayout.setBackgroundResource(R.drawable.feedback_item_left);
        }else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            replyContentLayout.setBackgroundResource(R.drawable.feedback_item_right);
        }
        replyContentLayout.setLayoutParams(layoutParams);

        return superView;
    }

}
