package com.fcmanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcmanager.bean.Ranking;
import com.fcmanager.db.RankingManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Game logic slash controller.
 */
class Worker extends Handler implements View.OnTouchListener, Runnable
{
    private static final String TAG = Changliang.DEBUG_TAG + "Worker";
    private final GameField field;
    private final List<Snake> snakes;
    private final List<Food> foods;
    private final Snake mSnake;
    private final ImageView dIndicator;
    private final ImageView sIndicator;
    private TextView text_today_best_name;
    private final TextView[] rankNames;
    private final TextView[] rankLengths;
    private final SurfaceHolder surfaceHolder;
    private final Paint paint;
    private boolean running;
    private float left;
    private float top;
    private Activity activity;

    //添加数据到数据库里面
    private RankingManager rankingManager;

    Worker(Activity activity, GameField field)
    {
        this.activity = activity;
        rankingManager = new RankingManager(activity);
        this.field = field;
        snakes = field.getSnakes();
        foods = field.getFoods();
        mSnake = field.getSnake();
        dIndicator = (ImageView) activity.findViewById(R.id.image_view_direction_indicator);
        sIndicator = (ImageView) activity.findViewById(R.id.image_view_speed_indicator);
        text_today_best_name = (TextView) activity.findViewById(R.id.text_today_best_name);
        SharedPreferences sp = activity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        text_today_best_name.setText(name);
        rankNames = new TextView[]
                {
                        (TextView) activity.findViewById(R.id.rank_list_name_1),
                        (TextView) activity.findViewById(R.id.rank_list_name_2),
                        (TextView) activity.findViewById(R.id.rank_list_name_3),
                        (TextView) activity.findViewById(R.id.rank_list_name_4),
                        (TextView) activity.findViewById(R.id.rank_list_name_5),
                        };
        rankLengths = new TextView[]
                {
                        (TextView) activity.findViewById(R.id.rank_list_length_1),
                        (TextView) activity.findViewById(R.id.rank_list_length_2),
                        (TextView) activity.findViewById(R.id.rank_list_length_3),
                        (TextView) activity.findViewById(R.id.rank_list_length_4),
                        (TextView) activity.findViewById(R.id.rank_list_length_5),
                        };
        surfaceHolder = ((GameView) activity.findViewById(R.id.gameView)).getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    public void run()
    {
        long st, tt = 0;
        while (running)
        {
            Log.d(TAG, "run: thread sleep for " + (System.currentTimeMillis() - tt) + " ms.");
            st = System.currentTimeMillis();
            tt = System.currentTimeMillis();
            // Game logic
            logic();
            Log.d(TAG, "run: logic in " + (System.currentTimeMillis() - tt) + " ms.");
            tt = System.currentTimeMillis();
            // Update layout, set rank, snake length, kill count etc.
            update();
            Log.d(TAG, "run: update in " + (System.currentTimeMillis() - tt) + " ms.");
            tt = System.currentTimeMillis();
            // Draw game field, foods, snakes.
            draw();
            Log.d(TAG, "run: draw in " + (System.currentTimeMillis() - tt) + " ms.");
            try
            {
                tt = System.currentTimeMillis();
                Thread.sleep(Math.max(0, Changliang.THREAD_SLEEP - System.currentTimeMillis() + st));
            } catch (InterruptedException e)
            {
                Log.e(TAG, "run: ", e);
            }
        }
    }

    private void logic()
    {
        field.move();
        field.check();
        field.rank();
    }

    private void update()
    {
        setRank();
        setLengthAndKillCount();
    }

    private void setRank()
    {
        Message msg = new Message();
        msg.what = Changliang.MSG_SET_RANK;
        Bundle data = new Bundle();
        ArrayList<CharSequence> names = new ArrayList<>(5);
        ArrayList<CharSequence> length = new ArrayList<>(5);
        for (int i = 0; i < 5; i++)
        {
            names.add(snakes.get(i).getName());
            length.add(String.valueOf(snakes.get(i).getLength()));
        }
        data.putCharSequenceArrayList(Changliang.RANK_SNAKE_NAMES, names);
        data.putCharSequenceArrayList(Changliang.RANK_SNAKE_LENGTH, length);
        msg.setData(data);
        synchronized (this) {sendMessage(msg);}
    }

    private void setLengthAndKillCount()
    {
        Message msg = new Message();
        msg.what = Changliang.MSG_LENGTH_AND_KILL_COUNT;
        Bundle data = new Bundle();
        data.putInt(Changliang.LENGTH, mSnake.getLength());
        data.putInt(Changliang.KILL_COUNT, mSnake.getKillCount());
        msg.setData(data);
        synchronized (this) {sendMessage(msg);}
    }

    private void draw()
    {
        Canvas canvas;
        synchronized (surfaceHolder)
        {
            if ((canvas = surfaceHolder.lockCanvas()) != null)
            {
                translate(canvas);
                drawLayer(canvas);
                drawList(foods, canvas);
                for (Snake snake : snakes)
                {
                    drawList(snake, canvas);
                    if (snake.isAlive() && needDrawing(snake.peekFirst()))
                    {
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(Changliang.snakeBodySize * 2 / 3);
                        Food head = snake.peekFirst();
                        paint.setColor(Changliang.SNAKE_NAME_FONT_COLOR);
                        canvas.drawText(snake.getName(), head.getX(), head.getY() - head.getSize(), paint);
                        paint.setColor(Color.WHITE);
                        canvas.drawCircle(head.getX(), head.getY(), head.getSize() / 3, paint);
                        paint.setColor(Color.BLACK);
                        canvas.drawCircle(head.getX(), head.getY(), head.getSize() / 4, paint);
                    }
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void translate(Canvas canvas)
    {
        // !! VERY IMPORTANT TO SET HEAD!!
        if (field.getSnake().isAlive())
        {
            Food head = field.getSnake().peekFirst();
            if (head.getX() - Changliang.screenWidth / 2 <= 0)
            {
                left = 0;
            } else if (head.getX() + Changliang.screenWidth / 2 >= Changliang.viewWidth)
            {
                left = Changliang.viewWidth - Changliang.screenWidth;
            } else
            {
                left = head.getX() - Changliang.screenWidth / 2;
            }
            if (head.getY() - Changliang.screenHeight / 2 <= 0)
            {
                top = 0;
            } else if (head.getY() + Changliang.screenHeight / 2 >= Changliang.viewHeight)
            {
                top = Changliang.viewHeight - Changliang.screenHeight;
            } else
            {
                top = head.getY() - Changliang.screenHeight / 2;
            }
        }
        canvas.translate(-left, -top);
    }
    //左侧按键控制蛇移动
    private void drawLayer(Canvas canvas)
    {
        //        Draw blank areas.
        paint.setColor(Changliang.BLANK_ZONE_BACKGROUND_COLOR);
        //         Upper blank area.
        if (top < Changliang.fieldTop)
        {
            canvas.drawRect(0, 0, Changliang.viewWidth, Changliang.blankHeight, paint);
        }
        //        Right blank area.
        if (left + Changliang.screenWidth > Changliang.fieldRight)
        {
            canvas.drawRect(Changliang.fieldRight,
                            Changliang.blankHeight,
                            Changliang.viewWidth,
                            Changliang.viewHeight, paint);
        }
        //        Bottom blank area.
        if (top + Changliang.screenHeight > Changliang.fieldBottom)
        {
            canvas.drawRect(0, Changliang.fieldBottom, Changliang.fieldRight, Changliang.viewHeight, paint);
        }
        //        Left blank area.
        if (left < Changliang.fieldLeft)
        {
            canvas.drawRect(0, Changliang.blankHeight, Changliang.fieldLeft, Changliang.fieldBottom, paint);
        }
        //        Draw field.
        paint.setColor(Changliang.FIELD_BACKGROUND_COLOR);
        canvas.drawRect(left < Changliang.fieldLeft ? Changliang.fieldLeft : left,
                        top < Changliang.fieldTop ? Changliang.fieldTop : top,
                        left + Changliang.screenWidth < Changliang.fieldRight ? left + Changliang.screenWidth : Changliang.fieldRight,
                        top + Changliang.screenHeight < Changliang.fieldBottom ? top + Changliang.screenHeight : Changliang.fieldBottom,
                        paint);
        //        Draw field lines.
        int x = (Changliang.fieldWidth % Changliang.gap) / 2;
        int y = (Changliang.fieldHeight % Changliang.gap) / 2;
        paint.setColor(Changliang.FIELD_LINE_COLOR);
        //        Draw vertical lines.
        for (x += Changliang.fieldLeft; x < Changliang.fieldRight; x += Changliang.gap)
        {
            canvas.drawLine(x, Changliang.fieldTop, x, Changliang.fieldBottom, paint);
        }
        //        Draw horizontal lines.
        for (y += Changliang.blankHeight; y < Changliang.blankHeight + Changliang.fieldHeight; y += Changliang.gap)
        {
            canvas.drawLine(Changliang.fieldLeft, y, Changliang.fieldRight, y, paint);
        }
    }

    private void drawList(List<Food> list, Canvas canvas)
    {
        for (Food food : list)
        {
            if (needDrawing(food))
            {
                food.draw(canvas, paint);
            }
        }
    }

    private boolean needDrawing(Food food)
    {
        return food.getX() >= left - food.getSize() + food.getSize() &&
               food.getX() <= left + Changliang.screenWidth &&
               food.getY() >= top - food.getSize() &&
               food.getY() <= top + Changliang.screenHeight + food.getSize();
    }

    void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    //加速 按下加速 松开
    public boolean onTouch(View v, MotionEvent event)
    {
        if (v.getId() == R.id.image_view_speed_background)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    sIndicator.setBackgroundResource(R.drawable.speed_indicator_pressed);
                    mSnake.setSpeed(Changliang.ACCELERATE);
                    return true;
                case MotionEvent.ACTION_UP:
                    sIndicator.setBackgroundResource(R.drawable.speed_indicator);
                    mSnake.setSpeed(Changliang.DEFAULT_SPEED);
                    return false;
            }
        }
        //控制方向
        if (v.getId() == R.id.image_view_direction_background)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    dIndicator.setBackgroundResource(R.drawable.direction_indicator_pressed);
                    setIndicator(event.getX(), event.getY());
                    return true;
                case MotionEvent.ACTION_MOVE:
                    setIndicator(event.getX(), event.getY());
                    return true;
                case MotionEvent.ACTION_UP:
                    dIndicator.setBackgroundResource(R.drawable.direction_indicator);
                    dIndicator.setTranslationX(0);
                    dIndicator.setTranslationY(0);
                    return false;
            }
        }
        return false;
    }

    private void setIndicator(float x, float y)
    {
        float oX = dIndicator.getLeft() + dIndicator.getWidth() / 2;
        float oY = dIndicator.getTop() + dIndicator.getHeight() / 2;
        float r = dIndicator.getLeft();
        float d = Changliang.getDistance(oX, oY, x, y);
        if (d != 0)
        {
            dIndicator.setTranslationX(r * (x - oX) / d);
            dIndicator.setTranslationY(r * (y - oY) / d);
            mSnake.setDirection((x - oX) / d, (y - oY) / d);
        }
    }

    @Override
    public void dispatchMessage(Message msg)
    {
        switch (msg.what)
        {
            case Changliang.MSG_LENGTH_AND_KILL_COUNT:
                setLengthAndKillCount(msg);
                break;
            case Changliang.MSG_SET_RANK:
                setRank(msg);
                break;
            default:
                super.dispatchMessage(msg);
                break;
        }
    }

    private void setRank(Message msg)
    {
        Bundle bundle = msg.getData();
        ArrayList<CharSequence> names = bundle.getCharSequenceArrayList(Changliang.RANK_SNAKE_NAMES);
        ArrayList<CharSequence> length = bundle.getCharSequenceArrayList(Changliang.RANK_SNAKE_LENGTH);
        if (length != null && names != null)
        {
            for (int i = 0; i < 5; i++)
            {
                rankNames[i].setText(names.get(i));
                rankLengths[i].setText(length.get(i));
            }
        }
    }

    private void setLengthAndKillCount(Message msg)
    {
        Bundle bundle = msg.getData();
        int length = bundle.getInt(Changliang.LENGTH);
        SharedPreferences sp = activity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        List<Ranking> rankings = rankingManager.queryById(name);
        if (rankings.size()<=0){
            rankingManager.add(name,length+"",length+"");
        }else {
            if (length>0) {
                rankingManager.updateRank(length + "", rankingManager.queryById(name).get(0).getId() + "");
            }
        }
    }
}
