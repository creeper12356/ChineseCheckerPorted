/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class HelpView
implements Viewable {
    final int HELP_MAIN;
    final int HELP_INTRO;
    final int HELP_KEYINFO;
    final int HELP_MODE;
    final int HELP_TIP;
    final int HELP_SUPPORT;
    final int HELP_GIFT;
    final int HELP_COAST;
    final int CutStrWidth;
    String[] strHelp;
    String helpIntro = "\u672c\u6e38\u620f\u4e2d\u68cb\u5b50\u79f0\u9a6c\uff0c\u4e14\u6709\u591a\u4e2a\u9a6c\u79cd\uff0c\u6bcf\u79cd\u9a6c\u90fd\u6709\u9a6c\u738b\uff08\u5e26\u7687\u51a0\u7684\uff09\uff1b\u6e38\u620f\u4e2d\u73a9\u5bb6\u9700\u8981\u5c06\u81ea\u5df1\u8425\u533a\u5185\u6240\u6709\u7684\u9a6c\u79fb\u52a8\u5230\u5bf9\u5bb6\u8425\u533a\u5185,\u6bcf\u4e2a\u73a9\u5bb6\u8f6e\u6d41\u79fb\u52a8\u3002\u6240\u6709\u7684\u9a6c\u6bcf\u6b21\u90fd\u53ea\u80fd\u79fb\u52a8\u4e00\u6b65\uff1b\u5982\u679c\u76f8\u90bb\u6709\u5176\u4ed6\u7684\u9a6c\uff0c\u5219\u53ef\u4ee5\u8df3\u8fc7\u8be5\u9a6c\uff1b\u8df3\u8fc7\u4e4b\u540e\u4ecd\u6709\u5176\u4ed6\u76f8\u90bb\u9a6c\uff0c\u5219\u53ef\u4ee5\u7ee7\u7eed\u8df3\uff1b\u5982\u679c\u76f8\u90bb\u7684\u662f\u9a6c\u738b\uff0c\u540c\u4e00\u9a6c\u79cd\u53ef\u4ee5\u8df3\u8fc7\uff0c\u4e0d\u540c\u9a6c\u79cd\u5219\u65e0\u6cd5\u8df3\uff1b\u5982\u679c\u4f60\u7684\u9a6c\u88ab\u5176\u4ed6\u73a9\u5bb6\u7684\u9a6c\u6240\u5305\u56f4,\u4e14\u65e0\u6cd5\u8df3\u6216\u79fb\u52a8,\u4f60\u53ea\u80fd\u5728\u6e38\u620f\u83dc\u5355\u4e2d\u9009\u62e9\u653e\u5f03\u6e38\u620f\uff01";
    String[] helpKey = new String[]{"\u65b9\u5411\u952e\u6216\u6570\u5b57\u952e\uff081\u30012\u30013\u30014\u30016\u30017\u30018\u30019\uff09\u79fb\u52a8\u7126\u70b9\uff0c\u6309OK\u952e\u6216\u6570\u5b57\u952e5\u9009\u5b9a.", "\u9009\u5b9a\u9a6c\u4e4b\u540e,\u4f1a\u81ea\u52a8\u663e\u793a\u4ee3\u8868\u53ef\u79fb\u52a8\u4f4d\u7f6e\u7684\u6570\u5b57,\u6309\u76f8\u5e94\u6570\u5b57\u6765\u79fb\u52a8\u4f60\u7684\u9a6c.", "\u5373\u4f7f\u5728\u4f60\u9009\u5b9a\u67d0\u4e2a\u9a6c\u540e,\u4ecd\u53ef\u6309\u53d6\u6d88\u952e/0\u952e\u6765\u9009\u62e9\u53e6\u4e00\u4e2a\u9a6c;\u4f46\u5982\u679c\u5df2\u7ecf\u79fb\u52a8,\u5219\u65e0\u6cd5\u518d\u8fd4\u56de.", "\u6e38\u620f\u4e2d\u6309\u53f3\u8f6f\u952e\u8fdb\u5165\u6e38\u620f\u83dc\u5355\uff1b\u83dc\u5355\u4e2d\u53f3\u8f6f\u952e\u548c0\u952e\u4e3a\u8fd4\u56de\u952e\uff0c\u65b9\u5411\u952e\u4e0a\u4e0b\u5de6\u53f3\u4e0e\u6570\u5b572846\u952e\u5bf9\u5e94."};
    String helpMode = "\u5728\u6545\u4e8b\u6a21\u5f0f\u4e2d,\u4f60\u5fc5\u987b\u8d62\u5f97\u6bd4\u8d5b\u6765\u5b9e\u73b0\u4f60\u7684\u613f\u671b.\u5e76\u4e14\u5b8c\u6210\u6240\u6709\u7684\u6bd4\u8d5b.\u5728\u6545\u4e8b\u6a21\u5f0f\u4e2d,\u4f60\u5fc5\u987b\u6253\u8d25\u6700\u540e\u7684\u5927\u738b,\u7136\u540e\u4f60\u7684\u613f\u671b\u4fbf\u53ef\u5b9e\u73b0.\u6545\u4e8b\u6a21\u5f0f\u7531\u6b64\u7ed3\u675f.\u5728\u5bf9\u6218\u6a21\u5f0f\u4e2d,\u4f60\u53ef\u4ee5\u4e0e\u7535\u8111AI\u6216\u8005\u5176\u4ed6\u5bf9\u624b\u5bf9\u6297.\u6700\u591a\u4e09\u4e2a\u73a9\u5bb6.\u5728\u5bf9\u6218\u6a21\u5f0f\u4e2d,\u4f60\u53ef\u4ee5\u9009\u62e92\u4eba\u6216\u80053\u4eba\u5bf9\u6218,\u4f60\u53ef\u4ee5\u9009\u62e9\u771f\u5b9e\u7684\u73a9\u5bb6\u6216\u8005\u7535\u8111AI\u5f53\u4f60\u7684\u5bf9\u624b.";
    String helpTip = "\u5728\u6545\u4e8b\u6a21\u5f0f\u4e2d,\u7b2c\u4e00\u6b21\u8d62\u5f97\u6bd4\u8d5b\u540e\u53ef\u5f00\u542f\u9690\u85cf\u7684\u6e38\u620f\uff1b\u800c\u4e14\u6bcf\u6b21\u8d62\u5f97\u6700\u540e\u7684\u6bd4\u8d5b\u540e\u80fd\u591f\u83b7\u5f97\u4e00\u4e9b\u9690\u85cf\u7684\u9a6c\uff0c\u4f46\u4f60\u5fc5\u987b\u5b8c\u6210\u6545\u4e8b\u6a21\u5f0f\u6240\u9700\u7684\u6761\u4ef6,\u624d\u80fd\u83b7\u5f97\u8fd9\u4e9b\u7279\u6b8a\u7684\u9a6c.";
    StringMgr strmgr;
    Image imgSelIcon;
    Image imgPattern;
    int state;
    int sel;
    int page;
    int fontHeight;
    int fontWidth;

    public HelpView() {
        this.HELP_MAIN = 0;
        this.HELP_INTRO = 1;
        this.HELP_KEYINFO = 2;
        this.HELP_MODE = 3;
        this.HELP_TIP = 4;
        this.HELP_SUPPORT = 5;
        this.HELP_GIFT = 6;
        this.HELP_COAST = 7;
        this.CutStrWidth = 120;
    }

    public void paint(Graphics graphics) {
        int n = Resource.halfWidth;
        int n2 = Resource.halfHeight;
        int n3 = Resource.isQVGA() ? this.fontHeight + 1 : this.fontHeight + 1;
        int n4 = Resource.isQVGA() ? 60 : 60;
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 0);
        Resource.drawPanel(graphics, Resource.halfWidth - 109, Resource.halfHeight - 140, 218, 280);
        switch (this.state) {
            case 0: {
                if (Resource.isQVGA()) {
                    graphics.setFont(Font.getFont((int)0, (int)0, (int)16));
                }
                TouchDo.setTouchArea(8, 0, 0);
                n3 = 40;
                graphics.setColor(0xFFFFFF);
                graphics.drawString("\u6e38\u620f\u5e2e\u52a9", n, (n2 -= 40) - 100 + 20, 1 | 0x10);
                graphics.drawString("1.\u6e38\u620f\u4ecb\u7ecd", 32 + this.imgSelIcon.getWidth() + 2, n2 - 38 + n3 * 0, 4 | 0x10);
                graphics.drawString("2.\u6309\u952e\u8bf4\u660e", 32 + this.imgSelIcon.getWidth() + 2, n2 - 38 + n3 * 1, 4 | 0x10);
                graphics.drawString("3.\u6e38\u620f\u6a21\u5f0f", 32 + this.imgSelIcon.getWidth() + 2, n2 - 38 + n3 * 2, 4 | 0x10);
                graphics.drawString("4.\u6e38\u620f\u63d0\u793a", 32 + this.imgSelIcon.getWidth() + 2, n2 - 38 + n3 * 3, 4 | 0x10);
                graphics.drawString("5.\u5173\u4e8e\u6e38\u620f", 32 + this.imgSelIcon.getWidth() + 2, n2 - 38 + n3 * 4, 4 | 0x10);
                graphics.setFont(Font.getDefaultFont());
                graphics.drawImage(this.imgSelIcon, 32, n2 - 38 + this.sel * n3, 4 | 0x10);
                break;
            }
            case 1: 
            case 3: 
            case 4: {
                graphics.setColor(0xFFFFFF);
                if (Resource.isQVGA()) {
                    graphics.setFont(Font.getFont((int)0, (int)0, (int)16));
                }
                if (this.state == 1) {
                    graphics.drawString("\u6e38\u620f\u4ecb\u7ecd", n, n2 - 100, 1 | 0x10);
                } else if (this.state == 3) {
                    graphics.drawString("\u6e38\u620f\u6a21\u5f0f", n, n2 - 100, 1 | 0x10);
                } else if (this.state == 4) {
                    graphics.drawString("\u6e38\u620f\u63d0\u793a", n, n2 - 100, 1 | 0x10);
                }
                graphics.setFont(Resource.sf);
                n = Resource.halfWidth - Resource.sf.stringWidth(this.strHelp[0]) / 2;
                TouchDo.setTouchArea(8, this.state, 0);
                for (int i = 0; i < 6; ++i) {
                    graphics.drawString(this.strHelp[this.sel + i], n, n2 - 28 - 18 + i * n3, 4 | 0x10);
                }
                if (this.sel != 0) {
                    graphics.drawImage(Resource.imgArrow[2], Resource.halfWidth, n2 - 30 - 18, 1 | 0x20);
                }
                if (this.sel + 6 >= this.strHelp.length) break;
                graphics.drawImage(Resource.imgArrow[3], Resource.halfWidth, n2 - 28 - 18 + 6 * n3, 1 | 0x10);
                break;
            }
            case 2: {
                int n5;
                graphics.setColor(0xFFFFFF);
                if (Resource.isQVGA()) {
                    graphics.setFont(Font.getFont((int)0, (int)0, (int)16));
                }
                if (this.page == 0) {
                    graphics.drawString("1.\u600e\u6837\u9009\u62e9\u9a6c", n, n2 - 100, 1 | 0x10);
                } else if (this.page == 1) {
                    graphics.drawString("2.\u600e\u6837\u79fb\u52a8\u9a6c", n, n2 - 100, 1 | 0x10);
                } else if (this.page == 2) {
                    graphics.drawString("3.\u9009\u9a6c\u540e\u5982\u4f55\u53d6\u6d88", n, n2 - 100, 1 | 0x10);
                } else if (this.page == 3) {
                    graphics.drawString("4.\u5176\u4ed6\u6309\u952e\u4fe1\u606f", n, n2 - 100, 1 | 0x10);
                }
                n = Resource.halfWidth - Resource.sf.stringWidth(this.strHelp[0]) / 2;
                graphics.setFont(Resource.sf);
                for (n5 = 0; n5 < this.strHelp.length; ++n5) {
                    graphics.drawString(this.strHelp[n5], n, n2 - 28 + n5 * n3, 4 | 0x10);
                }
                n5 = Resource.isQVGA() ? 122 : 92;
                if (this.page != 0) {
                    graphics.drawImage(Resource.imgArrow[0], 20, n2, 4 | 0x10);
                }
                if (this.page != 3) {
                    graphics.drawImage(Resource.imgArrow[1], Resource.totalWidth - 22, n2, 8 | 0x10);
                }
                TouchDo.setTouchArea(8, this.state, 0);
                break;
            }
            case 5: {
                if (Resource.isQVGA()) {
                    graphics.setFont(Font.getFont((int)0, (int)0, (int)0));
                }
                graphics.setColor(0xFFFFFF);
                graphics.drawString("Mobilebus", n, n2 - 48 + n3 * 0, 1 | 0x10);
                graphics.drawString(" ", n, n2 - 48 + n3 * 1, 1 | 0x10);
                graphics.drawString("support@mobilebus.co.kr", n, n2 - 48 + n3 * 2, 1 | 0x10);
                TouchDo.setTouchArea(8, this.state, 0);
                break;
            }
            case 7: {
                if (Resource.isQVGA()) {
                    graphics.setFont(Font.getFont((int)0, (int)0, (int)16));
                }
                graphics.setColor(0xFFFFFF);
            }
        }
    }

    public void keyPressed(int n) {
        if (this.state == 0) {
            int n2 = -1;
            if (n == 49) {
                n2 = 0;
            }
            if (n == 50) {
                n2 = 1;
            }
            if (n == 51) {
                n2 = 2;
            }
            if (n == 52) {
                n2 = 3;
            }
            if (n == 53) {
                n2 = 4;
            }
            if (n == 54) {
                n2 = 5;
            }
            if (n2 != -1) {
                Utils.playSound(17, false);
                this.goMenu(n2);
                return;
            }
            if (n == 55) {
                return;
            }
            if (n == 56) {
                return;
            }
            if (n == 57) {
                return;
            }
        }
        switch (n) {
            case -1: 
            case 50: {
                if (this.state == 0) {
                    this.sel = this.sel == 0 ? 4 : this.sel - 1;
                } else if (this.state == 1 || this.state == 3 || this.state == 4) {
                    --this.sel;
                    if (this.sel < 0) {
                        this.sel = 0;
                    }
                }
                Utils.playSound(5, false);
                break;
            }
            case -2: 
            case 56: {
                if (this.state == 0) {
                    this.sel = this.sel == 4 ? 0 : this.sel + 1;
                } else if (this.state == 1 || this.state == 3 || this.state == 4) {
                    ++this.sel;
                    if (this.sel + 6 > this.strHelp.length) {
                        this.sel = this.strHelp.length - 6;
                    }
                }
                Utils.playSound(5, false);
                break;
            }
            case -3: 
            case 52: {
                if (this.state == 2 && this.page > 0) {
                    --this.page;
                    this.strHelp = Resource.getNewStrArr(this.helpKey[this.page], 120);
                }
                Utils.playSound(5, false);
                break;
            }
            case -4: 
            case 54: {
                if (this.state == 2 && this.page < 3) {
                    ++this.page;
                    this.strHelp = Resource.getNewStrArr(this.helpKey[this.page], 120);
                }
                Utils.playSound(5, false);
                break;
            }
            case -6: 
            case -5: 
            case 53: {
                Utils.playSound(17, false);
                this.goMenu(this.sel);
                break;
            }
            case -8: 
            case -7: 
            case 48: {
                Utils.playSound(17, false);
                if (this.state == 0) {
                    Resource.aMainView.closePopup();
                    break;
                }
                if (this.strmgr != null) {
                    this.strmgr.clear();
                    this.strmgr = null;
                }
                if (this.state == 1) {
                    this.sel = 0;
                } else if (this.state == 2) {
                    this.sel = 1;
                } else if (this.state == 3) {
                    this.sel = 2;
                } else if (this.state == 4) {
                    this.sel = 3;
                } else if (this.state == 5) {
                    this.sel = 4;
                }
                this.state = 0;
            }
        }
    }

    public void keyReleased(int n) {
    }

    public void init() {
        this.sel = 0;
        this.state = 0;
        this.imgSelIcon = Resource.loadImage("/selicon_0.png");
        this.imgPattern = Resource.loadImage("/pattern_4.png");
        if (Resource.isQVGA()) {
            this.fontHeight = Font.getFont((int)0, (int)0, (int)16).getHeight();
            this.fontWidth = Font.getFont((int)0, (int)0, (int)16).stringWidth("\u8304\u4e34\u4fca\u9152\u919b\u81c2\u78ca\u514d\u4eff");
        } else {
            this.fontHeight = Font.getDefaultFont().getHeight();
            this.fontWidth = Font.getDefaultFont().stringWidth("\u8304\u4e34\u4fca\u9152\u919b\u81c2\u78ca\u514d\u4eff");
        }
    }

    public void clear() {
        this.imgSelIcon = null;
        this.imgPattern = null;
    }

    public void setStatus(int n) {
        this.state = n;
    }

    public int getStatus() {
        return this.state;
    }

    void goMenu(int n) {
        switch (this.state) {
            case 0: {
                if (n == 0) {
                    this.state = 1;
                    this.strHelp = Resource.getNewStrArr(this.helpIntro, 120);
                    this.sel = 0;
                    break;
                }
                if (n == 1) {
                    this.state = 2;
                    this.page = 0;
                    this.strHelp = Resource.getNewStrArr(this.helpKey[this.page], 120);
                    break;
                }
                if (n == 2) {
                    this.state = 3;
                    this.strHelp = Resource.getNewStrArr(this.helpMode, 120);
                    this.sel = 0;
                    break;
                }
                if (n == 3) {
                    this.state = 4;
                    this.strHelp = Resource.getNewStrArr(this.helpTip, 120);
                    this.sel = 0;
                    break;
                }
                if (n == 4) {
                    this.state = 5;
                    break;
                }
                if (n != 5) break;
                this.state = 7;
            }
        }
    }
}
