package com.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {
	private String text;
	private Paint textPaint;
	private static final float GESTURE_THRESHOLD_DIP = 16.0f;

	public TextProgressBar(Context context) {
		super(context);
		text = "HP";
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);

	}

	public TextProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		text = "HP";
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
	}

	public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		text = "HP";
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// First draw the regular progress bar, then custom draw our text
		super.onDraw(canvas);
		Rect bounds = new Rect();
		textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		textPaint.setTextSize((float) (GESTURE_THRESHOLD_DIP * scale + 0.5f));
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		int x = getWidth() / 2 - bounds.centerX();
		int y = getHeight() / 2 - bounds.centerY();
		canvas.drawText(text, x, y, textPaint);

	}

	public synchronized void setText(String text) {
		this.text = text;
		drawableStateChanged();
	}

	public void setTextColor(int color) {
		textPaint.setColor(color);
		drawableStateChanged();
	}

}