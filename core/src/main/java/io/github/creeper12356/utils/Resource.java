package io.github.creeper12356.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
// import javax.microedition.lcdui.Font;
// import javax.microedition.lcdui.Graphics;
// import javax.microedition.lcdui.Image;
// import javax.microedition.rms.InvalidRecordIDException;
// import javax.microedition.rms.RecordStore;
// import javax.microedition.rms.RecordStoreException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import io.github.creeper12356.core.Player;

// import io.github.creeper12356.core.Player;

// import io.github.creeper12356.core.Player;

public class Resource {
    // 数字按键的方向
    public static final int DIR_LEFT = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_UP = 3;
    public static final int DIR_DOWN = 4;
    public static final int DIR_CENTER = 5;
    public static final int DIR_LU = 6;
    public static final int DIR_LD = 7;
    public static final int DIR_RU = 8;
    public static final int DIR_RD = 9;

    public static final int MAX_PLAYERCNT = 3;
    public static final int MAX_MOVINGFRAME = 5;

    public static final int GAMEMODE_STORY = 1; // 故事模式
    public static final int GAMEMODE_VS = 2; // 对战模式

    public static final int LCD_QVGA = 1;
    public static final int LCD_QCIF = 2;
    public static final int LCD_QQVGA = 3;
    public static final int LCD_WQVGA = 4;
    public static int lcdSize;
    public static int HGAB;
    public static int VGAB;
    // public static MainView aMainView;
    public static Player[] players;
    public static int gameMode;
    public static int playerCnt;
    public static int stageNum;
    public static int accuMove;
    public static int accuCombo;
    public static byte[] stageClear;
    public static int totalWidth;
    public static int totalHeight;
    public static int halfWidth;
    public static int halfHeight;
    public static int boardPosX;
    public static int boardPosY;
    public static int boardZeroPosX;
    public static int boardZeroPosY;
    public static int gameSpeed;
    public static int soundVolume;
    public static int vibSwitch;
    public static byte[] enableDiaList;
    public static boolean bOpenSpecialGame;
    // public static Font sf;
    public static final byte[] homes;
    public static final byte[] targetHome;
    public static final int[] hInc;
    public static final int[] vInc;
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


    public static Texture imgBackBuffer;

    public static Texture[] imgDiaAvt; // 棋子头像图片
    public static Texture[] imgSmallNum;
    public static Texture[] imgBigNum;
    public static Texture[] imgArrow;
    public static Texture[] imgBallonChip;
    public static Texture[] imgPlayer;
    public static Texture[] imgEnemy;
    public static Texture[] imgPanelEdge;
    public static Texture[] imgButton;
    public static Random rand;

    public static float aniMoveDuration;
    public static float aniJumpDuration;
    public static float aniDelayDuration;
    public static int pointMgr;

    public static String version;
    public static String language;
    // public static Animation aniTalkButton;

    public static FreeTypeFontGenerator generator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public static boolean isQVGA() {
        return lcdSize == 1 || lcdSize == 4;
    }

    // public static Graphics getBackBuffer() {
    // return imgBackBuffer.getGraphics();
    // }

    public static int getRand(int n) {
        return Math.abs(rand.nextInt() % n);
    }

    /**
     * @brief 加载assets目录下的图片，需要手动释放
     * @param filename
     * @return
     */
    public static Texture loadImage(String filename) {
        return new Texture(Gdx.files.internal(filename));
    }

    /**
     * @brief 释放oldTexture，加载新的图片
     * @param oldTexture
     * @param filename
     * @return
     */
    public static Texture replaceTexture(Texture oldTexture, String filename) {
        if (oldTexture != null) {
            oldTexture.dispose();
        }
        return loadImage(filename);
    }

    public static int Fast_Distance(int n, int n2) {
        n = Math.abs(n);
        n2 = Math.abs(n2);
        int min = Math.min(n, n2);
        return n + n2 - (min >> 1) - (min >> 2) + (min >> 4);
    }

