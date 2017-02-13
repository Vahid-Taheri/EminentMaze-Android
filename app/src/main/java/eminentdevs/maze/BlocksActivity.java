package eminentdevs.maze;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


public class BlocksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Step 2 - Create blocks");

        setContentView(R.layout.activity_blocks);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_blocks);

        final Button random = (Button) findViewById(R.id.random);
        final Button regular = (Button) findViewById(R.id.regular);

        final EditText percentage = (EditText) findViewById(R.id.percentage);
        final Button create= (Button) findViewById(R.id.create);

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SecondPage = new Intent(BlocksActivity.this, MazeActivity.class);
                SecondPage.putExtra("width", getIntent().getExtras().getString("width"));
                SecondPage.putExtra("height", getIntent().getExtras().getString("height"));
                SecondPage.putExtra("type", "0");
                startActivity(SecondPage);
            }
        });
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random.setVisibility(View.GONE);
                regular.setVisibility(View.GONE);
                percentage.setVisibility(View.VISIBLE);
                create.setVisibility(View.VISIBLE);

                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent SecondPage = new Intent(BlocksActivity.this, MazeActivity.class);
                        SecondPage.putExtra("width", getIntent().getExtras().getString("width"));
                        SecondPage.putExtra("height", getIntent().getExtras().getString("height"));
                        SecondPage.putExtra("percentage", percentage.getText().toString());
                        SecondPage.putExtra("type", "1");
                        startActivity(SecondPage);
                    }
                });
            }
        });
    }
}
