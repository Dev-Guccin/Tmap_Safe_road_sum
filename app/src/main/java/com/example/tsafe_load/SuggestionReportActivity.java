package com.example.tsafe_load;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class SuggestionReportActivity extends Activity {
    EditText suggestionsEdit;
    Button suggestionBtn;
    Uri imgUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestions_report);

        suggestionsEdit = findViewById(R.id.suggestionsEdit);
        suggestionBtn = findViewById(R.id.suggestionBtn);

        suggestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMmsIntent("010-2047-0975", imgUri);
            }
        });
    }
    public void sendMmsIntent(String number, Uri imgUri){
        try{
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra("address", number);
            sendIntent.putExtra("sms_body", suggestionsEdit.getText().toString());
            sendIntent.setType("text/*");
            sendIntent.putExtra(Intent.EXTRA_STREAM, "test");
            startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.app_name)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
