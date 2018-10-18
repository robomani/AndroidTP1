package com.example.a1810078.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;
import java.util.FormatFlagsConversionMismatchException;

public class MineSweeper extends Activity {

    public static final int ROW_COUNT = 10;
    public static final int COL_COUNT = 10;
    public static final int MINE = 9;
    public static final int EXPOSED = 1;
    public static final int FLAG = 2;

    public int data[] = new int[100];
    public int mine[] = new int[100];

    public  int revealedCases = 0;
    public  int mineLeft = 10;

    private boolean gameEnded;

    public void GameOver()
    {
        gameEnded = true;
        TextView text = findViewById(R.id.lbl_Mines);
        text.setText("Game Over");
        GridLayout grid = findViewById(R.id.grid);
        for (int i = 0 ; i < ROW_COUNT;i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                final int x = j;
                final int y = i;
                int index = x + y * COL_COUNT;
                Button button = (Button) grid.getChildAt(index);
                if (data[j + i * COL_COUNT] == FLAG && mine[j + i * COL_COUNT] != MINE)
                {
                    mine[j + i * COL_COUNT] = MINE + 2;
                }
                else if (data[j + i * COL_COUNT] == FLAG && mine[j + i * COL_COUNT] == MINE)
                {
                    mine[j + i * COL_COUNT] = MINE + 3;
                }
                data[j + i * COL_COUNT] = EXPOSED;
            }
        }
        Refresh();
    }

    public void Refresh()
    {

        GridLayout grid = findViewById(R.id.grid);

        for (int i = 0 ; i < ROW_COUNT;i++)
        {
            for(int j = 0; j < COL_COUNT; j++)
            {
                final int x = j;
                final int y = i;
                int index = x + y * COL_COUNT;
                Button button = (Button) grid.getChildAt(index);
                if (data[index] == EXPOSED)
                {
                    switch (mine[index])
                    {
                        case 0:
                            button.setBackgroundResource(R.drawable.empty);
                            break;
                        case 1:
                            button.setBackgroundResource(R.drawable.m1);
                            break;
                        case 2:
                            button.setBackgroundResource(R.drawable.m2);
                            break;
                        case 3:
                            button.setBackgroundResource(R.drawable.m3);
                            break;
                        case 4:
                            button.setBackgroundResource(R.drawable.m4);
                            break;
                        case 5:
                            button.setBackgroundResource(R.drawable.m5);
                            break;
                        case 6:
                            button.setBackgroundResource(R.drawable.m6);
                            break;
                        case 7:
                            button.setBackgroundResource(R.drawable.m7);
                            break;
                        case 8:
                            button.setBackgroundResource(R.drawable.m8);
                            break;
                        case MINE:
                            button.setBackgroundResource(R.drawable.mine);
                            break;
                        case MINE + 1:
                            button.setBackgroundResource(R.drawable.boom);
                            break;
                        case MINE + 2:
                            button.setBackgroundResource(R.drawable.wrong);
                            break;
                        case MINE + 3:
                            button.setBackgroundResource(R.drawable.flag);
                            break;
                    }
                }
                else if(data[index] == FLAG)
                {
                    button.setBackgroundResource(R.drawable.flag);
                }
            }
        }
        if (!gameEnded && revealedCases == 90)
        {
            GameOver();
        }
    }

    public void SpawnMines()
    {
        for (int i = 0; i < 10; i++)
        {
            int x = ThreadLocalRandom.current().nextInt(0, 10);
            int y = ThreadLocalRandom.current().nextInt(0, 10);
            if (mine[x + y *10] == MINE)
            {
                i--;
            }
            else
            {
                mine[x + y * 10] = MINE;

                if (x > 0 && y > 0 && mine[(x-1)+(y-1)*10] != MINE)
                {
                    mine[(x-1)+(y-1)*10]++;
                }
                if (y > 0 && mine[(x)+(y-1)*10] != MINE)
                {
                    mine[(x)+(y-1)*10]++;
                }
                if (x < 9 && y > 0 && mine[(x+1)+(y-1)*10] != MINE)
                {
                    mine[(x+1)+(y-1)*10]++;
                }
                if (x > 0 && mine[(x-1)+(y)*10] != MINE)
                {
                    mine[(x-1)+(y)*10]++;
                }
                if (x > 0 && y < 9 && mine[(x-1)+(y+1)*10] != MINE)
                {
                    mine[(x-1)+(y+1)*10]++;
                }
                if (x <9 &&  mine[(x+1)+(y)*10] != MINE)
                {
                    mine[(x+1)+(y)*10]++;
                }
                if (x > 0 && y < 9 && mine[(x)+(y+1)*10] != MINE)
                {
                    mine[(x)+(y+1)*10]++;
                }
                if (x < 9 && y < 9 && mine[(x+1)+(y+1)*10] != MINE)
                {
                    mine[(x+1)+(y+1)*10]++;
                }

            }
        }
    }

    protected void Reveal(int x,int y)
    {
        int index = x + y * 10;
        if (data[index] == EXPOSED || data[index] == FLAG)
        {
            return;
        }
        data[index] = EXPOSED;

        if (mine[index] == MINE)
        {
            mine[index]++;
            GameOver();
        }
        else
        {
            revealedCases++;
            boolean revealMore = true;
            if (x > 0 && y > 0)
            {
                if (mine[(x-1)+(y-1)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (x > 0)
            {
                if (mine[(x-1)+(y)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (x > 0 && y < 9)
            {
                if (mine[(x-1)+(y+1)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (y > 0)
            {
                if (mine[(x)+(y-1)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (y > 0 && x < 9)
            {
                if (mine[(x+1)+(y-1)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (y < 9)
            {
                if (mine[(x)+(y+1)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (x < 9)
            {
                if (mine[(x+1)+(y)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (x < 9 && y < 9)
            {
                if ( mine[(x+1)+(y+1)*10] == MINE)
                {
                    revealMore = false;
                }
            }
            if (revealMore)
            {
                if (x > 0 && y > 0)
                {
                    Reveal(x-1,y-1);
                }
                if (x > 0)
                {
                    Reveal(x-1,y);
                }
                if (x > 0 && y < 9)
                {
                    Reveal(x-1,y+1);
                }
                if (y > 0)
                {
                    Reveal(x,y-1);
                }
                if (y > 0 && x < 9)
                {
                    Reveal(x+1,y-1);
                }
                if (y < 9)
                {
                    Reveal(x,y+1);
                }
                if (x < 9)
                {
                    Reveal(x+1,y);
                }
                if (x < 9 && y < 9)
                {
                    Reveal(x+1,y+1);
                }
            }
        }

        Refresh();

    }

    public void NewGame()
    {
        gameEnded = false;
        GridLayout grid = findViewById(R.id.grid);
        mineLeft = 10;
        for (int i = 0 ; i < ROW_COUNT;i++)
        {
            for(int j = 0; j < COL_COUNT; j++)
            {
                final int x = j;
                final int y = i;
                int index = x+y * COL_COUNT;
                Button button = (Button)grid.getChildAt(index);
                button.setBackgroundResource(R.drawable.btn_idle);
                mine[index] = 0;
                data[index] = 0;
            }
        }
        SpawnMines();
    }

    protected void OnCellClicked(int x, int y)
    {
        Reveal(x,y);
        Refresh();
    }

    protected void ToogleFlag(int x, int y)
    {
        if (data[x+y * COL_COUNT] == FLAG)
        {
            data[x+y * COL_COUNT] = 0;
            mineLeft++;
        }
        if (data[x+y * COL_COUNT] == 0)
        {
            data[x+y * COL_COUNT] = FLAG;
            mineLeft--;
        }
        TextView text = findViewById(R.id.lbl_Mines);
        text.setText("Mines restantes: " + Integer.toString(mineLeft));
        Refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweeper);

        if (savedInstanceState == null)
        {
            NewGame();
        }
        else
        {
            //Return the data of the 2 grid and update
            Refresh();
        }
        Button buttonStart = findViewById(R.id.btn_NewGame);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewGame();
            }
        });

        GridLayout grid = findViewById(R.id.grid);
        for (int i = 0 ; i < ROW_COUNT;i++)
        {
            for(int j = 0; j < COL_COUNT; j++)
            {
                final int x = j;
                final int y = i;
                int index = x+y * COL_COUNT;
                Button button = (Button)grid.getChildAt(index);
                button.setBackgroundResource(R.drawable.btn_idle);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnCellClicked(x, y);
                    }
                });
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ToogleFlag(x, y);
                        return true;
                    }
                });
            }
        }

    }
}
