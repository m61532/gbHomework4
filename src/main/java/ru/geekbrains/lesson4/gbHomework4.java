package ru.geekbrains.lesson4;


import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class gbHomework4 {

    static final char CROSS = 'X';
    static final char ZERO = '0';
    static final char EMPTY_LOT = '_';
    static final int SIZE = 5;
    static final int LINE_LENGTH_TO_WIN = 4;
    static char[][] field;
    static int[][] voteField;
    static int[][] direction = {{1,2,3}, {4,5,6}, {7,8,9}};
    static int quantity;
    static int directionCount;
    static int xBorderPlus;
    static int xBorderMinus;
    static int yBorderPlus;
    static int yBorderMinus;
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        fieldInit(SIZE);
        playerTurn(CROSS);
        victoryCheck(CROSS);
        fieldShow();
//        opponentTurn(ZERO);
//        victoryCheck(ZERO);
//        fieldShow();
//        playerTurn(CROSS);
//        victoryCheck(CROSS);
//        fieldShow();
//        opponentTurn(ZERO);
//        victoryCheck(ZERO);
//        fieldShow();
    }

    private static void fieldInit(int size) {
        field = new char[size][size];
        voteField = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(field[i], EMPTY_LOT);
        }
    }

    private static void fieldShow() {
        System.out.print("  ");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < field.length; j++) {
                System.out.print(voteField[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void playerTurn (char symbol) {
       boolean fieldCheck;
       do {
           System.out.println("Введите координаты X Y");
           int x = scanner.nextInt() - 1;
           int y = scanner.nextInt() - 1;
           fieldCheck = fieldCheckAndFill(x,y,symbol);
       } while (!fieldCheck);
    }

    private static void opponentTurn (char symbol) {
        boolean fieldCheck;
        do {
            int x = random.nextInt(4);
            int y = random.nextInt(4);
            fieldCheck = fieldCheckAndFill(x,y,symbol);
        } while (!fieldCheck);
    }

    private static boolean fieldCheckAndFill (int x, int y, char symbol){
        if ((x < SIZE) & (y < SIZE)){
            if (field[x][y] == EMPTY_LOT) {
                field[x][y] = symbol;
                return true;
            }
            System.out.println("Данная ячейка занята");
            return false;
        }   System.out.println("Введенные координаты выходят за границы поля");
        return false;
    }

    private static boolean victoryCheck(char symbol) {
        quantity = 0;

        //HorizontalCheck
        for (int i = 0; i < SIZE ; i++) {
            for (int j = 0; j < SIZE ; j++) {
                if (field[i][j] == symbol) {
                    quantity++;
                    if (quantity == LINE_LENGTH_TO_WIN) {
                        return true;
                    }
                }
                else {
                    quantity = 0;
                }
            }
        }
        // Vertical

    return false;
    }

    private static boolean drawCheck (){
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == EMPTY_LOT) return false;
            }
        }
        return true;
    }
}

//if (x == 0) {
//        xBorderMinus = x;
//        for (int i = 0; i < 3; i++) {
//        direction[0][i] = 0;
//        }
//        } else {
//        xBorderMinus = x - 1;
//        }
//        if (x == SIZE - 1) {
//        xBorderPlus = x;
//        for (int i = 0; i < 3; i++) {
//        direction[2][i] = 0;
//        }
//        } else {
//        xBorderPlus = x - 1;
//        }
//        if (y == 0) {
//        yBorderMinus = y;
//        for (int i = 0; i < 3; i++) {
//        direction[i][0] = 0;
//        }
//        } else {
//        yBorderMinus = y - 1;
//        }
//        if (y == SIZE - 1) {
//        yBorderPlus = y;
//        for (int i = 0; i < 3; i++) {
//        direction[i][2] = 0;
//        }
//        }
//
//        for (int i = xBorderMinus; i <= xBorderPlus ; i++) {
//        for (int j = yBorderMinus; j <=yBorderPlus ; j++) {
//        if ()
//        }
//        }