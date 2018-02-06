package com.fcmanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


class GameField
{
    private static final String TAG = Changliang.DEBUG_TAG + "GameField";
    private int id;
    private final List<Snake> snakes;
    private final List<Food> foods;
    private final Changliang direction;
    private final Changliang position;
    private final Snake mSnake;
    private final Food tFood;
    private Activity activity;

    GameField(Activity activity)
    {
        this.activity = activity;
        id = 0;
        snakes = new LinkedList<>();
        foods = new ArrayList<>(Changliang.FOOD_COUNT);
        direction = new Changliang(0, 0);
        position = new Changliang(0, 0);
        mSnake = new Snake(Changliang.DEFAULT_SNAKE_NAME, id, getRandomColor(), false, getRandomPosition(), getRandomDirection());
        tFood = new Food(mSnake.peekFirst());
        snakes.add(mSnake);
       // makeupAISnakes(Changliang.AI_SNAKE_COUNT);
        SharedPreferences sp = activity.getSharedPreferences("bg", Context.MODE_PRIVATE);
        int data = sp.getInt("bg",1);
        if (data==1){
            makeupAISnakes(10);
        }else if(data==2){
            makeupAISnakes(20);
        }else {
            makeupAISnakes(30);
        }
        initFood();
    }

    private void makeupAISnakes(int count)
    {
        for (int i = 0; i < count; i++)
        {
            id++;
            if (id==0){
                SharedPreferences sp = activity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

                String name = sp.getString("name", null);
                snakes.add(new Snake(name, id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==1){
                SharedPreferences sp = activity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                String name = sp.getString("name", null);
                snakes.add(new Snake(name, id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==2){
                snakes.add(new Snake(" Jack", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if(id==3){
                snakes.add(new Snake("James", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if(id==4){
                snakes.add(new Snake("Curry", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==5){
                snakes.add(new Snake("Wayne", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==6){
                snakes.add(new Snake("Hammer", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==6){
                snakes.add(new Snake("Grover", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==7){
                snakes.add(new Snake("Goss", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==8){
                snakes.add(new Snake("Abigail", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==9){
                snakes.add(new Snake("Caroline", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==10){
                snakes.add(new Snake("Editha", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==11){
                snakes.add(new Snake("Frederica", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==12){
                snakes.add(new Snake("Hellen", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==13){
                snakes.add(new Snake("Jean", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==14){
                snakes.add(new Snake("Julia", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }else if (id==15){
                snakes.add(new Snake("Lynn", id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }
            else {
                snakes.add(new Snake("AI-" + id, id, getRandomColor(), true, getRandomPosition(), getRandomDirection()));
            }
        }
    }

    private void initFood()
    {
        for (int i = 0; i < Changliang.FOOD_COUNT; i++)
        {
            addFood();
        }
    }

    private void addFood()
    {
        Changliang position = getRandomPosition();
        Food food = new Food(position.getX(), position.getY(), Changliang.foodSize, getRandomColor());
        while (hasCollision(food, foods, false))
        {
            position = getRandomPosition();
            food.setPosition(position.getX(), position.getY());
        }
        foods.add(food);
    }

    private Changliang getRandomPosition()
    {
        return position.setPosition((float) (Math.random() * (Changliang.fieldWidth - 2 * Changliang.snakeBodySize)) + Changliang.fieldLeft + 2 * Changliang.snakeBodySize,
                                    (float) (Math.random() * (Changliang.fieldHeight - 2 * Changliang.snakeBodySize)) + Changliang.fieldTop + 2 * Changliang.snakeBodySize);
    }

    void move()
    {
        for (Snake snake : snakes)
        {
            if (snake.isAlive())
            {
                if (snake.isAi())
                {
                    checkForDirection(snake);
                }
                snake.move();
            }
        }
    }

    private void checkForDirection(Snake snake)
    {
        int cnt = 0;
        Changliang d = snake.getDirection();
        Food head = snake.peekFirst();
        tFood.clone(head);
        while (cnt < Changliang.AI_DIRECTION_CHECK_COUNT)
        {
            cnt++;
            //            10% chance of changing current direction if int's not hunting.
            if (Math.random() > 0.9 && !snake.isHunting())
            {
                snake.setDirection(getRandomDirection());
            }
            tFood.setPosition(head.getX() + d.getX(), head.getY() + d.getY());
            if (!isValid(tFood))
            {
                snake.setDirection(getRandomDirection());
                cnt = 0;
                continue;
            }
            boolean hasCollision = false;
            for (Snake tSnake : snakes)
            {
                if (tSnake.getId() != snake.getId())
                {
                    if (hasCollision(tFood, tSnake, false))
                    {
                        if (tSnake.isAlive())
                        {
                            snake.setDirection(getRandomDirection());
                            hasCollision = true;
                            break;
                        } else
                        {
                            break;
                        }
                    }
                }
            }
            if (!hasCollision)
            {
                break;
            }
        }
    }

    void check()
    {
        int dCnt = checkForSnakes();
        checkForFood();
        makeupAISnakes(dCnt);
    }

    private int checkForSnakes()
    {
        // dead snake count holder.
        int cnt = 0;
        Food head;
        // Check for walls.
        for (Snake snake : snakes)
        {
            head = snake.peekFirst();
            if (snake.isAlive())
            {
                if (!isValid(head))
                {
                    snake.die();
                    cnt++;
                }
            }
        }
        // Check for other snakes.
        for (Snake snake : snakes)
        {
            head = snake.peekFirst();
            if (snake.isAlive())
            {
                for (Snake tSnake : snakes)
                {
                    if (snake.getId() != tSnake.getId())
                    {
                        if (tSnake.isAlive())
                        {
                            if (hasCollision(head, tSnake, false))
                            {
                                snake.die();
                                cnt++;
                                tSnake.setKillCount();
                            }
                        } else
                        {
                            Iterator<Food> it = tSnake.iterator();
                            while (it.hasNext())
                            {
                                Food body = it.next();
                                if (head.collidesWith(body, true))
                                {
                                    snake.eat(body);
                                    it.remove();
                                    snake.hunt(tSnake);
                                }
                            }
                        }
                    }
                }
            }
        }
        return cnt;
    }

    private boolean isValid(Food food)
    {
        return food.getX() - food.getSize() / 2 > Changliang.fieldLeft &&
               food.getX() + food.getSize() / 2 < Changliang.fieldRight &&
               food.getY() - food.getSize() / 2 > Changliang.fieldTop &&
               food.getY() + food.getSize() / 2 < Changliang.fieldBottom;
    }

    private boolean hasCollision(Food food, List<Food> list, boolean isFood)
    {
        for (Food tFood : list)
        {
            if (!food.equals(tFood) && food.collidesWith(tFood, isFood))
            {
                return true;
            }
        }
        return false;
    }

    private void checkForFood()
    {
        // Check if any snake ate food.
        for (Snake snake : snakes)
        {
            if (snake.isAlive())
            {
                for (Food food : foods)
                {
                    if (snake.peekFirst().collidesWith(food, true))
                    {
                        snake.eat(food);
                        shuffleFood(food);
                    }
                }
            }
        }
    }

    private void shuffleFood(Food food)
    {
        Changliang p = getRandomPosition();
        food.setPosition(p.getX(), p.getY());
        while (hasCollision(food, foods, false))
        {
            p = getRandomPosition();
            food.setPosition(p.getX(), p.getY());
        }
        food.setColor(getRandomColor());
    }

    private int getRandomColor()
    {
        switch ((int) (Math.random() * Integer.MAX_VALUE) % 10)
        {
            case 1:
                return Color.BLACK;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.CYAN;
            case 4:
                return Color.DKGRAY;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.MAGENTA;
            case 8:
                return Color.RED;
            case 9:
                return Color.YELLOW;
        }
        return Color.MAGENTA;
    }

    private Changliang getRandomDirection()
    {
        float cos = (float) Math.random();
        float sin = (float) Math.sqrt(1 - Math.pow(cos, 2));
        if (Math.random() > 0.5)
        {
            cos = -cos;
        }
        if (Math.random() > 0.5)
        {
            sin = -sin;
        }
        direction.setPosition(cos, sin);
        return direction;
    }

    Snake getSnake()
    {
        return mSnake;
    }

    List<Food> getFoods()
    {
        return foods;
    }

    List<Snake> getSnakes()
    {
        return snakes;
    }

    void rank()
    {
        Collections.sort(snakes, new Comparator<Snake>()
        {
            @Override
            public int compare(Snake aSnake, Snake bSnake)
            {
                return bSnake.getLength() - aSnake.getLength();
            }
        });
    }
}

