package com.onemeter.omm.onemm.fragment;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.onemeter.omm.onemm.MyApplication;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.Post;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */



public class ReplyFragment extends Fragment {
    Post post;
    MediaPlayer player;
    MediaRecorder recorder;
    int playbackPosition = 0;
    static final String RECORDED_FILE = "/sdcard/recorded.3GP";
    int endTime = 60;
    private final int PLATING = 2;
    private final int STOPPING = 0;
    private final int RECORDDING = 1;
    private final int LSTOPPING = 3;
    int state = 0;

    public ReplyFragment() {
        // Required empty public constructor
    }

    public static String POST = "post";

    public static ReplyFragment newInstance(Post post) {
        ReplyFragment fragment = new ReplyFragment();
        Bundle args = new Bundle();
        args.putSerializable(POST, post);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable(POST);
        }
    }

    @BindView(R.id.text_question)
    TextView qestionView;
    @BindView(R.id.image_questioner)
    ImageView qustionerView;
    @BindView(R.id.text_cost)
    TextView costView;
    @BindView(R.id.text_questioner_name)
    TextView qNameView;
    @BindView(R.id.text_time)
    TextView timeView;
    @BindView(R.id.image_play)
    ImageView playImage;
    @BindView(R.id.image_return)
    ImageView returnImage;
    @BindView(R.id.image_sound)
    ImageView soundImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reply, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        setData();
        return view;
    }

    public void setData(){
        qestionView.setText(post.getQuestionerContent());
        costView.setText(post.getPrice());
        qNameView.setText(post.getQuestionerName());
        Glide.with(qustionerView.getContext())
                .load(post.getQuestionerPhoto())
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .into(qustionerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            ((TabMyFragment) (getParentFragment())).popFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.image_play)
    public void playClick(View view){
        switch (state){
            case STOPPING:{
                startRecord();
                break;
            }
            case RECORDDING :{
                endRecord();
                break;
            }
            case PLATING :{
                playVoice();
                break;
            }
            case LSTOPPING :{
                stopVoice();
                break;
            }
        }
    }

    @OnClick(R.id.image_return)
    public void returnClick(View view){
        if (state == 2 || state == 3) {
            state = STOPPING;
            timeView.setText("0:00");
            playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_on));
            returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_return_off));
        }
    }

    long startTime = -1;

    Handler mHandler = new Handler(Looper.getMainLooper());

    Runnable countRunnable = new Runnable() {
        @Override
        public void run() {
            if (state == RECORDDING || state == LSTOPPING) {
                long time = SystemClock.elapsedRealtime();
                if (startTime == -1) {
                    startTime = time;
                }
                int gap = (int) (time - startTime);
                int count = 0 + gap / 1000;
                int rest = 1000 - gap % 1000;
                if (count < endTime) {
                    timeView.setText("0 : " + count);
                    mHandler.postDelayed(this, rest);
                } else if(state == RECORDDING) {
                    timeView.setText("1.00");
                    recorder.stop();
                    recorder.release();
                    playImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_play));
                    returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_return_on));
                } else if(state == LSTOPPING){
                    timeView.setText("0 : " + count);
                    state = PLATING;
                    playImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_play));
                    returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_return_on));
                }
            }
        }
    };

    private void startRecord(){
        if(recorder != null){
            recorder.stop();
            recorder.release();
            recorder = null;
        }// TODO Auto-generated method stub
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(RECORDED_FILE);
        try{
            Toast.makeText(getContext(),
                    "녹음을 시작합니다.", Toast.LENGTH_LONG).show();
            recorder.prepare();
            recorder.start();
            state = RECORDDING;
            startTime = -1;
            endTime = 60;
            playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_stop));
            soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_sound_on));
            mHandler.removeCallbacks(countRunnable);
            mHandler.post(countRunnable);
        }catch (Exception ex){
            Log.e("SampleAudioRecorder", "Exception : ", ex);
        }
    }

    private void endRecord(){
        if(recorder == null) return;
        recorder.stop();
        recorder.release();
        recorder = null;
        state = PLATING;
        playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_play));
        returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_return_on));
        soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_sound_off));
    }

    private void playVoice(){
        try{
//                    playAudio("http://ec2-52-78-124-76.ap-northeast-2.compute.amazonaws.com/images/recorded.mp4");
//                    playAudio("https://drive.google.com/open?id=0Bw3ZvGKva8tGOG93U19Wam52YWc");
            playAudio(RECORDED_FILE);
            Toast.makeText(getContext(), "음악파일 재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
        }
        timeView.setText("0:00");
        startTime = -1;
        state = LSTOPPING;
        playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_stop));
    }

    private void playAudio(String url) throws Exception{
        killMediaPlayer();
        player = new MediaPlayer();
        player.setDataSource(url);
        player.prepare();
        player.start();
        endTime = player.getDuration()/1000;
        mHandler.removeCallbacks(countRunnable);
        mHandler.post(countRunnable);
    }

    private void stopVoice(){
        if(player != null){
            playbackPosition = player.getCurrentPosition();
            player.pause();
            Toast.makeText(getContext(), "음악 파일 재생 중지됨.",Toast.LENGTH_SHORT).show();
            playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_play));
            state = PLATING;
        }
    }

    private void killMediaPlayer() {
        if(player != null){
            try {
                player.release();
            } catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    @OnClick(R.id.btn_back)
    public void backClick(View view){
        ((TabMyFragment) (getParentFragment())).popFragment();
    }
}



