package io.github.creeper12356.utils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils
 {
    public static final int SND_MOVE1 = 0;
    public static final int SND_MOVE2 = 1;
    public static final int SND_MOVE3 = 2;
    public static final int SND_MOVESP2 = 2;
    public static final int SND_MOVESP = 3;
    public static final int SND_ERROR = 4;
    public static final int SND_MENU = 5;
    public static final int SND_SELMOVE = 5;
    public static final int SND_SELECT = 6;
    public static final int SND_BGM2 = 7;
    public static final int SND_TRAIN = 8;
    public static final int SND_READY = 9;
    public static final int SND_LOGO = 9;
    public static final int SND_RESULT = 10;
    public static final int SND_FAILED = 11;
    public static final int SND_TALK = 12;
    public static final int SND_CHALLANGE = 13;
    public static final int SND_BGM4 = 14;
    public static final int SND_WIN_ALARM = 15;
    public static final int SND_HOMEIN = 16;
    public static final int SND_CLICK = 17;
    public static final int SND_MINICLEAR = 18;
    public static final int SND_DINGDONG = 19;
    public static final int SND_BAD = 20;
    static byte[] playClip;
    static byte[] sndMove1;
    static byte[] sndMove2;
    static byte[] sndMove3;
    static byte[] sndMove4;
    static byte[] sndMove5;
    static byte[] sndError;
    static byte[] sndMenu;
    static int lastSoundID;
    static int currentSoundID;
    static boolean repeat;
    public static boolean checkPlaySound;
    static Utils aUtils;
    static ByteArrayInputStream[] soundis;

    public static int RGB(int n, int n2, int n3) {
        return (n & 0xFF) << 16 | (n2 & 0xFF) << 8 | n3 & 0xFF;
    }

    public static void IntToByte(byte[] byArray, int n) {
        byArray[0] = (byte)((n & 0xFFFF0000) >> 24);
        byArray[1] = (byte)((n & 0xFF0000) >> 16);
        byArray[2] = (byte)((n & 0xFF00) >> 8);
        byArray[3] = (byte)(n & 0xFF);
    }

    public static int getInt(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
    }

    // public static void soundPlayInit() {
    //     sndTimer = new XTimer(100L);
    //     sndTimer.start();
    //     sndTimer.cancel();
    //     sndTimer.setTimerListener(aUtils);
    //     sndTimer.setTime(100L);
    //     sndTimer.resume();
    // }

    public static void playSound(int n, boolean bl) {
        checkPlaySound = true;
        lastSoundID = currentSoundID;
        currentSoundID = n;
        repeat = bl;
    }

    public static void stopSound() {
        repeat = false;
        // mmfp.stop();
    }

    public static void loadSoundIS() {
        try {
            Utils.soundis[0] = new ByteArrayInputStream(Utils.loadClip("/boing.mid"));
            Utils.soundis[1] = new ByteArrayInputStream(Utils.loadClip("/jump2.mid"));
            Utils.soundis[2] = new ByteArrayInputStream(Utils.loadClip("jump1.mid"));
            Utils.soundis[3] = new ByteArrayInputStream(Utils.loadClip("jump.mid"));
            Utils.soundis[4] = new ByteArrayInputStream(Utils.loadClip("error.mid"));
            Utils.soundis[5] = new ByteArrayInputStream(Utils.loadClip("/menu.mid"));
            Utils.soundis[6] = new ByteArrayInputStream(Utils.loadClip("/select.mid"));
            Utils.soundis[7] = new ByteArrayInputStream(Utils.loadClip("/bgm2.mid"));
            Utils.soundis[8] = new ByteArrayInputStream(Utils.loadClip("/train.mid"));
            Utils.soundis[9] = new ByteArrayInputStream(Utils.loadClip("/ready.mid"));
            Utils.soundis[10] = new ByteArrayInputStream(Utils.loadClip("/result.mid"));
            Utils.soundis[11] = new ByteArrayInputStream(Utils.loadClip("/failed.mid"));
            Utils.soundis[12] = new ByteArrayInputStream(Utils.loadClip("/talk.mid"));
            Utils.soundis[13] = new ByteArrayInputStream(Utils.loadClip("/challange.mid"));
            Utils.soundis[14] = new ByteArrayInputStream(Utils.loadClip("/bgm4.mid"));
            Utils.soundis[15] = new ByteArrayInputStream(Utils.loadClip("/win_alarm.mid"));
            Utils.soundis[16] = new ByteArrayInputStream(Utils.loadClip("/homein.mid"));
            Utils.soundis[17] = new ByteArrayInputStream(Utils.loadClip("/click.mid"));
            Utils.soundis[18] = new ByteArrayInputStream(Utils.loadClip("/stageclear2.mid"));
            Utils.soundis[19] = new ByteArrayInputStream(Utils.loadClip("/dingdong.mid"));
            Utils.soundis[20] = new ByteArrayInputStream(Utils.loadClip("/bad.mid"));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        // Utils.createPlayers();
    }

    // public static void createPlayers() {
    //     try {
    //         for (int i = 0; i < midPlayers.length; ++i) {
    //             Utils.midPlayers[i] = new MMFPlayer();
    //             Manager cfr_ignored_0 = Utils.midPlayers[i].sndManager;
    //             Utils.midPlayers[i].sndPlayer = Manager.createPlayer((InputStream)soundis[i], (String)"audio/midi");
    //             Utils.midPlayers[i].sndPlayer.realize();
    //             Utils.midPlayers[i].sndPlayer.prefetch();
    //             Utils.soundis[i] = null;
    //         }
    //     }
    //     catch (Exception exception) {
    //         exception.printStackTrace();
    //     }
    // }

    // public static void PlayMid(int n) {
    //     if (repeat) {
    //         Utils.midPlayers[n].sndPlayer.setLoopCount(-1);
    //     }
    //     midPlayers[n].play(repeat);
    // }

    public static byte[] loadClip(String string) {
        byte[] byArray = null;
        try {
            InputStream inputStream = aUtils.getClass().getResourceAsStream(string);
            byArray = new byte[inputStream.available()];
            inputStream.read(byArray);
            inputStream.close();
            inputStream = null;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return byArray;
    }

    // public static void loadClip2(ByteArrayInputStream byteArrayInputStream) {
    //     if (byteArrayInputStream.available() == 0) {
    //         byteArrayInputStream.reset();
    //     }
    //     try {
    //         Manager cfr_ignored_0 = Utils.mmfp.sndManager;
    //         Utils.mmfp.sndPlayer = Manager.createPlayer((InputStream)byteArrayInputStream, (String)"audio/midi");
    //         Utils.mmfp.sndPlayer.realize();
    //         Utils.mmfp.sndPlayer.prefetch();
    //         if (repeat) {
    //             Utils.mmfp.sndPlayer.setLoopCount(-1);
    //         }
    //     }
    //     catch (Exception exception) {
    //         exception.printStackTrace();
    //     }
    // }

    // public static void checkPlaySound() {
    //     if (Resource.soundVolume == 0) {
    //         return;
    //     }
    //     if (currentSoundID < midPlayers.length) {
    //         Utils.PlayMid(currentSoundID);
    //     } else {
    //         if (lastSoundID != currentSoundID) {
    //             mmfp.close();
    //             Utils.loadClip2(soundis[currentSoundID]);
    //         }
    //         mmfp.play(repeat);
    //     }
    //     checkPlaySound = false;
    // }

    // public static void playVib(int n) {
    //     if (Resource.vibSwitch == 1) {
    //         Display.getDisplay((MIDlet)DiaGameCard.diaapp).vibrate(100);
    //     }
    // }

    public static void stopVib() {
    }

    public static void volumeChange(int n) {
        Resource.soundVolume = Resource.soundVolume == 0 ? 1 : 0;
    }

    public static void cnfVolumeChange(int n) {
        Utils.volumeChange(n);
        Utils.playSound(0, false);
    }

    public static void openVolume() {
        if (Resource.soundVolume != 0) {
            Resource.soundVolume = 0;
        }
    }

    public static void offVolume() {
        if (Resource.soundVolume == 0) {
            Resource.soundVolume = 1;
        }
    }

    public static void setVolume() {
    }

    // public static void vibSwitchToggle() {
    //     Resource.vibSwitch = Resource.vibSwitch == 0 ? 1 : 0;
    //     Utils.playVib(2);
    // }

    // public static void gamespeedChange(int n) {
    //     if (n == 3) {
    //         if (Resource.gameSpeed > 50) {
    //             Resource.gameSpeed -= 50;
    //         }
    //     } else if (n == 4) {
    //         if (Resource.gameSpeed < 150) {
    //             Resource.gameSpeed += 50;
    //         }
    //     } else if (n == 5 && (Resource.gameSpeed -= 50) < 50) {
    //         Resource.gameSpeed = 150;
    //     }
    //     Resource.aTimer2.setTime(Resource.gameSpeed);
    //     Resource.aTimer2.resume();
    // }

    // public void performed(XTimer xTimer) {
    //     if (checkPlaySound) {
    //         Utils.checkPlaySound();
    //     }
    // }

    // static {
    //     aUtils = new Utils();
    //     mmfp = new MMFPlayer();
    //     midPlayers = new MMFPlayer[5];
    //     soundis = new ByteArrayInputStream[21];
    // }
}
