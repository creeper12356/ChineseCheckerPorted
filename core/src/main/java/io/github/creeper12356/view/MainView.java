/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class MainView {
    public static final int STATUS_MENU = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_STORY = 2;
    public static final int STATUS_RANK = 4;
    public static final int STATUS_GAME_OPTION = 6;
    public static final int STATUS_GAME_HELP = 7;
    public static final int STATUS_MINIGAME = 8;
    public static final int STATUS_SPECIALGAME = 9;
    public static final int STATUS_PAUSE = 10;
    private int status;
    private int lastStatus;
    public Viewable currentView;
    private DiaGameCard aCard;
    public BoardView aBoardView;
    protected MenuView menuView;
    private StoryView storyView;
    private ConfigView configView;
    private HelpView helpView;
    private RankView rankView;
    protected Continental continentalView;
    protected MiniGameView miniGameView;
    private Viewable oldView;
    private int popupCnt;

    public MainView(DiaGameCard diaGameCard) {
        this.aCard = diaGameCard;
        this.menuView = new MenuView();
        this.storyView = new StoryView();
        this.configView = new ConfigView();
        this.helpView = new HelpView();
        this.rankView = new RankView();
        this.continentalView = new Continental();
        this.miniGameView = new MiniGameView();
        this.oldView = null;
        this.popupCnt = 0;
    }

    public void openPopup(int n) {
        this.oldView = this.currentView;
        if (n == 6) {
            this.currentView = this.configView;
        } else if (n == 7) {
            this.currentView = this.helpView;
        }
        ++this.popupCnt;
        this.currentView.init();
    }

    public void closePopup() {
        this.currentView.clear();
        this.currentView = this.oldView;
        this.oldView = null;
        --this.popupCnt;
    }

    public void setStatus(int n, int n2) {
        this.status = n;
        if (this.currentView != null) {
            this.currentView.clear();
            this.currentView = null;
        }
        switch (this.status) {
            case 0: {
                this.currentView = this.menuView;
                break;
            }
            case 2: {
                this.currentView = this.storyView;
                break;
            }
            case 8: {
                this.currentView = this.miniGameView;
                break;
            }
            case 9: {
                this.currentView = this.continentalView;
                break;
            }
            case 1: {
                this.currentView = this.aBoardView;
                break;
            }
            case 4: {
                this.currentView = this.rankView;
            }
        }
        this.currentView.setStatus(n2);
        this.currentView.init();
    }

    public void drawPause(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, Resource.totalWidth, Resource.totalHeight);
        graphics.setColor(0);
        graphics.setFont(Resource.sf);
        graphics.drawString("\u662f\u5426\u7ee7\u7eed\u6e38\u620f\uff1f", Resource.halfWidth, Resource.halfHeight, 1 | 0x10);
    }

    public void paint(Graphics graphics) {
        if (this.currentView != null) {
            this.currentView.paint(graphics);
        }
    }

    public void repaint() {
        this.aCard.repaint(0, 0, Resource.totalWidth, Resource.totalHeight);
    }

    public void repaint2() {
    }

    public void repaint(int n, int n2, int n3, int n4) {
        this.aCard.repaint(n, n2, n3, n4);
    }

    public void repaint2(int n, int n2, int n3, int n4) {
    }

    public void keyPressed(int n) {
        if (this.currentView != null) {
            this.currentView.keyPressed(n);
        }
    }

    public void keyReleased(int n) {
        if (this.currentView != null) {
            if (this.status == 10) {
                if (n == -7) {
                    this.appDestroy();
                } else if (n == -6) {
                    this.showNotify();
                }
            } else {
                this.currentView.keyReleased(n);
            }
        }
    }

    public void hideNotify() {
        this.lastStatus = this.status;
        this.status = 10;
    }

    public void showNotify() {
        this.setStatus(this.lastStatus);
    }

    public void appDestroy() {
        this.aCard.exit();
    }

    public void init() {
        this.currentView.init();
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int n) {
        this.status = n;
    }
}
