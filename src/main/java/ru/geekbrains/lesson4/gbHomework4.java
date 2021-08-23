package ru.geekbrains.lesson4;

import java.util.Scanner;

public class gbHomework4 {

    //Common
    static boolean flag;
    static Scanner scanner = new Scanner(System.in);

    //Field
    private static char[][] field;
    private static final int SIZE = 5;
    private static final int LENGTH_TO_WIN = 4;
    private static final char CROSS = 'X';
    private static final char ZERO = '0';
    private static final char EMPTY_LOT = '_';

    //Player
    private static int choice;

    //Bot
    private static int[][] voteFieldForCross;
    private static int[][] voteFieldForZero;
    private static int maxVoteCrossCoordX;
    private static int maxVoteCrossCoordY;
    private static int maxVoteZeroCoordX;
    private static int maxVoteZeroCoordY;

    public static void main(String[] args) {

        while (true) {

            //Выбор стороны
            do {
                sideChoose();
            } while (choice == 0);

            fieldInit();

            switch (choice) {

                // Игрок ходит первым (X)
                case 1: {
                    while (true) {
                        playerMove(CROSS);
                        fieldShow();
                        if (gameStatus(CROSS)) {
                            System.out.println("Победа игрока\n");
                            break;
                        }
                        botMove(ZERO);
                        System.out.println("Ход бота\n");

                        fieldShow();
                        if (gameStatus(ZERO)) {
                            System.out.println("Победа бота\n");
                            break;
                        }
                    }
                    break;
                }

                // Игрок ходит вторым (0)
                case 2: {
                    while (true) {
                        botMove(CROSS);
                        System.out.println("Ход бота\n");
                        fieldShow();
                        if (gameStatus(CROSS)) {
                            System.out.println("Победа бота\n");
                            break;
                        }
                        playerMove(ZERO);
                        fieldShow();
                        if (gameStatus(ZERO)) {
                            System.out.println("Победа игрока\n");
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }


    public static int sideChoose() {
        System.out.println("Крестик или нолик? X – 1, 0 – 2");
        choice = scanner.nextInt();
        if (!(choice == 1 | choice == 2)) {
            choice = 0;
            System.out.println(choice);
        }
        return choice;
    }

    public static void fieldInit() {
        field = new char[SIZE][SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = EMPTY_LOT;
            }
        }
        voteFieldForCross = new int[SIZE][SIZE];
        voteFieldForZero = new int[SIZE][SIZE];

    }

    public static void fieldShow() {
        System.out.print("   ");
        for (int i = 1; i < SIZE + 1; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean cellFill(char symbol, int x, int y) {
        if (x < 0 || x > SIZE || y < 0 || y > SIZE) {
            System.out.println("Диапазон значений координат от 1 до " + SIZE);
            return false;
        }
        if (field[x][y] != EMPTY_LOT) {
            System.out.println("Данная ячейка занята");
            return false;
        } else {
            field[x][y] = symbol;
            return true;
        }
    }

    public static void playerMove(char symbol) {
        do {
            flag = false;
            System.out.println("Введите коррдинаты X и Y");
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            flag = cellFill(symbol, x, y);
        } while (!flag);
    }

    public static void botMove(char symbol) {

        int counter1 = 0;
        int counter2 = 0;
        int diff = SIZE - LENGTH_TO_WIN;

        //Очистка голосов
        for (int i = 0; i < voteFieldForCross.length; i++) {
            for (int j = 0; j < voteFieldForCross[i].length; j++) {
                voteFieldForCross[i][j] = 0;
            }
        }

        for (int i = 0; i < voteFieldForZero.length; i++) {
            for (int j = 0; j < voteFieldForZero[i].length; j++) {
                voteFieldForZero[i][j] = 0;
            }
        }

        //Расстановка голосов

        //Горизонтальная
        for (int i = 0; i < SIZE; i++) {
            counter1 = 0;
            counter2 = 0;
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == CROSS) {
                    counter1++;
                } else {
                    if (j < SIZE && voteFieldForCross[i][j] == 0) {
                        voteFieldForCross[i][j] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == ZERO) {
                    counter2++;
                } else {
                    if (j < SIZE && voteFieldForZero[i][j] == 0) {
                        voteFieldForZero[i][j] += counter2;
                    }
                    counter2 = 0;
                }
            }

            counter1 = 0;
            counter2 = 0;

            for (int j = SIZE - 1; j >= 0; j--) {
                if (field[i][j] == CROSS) {
                    counter1++;
                } else {
                    if (j >= 0 && voteFieldForCross[i][j] == 0) {
                        voteFieldForCross[i][j] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int j = SIZE - 1; j >= 0; j--) {
                if (field[i][j] == ZERO) {
                    counter2++;
                } else {
                    if (j >= 0 && voteFieldForZero[i][j] == 0) {
                        voteFieldForZero[i][j] += counter2;
                    }
                    counter2 = 0;
                }
            }
        }

        //Вертикальная

        for (int i = 0; i < SIZE; i++) {
            counter1 = 0;
            counter2 = 0;
            for (int j = 0; j < SIZE; j++) {
                if (field[j][i] == CROSS) {
                    counter1++;
                } else {
                    if (i < SIZE & voteFieldForCross[j][i] == 0) {
                        voteFieldForCross[j][i] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int j = 0; j < SIZE; j++) {
                if (field[j][i] == ZERO) {
                    counter2++;
                } else {
                    if (i < SIZE & voteFieldForZero[j][i] == 0) {
                        voteFieldForZero[j][i] += counter2;
                    }
                    counter2 = 0;
                }
            }

            counter1 = 0;
            counter2 = 0;

            for (int j = SIZE - 1; j >= 0; j--) {
                if (field[j][i] == CROSS) {
                    counter1++;
                } else {
                    if (j >= 0 && voteFieldForCross[j][i] == 0) {
                        voteFieldForCross[j][i] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int j = SIZE - 1; j >= 0; j--) {
                if (field[j][i] == ZERO) {
                    counter2++;
                } else {
                    if (j >= 0 && voteFieldForZero[j][i] == 0) {
                        voteFieldForZero[j][i] += counter2;
                    }
                    counter2 = 0;
                }
            }

        }

        //Диагональная восходящая
        for (int i = -1 - diff; i < diff; i++) {

            counter1 = 0;
            counter2 = 0;

            for (int y = 0; y < SIZE; y++) {
                if (i > y - 1 | SIZE + i - y < 0) {
                    counter1 = 0;
                    continue;
                }
                if (field[SIZE + i - y][y] == CROSS) {
                    counter1++;
                } else {
                    if (SIZE + i - y < SIZE && y < SIZE && voteFieldForCross[SIZE + i - y][y] == 0) {
                        voteFieldForCross[SIZE + i - y][y] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int y = 0; y < SIZE; y++) {
                if (i > y - 1 | SIZE + i - y < 0) {
                    counter2 = 0;
                    continue;
                }
                if (field[SIZE + i - y][y] == ZERO) {
                    counter2++;
                } else {
                    if (SIZE + i - y < SIZE && y < SIZE && voteFieldForZero[SIZE + i - y][y] == 0) {
                        voteFieldForZero[SIZE + i - y][y] += counter2;
                    }
                    counter2 = 0;
                }
            }

            counter1 = 0;
            counter2 = 0;

            for (int y = SIZE - 1; y >= 0; y--) {
                if (i > y - 1 | SIZE + i - y < 0) {
                    counter1 = 0;
                    continue;
                }
                if (field[SIZE + i - y][y] == CROSS) {
                    counter1++;
                } else {
                    if (SIZE + i - y >= 0 & y >= 0 & voteFieldForCross[SIZE + i - y][y] == 0) {
                        voteFieldForCross[SIZE + i - y][y] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int y = SIZE - 1; y >= 0; y--) {
                if (i > y - 1 | SIZE + i - y < 0) {
                    counter2 = 0;
                    continue;
                }
                if (field[SIZE + i - y][y] == ZERO) {
                    counter2++;
                } else {
                    if (SIZE + i - y >= 0 & y >= 0 & voteFieldForZero[SIZE + i - y][y] == 0) {
                        voteFieldForZero[SIZE + i - y][y] += counter2;
                    }
                    counter2 = 0;
                }
            }

        }

        //Диагональная нисходящая
        for (int i = -diff; i <= diff; i++) {

            counter1 = 0;
            counter2 = 0;

            for (int x = 0; x < SIZE; x++) {
                if (i + x < 0 | i + x > SIZE - 1) {
                    counter1 = 0;
                    continue;
                }

                if (field[x][i + x] == CROSS) {
                    counter1++;
                } else {
                    if (x < SIZE && i + x < SIZE && voteFieldForCross[x][i + x] == 0) {
                        voteFieldForCross[x][i + x] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int x = 0; x < SIZE; x++) {
                if (i + x < 0 | i + x > SIZE - 1) {
                    counter2 = 0;
                    continue;
                }

                if (field[x][i + x] == ZERO) {
                    counter2++;
                } else {
                    if (x < SIZE && i + x < SIZE && voteFieldForZero[x][i + x] == 0) {
                        voteFieldForZero[x][i + x] += counter2;
                    }
                    counter2 = 0;
                }
            }

            counter1 = 0;
            counter2 = 0;

            for (int x = SIZE - 1; x >= 0; x--) {
                if (i + x < 0 | i + x > SIZE - 1) {
                    counter1 = 0;
                    continue;
                }

                if (field[x][i + x] == CROSS) {
                    counter1++;
                } else {
                    if (x >= 0 && i + x >= 0 && voteFieldForCross[x][i + x] == 0) {
                        voteFieldForCross[x][i + x] += counter1;
                    }
                    counter1 = 0;
                }
            }

            for (int x = SIZE - 1; x >= 0; x--) {
                if (i + x < 0 | i + x > SIZE - 1) {
                    counter2 = 0;
                    continue;
                }

                if (field[x][i + x] == ZERO) {
                    counter2++;
                } else {
                    if (x >= 0 && i + x >= 0 && voteFieldForZero[x][i + x] == 0) {
                        voteFieldForZero[x][i + x] += counter2;
                    }
                    counter2 = 0;
                }
            }

        }


//        for (int i = 0; i < SIZE; i++) {
//            System.out.println();
//            for (int j = 0; j < SIZE; j++) {
//                System.out.print(voteFieldForCross[i][j] + " ");
//            }
//        }
//        System.out.println();
//
//        for (int i = 0; i < SIZE; i++) {
//            System.out.println();
//            for (int j = 0; j < SIZE; j++) {
//                System.out.print(voteFieldForZero[i][j] + " ");
//            }
//        }
//        System.out.println();

        counter1 = 0;
        counter2 = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (counter1 < voteFieldForCross[i][j] && field[i][j] == EMPTY_LOT) {
                    counter1 = voteFieldForCross[i][j];
                    maxVoteCrossCoordX = i;
                    maxVoteCrossCoordY = j;
                }

                if (counter2 < voteFieldForZero[i][j] && field[i][j] == EMPTY_LOT) {
                    counter2 = voteFieldForZero[i][j];
                    maxVoteZeroCoordX = i;
                    maxVoteZeroCoordY = j;
                }

            }
        }

        if (counter1 > counter2) {
            cellFill(symbol, maxVoteCrossCoordX, maxVoteCrossCoordY);
        } else {
            cellFill(symbol, maxVoteZeroCoordX, maxVoteZeroCoordY);
        }

    }

    public static boolean gameStatus(char symbol) {
        return victoryCheck(symbol) || drawCheck();
    }

    public static boolean drawCheck() {

        flag = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == EMPTY_LOT) {
                    flag = false;
                }
            }
        }

        return flag;
    }

    public static boolean victoryCheck(char symbol) {

        int counter1 = 0;
        int counter2 = 0;
        int diff = SIZE - LENGTH_TO_WIN;

        //Проверка на победу по горизонтали и вертикали
        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {
                //По горизонтали
                if (field[i][j] == symbol) {
                    counter1++;
                    if (counter1 == LENGTH_TO_WIN) {
                        return true;
                    }
                } else {
                    counter1 = 0;
                }

                //По вертикали
                if (field[j][i] == symbol) {
                    counter2++;
                    if (counter2 == LENGTH_TO_WIN) {
                        return true;
                    }
                } else {
                    counter2 = 0;
                }
            }
        }

        //Проверка по диагоналям

        //Восходящие диагонали
        for (int i = -1 - diff; i < diff; i++) {
            counter1 = 0;
            for (int y = 0; y < SIZE; y++) {
                if (i > y - 1 | SIZE + i - y < 0) {
                    counter1 = 0;
                    continue;
                }
                if (field[SIZE + i - y][y] == symbol) {
                    counter1++;
                    if (counter1 == LENGTH_TO_WIN) {
                        return true;
                    }
                } else {
                    counter1 = 0;
                }
            }
        }

        //Нисходящие диагонали
        for (int i = -diff; i <= diff; i++) {
            counter2 = 0;
            for (int x = 0; x < SIZE; x++) {
                if (i + x < 0 | i + x > SIZE - 1) {
                    counter2 = 0;
                    continue;
                }

                if (field[x][i + x] == symbol) {
                    counter2++;
                    if (counter2 == LENGTH_TO_WIN) {
                        return true;
                    }
                } else {
                    counter2 = 0;
                }
            }
        }
        return false;
    }
}
