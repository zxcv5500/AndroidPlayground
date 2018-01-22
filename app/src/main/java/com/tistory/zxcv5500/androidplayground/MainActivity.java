package com.tistory.zxcv5500.androidplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tistory.zxcv5500.androidplayground.doit.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
	TextView textView1;
	DateTimePicker dateTimePicker;

	private MyCustomView indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		indicator = (MyCustomView) findViewById(R.id.indicator);
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				int selected = indicator.getSelected();


				if (selected == 2) {
					selected = 0;
				} else {
					selected++;
				}
				Log.d("MainActivity", "selected=" + selected);
//				indicator.setSelected(selected);
			}
		});

		MultiCheckBox multiCheckBox = (MultiCheckBox) findViewById(R.id.multicheck);
		multiCheckBox.setOnMultiChangeListener(new MultiCheckBox.OnMultiChangeListener() {
			@Override
			public void onMultiChanged(boolean isFirstChecked, boolean isSecondChecked) {
				Toast.makeText(getApplicationContext(), "첫번째 체크 : " + isFirstChecked + ", 두번째 체크 : " + isSecondChecked, Toast.LENGTH_LONG).show();
			}
		});

		textView1 = findViewById(R.id.textView1);

		dateTimePicker = findViewById(R.id.dateTimePicker);
		dateTimePicker.setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
			@Override
			public void onDateTimeChanged(DateTimePicker view, int year, int monthOfyear, int dayOfYear, int hourOfDay, int minute) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, monthOfyear, dayOfYear, hourOfDay, minute);

				// 바뀐 시간 텍스트뷰에 표시
				textView1.setText(dateFormat.format(calendar.getTime()));
		}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);


	}
}
