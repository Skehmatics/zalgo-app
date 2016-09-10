package com.skehmatics.zalgo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seanponeil.zalgotextgenerator.ZalgoString;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ClipboardManager clipMan;
    private EditText testTextField;
    private TextView previewText;
    private SeekBar intensitySlider;

    private CheckBox upCheck;
    private CheckBox midCheck;
    private CheckBox downCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init objects
        testTextField = (EditText) findViewById(R.id.input_edittext);

        previewText = (TextView) findViewById(R.id.preview_text);

        intensitySlider = (SeekBar) findViewById(R.id.seekBar);

        upCheck = (CheckBox) findViewById(R.id.up_check);
        midCheck = (CheckBox) findViewById(R.id.mid_check);
        downCheck = (CheckBox) findViewById(R.id.down_check);

        clipMan = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        //Set listeners
        testTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updatePreview();
            }
        });

        intensitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        upCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updatePreview();
            }
        });
        midCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updatePreview();
            }
        });
        downCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updatePreview();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.license:
                startActivity(new Intent(this, Licenses.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (clipMan.hasPrimaryClip() && clipMan.getPrimaryClipDescription().hasMimeType("text/plain")) {
            String clipStr = clipMan.getPrimaryClip().getItemAt(0).getText().toString();
            testTextField.setText(clipStr);
        }
    }

    public void updatePreview() {
        previewText.setText(getString(R.string.preview_text, makeZalgo()));
    }

    public void test(View view) {
        //Generate Zalgo text
        String zalgoedText = makeZalgo();

        //Copy to clipboard
        ClipData data = ClipData.newPlainText("Zalgo Text", zalgoedText);
        clipMan.setPrimaryClip(data);

        //Notify
        Toast.makeText(MainActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

    }

    public String makeZalgo(){
        String text = testTextField.getText().toString();

        int intUp = 0;
        int intMid = 0;
        int intDown = 0;

        int intensity = intensitySlider.getProgress()+2;

        if (upCheck.isChecked()) {
            intUp = intensity;
        }
        if (midCheck.isChecked()){
            intMid = intensity;
        }
        if (downCheck.isChecked()){
            intDown = intensity;
        }

        return ZalgoString.generate(text, intUp, intMid, intDown);
    }
}
