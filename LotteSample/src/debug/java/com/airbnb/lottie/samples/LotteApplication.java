package com.airbnb.lottie.samples;

import android.app.Application;
import android.support.v4.util.Pair;
import android.view.Gravity;

import com.codemonkeylabs.fpslibrary.FrameDataCallback;
import com.codemonkeylabs.fpslibrary.TinyDancer;

public class LotteApplication extends Application implements ILotteApplication {

    private int droppedFrames;
    private long droppedFramesStartingNs;
    private long currentFrameNs;

    @Override
    public void onCreate() {
        TinyDancer.create()
                .startingGravity(Gravity.TOP|Gravity.END)
                .startingXPosition(0)
                .startingYPosition(0)
                .addFrameDataCallback(new FrameDataCallback() {
                    @Override
                    public void doFrame(long previousFrameNs, long currentFrameNs, int droppedFrames) {
                        LotteApplication.this.droppedFrames += droppedFrames;
                        LotteApplication.this.currentFrameNs = currentFrameNs;
                    }
                })
                .show(this);
    }

    @Override
    public void startRecordingDroppedFrames() {
        droppedFrames = 0;
        droppedFramesStartingNs = currentFrameNs;
    }

    @Override
    public Pair<Integer, Long> stopRecordingDroppedFrames() {
        long duration = currentFrameNs - droppedFramesStartingNs;
        Pair<Integer, Long> ret = new Pair<>(droppedFrames, duration);
        droppedFrames = 0;
        return ret;
    }
}