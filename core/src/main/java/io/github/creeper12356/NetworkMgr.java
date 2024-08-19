/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.StreamConnection
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class NetworkMgr {
    protected static NetworkMgr instance = null;
    StreamConnection _sSocket = null;
    DataOutputStream _dos = null;
    DataInputStream _dis = null;
    private boolean _bConnect = false;
    private static Object _netLock = new Object();
    private String _debugMessage = "";

    public NetworkMgr() {
        instance = this;
    }

    public static NetworkMgr getInstance() {
        if (instance == null) {
            instance = new NetworkMgr();
        }
        return instance;
    }

    boolean IsConnected() {
        return this._bConnect;
    }

    public String getDebugMessage() {
        return this._debugMessage;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean connect(String string) throws IOException {
        if (!this._bConnect) {
            Object object = _netLock;
            synchronized (object) {
                try {
                    this._sSocket = (StreamConnection)Connector.open((String)string);
                    this._dos = this._sSocket.openDataOutputStream();
                    this._dis = this._sSocket.openDataInputStream();
                    this._bConnect = true;
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                    this.close();
                    throw new IOException(iOException.getMessage());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    this.close();
                    throw new IOException(exception.getMessage());
                }
            }
        }
        return this._bConnect;
    }

    int recvDataSize() {
        int n = 0;
        byte[] byArray = null;
        try {
            byArray = this.recvData(4);
            n = byArray == null ? -1 : this.byte4ToInt(byArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            n = -2;
        }
        return n;
    }

    private int byte4ToInt(byte[] byArray) {
        int n = 0;
        for (int i = 0; i < 4; ++i) {
            n = this.getUnsignedValue(byArray[i]) << (3 - i) * 8 | n;
        }
        return n;
    }

    private int getUnsignedValue(byte n) {
        return n < 0 ? ~n : n;
    }

    byte[] recvData(int n) throws IOException {
        if (!this._bConnect) {
            return null;
        }
        byte[] byArray = null;
        while (byArray == null) {
            try {
                byArray = new byte[n];
                this._dis.readFully(byArray, 0, n);
            }
            catch (EOFException eOFException) {
                this._debugMessage = "EOF:" + eOFException.toString();
                byArray = null;
            }
            catch (Exception exception) {
                String string = exception.getMessage();
                if (string != null) continue;
                string = new String("I/O Exception : read null");
            }
        }
        return byArray;
    }

    int sendData(String string) {
        try {
            byte[] byArray = string.getBytes();
            int n = this.sendData(byArray);
            return n;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return -1;
        }
    }

    int sendData(byte[] byArray) throws IOException {
        if (!this._bConnect) {
            return -1;
        }
        try {
            this._dos.write(byArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            this.close();
            throw new IOException(exception.getMessage());
        }
        return byArray.length;
    }

    void close() {
        try {
            if (this._sSocket != null) {
                this._sSocket.close();
                this._sSocket = null;
                if (this._dis != null) {
                    try {
                        this._dis.close();
                        this._dis = null;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                if (this._dos != null) {
                    try {
                        this._dos.close();
                        this._dos = null;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            this._bConnect = false;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
