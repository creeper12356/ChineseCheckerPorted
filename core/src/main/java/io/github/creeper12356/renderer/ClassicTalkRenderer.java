package io.github.creeper12356.renderer;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClassicTalkRenderer implements TalkRenderer {
    private float talkTimeout = 5.0f; 

    private final ConcurrentLinkedQueue<TalkItem> talkBlockQueue = new ConcurrentLinkedQueue<>();
    private float curTimeout = 0.0f;
    private String currentTalkBlock = null;

    static final int QUEUE_STATE_IDLE = 0;
    static final int QUEUE_STATE_SHOWING = 1;
    static final int QUEUE_STATE_EXPIRE = 2;

    private int queueState = QUEUE_STATE_IDLE;

    private static final class TalkItem {
        final String text;
        final boolean isSeparator;
        final Runnable onShow;
        final Runnable onExpire;
        TalkItem(String text, boolean isSeparator, Runnable onShow, Runnable onExpire) {
            this.text = text;
            this.isSeparator = isSeparator;
            this.onShow = onShow;
            this.onExpire = onExpire;
        }
    }

    @Override
    public void setTalkTimeout(float timeout) {
        this.talkTimeout = timeout;
    }

    @Override
    public float getTalkTimeout() {
        return this.talkTimeout;
    }

    @Override
    public void addTalkToQueue(String message, Runnable onShow, Runnable onExpire) {
        talkBlockQueue.add(new TalkItem(message, false, onShow, null));
        talkBlockQueue.add(new TalkItem("", true, null, onExpire));
    }

    @Override
    public void render(float delta) {
        if (queueState == QUEUE_STATE_IDLE) {
            if(talkBlockQueue.isEmpty()) {
                return ;
            }

            TalkItem talkBlock = talkBlockQueue.poll();
            if (talkBlock.onShow != null) {
                talkBlock.onShow.run();
            }
            currentTalkBlock = talkBlock.text;
            if(!talkBlockQueue.peek().isSeparator) {
                queueState = QUEUE_STATE_SHOWING;
            } else {
                queueState = QUEUE_STATE_EXPIRE;
            }

        } else if (queueState == QUEUE_STATE_SHOWING) {
            System.out.println("Show talk block: " + currentTalkBlock);
            System.out.println("Current timeout: " + curTimeout);
            curTimeout += delta;
            if(curTimeout >= 0.05f) {
                curTimeout = 0.0f;
            } 
        } else if (queueState == QUEUE_STATE_EXPIRE) {
            System.out.println("Show talk block: " + currentTalkBlock);
            System.out.println("Current timeout: " + curTimeout);
            curTimeout += delta;
            if(curTimeout >= talkTimeout) {
                curTimeout = 0.0f;

                TalkItem expiredBlock = talkBlockQueue.poll(); 
                if (expiredBlock.onExpire != null) {
                    System.out.println("Running expire runnable");
                    expiredBlock.onExpire.run();
                }
                System.out.println("Expire!");
                queueState = QUEUE_STATE_IDLE;
            }
        }
    }


}
