package com.javarush.games.snake;
import com.javarush.engine.cell.*;
import java.util.ArrayList;
import java.util.List;
import static com.javarush.games.snake.SnakeGame.HEIGHT;
import static com.javarush.games.snake.SnakeGame.WIDTH;

public class Snake extends GameObject {

    private  List<GameObject> snakeParts = new ArrayList<GameObject>();
    private static  final String HEAD_SIGN = "\uD83D\uDC0D";
    private static final String BODY_SIGN = "\uD83D\uDD38";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake (int x, int y){
//создаем вид змейки в начале игры
        super(x, y);
        for (int i = 0; i < 3; i++) {
            snakeParts.add(new GameObject(x + i, y));
        }
    }

    public void draw(Game game){
// определяем цвет и вид змейки в состоянии жив и мертв
        if (isAlive){
            Color cellColor = Color.GREEN;
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, cellColor, 75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, cellColor, 75);
            }
        }else {
            Color cellColor = Color.RED;
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, cellColor, 75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, cellColor, 75);
            }
        }
    }

    public void setDirection(Direction direction){
// не должен изменять направление движения змейки, если параметр метода противоположен текущему направлению.180градусов нельзя
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }
        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }
        this.direction = direction;
    }

    public void move(Apple apple){
//если голова змеи ушла за границы поля. то она мертва, иначе рисуем новую  голову и удаляем последнюю ячейку
// если координаты новой головы змеи совпадают с координатами яблока, необходимо установить яблоку isAlive=false и не удалять хвост змеи.
        GameObject newHead = createNewHead();
        if (newHead.x >= WIDTH || newHead.x <  0 ||  newHead.y >= HEIGHT ||  newHead.y < 0){
           isAlive = false;
        } else if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
        } else{
            if (checkCollision(newHead)){
                isAlive = false;
                return;
            }
            snakeParts.add(0, newHead);
            removeTail();
        }
    }

    public GameObject createNewHead(){
//    - если змейка движется влево, new GameObject(headX-1, headY);
//    - если змейка движется вниз, new GameObject(headX, headY + 1)
        GameObject gameObject = null;
        switch (direction){
            case UP:
                gameObject  = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y-1);
                break;
            case DOWN:
                gameObject  = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y+1);
                break;
            case LEFT:
                gameObject  = new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
                break;
            case RIGHT:
                gameObject  = new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
                break;
            default:
                new GameObject(snakeParts.get(0).x, snakeParts.get(0).y);

        }
        return gameObject;
    }
    public void removeTail(){
//удаляем последнюю ячейку хвоста во время ее движения
        snakeParts.remove(snakeParts.size() - 1);
    }
    public boolean checkCollision(GameObject gameObject){
//  нужно проверять новосозданную голову змейки на совпадение со всеми остальными элементами её тела
        for (GameObject object : snakeParts){
            if (gameObject.x == object.x && gameObject.y == object.y){
             return true;
            }
        } return false;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
