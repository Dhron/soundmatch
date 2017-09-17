package com.example.simran.soundmatch;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import java.util.List;
import com.example.simran.soundmatch.tarsos.PitchDetectionResult;
import com.example.simran.soundmatch.tarsos.Yin;
import com.example.simran.soundmatch.utils.AudioUtils;

public class Tuner {
    private static final String TAG = Tuner.class.getSimpleName();
    private int sampleRate;
    private int bufferSize;
    private volatile int readSize;
    private volatile int amountRead;
    private volatile float[] buffer;
    private volatile short[] intermediaryBuffer;
    private AudioRecord audioRecord;
    private volatile Yin yin;
    private volatile Note currentNote;
    private volatile PitchDetectionResult result;
    private volatile boolean isRecording;
    private volatile Handler handler;
    private Thread thread;
    private Context mContext;
    private List<MusicItem> noteList = TunerActivity.wListMusic;
    private int currIndex = 0;

    //provide the tuner view implementing the TunerUpdate to the constructor
    public Tuner(Context context){
        mContext = context;
        init();

    }

    public void init(){
        this.sampleRate = AudioUtils.getSampleRate();
        this.bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
        this.readSize = bufferSize / 4;
        this.buffer = new float[readSize];
        this.intermediaryBuffer = new short[readSize];
        this.isRecording = false;
        this.audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, sampleRate, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        this.yin = new Yin(sampleRate, readSize);
        this.currentNote = new Note(Note.DEFAULT_FREQUENCY);
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void start(){
        if(audioRecord != null) {
            isRecording = true;
            audioRecord.startRecording();
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //Runs off the UI thread
                    findAndPrintNote();
                }
            }, "Tuner Thread");
            thread.start();
        }
    }

    private void findAndPrintNote(){

        while(isRecording){
            amountRead = audioRecord.read(intermediaryBuffer, 0, readSize);
            buffer = shortArrayToFloatArray(intermediaryBuffer);
            result = yin.getPitch(buffer);
            currentNote.changeTo(result.getPitch());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (currentNote.getFrequency() != Note.UNKNOWN_FREQUENCY) {
                        TextView txtView = (TextView) ((Activity) mContext).findViewById(R.id.note_text);
                        String currNote = currentNote.getNote();
                        txtView.setText(currNote);
                        if (currNote == noteList.get(currIndex).note) {
                            ++currIndex;
                            // insert UI
                        }
                        else {
                            currIndex = 0;
                        }


                    }
                }
            });
        }
    }

    private float[] shortArrayToFloatArray(short[] array){
        float[] fArray = new float[array.length];
        for(int i = 0; i < array.length; i++){
            fArray[i] = (float) array[i];
        }
        return fArray;
    }

    public void stop(){
        isRecording = false;
        if(audioRecord != null) {
            audioRecord.stop();
        }
    }

    public void release(){
        isRecording = false;
        if(audioRecord != null) {
            audioRecord.release();
        }
    }

    public boolean isInitialized(){
        if(audioRecord != null){
            return true;
        }
        return false;
    }

}