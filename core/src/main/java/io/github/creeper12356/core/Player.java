package io.github.creeper12356.core;

import io.github.creeper12356.utils.Resource;
import lombok.Data;

@Data
public class Player {
    // 玩家类型
    public static final int PLAYERTYPE_HUMAN = 1;
    public static final int PLAYERTYPE_CPU = 2;
    public static final int PLAYERTYPE_OFF = 3;

    // 玩家脸部状态
    public static final int FACE_NORMAL = 0;
    public static final int FACE_GOOD = 1;
    public static final int FACE_BAD = 2;

    DiaBoard diaBoard;
    DiaPiece[] dia; // 1个玩家有10个棋子

    int aiLevel; // AI玩家等级（1-6）
    int index; // 玩家编号
    int homePosition; // 玩家的家的角编号（共6个角，最下方为0，顺时针编号递增）
    int type; // PLAYERTYPE_HUMAN, PLAYERTYPE_CPU, PLAYERTYPE_OFF
    int point; // TODO
    int currentSel; // 当前选中的棋子编号
    int diaType; // 棋子类型
    int charID; // TODO
    int charFace; // TODO
    int rank; // TODO
    int moveSound; // 棋子移动的音效
    int[] moveGuide = new int[6];
    int possibleDirCnt; // 每一轮可以移动的方向数
    int jumpMove; // 本轮跳跃次数
    int moveCnt;
    int accuMoveCnt;
    int maxCombo;
    int accuCombo;
    MoveBuffer[] oldMoveBuffer = new MoveBuffer[20]; // 存储上一步移动的信息的缓冲区，大小为20
    int oldMoveCnt; // oldMoveBuffer缓冲区有效数据的数量
    int movingDirection;
    int[] movingList = new int[20];
    int[] bestMovingList = new int[20];
    int movingListCnt;
    int bestMovingListCnt;
    int targetH;
    int targetV;
    boolean newTarget;
    DiaPiece tempDia = new DiaPiece();

    /**
     * 
     * @param index 玩家编号
     */
    public Player(int index) {
        this.index = index;
        this.type = PLAYERTYPE_OFF;
        this.diaType = 0;
        this.dia = new DiaPiece[10];

        int i;
        for (i = 0; i < 10; ++i) {
            this.dia[i] = new DiaPiece();
        }
        for (i = 0; i < 20; ++i) {
            this.oldMoveBuffer[i] = new MoveBuffer();
        }
    }

    public void init(int type, int homePosition, int diaType, DiaBoard diaBoard) {
        this.diaBoard = diaBoard;
        this.type = type;
        this.diaType = diaType;
        this.homePosition = homePosition;

        this.point = 0;
        this.currentSel = 0;
        this.jumpMove = 0;
        this.moveCnt = 0;
        this.charFace = 0;
        this.rank = 0;
        this.accuMoveCnt = 0;
        this.accuCombo = 0;

        // 根据棋子类型设置棋子的音效
        switch (diaType) {
            case 7:
            case 11:
            case 12:
            case 18:
            case 21: {
                this.moveSound = 2;
                break;
            }
            case 0:
            case 1:
            case 2:
            case 3:
            case 4: {
                if (this.index == 0) {
                    this.moveSound = 0;
                    break;
                }
                if (this.index == 1) {
                    this.moveSound = 1;
                    break;
                }
                if (this.index != 2)
                    break;
                this.moveSound = 2;
                break;
            }
            default: {
                this.moveSound = 3;
            }
        }

        int i;
        for (i = 0; i < 10; ++i) {
            this.dia[i].rank = i == 0 ? (byte) 10 : (byte) 0; // 第一个棋子的rank为10（king），其他的为0
            this.dia[i].posx = Resource.homes[this.homePosition * 20 + i * 2 + 0];
            this.dia[i].posy = Resource.homes[this.homePosition * 20 + i * 2 + 1];
        }
        this.sortingDia();
    }

    public int getDiaRank(int n) {
        return this.dia[n].rank;
    }

    public int getDiaRank() {
        return this.dia[this.currentSel].rank;
    }

    public int getDiaPosX(int n) {
        return this.dia[n].posx;
    }

    public int getDiaPosY(int n) {
        return this.dia[n].posy;
    }