    public static void drawImageAtCenter(Texture img, SpriteBatch batch) {
        batch.draw(img, 240 / 2 - img.getWidth() / 2,
                Resource.halfHeight - img.getHeight() / 2);
    }

    public static void drawImageAtBottom(Texture img, SpriteBatch batch) {
        batch.draw(img, 240 / 2 - img.getWidth() / 2, 0);
    }

    public static void drawStringAtCenter(String str, BitmapFont font, SpriteBatch batch) {
        final GlyphLayout layout = new GlyphLayout(font, str);
        final float fontX = Resource.halfWidth - layout.width / 2;
        final float fontY = Resource.halfHeight + layout.height / 2;
        font.draw(batch, str, fontX, fontY);
    }

    public static void drawStringAtBottomLeft(String str, BitmapFont font, SpriteBatch batch) {
        final GlyphLayout layout = new GlyphLayout(font, str);
        final float fontX = 0;
        final float fontY = layout.height;
        font.draw(batch, str, fontX, fontY);
    }

    public static void drawStringAtBottomRight(String str, BitmapFont font, SpriteBatch batch) {
        final GlyphLayout layout = new GlyphLayout(font, str);
        final float fontX = 240 - layout.width;
        final float fontY = layout.height;
        font.draw(batch, str, fontX, fontY);
    }

    // static void drawTalkBalloon(Graphics graphics, int n, int n2, int n3, int n4,
    // int n5, int n6) {
    // int n7 = 0;
    // graphics.setColor(255, 255, 255);
    // graphics.setFont(sf);
    // graphics.fillRoundRect(n, n2, n3, n4, 10, 10);
    // graphics.setColor(0, 0, 0);
    // graphics.drawRoundRect(n, n2, n3 - 1, n4 - 1, 10, 10);
    // if (n6 == 0) {
    // switch (n5) {
    // case 1: {
    // n7 = 15;
    // break;
    // }
    // case 2: {
    // n7 = n3 - 15 - imgBallonChip[0].getWidth();
    // break;
    // }
    // case 5: {
    // n7 = n3 / 2 - imgBallonChip[0].getWidth();
    // }
    // }
    // graphics.drawImage(imgBallonChip[0], n + n7, n2 + n4 - 1, 4 | 0x10);
    // } else if (n6 == 1) {
    // int n8 = 0;
    // switch (n5) {
    // case 1: {
    // n7 = 0;
    // n8 = 1;
    // break;
    // }
    // case 2: {
    // n7 = n3 - imgBallonChip[2].getWidth();
    // n8 = 2;
    // }
    // }
    // graphics.drawImage(imgBallonChip[n8], n + n7, n2 + n4 - 1, 4 | 0x10);
    // } else if (n6 == 2) {
    // // empty if block
    // }
    // }

    // static void drawTalkButton(Graphics graphics, int n, int n2) {
    // int n3 = aniTalkButton.getFrame();
    // graphics.drawImage(imgBallonChip[3 + n3], n, n2 - imgBallonChip[3 +
    // n3].getHeight(), 4 | 0x10);
    // aniTalkButton.frameProcess();
    // }

    // static void drawPanel(Graphics graphics, int n, int n2, int n3, int n4) {
    // graphics.setColor(255, 255, 255);
    // graphics.drawRect(n, n2, n3 - 1, n4 - 1);
    // graphics.setColor(3483768);
    // graphics.drawRect(n + 1, n2 + 1, n3 - 3, n4 - 3);
    // graphics.setColor(6115991);
    // graphics.drawRect(n + 2, n2 + 2, n3 - 5, n4 - 5);
    // graphics.setColor(7695525);
    // graphics.fillRect(n + 3, n2 + 3, n3 - 6, n4 - 6);
    // graphics.drawImage(imgPanelEdge[0], n, n2, 4 | 0x10);
    // graphics.drawImage(imgPanelEdge[1], n + n3 - imgPanelEdge[1].getWidth(), n2,
    // 4 | 0x10);
    // graphics.drawImage(imgPanelEdge[2], n, n2 + n4 - imgPanelEdge[2].getHeight(),
    // 4 | 0x10);
    // graphics.drawImage(imgPanelEdge[3], n + n3 - imgPanelEdge[3].getWidth(), n2 +
    // n4 - imgPanelEdge[3].getHeight(),
    // 4 | 0x10);
    // }

