package com.onemeter.omm.onemm.fragment;


import android.content.Context;
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
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onemeter.omm.onemm.MainActivity;
import com.onemeter.omm.onemm.R;
import com.onemeter.omm.onemm.data.MyData;
import com.onemeter.omm.onemm.data.NetWorkResultType;
import com.onemeter.omm.onemm.manager.NetworkManager;
import com.onemeter.omm.onemm.manager.NetworkRequest;
import com.onemeter.omm.onemm.request.IdCheckRequest;
import com.onemeter.omm.onemm.request.ModifyProfileRequest;

import java.io.File;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileModifyFragment extends Fragment {


    public ProfileModifyFragment() {
        // Required empty public constructor
    }

    MyData myData;
    public static String MYDATA = "mydata";

    public static ProfileModifyFragment newInstance(MyData myData) {
        ProfileModifyFragment fragment = new ProfileModifyFragment();
        Bundle args = new Bundle();
        args.putSerializable(MYDATA, myData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myData = (MyData) getArguments().getSerializable(MYDATA);
        }
    }


    @BindView(R.id.btn_back)
    ImageView back;
    @BindView(R.id.btn_check)
    ImageView check;
    @BindView(R.id.edit_nickname)
    EditText nicknameView;
    @BindView(R.id.edit_name)
    EditText nameView;
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
    int endTime = 20;
    private final int ENDRECORD = 2;
    private final int STOPPING = 0;
    private final int RECORDDING = 1;
    private final int LISTENING = 3;
    int state = 0;
    int volLevel = 0;
    Visualizer mVisualizer;
    int count = 0;
    File mSavedFile;
    boolean checkIdFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_modify, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).changeHomeAsUp(true);
        nicknameView.setFilters(new InputFilter[]{filterAlphaNum});
        nicknameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkView.setVisibility(View.GONE);
                if(!b){
                    idCheck();
                }else{
                    checkIdFlag =false;
                }
            }
        });
        init();
        idCheck();
        return view;
    }

    private void popFragment(){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).popFragment();
        }else{
            ((TabSearchFragment) (getParentFragment())).popFragment();
        }
    }

    private void idCheck(){
        String nickname = "";
        if(!TextUtils.isEmpty(nicknameView.getText().toString())) {
            nickname = nicknameView.getText().toString();
            IdCheckRequest request = new IdCheckRequest(getContext(), nickname);
            NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType<String>>() {
                @Override
                public void onSuccess(NetworkRequest<NetWorkResultType<String>> request, NetWorkResultType<String> result) {
                    if(result.getResult().equals("0")){
                        checkView.setVisibility(View.VISIBLE);
                        checkView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_id_check_yes));
                        checkIdFlag = true;
                    }else{
                        checkView.setVisibility(View.VISIBLE);
                        checkView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_id_check_no));
                        checkIdFlag = false;
                    }
                }

                @Override
                public void onFail(NetworkRequest<NetWorkResultType<String>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }
    }

    private void init(){
        nicknameView.setText(myData.getNickname());
        nameView.setText(myData.getName());
        if(myData.getStateMessage() != null) messageView.setText(myData.getStateMessage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            if(getParentFragment() instanceof TabMyFragment){
//                ((TabMyFragment) (getParentFragment())).popFragment();
//            }else if(getParentFragment() instanceof TabHomeFragment){
//                ((TabHomeFragment) (getParentFragment())).popFragment();
//            }else if(getParentFragment() instanceof TabRankFragment){
//                ((TabRankFragment) (getParentFragment())).popFragment();
//            }else{
//                ((TabSearchFragment) (getParentFragment())).popFragment();
//            }
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_back)
    public void backClick(View view){
        if(getParentFragment() instanceof TabMyFragment){
            ((TabMyFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabHomeFragment){
            ((TabHomeFragment) (getParentFragment())).popFragment();
        }else if(getParentFragment() instanceof TabRankFragment){
            ((TabRankFragment) (getParentFragment())).popFragment();
        }else{
            ((TabSearchFragment) (getParentFragment())).popFragment();
        }
    }

    @OnClick(R.id.btn_check)
    public void checkClick(View view){
        if(state == STOPPING || state == ENDRECORD) {
            String name = nameView.getText().toString();
            String nickname = nicknameView.getText().toString();
            String message = messageView.getText().toString();
            if (TextUtils.isEmpty(nameView.getText().toString())) {
                Toast.makeText(getContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(nicknameView.getText().toString())) {
                Toast.makeText(getContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            } else if (!checkIdFlag) {

            } else {
                ModifyProfileRequest request = new ModifyProfileRequest(getContext(), nickname, name, message, mSavedFile);
                NetworkManager.getInstance().getNetworkData(NetworkManager.MYOKHTTP, request, new NetworkManager.OnResultListener<NetWorkResultType>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetWorkResultType> request, NetWorkResultType result) {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(messageView.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(nameView.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(nicknameView.getWindowToken(), 0);
                        popFragment();
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetWorkResultType> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        }
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
            case ENDRECORD:{
                playVoice();
                break;
            }
            case LISTENING:{
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
            playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_on));
            returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_return_off));
        }
    }

    long startTime = -1;

    Handler mHandler = new Handler(Looper.getMainLooper());

    Runnable countRunnable = new Runnable() {
        @Override
        public void run() {
            if (state == RECORDDING || state == LISTENING) {
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
                    playImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_play));
                    returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_record_return_on));
                } else if(state == LISTENING){
                    timeView.setText("0 : " + count);
                    state = ENDRECORD;
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
            endTime = 20;
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
        state = ENDRECORD;
        playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_play));
        returnImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_return_on));
        soundImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_sound_off));
    }

    private void playVoice(){
        try{
            playAudio(mSavedFile);
            Toast.makeText(getContext(), "음악파일 재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
        }
        timeView.setText("0:00");
        startTime = -1;
        state = LISTENING;
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
        float a = player.getDuration();
        endTime = Math.round(a/1000);

        mHandler.removeCallbacks(countRunnable);
        mHandler.post(countRunnable);
    }

    private void stopVoice(){
        if(player != null){
            playbackPosition = player.getCurrentPosition();
            player.pause();
            Toast.makeText(getContext(), "음악 파일 재생 중지됨.",Toast.LENGTH_SHORT).show();
            playImage.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_record_play));
            state = ENDRECORD;
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
                            if(state == LISTENING) {
                                int max = 0;
                                for (int i = 0; i < bytes.length; i++) {
                                    if (max < bytes[i] + 128)
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
                        }


                        public void onFftDataCapture(Visualizer visualizer,
                                                     byte[] bytes, int samplingRate) {
                        }
                    }, Visualizer.getMaxCaptureRate() / 5, true, false);
    }

    protected InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    @Override
    public void onDestroy() {
        state = STOPPING;
        if(recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        killMediaPlayer();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        state = STOPPING;
        if(recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        killMediaPlayer();
        super.onDetach();
    }
}
