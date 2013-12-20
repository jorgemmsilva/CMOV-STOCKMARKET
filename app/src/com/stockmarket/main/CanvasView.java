package com.stockmarket.main;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.stockmarket.basics.StockManager;


public class CanvasView  extends View{

	
	private Paint p;
	private int startX;
	private int startY;
	private int radius;
	Boolean graphtype = true;
	private ArrayList<Integer> colors;
	private ArrayList<Float> values1;
	private ArrayList<Float> values2;
	Bitmap bitmap;
	Context mContext;
	View subs;
	
	
	public CanvasView(Context context,AttributeSet ats) {
		super(context,ats);

	    mContext = context;

	    p = new Paint();
	    p.setAntiAlias(true);

	    colors = new ArrayList<Integer>();
	    values1 = new ArrayList<Float>();

	    startX = 50;
	    startY = 60;

	    
	    
	    values1 = ((StockManager) context.getApplicationContext()).getChartValues(1);
	    values2 = ((StockManager) context.getApplicationContext()).getChartValues(2);
	    colors = ((StockManager) context.getApplicationContext()).getChartColors();
	    
	}
	
	
	public CanvasView(Context context, View sa) {
		super(context);
		subs = sa;
	}

	public ArrayList<Integer> getcolors()
	{
		return colors;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(),
	            Bitmap.Config.ARGB_8888);

	    radius = canvas.getWidth() - 100;
	    
	    Canvas c = new Canvas(bitmap);

	    ArrayList<Float> values;
	    
	    if(graphtype)
	    {
	    	values = values1;
	    }
	    else
	    {
	    	values = values2;
	    }

	    float offset = 0;
	    float sum = 0;
	    for (int a = 0; a < values.size(); a++) {
	        sum += values.get(a);
	    }

	    float angle = (float) (360 / sum);


	    RectF rectF = new RectF();
	    rectF.set(startX, startY, startX + radius,
	    		startY + radius);

	    for (int i = 0; i < values.size(); i++) {

	        p.setColor(colors.get(i));

	        if (i == 0) {
	            canvas.drawArc(rectF, 0, values.get(i) * angle, true, p);
	            c.drawArc(rectF, 0, values.get(i) * angle, true, p);
	        } else {
	            canvas.drawArc(rectF, offset, values.get(i) * angle, true, p);
	            c.drawArc(rectF, offset, values.get(i) * angle, true, p);
	        }

	        offset += (values.get(i) * angle);
	    }

	    canvas.save();

	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		graphtype= !graphtype;
		this.invalidate();
		((StockManager) mContext.getApplicationContext()).setGraphtype(graphtype);
		//subs.invalidate();
		
		return false;

	}
	

}
