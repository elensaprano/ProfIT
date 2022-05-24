package com.javarush.games.snake;
import com.javarush.engine.cell.*;

import javax.swing.*;

import static com.javarush.games.snake.Direction.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int  GOAL = 28;
    private int score;

    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    private void createGame(){
        snake = new Snake( WIDTH / 2, HEIGHT / 2);
        //apple = new Apple(5, 5);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }
    private void drawScene(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0;  y < HEIGHT; y++){
                setCellValueEx(x, y, Color.PINK,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive){
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL){
            win();
        }
        drawScene();


    }
    @Override
    public void onKeyPress(Key key){
//метод управления змейкой с клавиатуры
        switch (key) {
            case LEFT:
                snake.setDirection(LEFT);
                break;
            case DOWN:
                snake.setDirection(DOWN);
                break;
            case RIGHT:
                snake.setDirection(RIGHT);
                break;
            case UP:
                snake.setDirection(UP);
                break;
            case SPACE:
                if (isGameStopped == true) {
                    createGame();
                }
                break;
        }
    }
    private void createNewApple(){
// должен генерировать случайные координаты ячейки в пределах игрового поля, на которой будет появляться яблоко.
// должен вызывать метод checkCollision(GameObject) до тех пор, пока координаты apple и любого из сегментов змеи совпадают.
        apple = new Apple (getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while (snake.checkCollision(apple)){
            apple = new Apple (getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }
    }
    private  void gameOver(){
//Проверка проигрыша
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "You dead inside.", Color.RED, 50);
    }
    private void win(){
//Проверка победы
        stopTurnTimer();
        isGameStopped  = true;
        showMessageDialog(Color.PINK, "you're dead inside anyway...", Color.RED, 50);
    }


}