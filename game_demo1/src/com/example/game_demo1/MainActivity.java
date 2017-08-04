package com.example.game_demo1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.game_demo1.view.MyView;

public class MainActivity extends Activity {
	private MyView myView;
	private TextView score;
	private int playerScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// myView = new MyView(this);
		// setContentView(myView);
		// myView = new MyView(this);
		LinearLayout mainLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.activity_main, null);
		setContentView(mainLayout);
		myView = new MyView(this);
		mainLayout.addView(myView);
		score = (TextView) findViewById(R.id.score);
		// score.setText("Your score:" + playerScore);
		myView.setOnItemClickListener(new GameItemClick() {
			@Override
			public void onItemClick(String msg) {
				score.setText(msg);
				//myView.resetGame();
			}
		});
		myView.startGame();
	}

	// 0,1,2,3 shang xia zuo you

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(myView.mStatus == 0){
			myView.startGame();
		}else if(myView.mStatus == 1){
			myView.setFlag(keyCode);
		
		}else if(myView.mStatus == 2){
			
			myView.resetGame();
			Log.i("TAG","_________23r23r23r_"+keyCode);
			//showDialog();
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDialog() {
		// TODO Auto-generated method stub
		AlertDialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Game Over").setIcon(
				android.R.drawable.stat_notify_error);
		builder.setMessage("You lose!");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				Log.i("TAG","__________"+myView.mStatus);
				
			}
		});
		dialog = builder.create();
		dialog.show();
	}

}
