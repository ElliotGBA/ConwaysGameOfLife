import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private int height;
    private int width;
    private Cell[][] boardMatrix;

    public Game(int height, int width) {
        this.height = height;
        this.width = width;
        boardMatrix = new Cell[height][width];

        createBoard();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(run, 1, 1, TimeUnit.SECONDS);

        placeFirstCells();

        gameLoop();
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            gameLoop();
        }
    };

    private void gameLoop() {
        renderGame();
        updateCells();
    }

    private void placeFirstCells() {
        Random rand = new Random();
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                int randInt = rand.nextInt(10);
                if (randInt == 1 || randInt == 2) {
                    boardMatrix[i][j].setState(1);
                }
            }
        }
    }

    private void updateCells() {
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                int[] cellNeighbours = checkSurroundingCells(i, j);
                // if cell has 0 or 1 neighbours and is alive : cell dies
                if ((cellNeighbours[1] == 1 || cellNeighbours[1] == 0) && boardMatrix[i][j].getState() == 1) {
                    boardMatrix[i][j].setNextState(0);
                // if cell has more than 4 neighbours and is alive : cell dies
                } else if (cellNeighbours[1] >= 4 && boardMatrix[i][j].getState() == 1) {
                    boardMatrix[i][j].setNextState(0);
                // if cell has 2 or 3 neighbours and is alive : cell stays alive
                } else if ((cellNeighbours[1] == 2 || cellNeighbours[1] == 3) && boardMatrix[i][j].getState() == 1) {
                    boardMatrix[i][j].setNextState(1);
                // if cell has 3 neighbours and is dead : cell becomes living
                } else if (cellNeighbours[1] == 3 && boardMatrix[i][j].getState() == 0) {
                    boardMatrix[i][j].setNextState(1);
                }
            }
        }
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                boardMatrix[i][j].update();
            }
        }
    }

    private int[] checkSurroundingCells(int i, int j) {
        int[] returnArr = new int[2];
        int rowStart = Math.max(i - 1, 0);
        int rowFinish = Math.min(i + 1, boardMatrix.length - 1);
        int colStart = Math.max(j - 1, 0);
        int colFinish = Math.min(j + 1, boardMatrix[i].length - 1);
        for (int curRow = rowStart; curRow <= rowFinish; curRow++) {
            for (int curCol = colStart; curCol <= colFinish; curCol++) {
                 if (boardMatrix[curRow][curCol].getState() == 0) {
                    returnArr[0]++;
                } else {
                    returnArr[1]++;
                }
            }
        }
        if (boardMatrix[i][j].getState() == 0) {
            returnArr[0]--;
        } else if (boardMatrix[i][j].getState() == 1) {
            returnArr[1]--;
        }
        return returnArr;
    }


    private void createBoard() {
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                boardMatrix[i][j] = new Cell(new Tuple(i, j), 0);
            }
        }
    }

    private void renderGame() {
        StringBuilder horizontalLine = new StringBuilder("+ ");
        horizontalLine.append("- ".repeat(Math.max(0, width)));
        horizontalLine.append("+");

        System.out.println(horizontalLine);
        for (int i = 0; i < boardMatrix.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < boardMatrix[i].length; j++) {
                System.out.print(boardMatrix[i][j].toString() + " ");
            }
            System.out.println("|");
        }
        System.out.println(horizontalLine);
    }

}
