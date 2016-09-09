package com.onemeter.omm.onemm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.IdCheckRequest;
import com.onemeter.omm.onemm.request.ModifyProfileRequest;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    ImageView back;
    @BindView(R.id.btn_check)
    ImageView check;
    @BindView(R.id.edit_nickname)
    EditText nicknameView;
    @BindView(R.id.edit_name)
    EditText nameVIew;
    @BindView(R.id.edit_message)
    EditText messageView;
    @BindView(R.id.image_id_check)
    ImageView checkView;
    @BindView(R.id.image_play)
    ImageView playImage;
    @BindView(R.id.image_return)
    ImageView returnImage;
    @BindView(R.id.image_sound)
    ImageView soundImage;
    @BindView(R.id.text_message)
    TextView timeView;
    MediaPlayer player;
    MediaRecorder recorder;
    int playbackPosition = 0;
    static final String RECORDED_FILE = "/sdcard/recorded.3GP";
    int endTime = 20;
    private final int PLATING = 2;
    private final int STOPPING = 0;
    private final int RECORDDING = 1;
    private final int LSTOPPING = 3;
    int state = 0;
    int volLevel = 0;
    Visualizer mVisualizer;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        nicknameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String nickname = "";
                    if(!TextUtils.isEmpty(nicknameView.getText().toString())) {
                        nickname = nicknameView.getText().toString();
                        IdCheckRequest request = new IdCheckRequest(ProfileActivity.this, nickname);
                        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                            @Override
                            public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                                if(result.getMessage().equals("0")){
                                    checkView.setVisibility(View.VISIBLE);
                                    checkView.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_id_check_yes));
                                }else{
                                    checkView.setVisibility(View.VISIBLE);
                                    checkView.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_id_check_no));
                                }
                            }

                            @Override
                            public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                            }
                        });
                    }
                }
            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AgreeActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
        if (state == 1 || state == 2 || state == 3) {
            state = STOPPING;
            timeView.setText("0:00");
            playImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_on));
            returnImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_return_off));
        }
    }

    @OnClick(R.id.btn_check)
    public void checkClick(View view){
        String name = null;
        String nickname = nicknameView.getText().toString();
        String message = messageView.getText().toString();
        if(!TextUtils.isEmpty(nameVIew.getText().toString())){
            name = nameVIew.getText().toString();
        }
//        ModifyProfileRequest request = new ModifyProfileRequest(ProfileActivity.this, nickname, name, message, null);
//        NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP,request, new NetworkManager.OnResultListener<NetWorkResultType>() {
//            @Override
//            public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
//                Toast.makeText(ProfileActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {
//            }
//        });
            Intent intent = new Intent(ProfileActivity.this, FollowActivity.class);
            startActivity(intent);
            finish();
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
                    timeView.setText("0 : 20");
                    endRecord();
                    playImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_play));
                    returnImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_return_on));
                } else if(state == LSTOPPING){
                    timeView.setText("0 : " + count);
                    state = PLATING;
                    playImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_play));
                    returnImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_return_on));
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
                        soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_off));
                        break;
                    case 1:
                        soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_1));
                        break;
                    case 2:
                        soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_2));
                        break;
                    case 3:
                        soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_on));
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
        recorder.setOutputFile(RECORDED_FILE);
        try{
            Toast.makeText(ProfileActivity.this,
                    "녹음을 시작합니다.", Toast.LENGTH_LONG).show();
            recorder.prepare();
            recorder.start();
            state = RECORDDING;
            startTime = -1;
            endTime = 20;
            playImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_stop));

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
        playImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_play));
        returnImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_return_on));
        soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_sound_off));
    }

    private void playVoice(){
        try{
            playAudio(RECORDED_FILE);
            Toast.makeText(ProfileActivity.this, "음악파일 재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
        }
        timeView.setText("0:00");
        startTime = -1;
        state = LSTOPPING;
        playImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_stop));
    }

    private void playAudio(String url) throws Exception{
        killMediaPlayer();
        player = new MediaPlayer();
        player.setDataSource(url);
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
        float a = player.getDuration();
        endTime = Math.round(a/1000);

        mHandler.removeCallbacks(countRunnable);
        mHandler.post(countRunnable);
    }

    private void stopVoice(){
        if(player != null){
            playbackPosition = player.getCurrentPosition();
            player.pause();
            Toast.makeText(ProfileActivity.this, "음악 파일 재생 중지됨.",Toast.LENGTH_SHORT).show();
            playImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,R.drawable.ic_record_play));
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
                            soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_off));
                        } else if (max < 100) {
                            soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_1));
                        } else if (max < 120) {
                            soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_2));
                        } else {
                            soundImage.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_record_sound_on));
                        }
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 5, true, false);
    }

}
