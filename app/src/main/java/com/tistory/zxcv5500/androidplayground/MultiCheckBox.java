package com.tistory.zxcv5500.androidplayground;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * Created by zxcv5500 on 2018-01-20.
 */

public class MultiCheckBox extends LinearLayout {

	public interface OnMultiChangeListener {
		public void onMultiChanged(boolean isFirstChecked, boolean isSecondChecked);
	}

	OnMultiChangeListener listener;

	public void setOnMultiChangeListener(OnMultiChangeListener lsnr) {
		listener = lsnr;

	}

	CheckBox checkBox;
	CheckBox checkBox2;

	public MultiCheckBox(Context context) {
		super(context);

		init(context);
	}

	public MultiCheckBox(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.multi_checkbox, this, true);

		checkBox = (CheckBox) findViewById(R.id.checkBox);
		checkBox2 = findViewById(R.id.checkBox2);

		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if (listener != null) {
					listener.onMultiChanged(isChecked, checkBox2.isChecked());
				}
			}
		});

		checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if (listener != null) {
					listener.onMultiChanged(checkBox.isChecked(), isChecked);
				}
			}
		});




	}

}