    // static void drawInCastle(Graphics graphics) {
    // int n;
    // int n2;
    // int n3;
    // graphics.setColor(0, 0, 0);
    // graphics.fillRect(0, 0, totalWidth, totalHeight);
    // Image image = Resource.loadImage("/bgchip_4.png");
    // int n4 = Resource.isQVGA() ? halfHeight - 204 : halfHeight - 102;
    // for (n3 = 0; n3 < totalWidth; n3 += image.getWidth()) {
    // graphics.drawImage(image, n3, n4, 4 | 0x10);
    // }
    // int n5 = Resource.isQVGA() ? 92 : 46;
    // graphics.setColor(213, 65, 0);
    // graphics.fillRect(0, n4 += image.getHeight(), totalWidth, n5);
    // image = Resource.loadImage("/bgchip_10.png");
    // int n6 = halfWidth - image.getWidth() * 3 / 2;
    // int n7 = n6 + image.getWidth() * 3;
    // for (n3 = n6; n3 < n7; n3 += image.getWidth()) {
    // graphics.drawImage(image, n3, n4, 4 | 0x10);
    // }
    // image = Resource.loadImage("/bgchip_8.png");
    // for (n3 = n7; n3 < totalWidth; n3 += image.getWidth()) {
    // graphics.drawImage(image, n3, n4, 4 | 0x10);
    // graphics.drawImage(image, halfWidth - (n3 - halfWidth) - image.getWidth(),
    // n4, 4 | 0x10);
    // }
    // n6 = image.getHeight();
    // image = Resource.loadImage("/bgchip_9.png");
    // graphics.drawImage(image, halfWidth - image.getWidth() / 2, n4 + n6 / 2, 4 |
    // 0x10);
    // n4 += n6;
    // image = Resource.loadImage("/bgchip_7.png");
    // for (n3 = 0; n3 < totalWidth; n3 += image.getWidth()) {
    // graphics.drawImage(image, n3, n4, 4 | 0x10);
    // graphics.drawImage(image, n3, n4 + image.getHeight(), 4 | 0x10);
    // graphics.drawImage(image, n3, n4 + image.getHeight() * 2, 4 | 0x10);
    // }
    // n3 = Resource.isQVGA() ? halfWidth - 72 : halfWidth - 36;
    // graphics.setColor(213, 65, 0);
    // if (Resource.isQVGA()) {
    // graphics.fillRect(n3, n4, 144, image.getHeight() * 3);
    // } else {
    // graphics.fillRect(n3, n4, 72, image.getHeight() * 3);
    // }
    // int n8 = Resource.isQVGA() ? 138 : 69;
    // image = Resource.loadImage("/bgchip_6.png");
    // n6 = image.getHeight();
    // graphics.drawImage(image, n3, n4, 4 | 0x10);
    // graphics.drawImage(image, n3, n4 + n6 * 1, 4 | 0x10);
    // graphics.drawImage(image, n3, n4 + n6 * 2, 4 | 0x10);
    // graphics.drawImage(image, n3 + n8, n4, 4 | 0x10);
    // graphics.drawImage(image, n3 + n8, n4 + n6 * 1, 4 | 0x10);
    // graphics.drawImage(image, n3 + n8, n4 + n6 * 2, 4 | 0x10);
    // if (Resource.isQVGA()) {
    // n8 = 6;
    // n2 = 10;
    // n = 132;
    // n5 = 2;
    // } else {
    // n8 = 3;
    // n2 = 5;
    // n = 66;
    // n5 = 1;
    // }
    // graphics.setColor(156, 41, 0);
    // graphics.fillRect(n3 + n8, n4 + n2, 66, 1);
    // graphics.fillRect(n3 + n8, n4 + n6 * 1 + n2, n, n5);
    // graphics.fillRect(n3 + n8, n4 + n6 * 2 + n2, n, n5);
    // n5 = Resource.isQVGA() ? 10 : 5;
    // graphics.setColor(123, 8, 24);
    // graphics.fillRect(n3 + n8, n4 + n2, n, n5);
    // graphics.fillRect(n3 + n8, n4 + n6 * 1 + n2, n, n5);
    // graphics.fillRect(n3 + n8, n4 + n6 * 2 + n2, n, n5);
    // n4 += n6 * 3;
    // n6 = n4;
    // image = Resource.loadImage("/bgchip_5.png");
    // for (n4 = n6; n4 < totalHeight; n4 += image.getHeight()) {
    // for (n3 = 0; n3 < totalWidth; n3 += image.getWidth()) {
    // graphics.drawImage(image, n3, n4, 4 | 0x10);
    // }
    // }
    // if (Resource.isQVGA()) {
    // n8 = 120;
    // n2 = 42;
    // } else {
    // n8 = 60;
    // n2 = 21;
    // }
    // image = Resource.loadImage("/bgchip_12.png");
    // graphics.drawImage(image, halfWidth - n8, halfHeight + n2 -
    // image.getHeight(), 4 | 0x10);
    // graphics.drawImage(image, halfWidth - n8, halfHeight + n2 - image.getHeight()
    // + 31, 4 | 0x10);
    // graphics.drawImage(image, halfWidth - n8, halfHeight + n2 - image.getHeight()
    // + 62, 4 | 0x10);
    // graphics.drawImage(image, halfWidth + n8 - image.getWidth(), halfHeight + n2
    // - image.getHeight(), 4 | 0x10);
    // graphics.drawImage(image, halfWidth + n8 - image.getWidth(), halfHeight + n2
    // - image.getHeight() + 31,
    // 4 | 0x10);
    // graphics.drawImage(image, halfWidth + n8 - image.getWidth(), halfHeight + n2
    // - image.getHeight() + 62,
    // 4 | 0x10);
    // image = null;
    // }

