package io.github.creeper12356.core;

import io.github.creeper12356.utils.Resource;

public class WJPlayer extends Player {

    public WJDiaPiece[] wjDia = new WJDiaPiece[10];

    public WJPlayer(int index) {
        super(index);
        for (int i = 0; i < 10; ++i) {
            wjDia[i] = new WJDiaPiece();
            dia[i] = wjDia[i];
        }
    }

    public void init(int type, int homePosition, DiaBoard diaBoard) {
        this.diaBoard = diaBoard;
        this.type = type;
        this.homePosition = homePosition;

        this.point = 0;
        this.currentSel = 0;
        this.jumpMove = 0;
        this.moveCnt = 0;
        this.charFace = 0;
        this.rank = 0;
        this.accuMoveCnt = 0;
        this.accuCombo = 0;

        for (int i = 0; i < 10; ++i) {
            this.dia[i].posx = Resource.homes[this.homePosition * 20 + i * 2 + 0];
            this.dia[i].posy = Resource.homes[this.homePosition * 20 + i * 2 + 1];
        }

        if(index == 0) {
            wjDia[0].type = WJDiaPiece.TYPE_TANG;
            wjDia[1].type = WJDiaPiece.TYPE_HORSE;
            wjDia[2].type = WJDiaPiece.TYPE_HORSE;
            wjDia[3].type = WJDiaPiece.TYPE_SHA;
            wjDia[4].type = WJDiaPiece.TYPE_SHA;
            wjDia[5].type = WJDiaPiece.TYPE_SHA;
            wjDia[6].type = WJDiaPiece.TYPE_PIG;
            wjDia[7].type = WJDiaPiece.TYPE_PIG;
            wjDia[8].type = WJDiaPiece.TYPE_MONKEY;
            wjDia[9].type = WJDiaPiece.TYPE_MONKEY;
        } else {
            wjDia[0].type = WJDiaPiece.TYPE_DEMON_KING;
            for(int i = 1;i < 10; ++i) {
                wjDia[i].type = WJDiaPiece.TYPE_DEMON;
            }
        }
        this.sortingDia();
    }

    public WJDiaPiece[] getDia() {
        return wjDia;
    }

    @Override
    public int getDiaPosX() {
        return wjDia[currentSel].posx;
    }

    @Override
    public int getDiaPosY() {
        return wjDia[currentSel].posy;
    }

    @Override
    public int getDiaPosX(int n) {
        return wjDia[n].posx;
    }

    @Override
    public int getDiaPosY(int n) {
        return wjDia[n].posy;
    }

    @Override
    public void sortingDia() {
        // for (int n = 0; n < 9; ++n) {
        // int n2 = n;
        // for (int i = n + 1; i < 10; ++i) {
        // if (this.dia[n2].posy <= this.dia[i].posy)
        // continue;
        // n2 = i;
        // }
        // DiaPiece diaPiece = this.dia[n2];
        // this.dia[n2] = this.dia[n];
        // this.dia[n] = diaPiece;
        // }
        for (int n = 0; n < 10; ++n) {
            this.diaBoard.setOnDia(this.dia[n].posx, this.dia[n].posy, this.index);
        }
    }
}
