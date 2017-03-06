package hu.ait.tictactoe.model;


public class TicTacToeModel {

    private static TicTacToeModel instance = null;

    private TicTacToeModel() {
    }

    public static TicTacToeModel getInstance() {
        if (instance == null) {
            instance = new TicTacToeModel();
        }

        return instance;
    }

    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    private short[][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    private short nextPlayer = CIRCLE;

    public void resetModel() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }
        }

        nextPlayer = CIRCLE;
    }

    public short checkWinner() {
        int initialPoint = model[0][0];
        int diagonalPoint = model[2][2];

        if (initialPoint == 1 || initialPoint == 2 ) {
            if (initialPoint == model[0][1] &&
                    initialPoint == model[0][2]) {
                if(initialPoint == 1) {
                    return 1;
                }
                return 2;
            }
            if (initialPoint == model[1][0] &&
                    initialPoint == model[2][0]) {
                if(initialPoint == 1) {
                    return 1;
                }
                return 2;
            }
            if (initialPoint == model[1][1] &&
                    initialPoint == diagonalPoint) {
                if(initialPoint == 1) {
                    return 1;
                }
                return 2;
            }
        }
        if (diagonalPoint == 1 || diagonalPoint == 2) {
            if (diagonalPoint == model[2][1] &&
                    diagonalPoint == model[2][0]) {
                if(diagonalPoint == 1) {
                    return 1;
                }
                return 2;
            }
            if (diagonalPoint == model[0][2] &&
                    diagonalPoint == model[1][2]) {
                if(diagonalPoint == 1) {
                    return 1;
                }
                return 2;
            }
        }

        if (model[1][1] == 1 || model[1][1] == 2) {
            if (model[2][1] == model[1][1] &&
                    model[1][1] == model[0][1]) {
                if(model[1][1] == 1) {
                    return 1;
                }
                return 2;
            }
            if (model[2][0] == model[1][1] && model[1][1] == model[0][2]) {
                if(model[1][1] == 1) {
                    return 1;
                }
                return 2;
            }
        }


        if (model[1][0] == model[1][1] && model[1][1] == model[1][2]) {
            System.out.println("I am at last");
            if (model[1][0] == 1) return 1;
            if (model[1][0] == 2) return 2;
        }

        return 0;
    }

    public boolean isFull() {
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++) {
                if (model[i][j] == EMPTY) return false;
            }
        }
        return true;
    }


    public void changeNextPlayer() {
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;

        /*if (nextPlayer == CIRCLE) {
            nextPlayer = CROSS;
        } else {
            nextPlayer = CIRCLE;
        }*/
    }

    public void setField(int x, int y, short player) {
        model[x][y] = player;
    }

    public short getField(int x, int y) {
        return model[x][y];
    }

    public short getNextPlayer() {
        return nextPlayer;
    }
}