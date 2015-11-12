package com.jeffe.gtp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 *
 */
public class AddGameListingActivity extends AppCompatActivity {
    private Button mBtnAddGame;
    private EditText mETxtTitle;
    private EditText mETxtConsole;
    private CheckBox mCBFinished;

    public static final int REQUESTCODE_ADDGAME = 1;
    public static final String EXTRA_GAMETITLE = "AddGameListingActivity.GameTitle";
    public static final String EXTRA_GAMECONSOLE = "AddGameListingActivity.GameConsole";
    public static final String EXTRA_GAMEFINISHED = "AddGameListingActivity.GameFinished";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgame);

        mETxtTitle = (EditText) findViewById(R.id.etxtAddGameTitle);
        mETxtConsole = (EditText) findViewById(R.id.etxtAddGameConsole);
        mCBFinished = (CheckBox) findViewById(R.id.cbAddGameFinished);

        mBtnAddGame = (Button) findViewById(R.id.btnAddGame);
        mBtnAddGame.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String title, console;
                boolean finished;
                title = mETxtTitle.getText().toString();
                console = mETxtConsole.getText().toString();
                finished = mCBFinished.isChecked();

                title = title.trim();
                console = console.trim();
                if ("".equals(title)) {
                    Toast.makeText(AddGameListingActivity.this,
                            "You must enter a game title", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                if ("".equals(console)) {
                    Toast.makeText(AddGameListingActivity.this,
                            "You must enter a game console", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_GAMETITLE, title);
                data.putExtra(EXTRA_GAMECONSOLE, console);
                data.putExtra(EXTRA_GAMEFINISHED, finished);

                setResult(RESULT_OK, data);
                finish();
            }
        });

        mETxtTitle.requestFocus();
    }
}