    static int square(int n, int n2) {
        int n3 = n;
        if (n2 <= 0) {
            return 0;
        }
        for (int i = 0; i < n2 - 1; ++i) {
            n3 *= n;
        }
        return n3;
    }

    // static void drawBigNum(Graphics graphics, int n, int n2, int n3, int n4) {
    // Resource.drawNum(graphics, n, n2, n3, n4, imgBigNum);
    // }

    // static void drawSmallNum(Graphics graphics, int n, int n2, int n3, int n4) {
    // Resource.drawNum(graphics, n, n2, n3, n4, imgSmallNum);
    // }

    // static void drawNum(Graphics graphics, int n, int n2, int n3, int n4, Image[]
    // imageArray) {
    // block11: {
    // int n5;
    // int n6;
    // int n7;
    // int n8;
    // block12: {
    // block10: {
    // if (n3 < 0) {
    // return;
    // }
    // n8 = 0;
    // n7 = n3;
    // n6 = 1;
    // n5 = imageArray[0].getWidth();
    // if (n4 != 1)
    // break block10;
    // while (n7 >= 10) {
    // n7 /= 10;
    // ++n6;
    // }
    // n8 = n;
    // for (int i = n6; i > 0; --i) {
    // int n9 = Resource.square(10, i - 1);
    // if (n9 == 0) {
    // n9 = 1;
    // }
    // graphics.drawImage(imageArray[n3 / n9], n8, n2, 4 | 0x10);
    // n3 %= n9;
    // n8 += n5 + 1;
    // }
    // break block11;
    // }
    // if (n4 != 2)
    // break block12;
    // while (n7 >= 10) {
    // n7 /= 10;
    // ++n6;
    // }
    // n8 = n - (n5 + 1) * n6;
    // for (int i = n6; i > 0; --i) {
    // int n10 = Resource.square(10, i - 1);
    // if (n10 == 0) {
    // n10 = 1;
    // }
    // graphics.drawImage(imageArray[n3 / n10], n8, n2, 4 | 0x10);
    // n3 %= n10;
    // n8 += n5 + 1;
    // }
    // break block11;
    // }
    // if (n4 != 5)
    // break block11;
    // while (n7 >= 10) {
    // n7 /= 10;
    // ++n6;
    // }
    // n8 = n - ((n5 + 2) * n6 - 2) / 2;
    // for (int i = n6; i > 0; --i) {
    // int n11 = Resource.square(10, i - 1);
    // if (n11 == 0) {
    // n11 = 1;
    // }
    // graphics.drawImage(imageArray[n3 / n11], n8, n2, 4 | 0x10);
    // n3 %= n11;
    // n8 += n5 + 1;
    // }
    // }
    // }

