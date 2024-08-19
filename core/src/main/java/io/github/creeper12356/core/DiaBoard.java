package io.github.creeper12356.core;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @brief 棋盘类
 */
public class DiaBoard {
    public static final int BOARD_WID = 19; // 棋盘宽度
    public static final int BOARD_HGT = 13; // 棋盘高度 
    // 二维数组，[posx][posy]
    byte[][] isShow = new byte[BOARD_WID][BOARD_HGT]; 
    
    /**
     * onDia[posx][posy]: 
     * -1: 没有棋子
     * 0,1,2: 普通棋子，玩家编号
     * 10,11,12: king棋子，玩家编号 + 10
     */
    byte[][] onDia = new byte[BOARD_WID][BOARD_HGT]; // 记录每个位置的棋子，onDia[posx][posy] : -1 没有棋子，< 10:
    byte[][] passed = new byte[BOARD_WID][BOARD_HGT];
    byte[][] evaluation = new byte[BOARD_WID][BOARD_HGT];

    DiaBoard() {
        this.Init();
    }

    void Init() {
        byte[] byArray = null;
        // 从default.dat文件中读取棋盘数据，存入byArray数组
        DataInputStream dataInputStream = new DataInputStream(this.getClass().getResourceAsStream("default.dat"));
        try {
            byArray = new byte[dataInputStream.available()];
            dataInputStream.read(byArray);
            dataInputStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        for (int i = 0; i < BOARD_HGT; ++i) {
            for (int j = 0; j < BOARD_WID; ++j) {
                this.isShow[j][i] = byArray[BOARD_WID * i + j];
                this.onDia[j][i] = -1;
                this.passed[j][i] = 0;
                this.evaluation = null;
            }
        }
    }

    /**
     * @brief 检查棋盘上的位置是否可以走
     * @param playerIndex 玩家编号
     * @param posx 
     * @param posy
     * @return 0: 不能走，1: 可以走，2: 可以跳过(不保证跳到的位置没有棋子)
     */
    int checkBoard(int playerIndex, int posx, int posy) {
        // 越界检查
        if (posx < 0) {
            return 0;
        }
        if (posy < 0) {
            return 0;
        }
        if (posy >= BOARD_HGT) {
            return 0;
        }
        if (posx >= BOARD_WID) {
            return 0;
        }

        if (this.isShow[posx][posy] == 1) {
            if (this.onDia[posx][posy] == -1 && this.passed[posx][posy] == 0) {
                // 该位置没有棋子，且没有走过
                return 1;
            }
            if (this.onDia[posx][posy] != -1) {
                // posx, posy位置有棋子
                if(this.onDia[posx][posy] >= 10 && this.onDia[posx][posy] - 10 != playerIndex) {
                    // 该位置的棋子是国王，且不是当前玩家的国王，无法跳过
                    return 0;
                } else {
                    // 己方所有棋子或者敌方普通棋子，可以跳过
                    return 2;
                }
            }
            // 没有棋子，但是走过了
            return 0;
        }
        return 0;
    }

    void setOnDia(int posx, int posy, int dia) {
        this.onDia[posx][posy] = (byte)dia;
    }

    /**
     * @brief 获取棋盘上的棋子
     * @param posx
     * @param posy
     * @return 棋子的编号
     */
    byte getOnDia(int posx, int posy) {
        // 越界检查
        if (posx < 0) {
            return -1;
        }
        if (posy < 0) {
            return -1;
        }
        if (posx >= BOARD_WID) {
            return -1;
        }
        if (posy >= BOARD_HGT) {
            return -1;
        }
        return this.onDia[posx][posy];
    }

    void setPassed(int posx, int posy, int passed) {
        this.passed[posx][posy] = (byte)passed;
    }

    void clearPassed() {
        for (int i = 0; i < BOARD_HGT; ++i) {
            for (int j = 0; j < BOARD_WID; ++j) {
                this.passed[j][i] = 0;
            }
        }
    }

    /**
     * @brief 清空棋盘上的棋子
     */
    void clearOnDia() {
        for (int i = 0; i < BOARD_HGT; ++i) {
            for (int j = 0; j < BOARD_WID; ++j) {
                this.onDia[j][i] = -1;
            }
        }
    }
}
