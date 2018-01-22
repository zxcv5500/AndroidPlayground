package com.tistory.zxcv5500.androidplayground.doit;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.tistory.zxcv5500.androidplayground.R;

import java.util.Calendar;

/**
 * Created by zxcv5500 on 2018-01-21.
 */

public class DateTimePicker extends LinearLayout {

	/**
	 * 날짜나 시간이 바뀔 때 호출되는 리스너 새로 정의
	 */
	public interface OnDateTimeChangedListener {
		void onDateTimeChanged(DateTimePicker view, int year, int monthOfyear, int dayOfYear, int hourOfDay, int minute);
	}


	/**
	 * 리스너 객체
	 */
	private OnDateTimeChangedListener onDateTimeChangedListener;

	private final DatePicker datePicker;

	private final CheckBox enableTimeCheckBox;

	private final TimePicker timePicker;


	/**
	 * 생성자
	 *
	 * @param context
	 */
	public DateTimePicker(Context context) {
		this(context, null);
	}

	/**
	 * 생성자
	 *
	 * @param context
	 * @param attrs
	 */
	public DateTimePicker(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		// XML 레이아웃을 인플레이션함
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.datetimepicker, this, true);

		// 시간 정보 참조
		Calendar calendar = Calendar.getInstance();
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DataTimePicker);
		final int _currentYear = a.getInt(R.styleable.DataTimePicker_year, calendar.get(Calendar.YEAR));
		final int _currentMonth = a.getInt(R.styleable.DataTimePicker_month, calendar.get(Calendar.MONTH));
		final int _currentDay = a.getInt(R.styleable.DataTimePicker_day, calendar.get(Calendar.DAY_OF_MONTH));
		final int _currentHour = a.getInt(R.styleable.DataTimePicker_hour, calendar.get(Calendar.HOUR_OF_DAY));
		final int _currentMinute = a.getInt(R.styleable.DataTimePicker_minute, calendar.get(Calendar.MINUTE));

		datePicker = (DatePicker) findViewById(R.id.datePicker);
		datePicker.init(_currentYear, _currentMonth, _currentDay, new DatePicker.OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
				// 새로 정의한 리스너로 이벤트 전달
				if (onDateTimeChangedListener != null) {
					onDateTimeChangedListener.onDateTimeChanged(
							DateTimePicker.this, year, monthOfYear, dayOfMonth,
							timePicker.getCurrentHour(), timePicker.getCurrentMinute());
				}
			}
		});

		// 체크박스 이벤트 처리
		enableTimeCheckBox = findViewById(R.id.enableTimeCheckBox);
		enableTimeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				timePicker.setEnabled(isChecked);
				timePicker.setVisibility(enableTimeCheckBox.isChecked()? View.VISIBLE:View.INVISIBLE);
			}
		});


		// 시간 선택 위젯 이벤트 처리
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
				if (onDateTimeChangedListener != null) {
					onDateTimeChangedListener.onDateTimeChanged(
							DateTimePicker.this, datePicker.getYear(),
							datePicker.getMonth(), datePicker.getDayOfMonth(), hourOfDay, minute
					);
				}
			}
		});

		timePicker.setCurrentHour(_currentHour);
		timePicker.setCurrentMinute(_currentMinute);
		timePicker.setEnabled(enableTimeCheckBox.isChecked());
		timePicker.setVisibility((enableTimeCheckBox.isChecked() ? View.VISIBLE : View.INVISIBLE));
	}

	public void setOnDateTimeChangedListener(OnDateTimeChangedListener onDateTimeChangedListener) {
		this.onDateTimeChangedListener = onDateTimeChangedListener;
	}

	public void updateDateTime(int year, int monthOfYear, int dayOfMonth, int currentHour, int currentMinute) {
	    datePicker.updateDate(year, monthOfYear, dayOfMonth);
	    timePicker.setCurrentHour(currentHour);
	    timePicker.setCurrentMinute(currentMinute);
	}

	public void updateDate(int year, int monthOfYear, int dayOfMonth) {
	    datePicker.updateDate(year, monthOfYear, dayOfMonth);
	}

	public void setIs24HourView(final boolean is24HourView){
		timePicker.setIs24HourView(is24HourView);
	}

	public int getYear() {
		return datePicker.getYear();
	}

	public int getMonth() {
		return datePicker.getMonth();
	}

	public int getDayOfMonth() {
		return datePicker.getDayOfMonth();
	}

	public int getCurrentHour() {
		return timePicker.getCurrentHour();
	}

	public int getCurrentMinute() {
		return timePicker.getCurrentMinute();
	}

	public boolean enableTime() {
		return enableTimeCheckBox.isChecked();
	}

}