    // public static String[] getNewStrArr(String string, int n) {
    // int n2;
    // StringBuffer stringBuffer = new StringBuffer(string);
    // StringBuffer[] stringBufferArray = new
    // StringBuffer[sf.stringWidth(string.toString()) / n + 5];
    // int n3 = 0;
    // stringBufferArray[0] = new StringBuffer();
    // int n4 = string.length();
    // for (n2 = 0; n2 < n4; ++n2) {
    // char c = stringBuffer.charAt(n2);
    // stringBufferArray[n3].append(c);
    // if (sf.stringWidth(stringBufferArray[n3].toString()) <= n || n2 >= n4 - 1)
    // continue;
    // stringBufferArray[++n3] = new StringBuffer();
    // }
    // String[] stringArray = new String[n3 + 1];
    // for (n2 = 0; n2 < n3 + 1; ++n2) {
    // stringArray[n2] = stringBufferArray[n2].toString();
    // }
    // stringBuffer = null;
    // stringBufferArray = null;
    // return stringArray;
    // }

    public static void newGame() {
        stageNum = 0;
        pointMgr = 0;
        accuMove = 0;
        accuCombo = 0;
        Resource.stageClear[0] = 0;
        Resource.stageClear[1] = 0;
        Resource.stageClear[2] = 0;
        Resource.stageClear[3] = 0;
        Resource.stageClear[4] = 0;
        Resource.stageClear[5] = 0;
    }

