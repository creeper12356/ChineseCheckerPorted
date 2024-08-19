/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

import io.github.creeper12356.utils.Resource;

public class StringMgr {
    String[] srcString;
    String[] outString;
    byte[][] cutString = new byte[128][];
    int curPoint;
    int totalLength;
    int cutLineCnt;
    int curDrawLine;
    int linePerPage;
    int totalPage;
    int curDrawPage;
    boolean bDraw = false;
    boolean bEndPage = false;
    boolean bEndString = false;
    boolean bAutoMode = false;
    boolean bEndAutoMode = false;
    int lineWidth;
    Animation aniAutoPage = new Animation();
    Animation aniAutoEnd = new Animation();

    StringMgr() {
    }

    StringMgr(String string, int n, int n2) {
        this.lineWidth = n;
        this.srcString = Resource.getNewStrArr(string, n * Resource.sf.getHeight() / 3);
        this.outString = new String[n2];
        this.cutLineCnt = this.srcString.length;
        this.totalPage = this.cutLineCnt / n2 + (this.cutLineCnt % n2 == 0 ? 0 : 1);
        this.linePerPage = n2;
        for (int i = 0; i < this.cutLineCnt; ++i) {
        }
    }

    void start() {
        this.curDrawLine = 0;
        this.curDrawPage = 0;
        this.curPoint = 0;
        this.bDraw = true;
        for (int i = 0; i < this.linePerPage; ++i) {
            try {
                if (i >= this.cutLineCnt) continue;
                this.outString[i] = this.srcString[i];
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    void setAutoMode() {
        this.aniAutoPage.init(1, 300, false);
        this.bAutoMode = true;
        this.bEndAutoMode = false;
    }

    boolean getEndAutoMode() {
        return this.bEndAutoMode;
    }

    void clear() {
        this.outString = null;
        this.cutString = null;
        this.bDraw = false;
    }

    String getString(int n) {
        if (n >= this.cutLineCnt) {
            return new String("");
        }
        return this.getNewStr(this.cutString[n]);
    }

    int keyReturn() {
        if (!this.bDraw) {
            return 2;
        }
        if (this.bEndString) {
            this.clear();
            return 0;
        }
        if (this.bEndPage) {
            this.nextPage();
            return 1;
        }
        this.flushPage();
        return 1;
    }

    void flushPage() {
        this.curDrawLine = this.linePerPage;
        this.bEndPage = true;
        if (this.curDrawPage + 1 == this.totalPage) {
            this.bEndString = true;
        }
    }

    void nextPage() {
        if (this.curDrawPage + 1 > this.totalPage) {
            return;
        }
        this.curPoint = 0;
        this.curDrawLine = 0;
        ++this.curDrawPage;
        this.bEndPage = false;
        for (int i = 0; i < this.linePerPage; ++i) {
            if (i + this.curDrawPage * this.linePerPage >= this.cutLineCnt) continue;
            this.outString[i] = this.srcString[i + this.curDrawPage * this.linePerPage];
        }
    }

    void drawTalk(Graphics graphics, int n, int n2, int n3, int n4, int n5) {
        int n6 = 0;
        if (!this.bAutoMode) {
            n6 = 9;
        }
        Resource.drawTalkBalloon(graphics, n, n2 - (this.linePerPage * 15 + 6) - 10, this.lineWidth * 7 + 6 + n6, this.linePerPage * 15 + 6, n3, n4);
        graphics.setColor(n5);
        graphics.setFont(Resource.sf);
        this.drawTalk(graphics, n + 3, n2 - 10 - (this.linePerPage * 15 + 6) + 3);
    }

    void drawTalk(Graphics graphics, int n, int n2) {
        int n3 = 0;
        if (!this.bDraw) {
            return;
        }
        for (int i = 0; i < this.linePerPage; ++i) {
            int n4;
            block11: {
                block9: {
                    block10: {
                        n3 = 0;
                        n4 = i;
                        if (n4 + this.curDrawPage * this.linePerPage >= this.cutLineCnt) break block9;
                        if (i >= this.curDrawLine) break block10;
                        n3 = this.outString[n4].length();
                        break block11;
                    }
                    if (i != this.curDrawLine) break;
                    ++this.curPoint;
                    n3 = this.curPoint;
                    if (this.curPoint != this.outString[n4].length()) break block11;
                    this.curPoint = 0;
                    ++this.curDrawLine;
                    if (this.curDrawLine != this.linePerPage && this.curDrawLine + this.curDrawPage * this.linePerPage != this.cutLineCnt) break block11;
                    this.bEndPage = true;
                    if (this.curDrawPage + 1 != this.totalPage) break block11;
                    this.bEndString = true;
                    if (!this.bAutoMode) break block11;
                    this.aniAutoEnd.init(2, 500, false);
                    break block11;
                }
                if (this.bEndPage && !this.bAutoMode) {
                    Resource.drawTalkButton(graphics, n + Resource.sf.stringWidth(this.outString[n4 - 1]) + 1, n2 + (i - 1) * 15 + Resource.sf.getHeight() - 1);
                }
                if (!this.bEndPage || !this.bAutoMode || this.aniAutoPage.frameProcess() != 0) break;
                if (!this.bEndString) {
                    this.nextPage();
                    break;
                }
                if (this.aniAutoEnd.getFrame() == 1) {
                    this.bEndAutoMode = true;
                }
                this.aniAutoEnd.frameProcess();
                break;
            }
            graphics.drawSubstring(this.outString[n4], 0, n3, n, n2 + i * 15, 4 | 0x10);
            if (this.bEndPage && i == this.linePerPage - 1 && this.bAutoMode && this.aniAutoPage.frameProcess() == 0) {
                if (!this.bEndString) {
                    this.nextPage();
                } else {
                    if (this.aniAutoEnd.getFrame() == 1) {
                        this.bEndAutoMode = true;
                    }
                    this.aniAutoEnd.frameProcess();
                }
            }
            if (!this.bEndPage || i != this.linePerPage - 1 || this.bAutoMode) continue;
            Resource.drawTalkButton(graphics, n + Resource.sf.stringWidth(this.outString[n4]) + 1, n2 + i * 15 + Resource.sf.getHeight() - 1);
        }
    }

    public String getNewStr(byte[] byArray) {
        try {
            return new String(byArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return " ";
        }
    }

    public void testEncode() {
        String[] stringArray = new String[]{"UTF-8", "GBK", "ISO-8859-1", "Unicode", "UTF-16", "GB2312", "UnicodeBig", "Big5"};
        String[] stringArray2 = new String[stringArray.length];
        String string = "\u4e2d\u6587\u54c8\u54c8abc";
        byte[] byArray = null;
        for (int i = 0; i < stringArray.length; ++i) {
            try {
                byArray = string.getBytes(stringArray[i]);
                stringArray2[i] = stringArray[i] + " Yes";
                continue;
            }
            catch (Exception exception) {
                stringArray2[i] = stringArray[i] + " N/A";
            }
        }
    }
}
