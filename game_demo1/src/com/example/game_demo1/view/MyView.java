package com.example.game_demo1.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.game_demo1.GameItemClick;
import com.example.game_demo1.R;

public class MyView extends View {
	private MyView myView;
	private Context mContext;
	private boolean isMoved;
	private int score;
	private MyHandler mHandler;

	private float left = getResources().getDimension(R.dimen.leftBlank);
	private float top = getResources().getDimension(R.dimen.topBlank);
	private Paint paint;
	private GameItemClick listener;
	private float textHeight;
	private int currentRow, currentCol;
	// private int x,y;
	private int initRow = 0, initCol = 0;
	private float cellX = -1, cellY = -1;
	public boolean[][] location = new boolean[10][10];

	private float fontSize = getResources().getDimension(
			R.dimen.default_font_size);

	private float gameWidth;
	private float gameHeight;
	private float cellWidth;
	private float cellHeight;
	private LinkedList<Point> llist = new LinkedList<Point>();
	private ArrayList<Point> list = new ArrayList<Point>();

	public MyView(Context context) {
		super(context);
		mContext = context;
		mHandler = new MyHandler();
		init();

		/*
		 * Iterator it =list.iterator(); while(it.hasNext()){ Point p =(Point)
		 * it.next(); }
		 * 
		 * for(Point p:list){
		 * 
		 * }
		 * 
		 * for(int i=0;i<list.size();i++){ list.get(i); }
		 */
	}

	private void init() {
		// TODO Auto-generated method stub
		paint = new TextPaint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(fontSize);
		textHeight = fontSize;
		paint.setStrokeWidth(6);

		for (int x = 0; x < location.length; x++) {
			for (int y = 0; y < location[x].length; y++) {
				location[x][y] = false;
			}
		}
		location[initRow][initCol] = true;
		currentRow = initRow;
		currentCol = initCol;
		checkCollision = true;
		// list.add(new Point(currentRow, currentCol));
		llist.add(new Point(currentRow, currentCol));

	}

	/*
	 * public int getCurrentRow(){ return currentRow; }
	 * 
	 * public int getCurrentCol(){ return currentCol; }
	 */
	private boolean noGoUp = false;
	private boolean noGoRight = false;
	private boolean noGoLeft = false;
	private boolean noGoDown = false;

	public void setRule(int x) {
		switch (x) {
		case 1:
			noGoRight = false;
			noGoLeft = false;
			noGoDown = false;
			noGoUp = true;
			break;
		case 2:
			noGoRight = false;
			noGoLeft = false;
			noGoDown = true;
			noGoUp = false;
			break;
		case 3:
			noGoRight = false;
			noGoLeft = true;
			noGoDown = false;
			noGoUp = false;
			break;
		case 4:
			noGoRight = true;
			noGoLeft = false;
			noGoDown = false;
			noGoUp = false;
			break;
		case 5:
			noGoRight = false;
			noGoLeft = false;
			noGoDown = false;
			noGoUp = false;
			break;

		}
	}

	private boolean bodyCollision = false;
	private boolean checkCollision = false;
	private boolean gameOver = false;
	public int mStatus = 0;

	public boolean getGameOver() {
		return gameOver;
	}

	public void goRight() {
		setRule(3);
		// int currentRow, int currentCol
		// this.currentRow = currentRow;
		for (Point p : llist) {
			if (p.x == currentRow && p.y == currentCol + 1) {
				bodyCollision = true;
			}
		}
		if (currentCol + 1 < location[currentCol].length
				&& bodyCollision == false) {
			// location[currentRow][currentCol] = false;
			// location[currentRow][currentCol + 1] = true;
			// list.add(new Point(currentRow, currentCol + 1));
			llist.add(new Point(currentRow, currentCol + 1));
			if (left + cellWidth * col == left + cellWidth * (currentCol + 1)
					&& top + cellHeight * row == top + cellHeight * currentRow) {
				checkCollision = true;
			} else {
				checkCollision = false;
				// list.remove(new Point(currentRow ,currentCol));
				// list.remove(1);
				llist.removeFirst();

			}
			currentCol = currentCol + 1;
			getGrade();
			invalidate();
		} else {

			mStatus = 2;
			Log.i("TAG", "gameoverrrrrrr");
		}

	}

