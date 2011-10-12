package com.github.hanachin.paddap;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class PaddapActivity extends Activity implements Runnable {
	private final String TAG = getClass().getSimpleName();
	private GestureDetector gestureDetector;
	private ScaleGestureDetector scaleGestureDetector;
	private float prevX;
	private float prevY;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gestureDetector = new GestureDetector(getApplicationContext(), simpleOnGestureListener);
        scaleGestureDetector = new ScaleGestureDetector(getApplicationContext(), simpleOnScaleGestureListener);
        LinearLayout linear = (LinearLayout)findViewById(R.id.pad);
        linear.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					prevX = event.getX();
					prevY = event.getY();
				}
				boolean result = gestureDetector.onTouchEvent(event) || scaleGestureDetector.onTouchEvent(event);
				return result;
			}
		});
        
    }
    private SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {
    	@Override
    	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    		Log.v(TAG, "onFling");
    		return super.onFling(e1, e2, velocityX, velocityY);
    	};
    	@Override
    	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    		Log.v(TAG, "onScroll");
    		write(String.format("%d,%d\n", (int)(e2.getX() - prevX), (int)(e2.getY() - prevY)));
    		prevX = e2.getX();
    		prevY = e2.getY();
    		return super.onScroll(e1, e2, distanceX, distanceY);
    	};
    	public void onShowPress(MotionEvent e) {
    		Log.v(TAG, "onShowPress");
    		write("click\n");
    	};
    };
    private SimpleOnScaleGestureListener simpleOnScaleGestureListener = new SimpleOnScaleGestureListener() {
    	@Override
    	public boolean onScale(ScaleGestureDetector detector) {
    		Log.v(TAG, "onScale");
    		return super.onScale(detector);
    	};
    	@Override
    	public boolean onScaleBegin(ScaleGestureDetector detector) {
    		Log.v(TAG, "onScaleBegin");
    		return super.onScaleBegin(detector);
    	};
    	@Override
    	public void onScaleEnd(ScaleGestureDetector detector) {
    		Log.v(TAG, "onScaleEnd");
    	};
    };

    private ServerSocket touchServer;
    private Socket touchClient;
    private int port = 6666;
	volatile Thread runner;
	@Override
	protected void onStart() {
		super.onStart();
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		runner = null;
	}
	
	synchronized protected void write(String s) {
		if (touchClient != null) {
			try {
				OutputStream out = touchClient.getOutputStream();
				out.write(s.getBytes());
				out.flush();
				out = null;
			} catch (IOException e) {
				Log.v(TAG, "Write error");
			}
		}
	}
	
	@Override
	public void run() {
		Thread thread = Thread.currentThread();
		try {
			touchServer = new ServerSocket(port);
			while (thread == runner) {
				if (touchClient == null) {
					try {
						touchClient = touchServer.accept();
					} catch (IOException e) {
					}
				}
				Thread.sleep(100);
			}
		} catch (IOException e) {
		} catch (InterruptedException e) {
		} finally {
			try {
				if (touchClient != null) {
					touchClient.close();
					touchClient = null;
				}
				touchServer.close();
				touchServer = null;
			} catch (IOException e) {
			}
		}
	}
}