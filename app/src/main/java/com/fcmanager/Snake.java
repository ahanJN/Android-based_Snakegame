package com.fcmanager;

import java.util.LinkedList;

class Snake extends LinkedList<Food> {
    private final String name;
    private final int id;
    private final boolean isAi;
    private int length;
    private int killCount;
    private int speed;
    private boolean hunting;
    private Changliang direction;

    Snake(String name, int id, int color, boolean isAi, Changliang head, Changliang direction) {
        super();
        this.name = name;
        this.id = id;
        this.isAi = isAi;
        this.length = Changliang.DEFAULT_SNAKE_LENGTH;
        this.killCount = 0;
        this.speed = Changliang.DEFAULT_SPEED;
        this.hunting = false;
        this.direction = new Changliang(0,0);
        setDirection(direction);
        for (int i = 0; i < Changliang.DEFAULT_SNAKE_SIZE; i++) {
            add(new Food(head.getX() + i * this.direction.getX(),
                    head.getY() + i * this.direction.getY(),
                    Changliang.snakeBodySize, color));
        }
    }

    void move() {
        for (int i = 0; i < speed; i++) {
            peekLast().setPosition(peekFirst().getX() + direction.getX(),
                    peekFirst().getY() + direction.getY());
            addFirst(removeLast());
        }
    }

    void eat(Food food) {
        if (food.getSize() == Changliang.foodSize) {
            length++;
            if (length % Changliang.GROW_PER_FOOD == 0) {
                add(new Food(peekLast()));
            }
        } else {
            length += Changliang.SNAKE_BODY_FOOD_VALUE;
            add(food.clone(peekLast()));
        }
    }

    void die() {
        length = 0;
        for (Food body : this) {
            body.setSize(Changliang.deadSnakeBodySize);
        }
    }

    String getName() {
        return name;
    }

    int getId() {
        return id;
    }

    boolean isAi() {
        return isAi;
    }

    boolean isAlive() {
        return length > 0;
    }

    int getLength() {
        return length;
    }

    int getKillCount() {
        return killCount;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    void setKillCount() {
        killCount++;
    }

    Changliang getDirection() {
        return direction;
    }

    void setDirection(Changliang direction) {
        setDirection(direction.getX(), direction.getY());
    }


    void setDirection(float cos, float sin) {
        this.direction.setPosition(cos * Changliang.snakeBodyDistance, sin * Changliang.snakeBodyDistance);
    }

    void hunt(Snake snake) {
        if (snake == null || snake.isEmpty()) {
            hunting = false;
            speed = Changliang.DEFAULT_SPEED;
            return;
        }
        hunting = true;
        speed = Changliang.ACCELERATE;
        Food food = snake.iterator().next();
        Food head = peekFirst();
        float d = Changliang.getDistanceBetween(head, food);
        setDirection((food.getX() - head.getX()) / d,
                (food.getY() - head.getY()) / d);
    }

    boolean isHunting() {
        return hunting;
    }
}