    public static void saveGame() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeInt(stageNum);
            dataOutputStream.writeInt(pointMgr);
            for (int i = 0; i < 6; ++i) {
                dataOutputStream.writeByte(stageClear[i]);
            }
            for (int i = 0; i < 25; ++i) {
                dataOutputStream.writeByte(enableDiaList[i]);
            }
            // dataOutputStream.writeBoolean(bOpenSpecialGame);
            // dataOutputStream.writeBoolean(Continental.newGame);
            // dataOutputStream.writeBoolean(MiniGameView.bNewGame1);
            // dataOutputStream.writeBoolean(MiniGameView.bNewGame2);
            Resource.saveData(System.getProperty("user.home") + "/.chinese.checker.dat",
                    byteArrayOutputStream.toByteArray());
            dataOutputStream.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void loadGame() {
        try {
            byte[] byArray = Resource.loadData(System.getProperty("user.home") + "/.chinese.checker.dat");
            if (byArray == null) {
                Resource.newGame();
                Resource.saveGame();
                return;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            stageNum = dataInputStream.readInt();
            pointMgr = dataInputStream.readInt();
            for (int i = 0; i < 6; ++i) {
                Resource.stageClear[i] = dataInputStream.readByte();
            }
            for (int i = 0; i < 25; ++i) {
                Resource.enableDiaList[i] = dataInputStream.readByte();
            }
            // bOpenSpecialGame = dataInputStream.readBoolean();
            // Continental.newGame = dataInputStream.readBoolean();
            // MiniGameView.bNewGame1 = dataInputStream.readBoolean();
            // MiniGameView.bNewGame2 = dataInputStream.readBoolean();
            dataInputStream.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
            Resource.newGame();
            Resource.saveGame();
        }
    }

    // public static void saveRms(String string, byte[] byArray, int n) {
    // try {
    // RecordStore recordStore = RecordStore.openRecordStore((String) string,
    // (boolean) true);
    // if (recordStore.getNumRecords() == 0) {
    // recordStore.addRecord(byArray, 0, byArray.length);
    // }
    // try {
    // recordStore.setRecord(n, byArray, 0, byArray.length);
    // } catch (InvalidRecordIDException invalidRecordIDException) {
    // recordStore.closeRecordStore();
    // }
    // recordStore.closeRecordStore();
    // } catch (RecordStoreException recordStoreException) {
    // return;
    // }
    // }

    public static void saveData(String filename, byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] loadData(String filename) {
        try {
            return Gdx.files.internal(filename).readBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // public static byte[] loadRms(String string, int n) {
    // byte[] byArray = null;
    // try {
    // RecordStore recordStore = RecordStore.openRecordStore((String) string,
    // (boolean) true);
    // if (recordStore.getNumRecords() == 0) {
    // return null;
    // }
    // byArray = recordStore.getRecord(n);
    // recordStore.closeRecordStore();
    // } catch (RecordStoreException recordStoreException) {
    // recordStoreException.printStackTrace();
    // return null;
    // }
    // return byArray;
    // }

    // public static void saveConfig() {
    // try {
    // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    // DataOutputStream dataOutputStream = new
    // DataOutputStream(byteArrayOutputStream);
    // dataOutputStream.writeInt(gameSpeed);
    // dataOutputStream.writeInt(soundVolume);
    // dataOutputStream.writeInt(vibSwitch);
    // dataOutputStream.close();
    // Resource.saveRms("conf", byteArrayOutputStream.toByteArray(), 1);
    // byteArrayOutputStream.close();
    // byteArrayOutputStream = null;
    // dataOutputStream = null;
    // } catch (IOException iOException) {
    // iOException.printStackTrace();
    // }
    // }

    // public static void loadConfig() {
    // try {
    // byte[] byArray = Resource.loadRms("conf", 1);
    // if (byArray == null) {
    // gameSpeed = 100;
    // soundVolume = 1;
    // vibSwitch = 1;
    // Resource.saveConfig();
    // return;
    // }
    // ByteArrayInputStream byteArrayInputStream = new
    // ByteArrayInputStream(byArray);
    // DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
    // gameSpeed = dataInputStream.readInt();
    // soundVolume = dataInputStream.readInt();
    // vibSwitch = dataInputStream.readInt();
    // dataInputStream.close();
    // byteArrayInputStream.close();
    // } catch (IOException iOException) {
    // gameSpeed = 100;
    // soundVolume = 1;
    // vibSwitch = 1;
    // Resource.saveConfig();
    // }
    // }

    // public static void saveRank() {
    // try {
    // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    // DataOutputStream dataOutputStream = new
    // DataOutputStream(byteArrayOutputStream);
    // for (int i = 0; i < 5; ++i) {
    // dataOutputStream.writeInt(RankView.localrank[i].point);
    // }
    // dataOutputStream.writeInt(Continental.bestTime);
    // dataOutputStream.close();
    // Resource.saveRms("rank", byteArrayOutputStream.toByteArray(), 1);
    // byteArrayOutputStream.close();
    // } catch (IOException iOException) {
    // iOException.printStackTrace();
    // }
    // }

    // public static void loadRank() {
    // try {
    // byte[] byArray = Resource.loadRms("rank", 1);
    // if (byArray == null) {
    // for (int i = 0; i < 5; ++i) {
    // RankView.localrank[i].rank = i + 1;
    // RankView.localrank[i].point = 0;
    // Continental.bestTime = 999;
    // }
    // Resource.saveRank();
    // return;
    // }
    // ByteArrayInputStream byteArrayInputStream = new
    // ByteArrayInputStream(byArray);
    // DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
    // for (int i = 0; i < 5; ++i) {
    // RankView.localrank[i].rank = i + 1;
    // RankView.localrank[i].point = dataInputStream.readInt();
    // }
    // Continental.bestTime = dataInputStream.readInt();
    // dataInputStream.close();
    // byteArrayInputStream.close();
    // } catch (IOException iOException) {
    // iOException.printStackTrace();
    // for (int i = 0; i < 5; ++i) {
    // RankView.localrank[i].rank = i + 1;
    // RankView.localrank[i].point = 0;
    // Continental.bestTime = 999;
    // }
    // Resource.saveRank();
    // }
    // }

    static {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/chinese.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "是否开启声音否故事模式对战模式游戏设置帮助特别菜单排行榜退出";

        HGAB = 11;
        VGAB = 16;
        totalWidth = 240;
        totalHeight = 320;
        halfHeight = 160;
        halfWidth = 120;

        players = new Player[3];
        for (int i = 0; i < 3; ++i) {
            players[i] = new Player(i);
        }
        stageClear = new byte[6];
        gameSpeed = 100;
        soundVolume = 20;
        vibSwitch = 1;
        
        enableDiaList = new byte[25];
        for (int i = 0; i < 5; ++i) {
            enableDiaList[i] = 1;
        }
        for (int i = 5; i < 25; ++i) {
            enableDiaList[i] = 0;
        }
        // sf = Font.getFont((int) 0, (int) 0, (int) 8);
        homes = new byte[] { 9, 12, 8, 11, 10, 11, 7, 10, 9, 10, 11, 10, 6, 9, 8, 9,
                10, 9, 12, 9, 0, 9, 1, 8, 2, 9, 2,
                7, 3, 8, 4, 9, 3, 6, 4, 7, 5, 8, 6, 9, 0, 3, 2, 3, 1, 4, 4, 3, 3, 4, 2, 5, 6,
                3, 5, 4, 4, 5, 3, 6, 9, 0,
                10, 1, 8, 1, 11, 2, 9, 2, 7, 2, 12, 3, 10, 3, 8, 3, 6, 3, 18, 3, 17, 4, 16,
                3, 16, 5, 15, 4, 14, 3, 15,
                6, 14, 5, 13, 4, 12, 3, 18, 9, 16, 9, 17, 8, 14, 9, 15, 8, 16, 7, 12, 9, 13,
                8, 14, 7, 15, 6 };
        targetHome = new byte[] { 9, 0, 18, 3, 18, 9, 9, 12, 0, 9, 0, 3 };
        hInc = new int[] { -1, 1, 2, 1, -1, -2 };
        vInc = new int[] { -1, -1, 0, 1, 1, 0 };
        imgDiaAvt = new Texture[25];
        imgSmallNum = new Texture[10];
        imgBigNum = new Texture[10];
        imgArrow = new Texture[4];
        imgBallonChip = new Texture[5];
        imgPlayer = new Texture[3];
        imgEnemy = new Texture[3];
        imgPanelEdge = new Texture[4];
        imgButton = new Texture[2];
        rand = new Random(System.currentTimeMillis());

        aniMoveDuration = 0.3f;
        aniJumpDuration = 0.15f;
        aniDelayDuration = 0.1f;
        // pointMgr = new PointMgr();
        // aniTalkButton = new Animation();
        
        version = "v1.0.0";
        language = "en";

        // 启动游戏时加载
        Resource.loadGame();
    }

    public static void dispose() {
        generator.dispose();

        disposeImageArray(imgDiaAvt);
        disposeImageArray(imgSmallNum);
        disposeImageArray(imgBigNum);
        disposeImageArray(imgArrow);
        disposeImageArray(imgBallonChip);
        disposeImageArray(imgPlayer);
        disposeImageArray(imgEnemy);
        disposeImageArray(imgPanelEdge);
        disposeImageArray(imgButton);
    }

    private static void disposeImage(Texture img) {
        if (img != null) {
            img.dispose();
        }
    }

    private static void disposeImageArray(Texture[] imgArray) {
        for (Texture img : imgArray) {
            disposeImage(img);
        }
    }
}
