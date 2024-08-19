/*
 * Decompiled with CFR 0.152.
 */

import io.github.creeper12356.utils.Resource;

public class TouchDo {
    public static final int EMULY = 0;
    public static final int width = Resource.totalWidth;
    public static final int height = Resource.totalHeight;
    public static final int KEY_LEFT = -3;
    public static final int KEY_RIGHT = -4;
    public static final int KEY_UP = -1;
    public static final int KEY_DOWN = -2;
    public static final int KEY_RETURN = -5;
    public static final int KEY_MENU = -7;
    public static final int KEY_CONFIRM = -6;
    public static final int KEY_CANCEL = -8;
    public static final int KEY_NUM0 = 48;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;
    public static final int KEY_STAR = 42;
    public static final int KEY_POUND = 35;
    public static final int KEY_ALL = 99;
    public static int[][] touchArray = new int[11][6];
    public static final int TOUCHW = 35;
    public static final int TOUCHH = 35;
    public static final int TOUCHW2 = 110;
    public static final int TitleView = 1;
    public static final int MenuView = 2;
    public static final int StoryView = 3;
    public static final int MiniGameView = 4;
    public static final int ContinentalView = 5;
    public static final int BoardView = 6;
    public static final int ConfigView = 7;
    public static final int HelpView = 8;
    public static final int RankView = 9;
    public static String[] strClass = new String[]{"noClass", "TileView", "MenuView", "StoryView", "MiniGameView", "ContinentalView", "BoardView", "ConfigView", "HelpView", "RankView"};
    public static int touchNum = 0;

    public TouchDo() {
        TouchDo.init();
    }

    public static void init() {
        touchNum = 0;
        for (int i = 0; i < touchArray.length; ++i) {
            TouchDo.touchArray[i][0] = -1;
            TouchDo.touchArray[i][1] = -1;
            TouchDo.touchArray[i][2] = -1;
            TouchDo.touchArray[i][3] = -1;
            TouchDo.touchArray[i][4] = 100;
            TouchDo.touchArray[i][5] = 0;
        }
    }