    public int getDiaPosX() {
        return this.dia[this.currentSel].posx;
    }

    public int getDiaPosY() {
        return this.dia[this.currentSel].posy;
    }

    public int getDiaPixx() {
        return this.dia[this.currentSel].pixx / 10;
    }

    public int getDiaPixy() {
        return this.dia[this.currentSel].pixy / 10;
    }

    /**
     * @brief 根据posy排序棋子(从小到大)
     */
    public void sortingDia() {
        int n;
        for (n = 0; n < 9; ++n) {
            int n2 = n;
            for (int i = n + 1; i < 10; ++i) {
                if (this.dia[n2].posy <= this.dia[i].posy)
                    continue;
                n2 = i;
            }
            DiaPiece diaPiece = this.dia[n2];
            this.dia[n2] = this.dia[n];
            this.dia[n] = diaPiece;
        }
        for (n = 0; n < 10; ++n) {
            this.diaBoard.setOnDia(this.dia[n].posx, this.dia[n].posy, this.index + this.dia[n].rank);
        }
    }

    /**
     * @brief 左/右搜索，找到最近的棋子并选中
     * @param n 1表示向左搜索，2表示向右搜索
     * @return 1表示搜索到了更近的棋子, 0表示没有搜索到
     */
    int SearchLeftRight(int n) {
        int n2 = this.dia[this.currentSel].posx * Resource.HGAB;
        int n3 = this.dia[this.currentSel].posy * Resource.VGAB;
        int minIndex = -1;
        int minDist = 9999;
        // 遍历所有棋子
        for (int i = 0; i < 10; ++i) {
            if (i == this.currentSel)
                continue;

            int n7 = this.dia[i].posx * Resource.HGAB;
            int n8 = this.dia[i].posy * Resource.VGAB;
            int dist;
            // n == 1表示向左搜索，n == 2表示向右搜索
            if (n == 1 && n7 >= n2 || // 不在左边
                    n == 2 && n7 <= n2 || // 不在右边
                    this.dia[i].posy != this.dia[this.currentSel].posy || // 不在同一行
                    minDist <= (dist = Resource.Fast_Distance(n2 - n7, n3 - n8)) // 距离比之前的大
            ) {
                continue;
            }

            minDist = dist;
            minIndex = i;
        }
        if (minIndex != -1) {
            // 找到了更近的棋子
            this.currentSel = minIndex;
            return 1;
        }
        return 0;
    }

    /**
     * @brief 查找指定方向上最近的棋子
     * @param n
     * @return
     */
    public int searchDia(int n) {
        // Utils.playSound(5, false); // play menu.mid
        if ((n == 1 || n == 2) && this.SearchLeftRight(n) == 1) {
            return -1;
        }
        int n2 = this.dia[this.currentSel].posx * Resource.HGAB;
        int n3 = this.dia[this.currentSel].posy * Resource.VGAB;
        int n4 = 9999;
        int n5 = -1;
        for (int i = 0; i < 10; ++i) {
            int n6;
            if (i == this.currentSel)
                continue;
            int n7 = this.dia[i].posx * Resource.HGAB;
            int n8 = this.dia[i].posy * Resource.VGAB;
            if (n == 3 && n8 >= n3 ||
                    n == 4 && n8 <= n3 ||
                    n == 1 && n7 >= n2 ||
                    n == 2 && n7 <= n2 ||
                    n == 6 && (n7 >= n2 || n8 >= n3) ||
                    n == 7 && (n7 >= n2 || n8 <= n3) ||
                    n == 8 && (n7 <= n2 || n8 >= n3) ||
                    n == 9 && (n7 <= n2 || n8 <= n3) ||
                    n4 < (n6 = Resource.Fast_Distance(n2 - n7, n3 - n8))) {
                continue;
            }
            n4 = n6;
            n5 = i;
        }
        if (n5 != -1) {
            this.currentSel = n5;
        }
        return 0;
    }

