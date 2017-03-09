package iflab.test;

import java.util.Calendar;

import iflab.test.R.drawable;
import android.R.attr;
import android.R.style;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class firstActivity extends Activity {
private SQLiteDatabase mydb=null;
private final static String DATABASE_NAME="FirstDataBase.db";
private final static String TABLE_NAME="firstTable";
private final static String ID="_id";
private final static String NAME="name";
private final static String AGE="age";
private final static String HOME="home";
private final static String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY,"+ NAME+" TEXT,"+AGE+" TEXT,"+HOME+" TEXT)";
private EditText editText=null;
private EditText edit1=null;
private EditText edit2=null;
private EditText edit3=null;

/** Called when the activity is first created. */

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Button btn1=(Button)findViewById(R.id.button1);
    Button btn2=(Button)findViewById(R.id.button2);
    Button btn3=(Button)findViewById(R.id.button3);
    Button btn4=(Button)findViewById(R.id.button4);
    editText=(EditText)findViewById(R.id.editText1);
    edit1=(EditText)findViewById(R.id.edit1);
    edit2=(EditText)findViewById(R.id.edit2);
    edit3=(EditText)findViewById(R.id.edit3);

    edit1.setText("");
    edit2.setText("");
    edit3.setText("");

    mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    try
    {
    	mydb.execSQL(CREATE_TABLE);
    }
    catch(Exception e)
    {
    }
    
    ContentValues cv=new ContentValues();
    cv.put(NAME, "张三");
    cv.put(AGE, "18");
    cv.put(HOME, "北京");
    mydb.insert(TABLE_NAME, null, cv);
    
    cv.put(NAME, "李四");
    cv.put(AGE, "19");
    cv.put(HOME, "河北");
    mydb.insert(TABLE_NAME, null, cv);
    
    cv.put(NAME, "王五");
    cv.put(AGE, "17");
    cv.put(HOME, "江苏");
    mydb.insert(TABLE_NAME, null, cv);
    
    showData();
    
    mydb.close();
    
    btn1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
			ContentValues cv=new ContentValues();
			cv.put(NAME, edit1.getText().toString());
			cv.put(AGE, edit2.getText().toString());
			cv.put(HOME, edit3.getText().toString());
			mydb.insert(TABLE_NAME, null, cv);
			showData();
			mydb.close();
		}
	});
    btn2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
			String whereClause="name=?";
			String[] whereArgs={edit1.getText().toString()};
			mydb.delete(TABLE_NAME, whereClause, whereArgs);
			showData();
			mydb.close();			
		}
	});
    btn3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);	
			String selection="name=?";
			String[] selectionArgs={edit1.getText().toString()};
			Cursor cur=mydb.query(TABLE_NAME, new String[] {ID,NAME,AGE,HOME}, selection, selectionArgs, null, null, null);
		    if(cur!=null)
		    {
		    	if(cur.moveToFirst())
		    	{
		    			String name=cur.getString(1);
		    			String age=cur.getString(2);
		    			String home=cur.getString(3);
		    			edit1.setText(name);
		    			edit2.setText(age);
		    			edit3.setText(home);
		    	}	
		    }
		    else
		    {
		    }
			showData();
			mydb.close();
		}
	});
    btn4.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mydb=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
			ContentValues cv=new ContentValues();
			cv.put(NAME, edit1.getText().toString());
			cv.put(AGE, edit2.getText().toString());
			cv.put(HOME, edit3.getText().toString());		
			String whereClause="name=?";
			String[] whereArgs={edit1.getText().toString()};
			mydb.update(TABLE_NAME, cv, whereClause, whereArgs);
			showData();
			mydb.close();			
		}
	});


    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

public void showData()
{
    editText.setText("数据库内容：\n");
    editText.append("姓名\t\t年龄\t\t籍贯\n");
   
    Cursor cur=mydb.query(TABLE_NAME, new String[] {ID,NAME,AGE,HOME}, null, null, null, null, null);
    int count=cur.getCount();
    if(cur!=null && count>=0)
    {
    	if(cur.moveToFirst())
    	{
    		do
    		{
    			String name=cur.getString(1);
    			String age=cur.getString(2);
    			String home=cur.getString(3);
    			editText.append(""+name+"\t\t"+age+"\t\t"+home+"\n");
    		}while(cur.moveToNext());
    	}	
    }
}


}