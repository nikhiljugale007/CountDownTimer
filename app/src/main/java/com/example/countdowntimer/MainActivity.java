package com.example.countdowntimer;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
	// Initializing my textView
	TextView textView ;
	EditText textViewInputHours, textViewInputMinute , getTextViewInputSeconds;
	Button buttonStart , buttonReset;
	MediaPlayer mediaPlayer ,mediaPlayerStop;
	CountDownTimer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		intilization();

//		count(3);


		buttonStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if( textViewInputHours.getText().toString().isEmpty() || textViewInputMinute.getText().toString().isEmpty() || getTextViewInputSeconds.getText().toString().isEmpty()){
					Toast.makeText(MainActivity.this, "Enter valid countdown time", Toast.LENGTH_SHORT).show();
				}
				else{
					int countdowntime =  Integer.parseInt(textViewInputHours.getText().toString())*60 *60 + Integer.parseInt(textViewInputMinute.getText().toString())*60  + Integer.parseInt(getTextViewInputSeconds.getText().toString());
					if(countdowntime == 0){
						Toast.makeText(MainActivity.this, "Enter valid countdown time", Toast.LENGTH_SHORT).show();
					}
//					Toast.makeText(MainActivity.this, " Time = "+ countdowntime, Toast.LENGTH_SHORT).show();
					count(countdowntime);
					mediaPlayer.setLooping(true);
					buttonStart.setClickable(false);
					buttonStart.setText("Counting");
					buttonStart.setBackgroundColor(Color.GRAY);
					mediaPlayer.start();
				}
			}
		});

		buttonReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				timer.cancel();
				mediaPlayer.pause();
				textView.setText("00:00:00");
				Toast.makeText(MainActivity.this, "Time end", Toast.LENGTH_SHORT).show();
				buttonStart.setClickable(true);
				buttonStart.setText("Start");
				buttonStart.setBackgroundColor(Color.GREEN);
				textViewInputHours.setText("");
				textViewInputMinute.setText("");
				getTextViewInputSeconds.setText("");
			}
		});

	}

	private void intilization() {
		textView = findViewById(R.id.textView);
		textViewInputHours = findViewById(R.id.textViewInputHours);
		textViewInputMinute = findViewById(R.id.textViewInputMinutes);
		getTextViewInputSeconds = findViewById(R.id.textViewInputSeconds);
		buttonStart = findViewById(R.id.buttonStart);
		buttonReset = findViewById(R.id.buttonReset);
		buttonStart.setBackgroundColor(Color.GREEN);
		buttonReset.setBackgroundColor(Color.GREEN);
		mediaPlayer = MediaPlayer.create(this,R.raw.counter_end);
		mediaPlayerStop = MediaPlayer.create(this,R.raw.counter_complete);

	}


	private  void count(int time){
		timer = new CountDownTimer(time*1000, 1000) {
			public void onTick(long millisUntilFinished) {
				// Used for formatting digit to be in 2 digits only
				NumberFormat f = new DecimalFormat("00");
				long hour = (millisUntilFinished / 3600000) % 24;
				long min = (millisUntilFinished / 60000) % 60;
				long sec = (millisUntilFinished / 1000) % 60;
				textView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
			}

			// When the task is over it will print 00:00:00 there
			@SuppressLint("ResourceAsColor")
			public void onFinish() {
				mediaPlayer.pause();
				mediaPlayerStop.start();
				textView.setText("00:00:00");
				//Toast.makeText(MainActivity.this, "Time end", Toast.LENGTH_SHORT).show();
				buttonStart.setClickable(true);
				buttonStart.setBackgroundColor(Color.GREEN);
				buttonStart.setText("Start");
				textViewInputHours.setText("");
				textViewInputMinute.setText("");
				getTextViewInputSeconds.setText("");

			}
		};
		timer.start();
	}
}