    public static void setTouchArea(int n, int n2, int n3) {
        TouchDo.init();
        switch (n) {
            case 1: {
                if (n2 == 1) {
                    touchNum = 2;
                    TouchDo.addTouchArea(false, 0, 1, height - 35 - 1, 35, 35, -6);
                    TouchDo.addTouchArea(false, 1, width - 35 - 1, height - 35 - 1, 35, 35, -7);
                    break;
                }
                if (n2 != 2) break;
                touchNum = 1;
                TouchDo.addTouchArea(false, 0, 1, 1, width - 2, height - 2, 99);
                break;
            }
            case 2: {
                if (n2 == 0 || n2 == 1) {
                    touchNum = 7;
                    for (int i = 0; i < touchNum; ++i) {
                        TouchDo.addTouchArea(false, i, 65, 56 + i * 35, 110, 35, i);
                    }
                    break;
                }
                if (n2 == 2) {
                    if (n3 == 0) {
                        touchNum = 3;
                        TouchDo.addTouchArea(false, 1, 65, 127, 110, 35, 49);
                        TouchDo.addTouchArea(false, 2, 65, 163, 110, 35, 50);
                    } else if (n3 == 1) {
                        touchNum = 3;
                        TouchDo.addTouchArea(false, 1, 53, 166, 35, 35, 49);
                        TouchDo.addTouchArea(false, 2, 89, 166, 35, 35, 50);
                    } else if (n3 == 2) {
                        touchNum = 3;
                        TouchDo.addTouchArea(false, 1, 53, 166, 35, 35, 49);
                        TouchDo.addTouchArea(false, 2, 89, 166, 35, 35, 50);
                    }
                    TouchDo.addTouchArea(true, 0, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                    break;
                }
                if (n2 == 3) {
                    if (n3 == 0) {
                        touchNum = 11;
                        TouchDo.addTouchArea(false, 0, 67, 61, 35, 35, 0);
                        TouchDo.addTouchArea(false, 1, 122, 61, 35, 35, 1);
                        TouchDo.addTouchArea(false, 2, 67, 114, 35, 35, 2);
                        TouchDo.addTouchArea(false, 3, 122, 114, 35, 35, 3);
                        TouchDo.addTouchArea(false, 4, 166, 114, 35, 35, 4);
                        TouchDo.addTouchArea(false, 5, 67, 167, 35, 35, 5);
                        TouchDo.addTouchArea(false, 6, 122, 167, 35, 35, 6);
                        TouchDo.addTouchArea(false, 7, 166, 167, 35, 35, 7);
                        TouchDo.addTouchArea(false, 8, 64, 221, 110, 35, 8);
                        TouchDo.addTouchArea(false, 9, 2, 283, 35, 35, 42);
                        TouchDo.addTouchArea(true, 10, 202, 283, 35, 35, -8);
                        break;
                    }
                    if (n3 != 1) break;
                    touchNum = 1;
                    TouchDo.addTouchArea(false, 0, 2, 283, 35, 35, 9);
                    break;
                }
                if (n2 == 6) {
                    if (n3 == 0) {
                        touchNum = 3;
                        TouchDo.addTouchArea(false, 1, 65, 127, 110, 35, 49);
                        TouchDo.addTouchArea(false, 2, 65, 163, 110, 35, 50);
                    } else if (n3 == 1) {
                        touchNum = 1;
                    }
                    TouchDo.addTouchArea(true, 0, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                    break;
                }
                if (n2 == 4) {
                    if (n3 != 0) break;
                    touchNum = 3;
                    TouchDo.addTouchArea(true, 0, 130, 282, 35, 35, -3);
                    TouchDo.addTouchArea(true, 1, 166, 282, 35, 35, -4);
                    TouchDo.addTouchArea(true, 2, 202, 282, 35, 35, -5);
                    break;
                }
                if (n2 != 5) break;
                touchNum = 1;
                TouchDo.addTouchArea(false, 0, 3, 282, 35, 35, -5);
                break;
            }
            case 7: {
                touchNum = 4;
                for (int i = 0; i < 3; ++i) {
                    TouchDo.addTouchArea(false, i, 65, 99 + i * 45, 110, 35, i);
                }
                TouchDo.addTouchArea(true, 3, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                break;
            }
            case 5: {
                if (n2 == -1) {
                    touchNum = 2;
                    TouchDo.addTouchArea(true, 0, 203, 282, 35, 35, -8);
                    TouchDo.addTouchArea(true, 1, 153, 282, 35, 35, -5);
                    break;
                }
                if (n2 == -2) {
                    if (n3 == 0) {
                        touchNum = 6;
                        TouchDo.addTouchArea(true, 0, 203, 282, 35, 35, -8);
                        TouchDo.addTouchArea(false, 1, 64, 83, 110, 32, 49);
                        TouchDo.addTouchArea(false, 2, 64, 116, 110, 32, 50);
                        TouchDo.addTouchArea(false, 3, 64, 149, 110, 32, 51);
                        TouchDo.addTouchArea(false, 4, 64, 182, 110, 32, 52);
                        TouchDo.addTouchArea(false, 5, 64, 215, 110, 32, 53);
                        break;
                    }
                    touchNum = 3;
                    TouchDo.addTouchArea(true, 0, 203, 282, 35, 35, -8);
                    TouchDo.addTouchArea(false, 1, 64, 111, 110, 35, 49);
                    TouchDo.addTouchArea(false, 2, 64, 147, 110, 35, 50);
                    break;
                }
                if (n3 > -1) {
                    touchNum = 7;
                } else if (n3 == -1) {
                    touchNum = 8;
                    TouchDo.addTouchArea(false, 7, 160, 243, 35, 35, 35);
                }
                TouchDo.addTouchArea(true, 0, 9, 282, 35, 35, -1);
                TouchDo.addTouchArea(true, 1, 45, 282, 35, 35, -2);
                TouchDo.addTouchArea(true, 2, 81, 282, 35, 35, -3);
                TouchDo.addTouchArea(true, 3, 117, 282, 35, 35, -4);
                TouchDo.addTouchArea(true, 4, 153, 282, 35, 35, -5);
                TouchDo.addTouchArea(true, 5, 203, 282, 35, 35, -8);
                TouchDo.addTouchArea(false, 6, 0, 243, 35, 35, 42);
                break;
            }
            case 8: {
                if (n2 == 0) {
                    touchNum = 6;
                    for (int i = 0; i < 5; ++i) {
                        TouchDo.addTouchArea(false, i, 65, 74 + i * 40, 110, 35, 49 + i);
                    }
                    TouchDo.addTouchArea(true, 5, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                    break;
                }
                if (n2 == 1 || n2 == 3 || n2 == 4) {
                    touchNum = 3;
                    TouchDo.addTouchArea(false, 0, 103, 84, 35, 35, -1);
                    TouchDo.addTouchArea(false, 1, 103, 240, 35, 35, -2);
                    TouchDo.addTouchArea(true, 2, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                    break;
                }
                if (n2 == 2) {
                    touchNum = 3;
                    TouchDo.addTouchArea(false, 0, 7, 146, 35, 35, -3);
                    TouchDo.addTouchArea(false, 1, 198, 146, 35, 35, -4);
                    TouchDo.addTouchArea(true, 2, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                    break;
                }
                if (n2 != 5) break;
                touchNum = 1;
                TouchDo.addTouchArea(true, 0, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                break;
            }
            case 9: {
                touchNum = 1;
                TouchDo.addTouchArea(true, 0, width - 35 - 3, height - 35 - 3, 35, 35, -8);
                break;
            }
            case 3: {
                if (n2 == 1) {
                    touchNum = 2;
                    TouchDo.addTouchArea(true, 0, 3, 282, 35, 35, -5);
                    TouchDo.addTouchArea(false, 1, 202, 282, 35, 35, 35);
                    break;
                }
                if (n2 == 2) {
                    if (n3 == 0) {
                        touchNum = 1;
                        TouchDo.addTouchArea(true, 0, 3, 282, 35, 35, -5);
                        break;
                    }
                    if (n3 != 1) break;
                    touchNum = 2;
                    TouchDo.addTouchArea(false, 0, 3, 168, 110, 35, 49);
                    TouchDo.addTouchArea(false, 1, 3, 204, 110, 35, 50);
                    break;
                }
                if (n2 != 3) break;
                touchNum = 1;
                TouchDo.addTouchArea(true, 0, 3, 282, 35, 35, -5);
                break;
            }
            case 6: {
                if (n3 == -1) {
                    if (n2 == 9) {
                        touchNum = 1;
                        TouchDo.addTouchArea(true, 0, 2, 282, 35, 35, -5);
                        break;
                    }
                    if (n2 == 8) {
                        touchNum = 0;
                        break;
                    }
                    if (n2 == 11 || n2 == 13 || n2 == 14) {
                        touchNum = 0;
                        break;
                    }
                    touchNum = 10;
                    TouchDo.addTouchArea(true, 0, 11, 211, 35, 35, 49);
                    TouchDo.addTouchArea(true, 1, 47, 211, 35, 35, 51);
                    TouchDo.addTouchArea(true, 2, 1, 247, 35, 35, 52);
                    TouchDo.addTouchArea(true, 3, 37, 247, 35, 35, -5);
                    TouchDo.addTouchArea(true, 4, 73, 247, 35, 35, 54);
                    TouchDo.addTouchArea(true, 5, 4, 284, 35, 35, 55);
                    TouchDo.addTouchArea(true, 6, 40, 284, 35, 35, 57);
                    TouchDo.addTouchArea(true, 7, 76, 284, 35, 35, -1);
                    TouchDo.addTouchArea(true, 8, 112, 284, 35, 35, -2);
                    TouchDo.addTouchArea(true, 9, 133, 247, 35, 35, -8);
                    break;
                }
                if (n3 == 0) {
                    touchNum = 6;
                    TouchDo.addTouchArea(false, 0, 64, 93, 110, 35, 49);
                    TouchDo.addTouchArea(false, 1, 64, 129, 110, 35, 50);
                    TouchDo.addTouchArea(false, 2, 64, 165, 110, 35, 51);
                    TouchDo.addTouchArea(false, 3, 64, 201, 110, 35, 52);
                    TouchDo.addTouchArea(false, 4, 64, 237, 110, 35, 53);
                    TouchDo.addTouchArea(true, 5, 202, 282, 35, 35, -8);
                    break;
                }
                if (n3 == 1) {
                    touchNum = 3;
                    TouchDo.addTouchArea(false, 0, 64, 134, 110, 35, 49);
                    TouchDo.addTouchArea(false, 1, 64, 174, 110, 35, 50);
                    TouchDo.addTouchArea(true, 2, 202, 282, 35, 35, -8);
                    break;
                }
                if (n3 != 2 && n3 != 3) break;
                touchNum = 3;
                TouchDo.addTouchArea(false, 0, 64, 134, 110, 35, 49);
                TouchDo.addTouchArea(false, 1, 64, 174, 110, 35, 50);
                TouchDo.addTouchArea(true, 2, 202, 282, 35, 35, -8);
                break;
            }
            case 4: {
                touchNum = 5;
                TouchDo.addTouchArea(true, 0, 31, 282, 35, 35, -1);
                TouchDo.addTouchArea(true, 1, 67, 282, 35, 35, -2);
                TouchDo.addTouchArea(true, 2, 103, 282, 35, 35, -3);
                TouchDo.addTouchArea(true, 3, 139, 282, 35, 35, -4);
                TouchDo.addTouchArea(true, 4, 175, 282, 35, 35, -5);
                break;
            }
        }
    }

    public static void addTouchArea(boolean bl, int n, int n2, int n3, int n4, int n5, int n6) {
        TouchDo.touchArray[n][0] = n2;
        TouchDo.touchArray[n][1] = n3;
        TouchDo.touchArray[n][2] = n4;
        TouchDo.touchArray[n][3] = n5;
        TouchDo.touchArray[n][4] = n6;
        TouchDo.touchArray[n][5] = bl ? 1 : 0;
    }

    public static int getTouchAction(int n, int n2) {
        int n3 = 200;
        n2 += 0;
        for (int i = 0; i < touchNum; ++i) {
            if (n < touchArray[i][0] || n > touchArray[i][0] + touchArray[i][2] || n2 < touchArray[i][1] || n2 > touchArray[i][1] + touchArray[i][3]) continue;
            n3 = touchArray[i][4];
            break;
        }
        return n3;
    }
}