    void moveDia(int diaIndex, int dx, int dy) {
        this.diaBoard.setOnDia(this.dia[diaIndex].posx, this.dia[diaIndex].posy, -1);
        this.diaBoard.setPassed(this.dia[diaIndex].posx, this.dia[diaIndex].posy, 1);

        this.dia[diaIndex].posx = (byte) (this.dia[diaIndex].posx + dx);
        this.dia[diaIndex].posy = (byte) (this.dia[diaIndex].posy + dy);

        this.diaBoard.setOnDia(this.dia[diaIndex].posx, this.dia[diaIndex].posy, this.index + this.dia[diaIndex].rank);
    }

    public int computeMoveGuide() {
        return this.computeMoveGuide(this.currentSel);
    }

    /**
     * @brief 计算指定棋子可以移动的方向
     * @param diaIndex 棋子编号
     * @return 可以移动的方向数
     */
    int computeMoveGuide(int diaIndex) {
        byte curPosx = this.dia[diaIndex].posx;
        byte curPosy = this.dia[diaIndex].posy;
        if (this.moveCnt > 0 && this.jumpMove == 0) {
            // 本轮已经移动过了，且不是跳跃移动，停止移动
            this.possibleDirCnt = 0;
            for (int i = 0; i < 6; ++i) {
                this.moveGuide[i] = 0;
            }
            return 0;
        }
        int n2 = 0;
        // 在6个方向上检查是否可以移动
        for (int i = 0; i < 6; ++i) {
            this.moveGuide[i] = this.diaBoard.checkBoard(this.index, curPosx + Resource.hInc[i],
                    curPosy + Resource.vInc[i]);
            if (this.moveGuide[i] == 2) {
                this.moveGuide[i] = this.diaBoard.checkBoard(this.index, curPosx + Resource.hInc[i] * 2,
                        curPosy + Resource.vInc[i] * 2);
                this.moveGuide[i] = this.moveGuide[i] == 1 ? 2 : 0;
            }
            if (this.jumpMove > 0 && this.moveGuide[i] == 1) {
                this.moveGuide[i] = 0;
            }

            // 统计可以移动的方向数
            if (this.moveGuide[i] == 0)
                continue;
            ++n2;
        }
        this.possibleDirCnt = n2;
        return n2;
    }

    public int getMoveGuide(int n) {
        return this.moveGuide[n];
    }

    public int[] getMoveGuide() {
        int[] nArray = new int[6];
        for (int i = 0; i < 6; ++i) {
            nArray[i] = this.moveGuide[i];
        }
        return nArray;
    }

    /**
     * @brief 是否可以继续移动
     * @return
     */
    public boolean isMoreMove() {
        if (this.jumpMove > 0) {
            this.computeMoveGuide();
            boolean bl = false;
            for (int i = 0; i < 6; ++i) {
                if (this.moveGuide[i] != 2)
                    continue;
                bl = true;
            }
            return bl;
        }
        return false;
    }

    /**
     * 
     * @param n 移动的方向
     * @return TODO
     */
    public boolean initMovingDia(int n) {
        if (n == -1) {
            return false;
        }
        if (this.moveGuide[n] == 0) {
            return false;
        }
        this.movingDirection = n;
        this.dia[this.currentSel].pixx = this.dia[this.currentSel].posx * Resource.HGAB * 10;
        this.dia[this.currentSel].pixy = this.dia[this.currentSel].posy * Resource.VGAB * 10;
        int n2 = 0;
        int n3 = 0;
        n2 = Resource.hInc[n];
        n3 = Resource.vInc[n];
        if (this.moveGuide[n] == 2) {
            n2 *= 2;
            n3 *= 2;
            ++this.jumpMove;
        }
        this.moveDia(this.currentSel, n2, n3);
        ++this.moveCnt;
        // Utils.playSound(this.moveSound, false);
        return true;
    }

    void movingDia(int n) {
        int n2 = 0;
        int n3 = 0;
        n2 = Resource.hInc[this.movingDirection] * (Resource.HGAB * 10 / n);
        n3 = Resource.vInc[this.movingDirection] * (Resource.VGAB * 10 / n);
        if (this.moveGuide[this.movingDirection] == 2) {
            n2 *= 2;
            n3 *= 2;
        }
        this.dia[this.currentSel].pixx += n2;
        this.dia[this.currentSel].pixy += n3;
    }

