package eminentdevs.maze;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static eminentdevs.maze.R.color.Empty;
import static eminentdevs.maze.R.color.Finish;
import static eminentdevs.maze.R.color.First;


public class MazeActivity extends AppCompatActivity {
    int gridWidth;
    byte width, height;
    Maze m;
    byte i, j;
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Step 3 - Define start and end points");

        setContentView(R.layout.activity_maze);
        width = Byte.parseByte(getIntent().getExtras().getString("width"));
        height = Byte.parseByte(getIntent().getExtras().getString("height"));

        if (width < 4) width = 4;
        if (width > 50) width = 50;

        if (height < 4) height = 4;
        if (height > 70) height = 70;

        m = new Maze(width, height);
        if (Integer.parseInt(getIntent().getExtras().getString("type")) == 0) {
            m.createBlocks();
        } else {
            m.createRandomBlocks(Byte.parseByte(getIntent().getExtras().getString("percentage")));
        }

        //m.Solve();
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);


        final GridLayout MazeMap = (GridLayout) findViewById(R.id.MazeMap);
        MazeMap.setOrientation(0);
        MazeMap.setColumnCount((int) width);
        MazeMap.setRowCount((int) height);
        MazeMap.setBackgroundResource(R.drawable.mazebox);

        final Button solve = (Button) findViewById(R.id.solve);
        final Button Reset = (Button) findViewById(R.id.reset);

        //MazeMap.getInd
        for (byte i = 0; i < height; i++) {
            for (byte j = 0; j < width; j++) {
                final Button block = new Button(this);
                block.setId(i * width + j);
                block.setBackgroundResource(R.drawable.empty);
                block.setMinimumWidth(0);
                block.setMinimumHeight(0);
                MazeMap.post(new Runnable() {
                    public void run() {
                        if (height <= width) {
                            gridWidth = layout.getWidth() - 90;
                            block.setWidth(gridWidth / (int) width);
                            block.setHeight(gridWidth / (int) width);
                        } else {
                            gridWidth = layout.getHeight() - 400;
                            block.setWidth(gridWidth / (int) height);
                            block.setHeight(gridWidth / (int) height);
                        }

                    }
                });
                block.setPadding(0, 0, 0, 0);
                //block.setLayoutParams(parms);

                if (m.Map[i][j].getStatus() == -1)
                    block.setBackgroundResource(R.drawable.block);
                final byte row = i;
                final byte col = j;
                block.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (m.Map[row][col].getStatus() == -1) {
                            Toast.makeText(MazeActivity.this, "This cell is blocked!", Toast.LENGTH_SHORT).show();
                        } else if (m.Map[row][col].getStatus() == 0) {
                            if (m.getFirst() == null) {
                                m.setFirst(m.Map[row][col]);
                                block.setBackgroundColor(getResources().getColor(First));
                            } else {
                                Button b = (Button) findViewById(m.Map[m.getFirst().getRow()][m.getFirst().getCol()].getRow() * width + m.Map[m.getFirst().getRow()][m.getFirst().getCol()].getCol());
                                b.setBackgroundResource(R.drawable.empty);
                                m.setFirst(m.Map[row][col]);
                                block.setBackgroundColor(getResources().getColor(First));
                            }
                            if (m.getFirst() != null && m.getEnd() != null) {
                                solve.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                });
                block.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (m.Map[row][col].getStatus() == -1) {
                            Toast.makeText(MazeActivity.this, "This cell is blocked!", Toast.LENGTH_SHORT).show();
                        } else if (m.Map[row][col].getStatus() == 0) {
                            if (m.getEnd() == null) {
                                m.setEnd(m.Map[row][col]);
                                block.setBackgroundColor(getResources().getColor(Finish));
                            } else {
                                Button b = (Button) findViewById(m.Map[m.getEnd().getRow()][m.getEnd().getCol()].getRow() * width + m.Map[m.getEnd().getRow()][m.getEnd().getCol()].getCol());
                                b.setBackgroundResource(R.drawable.empty);
                                m.setEnd(m.Map[row][col]);
                                block.setBackgroundColor(getResources().getColor(Finish));
                            }
                            if (m.getFirst() != null && m.getEnd() != null) {
                                solve.setVisibility(View.VISIBLE);
                            }
                        }

                        return true;
                    }
                });

                MazeMap.addView(block);
            }
        }

        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("Final step - Solution of maze");
                TextView help = (TextView) findViewById(R.id.help);
                help.setVisibility(View.GONE);
                m.Solve();
                if (m.Solution.length == 0) {
                    Toast.makeText(MazeActivity.this, "The maze doesn't have solution!", Toast.LENGTH_SHORT).show();
                } else {
                    solve.setVisibility(View.GONE);
                    ShowSolution();
                }
            }
        });


        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("Step 3 - Define start and end points");
                TextView help = (TextView) findViewById(R.id.help);
                help.setVisibility(View.VISIBLE);
                for (byte i = 0; i < height; i++) {
                    for (byte j = 0; j < width; j++) {
                        if (m.Map[i][j].getStatus() == 1) {
                            final Button b = (Button) findViewById(i * width + j);
                            b.setBackgroundColor(getResources().getColor(Empty));
                        }
                    }
                }
                m.Reset();
                Reset.setVisibility(View.GONE);

            }
        });
    }

    public void ShowSolution() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (k = 1; k < m.Moves.length; k++) {
                    final Button b = (Button) findViewById(m.Moves[k].getRow() * width + m.Moves[k].getCol());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            b.setBackgroundColor(getResources().getColor(R.color.Current));
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.d("Threading", e.getLocalizedMessage());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            b.setBackgroundColor(getResources().getColor(R.color.Empty));
                        }
                    });
                }
                for (k = 1; k < m.Solution.length - 1; k++) {
                    final Button b = (Button) findViewById(m.Solution[k].getRow() * width + m.Solution[k].getCol());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            b.setBackgroundColor(getResources().getColor(R.color.Marked));
                        }
                    });

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Log.d("Threading", e.getLocalizedMessage());
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button Reset = (Button) findViewById(R.id.reset);
                        Reset.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();

    }
}
