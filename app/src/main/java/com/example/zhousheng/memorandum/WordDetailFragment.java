package com.example.zhousheng.memorandum;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhousheng.memorandum.DB_col.Words;


public class WordDetailFragment extends Fragment {
    private static final String TAG="myTag";
    public static final String ARG_ID = "id";

    private String mID;//事件主键
    private OnFragmentInteractionListener mListener;//本Fragment所在的Activity


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param wordID Parameter 1.
     * @return A new instance of fragment WordDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordDetailFragment newInstance(String wordID) {
        WordDetailFragment fragment = new WordDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, wordID);
        fragment.setArguments(args);
        return fragment;
    }

    public WordDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mID = getArguments().getString(ARG_ID);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        Log.v(TAG,mID);

        WordsDB wordsDB=WordsDB.getWordsDB();

        if(wordsDB!=null && mID!=null){
            TextView textViewWord=(TextView)view.findViewById(R.id.word);
            TextView textViewWordMeaning=(TextView)view.findViewById(R.id.wordmeaning);
            TextView textViewWordSample=(TextView)view.findViewById(R.id.wordsample);

            Words.WordDescription item=wordsDB.getSingleWord(mID);
            if(item!=null){
                textViewWord.setText(item.word);
                textViewWordMeaning.setText(item.meaning);
                textViewWordSample.setText(item.sample);
            }
            else{
                textViewWord.setText("");
                textViewWordMeaning.setText("");
                textViewWordSample.setText("");
            }

        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onWordDetailClick(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onWordDetailClick(Uri uri);

    }

}