	public void goDown() {
		/*
		 * if(llist.size()>=2){
		 * Log.i("TAG","__size()-2.x_____"+(llist.get(llist.size()-2).x));
		 * Log.i("TAG","___size()-2.y____"+(llist.get(llist.size()-2).y));
		 * Log.i("TAG","__size()__x___"+(llist.get(llist.size()-1).x));
		 * Log.i("TAG","__size()__y___"+(llist.get(llist.size()-1).y));
		 * if((llist.get(llist.size()-2).x == currentRow)&&(
		 * llist.get(llist.size()-2).y == currentCol + 1)){ goDown();
		 * 
		 * } }
		 */
		setRule(1);
		for (Point p : llist) {
			if (p.x == currentRow + 1 && p.y == currentCol) {
				bodyCollision = true;
			}
		}

		if (currentRow + 1 < location[currentRow].length
				&& bodyCollision == false
		// && location[currentRow + 1][currentCol] == false
		) {
			// location[currentRow][currentCol] = false;
			// location[currentRow + 1][currentCol] = true;

			// list.add(new Point(currentRow + 1, currentCol));
			llist.add(new Point(currentRow + 1, currentCol));
			if (left + cellWidth * col == left + cellWidth * (currentCol)
					&& top + cellHeight * row == top + cellHeight
							* (currentRow + 1)) {
				checkCollision = true;
			} else {
				checkCollision = false;
				// list.remove(new Point(currentRow ,currentCol));
				// list.remove(list.size(1));
				llist.removeFirst();
			}

			currentRow = currentRow + 1;
			getGrade();
			invalidate();
		} else {

			mStatus = 2;
			Log.i("TAG", "gameoverrrrrrr");
		}
	}

	public void goUp() {
		// int currentRow, int currentCol
		// this.currentRow = currentRow;
		setRule(2);
		for (Point p : llist) {
			if (p.x == currentRow - 1 && p.y == currentCol) {
				bodyCollision = true;
			}
		}

		if (currentRow - 1 >= 0 && bodyCollision == false) {
			// location[currentRow][currentCol] = false;
			// location[currentRow - 1][currentCol] = true;

			// list.add(new Point(currentRow - 1, currentCol));
			llist.add(new Point(currentRow - 1, currentCol));
			if (left + cellWidth * col == left + cellWidth * currentCol
					&& top + cellHeight * row == top + cellHeight
							* (currentRow - 1)) {
				checkCollision = true;
			} else {
				checkCollision = false;
				// list.remove(new Point(currentRow ,currentCol));
				// list.remove(1);
				llist.removeFirst();

			}
			currentRow = currentRow - 1;
			getGrade();
			invalidate();
		} else {
			Log.i("TAG", "_________currentRow__" + currentRow);
			Log.i("TAG", "_________currentCol___" + currentCol);
			mStatus = 2;
			Log.i("TAG", "gameoverrrrrrr");
		}

	}

	public void goLeft() {
		// int currentRow, int currentCol
		// this.currentRow = currentRow;
		setRule(4);

		for (Point p : llist) {
			if (p.x == currentRow && p.y == currentCol - 1) {
				bodyCollision = true;
			}
		}

		if (currentCol - 1 >= 0 && bodyCollision == false) {
			// location[currentRow][currentCol] = false;
			// location[currentRow][currentCol - 1] = true;

			// list.add(new Point(currentRow, currentCol - 1));
			llist.add(new Point(currentRow, currentCol - 1));
			if (left + cellWidth * col == left + cellWidth * (currentCol - 1)
					&& top + cellHeight * row == top + cellHeight * currentRow) {
				checkCollision = true;
			} else {
				checkCollision = false;
				// list.remove(new Point(currentRow,currentCol));
				// list.remove(list.size() - 1);
				llist.removeFirst();

			}
			currentCol = currentCol - 1;
			getGrade();
			invalidate();
		} else {
			Log.i("TAG", "_________currentRow__" + currentRow);
			Log.i("TAG", "_________currentCol___" + currentCol);
			mStatus = 2;
			Log.i("TAG", "gameoverrrrrrr");

		}
	}

	public void getGrade() {

		if (listener != null) {
			listener.onItemClick(String.valueOf(llist.size()));
		}
		;
		this.invalidate();

	}

	/*
	 * @Override public boolean dispatchTouchEvent(MotionEvent motion) { switch
	 * (motion.getAction()) { case MotionEvent.ACTION_DOWN:
	 * setCellX(motion.getX()); setCellY(motion.getY()); isMoved = false; if
	 * (inBoundary()) { if (getScore(dst)) { //score++; if (listener != null) {
	 * listener.onItemClick(String.valueOf(score)); } ; this.invalidate(); } }
	 * break; }
	 * 
	 * return true;
	 * 
	 * }
	 */

	private int getOrientation(float dx, float dy) {
		Log.i("TAG1", "________GET dX___" + dx);
		Log.i("TAG1", "________GET dY___" + dy);
		if (Math.abs(dx) > Math.abs(dy)) {
			return dx > 0 ? 'r' : 'l';
		} else {
			return dy > 0 ? 'b' : 't';
		}
	}

