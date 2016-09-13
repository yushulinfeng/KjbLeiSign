package org.kjb.lei.sign.utils.tools;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 时钟工具类
 */
public class TimerTool {
    private static TimerTool timerTool = null;
    private final int TIMER_CODE = 0x100;
    private TimerListener listener;
    private Handler handler;
    private Timer timer;
    private TimerTask task;
    private boolean isRun = false;

    private TimerTool() {
    }

    public TimerTool getInstance() {
        if (timerTool == null) timerTool = new TimerTool();
        return timerTool;
    }

    private void initTimer() {
        isRun = false;
        if (handler == null)
            handler = new Handler() {
                public void handleMessage(Message message) {
                    if (message.what == TIMER_CODE) {
                        onTimerTask();
                    }
                }
            };
        if (task == null)
            task = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(TIMER_CODE);
                }
            };
        if (timer == null)
            timer = new Timer(true);
    }

    private void onTimerTask() {
        if (listener != null)
            listener.onTimerAction();
    }

    //////核心方法//////按顺序调用即可//////

    public void setListener(TimerListener new_listener) {
        listener = new_listener;
    }

    public void startTimer(long delay) {
        if (timer == null) initTimer();
        if (isRun) return;//防止多次调用
        isRun = true;
        timer.schedule(task, 100, delay);
    }

    public void destroyTimer() {
        if (task != null)
            task.cancel();
        task = null;
        if (timer != null)
            timer.cancel();
        timer = null;
        isRun = false;
    }

    interface TimerListener {
        void onTimerAction();
    }

}
