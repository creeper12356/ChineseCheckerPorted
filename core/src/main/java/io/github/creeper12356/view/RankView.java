/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class RankView
implements Viewable,
XTimerListener {
    static final int RANK_LOCAL = 1;
    static final int RANK_TOP5 = 2;
    static final int RANK_MYRANK = 3;
    static final int RANK_CONNECT = 4;
    static final int PROCESS_INFO = 1;
    static final int PROCESS_CONNECT = 2;
    static final int PROCESS_ERROR = 3;
    int state;
    int process;
    int cx;
    int cy;
    public static Rank[] localrank = new Rank[5];
    public static Rank[] top5rank = new Rank[5];
    public static Rank[] myrank = new Rank[5];
    public static Rank[] curRank = null;
    NetThread rank;
    Image[] imgKeyHelp = new Image[8];
    Image[] imgButton = new Image[2];
    Image[] imgRankChip = new Image[4];
    Image imgPattern;
    Animation aniListScroll;
    public static int userrank;

    RankView() {
        for (int i = 0; i < 5; ++i) {
            RankView.localrank[i] = new Rank();
            RankView.top5rank[i] = new Rank();
            RankView.myrank[i] = new Rank();
        }
    }

    public void performed(XTimer xTimer) {
        Resource.aMainView.repaint();
    }

    public void paint(Graphics graphics) {
        TouchDo.setTouchArea(9, 0, 0);
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 0);
        if (this.state == 1) {
            this.drawLocalRank(graphics);
        } else if (this.state == 2) {
            this.drawNetRank(graphics);
        } else if (this.state == 3) {
            this.drawNetRank(graphics);
        } else if (this.state == 4) {
            this.drawConnect(graphics);
        }
    }

    public void keyPressed(int n) {
        switch (this.state) {
            case 1: {
                this.keyLocal(n);
                break;
            }
            case 4: {
                this.keyConnect(n);
                break;
            }
            case 2: 
            case 3: {
                this.keyNetRank(n);
            }
        }
    }

    public void keyReleased(int n) {
    }

    void keyLocal(int n) {
        switch (n) {
            case -8: 
            case -7: 
            case -6: 
            case -5: 
            case 48: {
                Resource.aMainView.setStatus(0, 1);
            }
        }
    }

    void keyConnect(int n) {
        if (this.process == 1) {
            if (n == 49) {
                Thread thread = new Thread(this.rank);
                thread.start();
                this.rank.Connect();
                this.process = 2;
            } else if (n == 50) {
                this.state = 1;
                this.aniListScroll.init(6, 100, false);
            }
        }
    }

    void keyNetRank(int n) {
        if (n == 35) {
            if (this.state == 2) {
                curRank = myrank;
                this.state = 3;
                this.aniListScroll.init(6, 100, false);
            } else if (this.state == 3) {
                curRank = top5rank;
                this.state = 2;
                this.aniListScroll.init(6, 100, false);
            }
        } else if (n == -8 || n == -5 || n == -6 || n == 48) {
            Resource.aMainView.setStatus(0, 1);
        }
    }

    public void init() {
        Resource.aTimer2.setTimerListener(this);
        Resource.aTimer2.setTime(100L);
        Resource.aTimer2.resume();
        this.state = 1;
        this.cx = Resource.halfWidth;
        this.cy = Resource.halfHeight;
        try {
            this.imgKeyHelp[0] = Image.createImage((String)"/rankchip_7.png");
            this.imgKeyHelp[1] = Image.createImage((String)"/rankchip_19.png");
            this.imgKeyHelp[2] = Image.createImage((String)"/rankchip_9.png");
            this.imgKeyHelp[3] = Image.createImage((String)"/rankchip_10.png");
            this.imgKeyHelp[4] = Image.createImage((String)"/rankchip_11.png");
            this.imgKeyHelp[5] = Image.createImage((String)"/rankchip_12.png");
            this.imgKeyHelp[6] = Image.createImage((String)"/rankchip_13.png");
            this.imgKeyHelp[7] = Image.createImage((String)"/rankchip_14.png");
            this.imgRankChip[0] = Image.createImage((String)"/rankchip_15.png");
            this.imgRankChip[1] = Image.createImage((String)"/rankchip_16.png");
            this.imgRankChip[2] = Image.createImage((String)"/rankchip_17.png");
            this.imgRankChip[3] = Image.createImage((String)"/rankchip_18.png");
            this.imgPattern = Image.createImage((String)"/pattern_4.png");
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.rank = new NetThread();
        this.aniListScroll = new Animation();
        this.aniListScroll.init(6, 100, false);
    }

    public void clear() {
        int n;
        for (n = 0; n < 8; ++n) {
            this.imgKeyHelp[n] = null;
        }
        for (n = 0; n < 4; ++n) {
            this.imgRankChip[n] = null;
        }
        this.imgPattern = null;
    }

    public void setStatus(int n) {
    }

    public int getStatus() {
        return 0;
    }

    public static void newRecord() {
        int n = Resource.pointMgr.getPoint();
        for (int i = 0; i < 5; ++i) {
            if (n < RankView.localrank[i].point) continue;
            for (int j = 3; j >= i; --j) {
                RankView.localrank[j + 1].point = RankView.localrank[j].point;
            }
            RankView.localrank[i].point = n;
            break;
        }
    }

    public static void receiveRank(byte[] byArray) {
        int n;
        byte[] byArray2 = new byte[7];
        if (byArray == null) {
            return;
        }
        for (n = 0; n < 5; ++n) {
            RankView.top5rank[n].rank = Utils.getInt(byArray, 0 + n * 15);
            RankView.top5rank[n].point = Utils.getInt(byArray, 4 + n * 15);
            System.arraycopy(byArray, 8 + n * 15, byArray2, 0, 7);
            RankView.top5rank[n].tel = RankView.top5rank[n].point == -1 ? null : new String(byArray2, 3, 4);
        }
        userrank = -1;
        for (n = 0; n < 5; ++n) {
            RankView.myrank[n].rank = Utils.getInt(byArray, 75 + n * 15);
            RankView.myrank[n].point = Utils.getInt(byArray, 79 + n * 15);
            System.arraycopy(byArray, 83 + n * 15, byArray2, 0, 7);
            if (RankView.myrank[n].point == -1) {
                RankView.myrank[n].tel = null;
                continue;
            }
            RankView.myrank[n].tel = new String(byArray2, 3, 4);
            String string = new String(byArray2, 0, 3);
            if (string.compareTo("999") != 0) continue;
            userrank = n;
        }
        curRank = top5rank;
    }

    void drawLocalRank(Graphics graphics) {
        graphics.setColor(531282);
        int n = Resource.isQVGA() ? 110 : 67;
        graphics.drawImage(this.imgRankChip[3], this.cx - this.imgRankChip[3].getWidth() / 2, this.cy - n, 4 | 0x10);
        if (Resource.isQVGA()) {
            graphics.drawImage(this.imgKeyHelp[0], this.cx - 98, this.cy - 68, 4 | 0x10);
            graphics.drawImage(this.imgKeyHelp[1], this.cx + 48, this.cy - 68, 4 | 0x10);
            graphics.setFont(Font.getFont((int)0, (int)1, (int)16));
            for (int i = 0; i < this.aniListScroll.getFrame(); ++i) {
                graphics.drawString("" + RankView.localrank[i].rank, this.cx - 78, this.cy - 48 + i * 30, 1 | 0x10);
                graphics.drawString("" + RankView.localrank[i].point, this.cx + 68, this.cy - 48 + i * 30, 1 | 0x10);
            }
        } else {
            graphics.drawImage(this.imgKeyHelp[0], this.cx - 49, this.cy - 50, 4 | 0x10);
            graphics.drawImage(this.imgKeyHelp[1], this.cx + 24, this.cy - 50, 4 | 0x10);
            for (int i = 0; i < this.aniListScroll.getFrame(); ++i) {
                graphics.drawString("" + RankView.localrank[i].rank, this.cx - 39, this.cy - 39 + i * 20, 1 | 0x10);
                graphics.drawString("" + RankView.localrank[i].point, this.cx + 42, this.cy - 39 + i * 20, 8 | 0x10);
            }
        }
        this.aniListScroll.frameProcess();
    }

    void drawNetRank(Graphics graphics) {
        int n = this.state == 3 ? userrank : -1;
        graphics.setColor(531282);
        if (Resource.isQVGA()) {
            graphics.drawImage(this.imgRankChip[0], this.cx - this.imgRankChip[0].getWidth() / 2, this.cy - 110, 4 | 0x10);
            if (this.state == 2) {
                graphics.drawImage(this.imgRankChip[1], this.cx - this.imgRankChip[0].getWidth() / 2 + 3, this.cy - 110 + 8, 4 | 0x10);
            } else if (this.state == 3) {
                graphics.drawImage(this.imgRankChip[2], this.cx - this.imgRankChip[0].getWidth() / 2 + 3, this.cy - 110 + 6, 4 | 0x10);
            }
        } else {
            graphics.drawImage(this.imgRankChip[0], this.cx - this.imgRankChip[0].getWidth() / 2, this.cy - 70, 4 | 0x10);
            if (this.state == 2) {
                graphics.drawImage(this.imgRankChip[1], this.cx - this.imgRankChip[0].getWidth() / 2 + 3, this.cy - 70 + 4, 4 | 0x10);
            } else if (this.state == 3) {
                graphics.drawImage(this.imgRankChip[2], this.cx - this.imgRankChip[0].getWidth() / 2 + 3, this.cy - 70 + 3, 4 | 0x10);
            }
        }
        if (Resource.isQVGA()) {
            graphics.drawImage(this.imgKeyHelp[0], this.cx - 110, this.cy - 68, 4 | 0x10);
            graphics.drawImage(this.imgKeyHelp[1], this.cx + 6, this.cy - 68, 4 | 0x10);
            graphics.drawImage(this.imgKeyHelp[3], this.cx + 64, this.cy - 68, 4 | 0x10);
            graphics.setFont(Font.getFont((int)0, (int)0, (int)16));
            for (int i = 0; i < this.aniListScroll.getFrame(); ++i) {
                if (n != -1 && n == i) {
                    graphics.setColor(24, 79, 138);
                }
                graphics.drawString("" + RankView.curRank[i].rank, this.cx - 88, this.cy - 48 + i * 30, 1 | 0x10);
                if (RankView.curRank[i].point == -1) {
                    graphics.drawString("---", this.cx + 26, this.cy - 48 + i * 30, 1 | 0x10);
                } else {
                    graphics.drawString("" + RankView.curRank[i].point, this.cx + 40, this.cy - 48 + i * 30, 8 | 0x10);
                }
                if (RankView.curRank[i].tel == null) {
                    graphics.drawString("---", this.cx + 84, this.cy - 48 + i * 30, 1 | 0x10);
                } else {
                    graphics.drawString("~" + RankView.curRank[i].tel, this.cx + 84, this.cy - 48 + i * 30, 1 | 0x10);
                }
                if (n == -1 || n != i) continue;
                graphics.setColor(138, 79, 24);
            }
        } else {
            graphics.drawImage(this.imgKeyHelp[0], this.cx - 55, this.cy - 50, 4 | 0x10);
            graphics.drawImage(this.imgKeyHelp[1], this.cx + 3, this.cy - 50, 4 | 0x10);
            graphics.drawImage(this.imgKeyHelp[3], this.cx + 32, this.cy - 50, 4 | 0x10);
            for (int i = 0; i < this.aniListScroll.getFrame(); ++i) {
                if (n != -1 && n == i) {
                    graphics.setColor(24, 79, 138);
                }
                graphics.drawString("" + RankView.curRank[i].rank, this.cx - 44, this.cy - 39 + i * 20, 1 | 0x10);
                if (RankView.curRank[i].point == -1) {
                    graphics.drawString("---", this.cx + 13, this.cy - 39 + i * 20, 1 | 0x10);
                } else {
                    graphics.drawString("" + RankView.curRank[i].point, this.cx + 19, this.cy - 39 + i * 20, 8 | 0x10);
                }
                if (RankView.curRank[i].tel == null) {
                    graphics.drawString("---", this.cx + 42, this.cy - 39 + i * 20, 1 | 0x10);
                } else {
                    graphics.drawString("~" + RankView.curRank[i].tel, this.cx + 42, this.cy - 39 + i * 20, 1 | 0x10);
                }
                if (n == -1 || n != i) continue;
                graphics.setColor(138, 79, 24);
            }
        }
        if (this.state == 2) {
            graphics.drawImage(this.imgKeyHelp[5], Resource.totalWidth - this.imgKeyHelp[5].getWidth(), Resource.totalHeight - 1 - this.imgKeyHelp[5].getHeight(), 4 | 0x10);
        } else if (this.state == 3) {
            graphics.drawImage(this.imgKeyHelp[6], Resource.totalWidth - this.imgKeyHelp[6].getWidth(), Resource.totalHeight - 1 - this.imgKeyHelp[6].getHeight(), 4 | 0x10);
        }
        this.aniListScroll.frameProcess();
    }

    void drawConnect(Graphics graphics) {
        switch (this.process) {
            case 1: {
                graphics.drawString("\u73d0\u6b27\u9611 \u6bbf\u5e9f\u94a6\u806a\u4fc3", this.cx, this.cy - 30, 1 | 0x10);
                graphics.drawString("\u5bb6\u6a0a\u72fc \u70f9\u62f3\u4e30\u554a \u4f55", this.cx, this.cy - 30 + 15, 1 | 0x10);
                graphics.drawString("\u82de\u9093\u806a\u4fc3. \u62cc\u52a0 \u67f3", this.cx, this.cy - 30 + 30, 1 | 0x10);
                graphics.drawString("\u9752 \u4e14\u9cd6\u5938?", this.cx, this.cy - 30 + 45, 1 | 0x10);
                graphics.drawString("1.YES  2.NO", this.cx, this.cy - 30 + 60, 1 | 0x10);
                break;
            }
            case 2: {
                if (this.rank.state == 1) {
                    graphics.drawString("\u6977\u642c\u541d", this.cx, this.cy, 1 | 0x10);
                } else if (this.rank.state == 3) {
                    graphics.drawString("\u8350\u811a\u541d", this.cx, this.cy, 1 | 0x10);
                } else if (this.rank.state == 2) {
                    graphics.drawString("\u4ef7\u811a\u541d", this.cx, this.cy, 1 | 0x10);
                } else if (this.rank.state == 5) {
                    graphics.drawString("\u5777\u5e45!!", this.cx, this.cy, 1 | 0x10);
                }
                if (this.rank.complete == 1) {
                    this.state = 2;
                    this.aniListScroll.init(6, 100, false);
                    break;
                }
                if (this.rank.complete != 2) break;
                this.rank.Stop();
                this.rank = null;
                this.process = 1;
                break;
            }
            case 3: {
                this.rank.Stop();
                this.rank = null;
                this.process = 1;
            }
        }
    }
}
