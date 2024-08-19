/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ConfigView
implements XTimerListener,
Viewable {
    int sel;
    int state;
    Image imgPopupMenu;
    Image[] imgVolume = new Image[5];
    Image[] configChip = new Image[3];
    Image imgSelIcon;

    public void paint(Graphics graphics) {
        int n;
        int n2;
        int n3;
        int n4;
        TouchDo.setTouchArea(7, 0, 0);
        graphics.drawImage(Resource.imgBackBuffer, 0, 0, 4 | 0x10);
        if (Resource.isQVGA()) {
            n4 = 96;
            n3 = 86;
        } else {
            n4 = 48;
            n3 = 43;
        }
        int n5 = Resource.halfWidth - n4;
        int n6 = Resource.halfHeight - n3;
        graphics.setColor(148, 65, 24);
        if (Resource.isQVGA()) {
            n2 = 190;
            n = 166;
        } else {
            n2 = 95;
            n = 83;
        }
        graphics.fillRect(n5 + 1, n6, n2 - 1, n + 1);
        graphics.fillRect(n5, n6 + 1, n2 + 1, n - 1);
        graphics.setColor(255, 230, 209);
        if (Resource.isQVGA()) {
            n2 = 187;
            n = 163;
        } else {
            n2 = 92;
            n = 80;
        }
        graphics.fillRect(n5 + 2, n6 + 2, n2, n);
        if (Resource.isQVGA()) {
            n4 = 22;
            n3 = 21;
        } else {
            n4 = 11;
            n3 = 9;
        }
        graphics.drawImage(this.imgPopupMenu, Resource.halfWidth - n4, n6 - n3, 4 | 0x10);
        graphics.setColor(531282);
        graphics.setFont(Resource.sf);
        graphics.drawString("\u58f0\u97f3", Resource.halfWidth - 28, Resource.halfHeight - 50, 4 | 0x10);
        graphics.drawString("\u632f\u52a8", Resource.halfWidth - 28, Resource.halfHeight - 5, 4 | 0x10);
        graphics.drawString("\u901f\u5ea6", Resource.halfWidth - 28, Resource.halfHeight + 40, 4 | 0x10);
        Object var8_8 = null;
        if (Resource.soundVolume == 0) {
            if (Resource.isQVGA()) {
                n4 = 30;
                n3 = 15;
            } else {
                n4 = 24;
                n3 = 19;
            }
            graphics.drawImage(this.configChip[1], Resource.halfWidth + n4 - this.configChip[1].getWidth() / 2, Resource.halfHeight - (n3 += 20) - this.configChip[1].getHeight(), 4 | 0x10);
        } else {
            if (Resource.isQVGA()) {
                n4 = 30;
                n3 = 15;
            } else {
                n4 = 24;
                n3 = 19;
            }
            graphics.drawImage(this.configChip[2], Resource.halfWidth + n4 - this.configChip[2].getWidth() / 2, Resource.halfHeight - (n3 += 20) - this.configChip[2].getHeight(), 4 | 0x10);
        }
        var8_8 = null;
        if (Resource.vibSwitch == 1) {
            if (Resource.isQVGA()) {
                n4 = 30;
                n3 = 4;
            } else {
                n4 = 24;
                n3 = 2;
            }
            graphics.drawImage(this.configChip[2], Resource.halfWidth + n4 - this.configChip[2].getWidth() / 2, Resource.halfHeight - n3, 4 | 0x10);
        } else if (Resource.vibSwitch == 0) {
            if (Resource.isQVGA()) {
                n4 = 30;
                n3 = 4;
            } else {
                n4 = 24;
                n3 = 2;
            }
            graphics.drawImage(this.configChip[1], Resource.halfWidth + n4 - this.configChip[1].getWidth() / 2, Resource.halfHeight - n3, 4 | 0x10);
        }
        var8_8 = null;
        if (Resource.isQVGA()) {
            n4 = 14;
            n3 = 24;
        } else {
            n4 = 12;
            n3 = 22;
        }
        n3 += 20;
        if (Resource.gameSpeed <= 150) {
            graphics.drawImage(this.configChip[0], Resource.halfWidth + n4, Resource.halfHeight + n3, 4 | 0x10);
        }
        if (Resource.gameSpeed <= 100) {
            graphics.drawImage(this.configChip[0], Resource.halfWidth + n4 + this.configChip[0].getWidth() + 1, Resource.halfHeight + n3, 4 | 0x10);
        }
        if (Resource.gameSpeed == 50) {
            graphics.drawImage(this.configChip[0], Resource.halfWidth + n4 + (this.configChip[0].getWidth() + 1) * 2, Resource.halfHeight + n3, 4 | 0x10);
        }
        if (Resource.isQVGA()) {
            n4 = 60;
            n3 = 30;
        } else {
            n4 = 44;
            n3 = 30;
        }
        if (this.sel == 0) {
            n3 += 20;
        } else if (this.sel == 2) {
            n3 -= 20;
        }
        graphics.drawImage(this.imgSelIcon, Resource.halfWidth - n4, Resource.halfHeight - n3 + this.sel * 25, 4 | 0x10);
    }

    public void keyPressed(int n) {
        if (n >= 0 && n <= 2) {
            this.sel = n;
            n = -5;
        }
        switch (n) {
            case -1: 
            case 50: {
                this.sel = this.sel == 0 ? 2 : this.sel - 1;
                break;
            }
            case -2: 
            case 56: {
                this.sel = this.sel == 2 ? 0 : this.sel + 1;
                break;
            }
            case -3: 
            case 52: {
                if (this.sel == 0) {
                    Utils.cnfVolumeChange(4);
                    break;
                }
                if (this.sel == 1) {
                    Utils.vibSwitchToggle();
                    break;
                }
                if (this.sel != 2) break;
                Utils.gamespeedChange(4);
                break;
            }
            case -4: 
            case 54: {
                if (this.sel == 0) {
                    Utils.cnfVolumeChange(3);
                    break;
                }
                if (this.sel == 1) {
                    Utils.vibSwitchToggle();
                    break;
                }
                if (this.sel != 2) break;
                Utils.gamespeedChange(3);
                break;
            }
            case -6: 
            case -5: 
            case 53: {
                this.goMenu(this.sel);
                break;
            }
            case -8: 
            case -7: 
            case 48: {
                Resource.saveConfig();
                Resource.aMainView.closePopup();
            }
        }
    }

    public void keyReleased(int n) {
    }

    public void performed(XTimer xTimer) {
    }

    public void init() {
        this.sel = 0;
        this.imgPopupMenu = Resource.loadImage("/menuchip_1.png");
        for (int i = 0; i < 5; ++i) {
            this.imgVolume[i] = Resource.loadImage("/volume_" + i + ".png");
        }
        this.configChip[0] = Resource.loadImage("/aimchip_0.png");
        this.configChip[1] = Resource.loadImage("/textchip_2.png");
        this.configChip[2] = Resource.loadImage("/textchip_3.png");
        this.imgSelIcon = Resource.loadImage("/selicon_0.png");
    }

    public void clear() {
        this.imgPopupMenu = null;
        for (int i = 0; i < 5; ++i) {
            this.imgVolume[i] = null;
        }
        this.configChip[0] = null;
        this.configChip[1] = null;
        this.configChip[1] = null;
        this.imgSelIcon = null;
    }

    public void setStatus(int n) {
    }

    public int getStatus() {
        return 0;
    }

    void goMenu(int n) {
        switch (n) {
            case 0: {
                Utils.cnfVolumeChange(5);
                break;
            }
            case 1: {
                Utils.vibSwitchToggle();
                break;
            }
            case 2: {
                Utils.gamespeedChange(5);
            }
        }
    }
}