	private float downX;
	private float downY;
	private int orientation;
	private float x, y ;
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		
		if(mStatus == 0){
			startGame();
		}else if(mStatus == 1){
			 x = event.getX();
			 y = event.getY();
		
		}else if(mStatus == 2){
			
			resetGame();
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			downX = x;
			downY = y;
			Log.i("TAG1", "________downX___" + downX);
			Log.i("TAG1", "________downY___" + downY);
			
			break;
		}
		case MotionEvent.ACTION_UP: {
			float dx = x - downX;
			float dy = y - downY;
			Log.i("TAG1", "________GET__dx_" + dx);
			Log.i("TAG1", "________GET_dy__" + dy);
			if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
				 orientation = getOrientation(dx, dy);
			/*	switch (orientation) {
				case 'r':
			//	flag = KeyEvent.KEYCODE_R;
					Log.i("TAG1", "_______aaaaaa__" );
					break;
				case 'l':
				//	flag = KeyEvent.KEYCODE_L;
					break;
				case 't':
				//	flag = KeyEvent.KEYCODE_W;
					break;
				case 'b':
				// = KeyEvent.KEYCODE_A;
				 * 
					break;
				}*/
				
			}
			break;
		}
		}
		
		return true;

	}

	private boolean getScore(Rect cellRect) {
		// TODO Auto-generated method stub
		boolean result = false;
		if (cellX != -1 && cellY != -1) {
			if (cellX >= cellRect.left && cellX <= cellRect.right
					&& cellY >= cellRect.top && cellY <= cellRect.bottom) {
				result = true;
			} else {
				result = false;
			}

		}
		return result;
	}

	private boolean inBoundary() {
		// TODO Auto-generated method stub
		if (cellX < mContext.getResources().getDimension(R.dimen.leftBlank)
				|| cellX > (getMeasuredWidth() - mContext.getResources()
						.getDimension(R.dimen.leftBlank))
				|| cellY < top
				|| cellY > (getMeasuredHeight() - mContext.getResources()
						.getDimension(R.dimen.topBlank))) {
			return false;
		} else {

			return true;
		}
	}

	private void setCellY(float cellY) {
		// TODO Auto-generated method stub
		this.cellY = cellY;
	}

	private void setCellX(float cellX) {
		// TODO Auto-generated method stub
		this.cellX = cellX;
	}

	public void setOnItemClickListener(GameItemClick listener) {
		this.listener = listener;
	}

	/*
	 * private boolean redrawForKeyDown = false; private int currentDay1 = -1;
	 * public int currentDay = -1, currentDayIndex = -1; private boolean
	 * isCurrentDay(int dayIndex, int currentDayIndex, Rect cellRect) { boolean
	 * result = false; if (redrawForKeyDown == true) { result = dayIndex == (7 *
	 * ((currentRow > 0) ? currentRow : 0) + currentCol); if (result)
	 * redrawForKeyDown = false;
	 * 
	 * } else if (cellX != -1 && cellY != -1) { if (cellX >= cellRect.left &&
	 * cellX <= cellRect.right && cellY >= cellRect.top && cellY <=
	 * cellRect.bottom) { result = true; } else { result = false; } } else {
	 * result = (dayIndex == currentDayIndex);
	 * 
	 * } if (result) { if (currentRow > 0 && currentRow < 6) { currentDay1 =
	 * currentDay;
	 * 
	 * } currentDayIndex = -1; cellX = -1; cellY = -1;
	 * 
	 * } return result; }
	 */
	private Rect src, src2;
	private Rect dst, dst2;
	private int diff;
	private int col, row;
	private boolean checkRandomFood = false;

	@Override
	protected void onDraw(Canvas canvas) {
		int score = 0;
		Log.i("TAG", "__*********___" + "flag__" + flag);
		paint.setColor(mContext.getResources().getColor(R.color.navy));
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_launcher);
		Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.day);
		// left = mContext.getResources().getDimension(R.dimen.leftBlank);
		// top = mContext.getResources().getDimension(R.dimen.topBlank);
		gameWidth = getMeasuredWidth() - left * 2;
		gameHeight = getMeasuredHeight() - top * 2;
		cellWidth = gameWidth / 10;
		cellHeight = gameHeight / 10;
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(left + cellWidth * i, top, left + cellWidth * i,
					gameHeight + top, paint);
		}
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(left, top + cellHeight * i, left + gameWidth, top
					+ cellHeight * i, paint);
		}
		Random rand = new Random();
		src = new Rect();
		dst = new Rect();
		if (checkCollision == true) {
			diff = rand.nextInt(100);
			row = diff / 10;
			col = diff % 10;
			for (Point p : llist) {
				if (p.x == row && p.y == col) {
					diff = rand.nextInt(100);
					row = diff / 10;
					col = diff % 10;
				}
			}

		}

		dst.left = (int) (left + cellWidth * col);
		dst.top = (int) (top + cellHeight * row);
		dst.bottom = (int) (dst.top + cellHeight + 1);
		dst.right = (int) (dst.left + cellWidth + 1);

		src.left = 0;
		src.top = 0;
		src.right = bitmap.getWidth();
		src.bottom = bitmap.getHeight();
		canvas.drawBitmap(bitmap, src, dst, paint);

		Log.i("TAG", "___________________________" + llist.size());
		Log.i("TAG", "___________________________" + llist);

		/*
		 * for (int x = 0; x < location.length; x++) { for (int y = 0; y <
		 * location[x].length; y++) { if (location[x][y] == true) { dst2 = new
		 * Rect(); dst2.left = (int) (left + cellWidth * y); dst2.top = (int)
		 * (top + cellHeight * x); dst2.bottom = (int) (dst2.top + cellHeight +
		 * 1); dst2.right = (int) (dst2.left + cellWidth + 1); src2 = new
		 * Rect(); src2.left = 0; src2.top = 0; src2.right = bitmap.getWidth();
		 * src2.bottom = bitmap.getHeight(); canvas.drawBitmap(bitmap, src2,
		 * dst2, paint);
		 * 
		 * } } }
		 */

		for (Point p : llist) {
			location[p.x][p.y] = false;
			dst2 = new Rect();
			dst2.left = (int) (left + cellWidth * p.y);
			dst2.top = (int) (top + cellHeight * p.x);
			dst2.bottom = (int) (dst2.top + cellHeight + 1);
			dst2.right = (int) (dst2.left + cellWidth + 1);
			src2 = new Rect();
			src2.left = 0;
			src2.top = 0;
			src2.right = bitmap.getWidth();
			src2.bottom = bitmap.getHeight();
			canvas.drawBitmap(bitmap2, src2, dst2, paint);
		}

		// }
	}

	private long mCurrentTheadID;

	public void startGame2() {
		if (mStatus != 0) {
			return;
		}
		mStatus = 1;
		new Thread(new Runnable() {

			@Override
			public void run() {

				mCurrentTheadID = Thread.currentThread().getId();

				while (true && mStatus == 1) {
					try {
						Thread.sleep(200);
						Log.i("TAG", "_________THREAD!!!!!_" + mStatus);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (mStatus == 1
							&& mCurrentTheadID == Thread.currentThread()
									.getId()) {
						mHandler.sendEmptyMessage(1);
					}
				}
			}

		}).start();
	}

	public void startGame() {
		if (mStatus != 0) {
			return;
		}
		mStatus = 1;
		mHandler.removeMessages(1);//
		mHandler.sendEmptyMessage(1);
	}

	private int flag = 0;

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.i("TAG", "_mStatus___" + mStatus);
			if (mStatus != 2) {
				super.handleMessage(msg);

				switch (orientation) {
				case 't': {
					if (noGoUp == true) {
						goDown();
						break;
					}
					goUp();
					Log.i("TAG", "_go_up");
					break;

				}
				case 'b': {
					if (noGoDown == true) {
						goUp();
						break;
					}
					goDown();
					Log.i("TAG", "_GO DOWN");
					break;
				}
				case 'l': {
					if (noGoLeft == true) {
						goRight();
						break;
					}
					goLeft();
					Log.i("TAG", "_GO LEFT");
					break;
				}
				case 'r': {

					if (noGoRight == true) {
						goLeft();
						break;
					}
					goRight();
					Log.i("TAG", "_GO RIGHT");
					break;
				}

				}

				this.sendEmptyMessageDelayed(1, 200);

			}
		}

	}

	public void setFlag(int keyCode) {
		// TODO Auto-generated method stub
		flag = keyCode;
	}

	public void resetGame() {
		// TODO Auto-generated method stub
		// flag =KeyEvent.KEYCODE_J;
		// /Log.i("TAG","__*********_______resetGame_"+"flag__"+flag);
		mStatus = 0;
		//flag = 0;
		currentRow = 0;
		currentCol = 0;
		llist.removeAll(llist);
		llist.add(new Point(currentRow, currentCol));
		setRule(5);
		
		location = new boolean[10][10];

		gameOver = false;

		bodyCollision = false;
		this.invalidate();

	}
}
