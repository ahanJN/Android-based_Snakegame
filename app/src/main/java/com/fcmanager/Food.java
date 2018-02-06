package com.fcmanager;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;


class Food extends Changliang
{
    private int size;
    private int color;

    Food(float x, float y, int size, int color)
    {
        super(x, y);
        this.size = size;
        this.color = color;
    }

    Food(Food food)
    {
        this(food.getX(), food.getY(), food.getSize(), food.getColor());
    }

    int getSize()
    {
        return size;
    }

    void setSize(int size)
    {
        if (size > 0)
        {
            this.size = size;
        }
    }

    int getColor()
    {
        return color;
    }

    boolean collidesWith(@NonNull Food food, boolean isFood)
    {
        int correction = 0;
        if (isFood)
        {
            correction = this.size / 2;
        }
        return Changliang.getDistanceBetween(this, food) <= this.size / 2 + food.getSize() / 2 + correction;
    }

    Food clone(Food food)
    {
        this.setPosition(food.getX(), food.getY());
        this.size = food.getSize();
        this.color = food.getColor();
        return this;
    }

    void draw(Canvas canvas, Paint paint)
    {
        paint.setColor(color);
        canvas.drawCircle(getX(), getY(), size / 2, paint);
    }

    void setColor(int color)
    {
        this.color = color;
    }
}
