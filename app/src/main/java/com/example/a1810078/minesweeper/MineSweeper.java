package com.example.a1810078.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
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

    public void GameOver()
    {

    }

    public void Refresh()
    {
        GridLayout grid = findViewById(R.id.grid);
        int i;
        int j;
        for (i = 0 ; i < ROW_COUNT;i++);
        {
            for(j = 0; j < COL_COUNT; j++);
            {
                final int x = j;
                final int y = i;
                int index = x+y * COL_COUNT;
                Button button = (Button) grid.getChildAt(index);
                if (data[x + y * 10] == EXPOSED)
                {
                    switch (mine[x + y * 10])
                    {
                        case 0:
                            //button.setBackground(btn_empty);
                        case 1:
                            //button.setBackground(btn_1);
                        case 2:
                            //button.setBackground(btn_2);
                        case 3:
                            //button.setBackground(btn_3);
                        case 4:
                            //button.setBackground(btn_4);
                        case 5:
                            //button.setBackground(btn_5);
                        case 6:
                            //button.setBackground(btn_6);
                        case 7:
                            //button.setBackground(btn_7);
                        case 8:
                           // button.setBackground(btn_8);
                    }
                }
                else if(data[x + y * 10] == FLAG)
                {
                    //button.setBackground(btn_flag);
                }

            }
        }
    }

    public void SpawnMines()
    {
        for (int i = 0; i < 10; i++)
        {
            int x = ThreadLocalRandom.current().nextInt(0, 10);
            int y = ThreadLocalRandom.current().nextInt(0, 10);
            if (mine[(x-1)+(y-1)*10] == MINE)
            {
                i--;
            }
            else
            {
                mine[(x-1)+(y-1)*10] = MINE;

                if (mine[(x-1)+(y-1)*10] != MINE)
                {
                    mine[(x-1)+(y-1)*10]++;
                }
                if (mine[(x)+(y-1)*10] != MINE)
                {
                    mine[(x)+(y-1)*10]++;
                }
                if (mine[(x+1)+(y-1)*10] != MINE)
                {
                    mine[(x+1)+(y-1)*10]++;
                }
                if (mine[(x-1)+(y)*10] != MINE)
                {
                    mine[(x-1)+(y)*10]++;
                }
                if (mine[(x-1)+(y+1)*10] != MINE)
                {
                    mine[(x-1)+(y+1)*10]++;
                }
                if (mine[(x+1)+(y)*10] != MINE)
                {
                    mine[(x+1)+(y)*10]++;
                }
                if (mine[(x)+(y+1)*10] != MINE)
                {
                    mine[(x)+(y+1)*10]++;
                }
                if (mine[(x+1)+(y+1)*10] != MINE)
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

        if (data[index] == MINE)
        {
            GameOver();
        }
        else
        {
            if (mine[(x-1)+(y-1)*10] != MINE && mine[(x)+(y-1)*10] != MINE &&
                    mine[(x+1)+(y-1)*10] != MINE && mine[(x-1)+(y)*10] != MINE &&
                            mine[(x-1)+(y+1)*10] != MINE && mine[(x+1)+(y)*10] != MINE &&
                                    mine[(x)+(y+1)*10] != MINE && mine[(x+1)+(y+1)*10] != MINE)
            {
                Reveal(x-1,y-1);
                Reveal(x,y-1);
                Reveal(x+1,y-1);
                Reveal(x-1,y);
                Reveal(x+1,y);
                Reveal(x-1,y+1);
                Reveal(x,y+1);
                Reveal(x+1,y+1);
            }
        }

        Refresh();

    }

    protected void OnCellClicked(int x, int y)
    {
        Reveal(x,y);
        Refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweeper);

        if (savedInstanceState.isEmpty())
        {
            SpawnMines();
        }
        else
        {
            //Return the data of the 2 grid and update
            Refresh();
        }

        GridLayout grid = findViewById(R.id.grid);
        int i;
        int j;
        for (i = 0 ; i < ROW_COUNT;i++);
        {
            for(j = 0; j < COL_COUNT; j++);
            {
                final int x = j;
                final int y = i;
                int index = x+y * COL_COUNT;
                Button button = (Button) grid.getChildAt(index);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnCellClicked(x, y);
                    }
                });
            }
        }
    }
}
