package io.github.creeper12356.core;

public class WJDiaPiece extends DiaPiece {
    // 棋子类型
    public final static int TYPE_TANG = 0;
    public final static int TYPE_MONKEY = 1;
    public final static int TYPE_PIG = 2;
    public final static int TYPE_SHA = 3;
    public final static int TYPE_HORSE = 4;

    public final static int TYPE_DEMON = 5;
    public final static int TYPE_DEMON_KING = 6;
    
    int type;
    public WJDiaPiece() {
        super();
    }

    public int getType() {
        return type;
    }
    
}