    /**
     * @brief 计算玩家的得分
     * @return 得分，即棋子到达对面家的数量
     */
    public int calcPoint() {
        int n = (this.homePosition + 3) % 6;
        this.point = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (this.dia[i].posx != Resource.homes[n * 20 + j * 2 + 0] ||
                        this.dia[i].posy != Resource.homes[n * 20 + j * 2 + 1])
                    continue;
                ++this.point;
            }
        }
        return this.point;
    }

    /**
     * @brief 结束回合
     */
    public void endTurn() {
        this.charFace = 0;
        if (this.jumpMove > 1) {
            this.accuCombo += this.jumpMove - 1;
        }
        if (this.jumpMove > this.maxCombo) {
            this.maxCombo = this.jumpMove;
        }
        this.jumpMove = 0;
        ++this.accuMoveCnt;
        this.moveCnt = 0;
    }

    /**
     * TODO
     */
    void getTargetHome() {
        int n = 9999;
        int n2 = (this.homePosition + 3) % 6; // 对角编号
        this.targetH = Resource.homes[n2 * 20 + 0];
        this.targetV = Resource.homes[n2 * 20 + 1];
        this.newTarget = false;
        if ((this.diaBoard.getOnDia(this.targetH, this.targetV) == this.index + 10 ||
                this.diaBoard.getOnDia(this.targetH, this.targetV) == this.index + 0)
                && this.calcPoint() >= 8) {

            for (int i = 1; i < 10; ++i) {
                int dist;
                int n4 = Resource.homes[n2 * 20 + i * 2 + 0];
                int n5 = Resource.homes[n2 * 20 + i * 2 + 1];
                if (this.diaBoard.getOnDia(n4, n5) != -1 ||
                        (dist = Resource.Fast_Distance(
                                Math.abs(n4 * 16 - Resource.homes[n2 * 20 + 0] * 16),
                                Math.abs(n5 * 24 - Resource.homes[n2 * 20 + 1] * 24))) >= n)
                    continue;

                n = dist;
                this.targetH = n4;
                this.targetV = n5;
                this.newTarget = true;
            }
        }
    }

    /**
     * TODO
     * 
     * @param player
     */
    public void getComMove(Player player) {
        int n;
        int n2;
        int n3 = -99999;
        int n4 = 0;
        int n5 = -1;
        int n6 = 99999;
        for (n2 = 0; n2 < 10; ++n2) {
            if (this.computeMoveGuide(n2) == 0 || this.calcPoint() >= 9 && this.checkHome(this.dia[n2])
                    || this.calcPoint() >= 8 && this.checkHome(this.dia[n2]) && this.checkHome())
                continue;
            n = this.moveBest(n2);
            if (this.aiLevel >= 4) {
                n4 = 99999;
                for (int i = 0; i < 10; ++i) {
                    if (player.computeMoveGuide(i) == 0)
                        continue;
                    int n7 = player.moveBest(i);
                    if (n7 < n4) {
                        n4 = n7;
                    }
                    player.unmoveBest(i);
                }
                this.unmoveBest(n2);
                if (n4 > n3) {
                    n6 = n;
                    n3 = n4;
                    n5 = n2;
                    System.arraycopy(this.bestMovingList, 0, this.movingList, 0, 20);
                    this.movingListCnt = this.bestMovingListCnt;
                    continue;
                }
                if (n4 != n3 || n >= n6)
                    continue;
                n6 = n;
                n3 = n4;
                n5 = n2;
                System.arraycopy(this.bestMovingList, 0, this.movingList, 0, 20);
                this.movingListCnt = this.bestMovingListCnt;
                continue;
            }
            this.unmoveBest(n2);
            if (n >= n6)
                continue;
            n6 = n;
            n3 = n4;
            n5 = n2;
            System.arraycopy(this.bestMovingList, 0, this.movingList, 0, 20);
            this.movingListCnt = this.bestMovingListCnt;
        }
        if (n5 == -1) {
            n6 = 99999;
            for (n2 = 0; n2 < 10; ++n2) {
                if (this.computeMoveGuide(n2) == 0)
                    continue;
                n = this.moveBest(n2);
                this.unmoveBest(n2);
                if (n >= n6)
                    continue;
                n6 = n;
                n3 = n4;
                n5 = n2;
                System.arraycopy(this.bestMovingList, 0, this.movingList, 0, 20);
                this.movingListCnt = this.bestMovingListCnt;
            }
        }
        this.currentSel = n5;
    }

    /**
     * TODO
     * 
     * @param n
     * @return
     */
    int moveBest(int n) {
        int[] nArray = new int[20];
        this.getTargetHome();
        this.currentSel = n;
        this.oldMoveCnt = 0;
        this.moveCnt = 0;
        this.jumpMove = 0;
        this.tempDia.posx = this.dia[n].posx;
        this.tempDia.posy = this.dia[n].posy;
        Evaluation evaluation = this.getBestEval(nArray, 0);
        System.arraycopy(nArray, 0, this.bestMovingList, 0, 20);
        this.bestMovingListCnt = evaluation.cnt;
        this.moveCnt = 0;
        while (this.moveCnt < evaluation.cnt) {
            this.computeMoveGuide();
            this.moveDiaDir(nArray[this.moveCnt], this.getMoveGuide(nArray[this.moveCnt]));
        }
        this.diaBoard.clearPassed();
        this.moveCnt = 0;
        this.jumpMove = 0;
        nArray = null;
        return evaluation.value;
    }

    /**
     * TODO
     * 
     * @param n
     */
    void unmoveBest(int n) {
        this.currentSel = n;
        while (this.unmoveDiaDir() > 0) {
        }
        this.moveCnt = 0;
        this.jumpMove = 0;
    }

    /**
     * @brief 获取AI的搜索深度
     * @return
     */
    int getDepth() {
        if (this.aiLevel == 1) {
            return 1;
        }
        if (this.aiLevel == 2) {
            return 3;
        }
        if (this.aiLevel == 3) {
            return 19;
        }
        if (this.aiLevel == 4) {
            return 2;
        }
        if (this.aiLevel == 5) {
            return 3;
        }
        if (this.aiLevel == 6) {
            return 19;
        }
        return 19;
    }

    /**
     * @brief AI搜索最佳移动，核心函数
     * @param nArray
     * @param depth  当前搜索深度
     * @return
     */
    Evaluation getBestEval(int[] nArray, int depth) {
        Evaluation evaluation = new Evaluation();
        int[] nArray2 = new int[19 - depth];
        boolean findBetterEval = false;
        this.computeMoveGuide();
        int[] moveGuide = this.getMoveGuide();
        if (depth == this.getDepth() || this.possibleDirCnt == 0) {
            evaluation.value = this.getEvaluation();
            evaluation.cnt = depth;
            evaluation.neighbor = this.getNeighbor(this.dia[this.currentSel]);
            return evaluation;
        }

        evaluation.value = depth == 0 ? 99999 : this.getEvaluation();
        for (int i = 0; i < 6; ++i) {
            if (moveGuide[i] == 0)
                continue; // 这个方向不能移动

            this.moveDiaDir(i, moveGuide[i]);
            Evaluation evaluation2 = this.getBestEval(nArray2, depth + 1);
            this.unmoveDiaDir();
            if (evaluation2.value > evaluation.value)
                continue;
            evaluation = evaluation2;
            nArray[0] = i;
            System.arraycopy(nArray2, 0, nArray, 1, 19 - (depth + 1));
            findBetterEval = true;
        }
        if (!findBetterEval) {
            evaluation.cnt = depth;
            evaluation.neighbor = this.getNeighbor(this.dia[this.currentSel]);
            return evaluation;
        }
        return evaluation;
    }

    /**
     * @brief 评估玩家的棋子距离目标位置的总距离
     * @details 这个`getEvaluation`函数似乎是在评估玩家的棋子距离目标位置的总距离。
     *          这个函数可能被用于AI决策，以确定哪个移动会使棋子更接近目标位置，或者用于评估游戏的状态，以确定玩家的进度。
     * @return
     */
    int getEvaluation() {
        int n = 0;
        if (this.calcPoint() >= 9) {
            n = Resource.Fast_Distance(
                    Math.abs(this.targetH * 16 - this.dia[this.currentSel].posx * 16),
                    Math.abs(this.targetV * 24 - this.dia[this.currentSel].posy * 24));
            return n;
        }
        for (int i = 0; i < 10; ++i) {
            n += Resource.Fast_Distance(
                    Math.abs(this.targetH * 16 - this.dia[i].posx * 16),
                    Math.abs(this.targetV * 24 - this.dia[i].posy * 24));
        }
        return n;
    }

    boolean checkHome(DiaPiece diaPiece) {
        int n = (this.homePosition + 3) % 6;
        for (int i = 0; i < 10; ++i) {
            if (diaPiece.posx != Resource.homes[n * 20 + i * 2 + 0] ||
                    diaPiece.posy != Resource.homes[n * 20 + i * 2 + 1])
                continue;
            return true;
        }
        return false;
    }

    boolean checkHomeCurDia() {
        return this.checkHome(this.dia[this.currentSel]);
    }

    boolean checkHome() {
        int n = 0;
        int n2 = (this.homePosition + 3) % 6;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (this.dia[j].posx != Resource.homes[n2 * 20 + i * 2 + 0] ||
                        this.dia[j].posy != Resource.homes[n2 * 20 + i * 2 + 1])
                    continue;
                ++n;
            }
        }
        return n > 2;
    }

    /**
     * @brief 获取指定棋子周围的敌方棋子数量
     * @param diaPiece 棋子
     * @return
     */
    int getNeighbor(DiaPiece diaPiece) {
        int n = 0;
        for (int i = 0; i < 6; ++i) {
            byte dia = this.diaBoard.getOnDia(diaPiece.posx + Resource.hInc[i], diaPiece.posy + Resource.vInc[i]);
            if (dia != this.index + 10 && dia != this.index + 0)
                continue;
            // 统计6个方向上敌方棋子的数量
            ++n;
        }
        return n;
    }

    /**
     * @brief 撤销上一步移动
     * @return
     */
    int unmoveDiaDir() {
        if (this.oldMoveCnt == 0) {
            // 没有上一步移动
            return 0;
        }
        --this.oldMoveCnt;
        int n = this.oldMoveBuffer[this.oldMoveCnt].p;
        int n2 = this.oldMoveBuffer[this.oldMoveCnt].i;
        int n3 = this.oldMoveBuffer[this.oldMoveCnt].dir;
        int n4 = this.oldMoveBuffer[this.oldMoveCnt].dis;
        n3 = (n3 + 3) % 6;
        int n5 = Resource.hInc[n3];
        int n6 = Resource.vInc[n3];
        if (n4 == 2) {
            n5 *= 2;
            n6 *= 2;
            --this.jumpMove;
        }
        this.diaBoard.setOnDia(this.dia[n2].posx, this.dia[n2].posy, -1);
        this.dia[this.currentSel].posx = (byte) (this.dia[this.currentSel].posx + n5);
        this.dia[this.currentSel].posy = (byte) (this.dia[this.currentSel].posy + n6);
        this.diaBoard.setOnDia(this.dia[n2].posx, this.dia[n2].posy, this.index + this.dia[n2].rank);
        this.diaBoard.setPassed(this.dia[n2].posx, this.dia[n2].posy, 0);
        --this.moveCnt;
        return this.oldMoveCnt;
    }

    void moveDiaDir(int n, int n2) {
        this.moveDiaDir(this.currentSel, n, n2);
    }

    /**
     * @brief 向指定方向移动棋子
     * @param diaIndex 棋子编号
     * @param dir      移动的方向
     * @param dis      移动的距离（1或2）
     */
    void moveDiaDir(int diaIndex, int dir, int dis) {
        int dx = 0;
        int dy = 0;
        dx = Resource.hInc[dir];
        dy = Resource.vInc[dir];
        if (dis == 2) {
            dx *= 2;
            dy *= 2;
            ++this.jumpMove;
        }
        this.moveDia(diaIndex, dx, dy);
        ++this.moveCnt;
        this.oldMoveBuffer[this.oldMoveCnt].p = this.index;
        this.oldMoveBuffer[this.oldMoveCnt].i = diaIndex;
        this.oldMoveBuffer[this.oldMoveCnt].dir = dir;
        this.oldMoveBuffer[this.oldMoveCnt].dis = dis;
        ++this.oldMoveCnt;
    }
}
