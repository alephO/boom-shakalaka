package me.aleph0.test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by aleph0 on 12/2/15.
 */
public class LoginActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Button btnSave = (Button) findViewById(R.id.bOK);
		Button btnCancle = (Button) findViewById(R.id.bCancel);

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent data = new Intent();

				EditText txt_uname = (EditText) findViewById(R.id.eUName);
				EditText txt_pwd = (EditText) findViewById(R.id.ePWD);

				//don't use uri since we have two values to pass
				Bundle extra = new Bundle();
				extra.putString("extra_UNAME",txt_uname.getText().toString());
				extra.putString("extra_PWD",txt_pwd.getText().toString());
				data.putExtras(extra);
				setResult(RESULT_OK,data);
				finish();
			}
		});

		btnCancle.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				setResult(RESULT_CANCELED,new Intent());
				finish();
			}
		});
	}
}

