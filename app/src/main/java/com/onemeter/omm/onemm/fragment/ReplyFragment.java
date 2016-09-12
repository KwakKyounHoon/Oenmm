package com.onemeter.omm.onemm.fragment;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Environment;
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
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.data.Post;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.ReplyRequest;

import java.io.File;
import java.io.IOException;

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
    //    static final String RECORDED_FILE = "/sdcard/recorded.3GP";
    File mSavedFile;
    int endTime = 60;
    private final int PLATING = 2;
    private final int STOPPING = 0;
    private final int RECORDDING = 1;
    private final int LSTOPPING = 3;
    int volLevel = 0;
    Visualizer mVisualizer;
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

    @OnClick(R.id.btn_send)
    public void sendClick(View view){
        if(state > 1) {
            ReplyRequest request = new ReplyRequest(getContext(), post.getQuestionId(), endTime + "", mSavedFile);
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                    Toast.makeText(getContext(), result.getMessage() + "", Toast.LENGTH_SHORT).show();
                    ((TabMyFragment) (getParentFragment())).popFragment();
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }
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
                    timeView.setText("1 : 00");
                    endRecord();
                    playImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_play));
                    returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_return_on));
                }else if(state == LSTOPPING){
                    timeView.setText("0 : " + count);
                    state = PLATING;
                    playImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_play));
                    returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_return_on));
                }
            }
        }
    };

    Runnable volRunnable = new Runnable() {
        @Override
        public void run() {
            if (state == RECORDDING && recorder != null) {
                mHandler.postDelayed(this, 200);
                if (recorder != null) {
                    int a = recorder.getMaxAmplitude();
                    if (a < 500) {
                        volLevel = 0;
                    } else if (a < 7000) {
                        volLevel = 1;
                    } else if (a < 20000) {
                        volLevel = 2;
                    } else {
                        volLevel = 3;
                    }
                }else{
                    volLevel = 0;
                }
                switch (volLevel) {
                    case 0:
                        soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_off));
                        break;
                    case 1:
                        soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_1));
                        break;
                    case 2:
                        soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_2));
                        break;
                    case 3:
                        soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_on));
                        break;
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
//        recorder.setOutputFile(RECORDED_FILE);
        File file = getSavedFile();
        recorder.setOutputFile(file.getAbsolutePath());
        try{
            Toast.makeText(getContext(),
                    "녹음을 시작합니다.", Toast.LENGTH_LONG).show();
            recorder.prepare();
            recorder.start();
            state = RECORDDING;
            startTime = -1;
            endTime = 60;
            playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_stop));
            mHandler.removeCallbacks(countRunnable);
            mHandler.removeCallbacks(volRunnable);
            mHandler.post(countRunnable);
            mHandler.post(volRunnable);
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
        player = new MediaPlayer();
        try {
            player.setDataSource(mSavedFile.getPath());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        float a = player.getDuration();
        endTime = Math.round(a/1000);
    }

    private void playVoice(){
        try{
//                    playAudio("http://ec2-52-78-124-76.ap-northeast-2.compute.amazonaws.com/images/recorded.mp4");
//                    playAudio("https://drive.google.com/open?id=0Bw3ZvGKva8tGOG93U19Wam52YWc");
            playAudio(mSavedFile);
            Toast.makeText(getContext(), "음악파일 재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
        }
        timeView.setText("0:00");
        startTime = -1;
        state = LSTOPPING;
        playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_stop));
    }

    private void playAudio(File file) throws Exception{
        killMediaPlayer();
        player = new MediaPlayer();
        player.setDataSource(file.getPath());
        setupVisualizerFxAndUI();
        mVisualizer.setEnabled(true);
        player
                .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mVisualizer.setEnabled(false);
                    }
                });
        player.prepare();
        player.start();
//        float a = player.getDuration();
//        endTime = Math.round(a/1000);
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

    public File getSavedFile() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "my_audio");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        mSavedFile = new File(dir, "1MM_" + System.currentTimeMillis() + ".3GP");
        return mSavedFile;
    }

    private void setupVisualizerFxAndUI(){
        mVisualizer = new Visualizer(player.getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        int max = 0;
                        for(int i = 0; i < bytes.length; i++){
                            if(max < bytes[i] + 128)
                                max = bytes[i];
                        }
                        if (max < 10) {
                            soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_off));
                        } else if (max < 100) {
                            soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_1));
                        } else if (max < 120) {
                            soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_2));
                        } else {
                            soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_sound_on));
                        }
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 5, true, false);
    }

    @OnClick(R.id.btn_back)
    public void backClick(View view){
        ((TabMyFragment) (getParentFragment())).popFragment();
    }
}



