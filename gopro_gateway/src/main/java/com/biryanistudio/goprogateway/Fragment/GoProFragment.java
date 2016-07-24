package com.biryanistudio.goprogateway.Fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.biryanistudio.goprogateway.FFmpeg.FFmpegStream;
import com.biryanistudio.goprogateway.FFmpeg.FFmpegUpload;

/**
 * Created by sravan953 on 13/06/16.
 */
public class GoProFragment extends Fragment implements View.OnClickListener {
    final private String TAG = getClass().getSimpleName();
    private TextView mTextLog;
    private Button mButtonStartStream;
    private static Button mButtonStartUpload;
    private Button mButtonStopStream;
    private Intent mIntentStartStream;
    private Intent mIntentStartUpload;

    public static class UploadReadyReceiver extends BroadcastReceiver {
        public UploadReadyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if(!mButtonStartUpload.isEnabled())
                mButtonStartUpload.setEnabled(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gopro, container, false);
        mTextLog = (TextView) view.findViewById(R.id.tv_log);
        mButtonStartStream = (Button) view.findViewById(R.id.btn_start_stream);
        mButtonStartStream.setOnClickListener(this);
        mButtonStartUpload = (Button) view.findViewById(R.id.btn_start_upload);
        mButtonStartUpload.setOnClickListener(this);
        mButtonStartUpload.setEnabled(false);
        mButtonStopStream = (Button) view.findViewById(R.id.btn_stop_stream);
        mButtonStopStream.setOnClickListener(this);
        mButtonStopStream.setEnabled(false);

        mIntentStartStream = new Intent(getActivity(), FFmpegStream.class);
        mIntentStartUpload = new Intent(getActivity(), FFmpegUpload.class);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().stopService(mIntentStartStream);
        getActivity().stopService(mIntentStartUpload);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_start_stream:
                getActivity().startService(mIntentStartStream);
                mButtonStartStream.setEnabled(false);
                mButtonStopStream.setEnabled(true);
                break;
            case R.id.btn_start_upload:
                getActivity().startService(mIntentStartUpload);
                break;
            case R.id.btn_stop_stream:
                getActivity().stopService(mIntentStartStream);
                getActivity().stopService(mIntentStartUpload);
                mButtonStartStream.setEnabled(true);
                mButtonStartUpload.setEnabled(false);
                mButtonStopStream.setEnabled(false);
                break;
            default:
                break;

        }
    }
}
