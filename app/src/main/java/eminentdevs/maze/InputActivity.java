package eminentdevs.maze;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Step 1 - Create empty maze");


        setContentView(R.layout.activity_input);

        final EditText WidthIn = (EditText) findViewById(R.id.width);
        final EditText HeightIn = (EditText) findViewById(R.id.height);

        Button Submit = (Button) findViewById(R.id.random);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WidthIn.getText().toString().matches("")) {
                    Toast.makeText(InputActivity.this, "Please enter the width of maze!", Toast.LENGTH_SHORT).show();
                } else if (HeightIn.getText().toString().matches("")) {
                    Toast.makeText(InputActivity.this, "Please enter the height of maze!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent SecondPage = new Intent(InputActivity.this, BlocksActivity.class);
                    SecondPage.putExtra("width", WidthIn.getText().toString());
                    SecondPage.putExtra("height", HeightIn.getText().toString());
                    startActivity(SecondPage);
                }
            }
        });
    }
}
