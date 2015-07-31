package com.hedwig.gameofkillers.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.fb.model.Conversation;
import com.hedwig.gameofkillers.R;;

/**
 * Created by hazel on 2015-07-26.
 */
public class MyFeedbackFragment extends FeedbackFragment {
    private static final String TAG = MyFeedbackFragment.class.getSimpleName();

    public MyFeedbackFragment() {
        super();
    }

    public static FeedbackFragment newInstance(String s) {
        FeedbackFragment localFeedbackFragment = new MyFeedbackFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString(BUNDLE_KEY_CONVERSATION_ID, s);
        localFeedbackFragment.setArguments(localBundle);
        return localFeedbackFragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        assert(view != null);
        ListView listView = (ListView) view.findViewById(R.id.umeng_fb_reply_list);

        FeedbackAgent feedbackAgent = new FeedbackAgent(getActivity());
        String str = getArguments().getString("conversation_id");
        Conversation conversation = feedbackAgent.getConversationById(str);
        FeedbackListAdapter feedbackListAdapter =
                new FeedbackListAdapter(getActivity(), conversation);
        listView.setAdapter(feedbackListAdapter);

        /*EditText input = (EditText) view.findViewById(R.id.umeng_fb_send_content);
        final Button sendButton = (Button) view.findViewById(R.id.umeng_fb_send_btn);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    sendButton.setEnabled(true);
                    sendButton.setTextColor(getResources().getColor(R.color.umeng_fb_white));
                    sendButton.setBackgroundColor(getResources().getColor(R.color.umeng_fb_lightblue));
                } else {
                    sendButton.setEnabled(false);
                    sendButton.setTextColor(getResources().getColor(R.color.umeng_fb_white));
                    sendButton.setBackgroundColor(getResources().getColor(R.color.theme));
                }
            }
        });*/

        return view;
    }
}
