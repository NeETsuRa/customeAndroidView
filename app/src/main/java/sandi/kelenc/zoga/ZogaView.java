package sandi.kelenc.zoga;

import java.util.Formatter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class ZogaView extends View {
	private int xMin = 0, xMax, yMin = 0, yMax;
	private float polmer = 50, xZoge = polmer + 25;
	private float yZoge = polmer + 40;
	private float hitrostX = 5, hitrostY = 3;
	private RectF zogaMeje;
	private Paint paint;
	private float oldX=0, oldY=0;
	
	private StringBuilder status = new StringBuilder();
	private Formatter formatter = new Formatter(status);
	
	ZogaView(Context context){
		super(context);
		zogaMeje = new RectF();
		paint = new Paint();
		paint.setTypeface(Typeface.SERIF);
		paint.setTextSize(16);
		
		this.setFocusableInTouchMode(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		float x = event.getX(), y = event.getY();
		float dx, dy;
		float faktor = 5.0f / ((xMax > yMax)? yMax:xMax);
		
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
			dx = x - oldX;
			dy = y - oldY;
			hitrostX += dx*faktor;
			hitrostY += dy*faktor;
		}
		
		oldX = x;
		oldY = y;
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		//izris zoge
		zogaMeje.set(xZoge-polmer, yZoge-polmer, xZoge+polmer, yZoge+polmer);
		paint.setColor(Color.BLUE);
		canvas.drawOval(zogaMeje, paint);
		
		paint.setColor(Color.YELLOW);
		canvas.drawText(status.toString(), 10, 30, paint);
		
		//premakni zogo
		update();
		
		try{
			Thread.sleep(1);
		}catch (InterruptedException e) {}
		
		invalidate();
	}
	
	private void update(){
		xZoge += hitrostX;
		yZoge += hitrostY;
		
		if(xZoge + polmer > xMax){
			hitrostX = -hitrostX;
			xZoge = xMax - polmer;
		}else if(xZoge - polmer < xMin){
			hitrostX = -hitrostX;
			xZoge = xMin + polmer;
		}
		
		if(yZoge + polmer > yMax){
			hitrostY = -hitrostY;
			yZoge = yMax - polmer;
		}else if(yZoge - polmer < yMin){
			hitrostY = -hitrostY;
			yZoge = yMin + polmer;
		}
		
		//spremeni tekst
		status.delete(0, status.length());
		formatter.format("Zoga na (%3.0f, %3.0f), hitrost (%2.0f, %2.0f)",
				xZoge, yZoge, hitrostX, hitrostY);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldW, int oldH){
		xMax = w-1;
		yMax = h-1;
	}
	
}
