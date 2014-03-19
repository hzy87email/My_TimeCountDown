package com.jason.timetest;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 倒计时的Demo
 * @author jason
 * @data 2014-3-19
 */
public class MainActivity extends Activity implements OnClickListener {

	private TextView tv_time;
	private Button bt_start;
	static int minute = -1;
	static int second = -1;
	private Timer timer;
	private TimerTask timerTask;
	private boolean isPause = true;

	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			if (minute == 0) {
				if (second == 0) {
					tv_time.setText("Time out !");
					if (timer != null) {
						timer.cancel();
						timer = null;
					}
					if (timerTask != null) {
						timerTask = null;
					}
				}else {
					second--;
					if (second >= 10) {
						tv_time.setText("0"+minute + ":" + second);
					}else {
						tv_time.setText("0"+minute + ":0" + second);
					}
				}
			}else {
				if (second == 0) {
					second =59;
					minute--;
					if (minute >= 10) {
						tv_time.setText(minute + ":" + second);
					}else {
						tv_time.setText("0"+minute + ":" + second);
					}
				}else {
					second--;
					if (second >= 10) {
						if (minute >= 10) {
							tv_time.setText(minute + ":" + second);
						}else {
							tv_time.setText("0"+minute + ":" + second);
						}
					}else {
						if (minute >= 10) {
							tv_time.setText(minute + ":0" + second);
						}else {
							tv_time.setText("0"+minute + ":0" + second);
						}
					}
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_time = (TextView) findViewById(R.id.tv_time);
		bt_start = (Button) findViewById(R.id.bt_start);

		bt_start.setOnClickListener(this);

		if (minute == -1 && second == -1) {
			minute = 3;
			second = 0;
		}

		tv_time.setText(minute + ":" + second);

		timerTask = new TimerTask() {

			@Override
			public void run() {
				Message msg = Message.obtain();
				msg.what = 0;
				if (isPause) {
					mHandler.sendMessage(msg);
				}
			}
		};

		timer = new Timer();
		timer.schedule(timerTask, 0, 1000);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_start:
			if (bt_start.getText().toString().trim().equals("暂停")) {
				bt_start.setText("开始");
				isPause = false;
			} else {
				bt_start.setText("暂停");
				isPause = true;
			}

			break;

		default:
			break;
		}
	}

}
