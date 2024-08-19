/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

class NetThread
implements Runnable {
    static final int stWAIT = 0;
    static final int stCONNECT = 1;
    static final int stSEND = 2;
    static final int stRECEIVE = 3;
    static final int stCLOSE = 4;
    static final int stERROR = 5;
    static final int NONE = 0;
    static final int SUCCEED = 1;
    static final int FAILED = 2;
    int state = 0;
    int retry = 0;
    int complete = 0;
    boolean bStop = false;

    NetThread() {
    }

    void Connect() {
        if (this.state == 0) {
            this.state = 1;
            this.bStop = false;
        }
    }

    void Stop() {
        this.bStop = true;
    }

    public String getMIN() {
        String string = System.getProperty("m.CARRIER");
        String string2 = null;
        string2 = System.getProperty("m.VENDER").equals("LG") && System.getProperty("m.MODEL").equals("56") ? ((string2 = System.getProperty("MIN")).charAt(0) == '0' ? string2.substring(3) : string2.substring(2)) : System.getProperty("m.MIN");
        string2 = string.equals("SKT") ? "011" + string2 : (string.equals("STI") ? "017" + string2 : (string.equals("KTF") ? "016" + string2 : (string.equals("HSP") ? "018" + string2 : (string.equals("LGT") ? "019" + string2 : (string.equals("010") ? "010" + string2 : string + string2)))));
        return string2;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1L);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (this.bStop) {
                NetworkMgr.getInstance().close();
                return;
            }
            switch (this.state) {
                case 0: {
                    break;
                }
                case 1: {
                    try {
                        if (!NetworkMgr.getInstance().connect("socket://dreampix.pe.kr:9203")) {
                            NetworkMgr.getInstance().close();
                            this.state = 5;
                            break;
                        }
                        this.state = 2;
                    }
                    catch (Exception exception) {
                        this.state = 5;
                    }
                    break;
                }
                case 2: {
                    byte[] byArray = new byte[19];
                    byArray[0] = 0;
                    try {
                        byArray[0] = (byte)(RankView.localrank[0].point & 0xFF);
                        byArray[1] = (byte)((RankView.localrank[0].point & 0xFF00) >> 8);
                        byArray[2] = (byte)((RankView.localrank[0].point & 0xFF0000) >> 16);
                        byArray[3] = (byte)((RankView.localrank[0].point & 0xFF000000) >> 24);
                        byte[] byArray2 = this.getMIN().getBytes();
                        System.arraycopy(byArray2, 0, byArray, 4, byArray2.length);
                        NetworkMgr.getInstance().sendData(byArray);
                        this.state = 3;
                    }
                    catch (IOException iOException) {
                        this.state = 5;
                    }
                    break;
                }
                case 3: {
                    byte[] byArray = new byte[150];
                    try {
                        byArray = NetworkMgr.getInstance().recvData(150);
                        RankView.receiveRank(byArray);
                        this.state = 4;
                        this.complete = 1;
                    }
                    catch (IOException iOException) {
                        this.state = 5;
                    }
                    break;
                }
                case 4: {
                    NetworkMgr.getInstance().close();
                    return;
                }
                case 5: {
                    ++this.retry;
                    if (this.retry == 3) {
                        this.state = 4;
                        this.complete = 2;
                        break;
                    }
                    NetworkMgr.getInstance().close();
                    this.state = 1;
                }
            }
        }
    }
}
