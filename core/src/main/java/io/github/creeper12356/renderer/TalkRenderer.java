package io.github.creeper12356.renderer;

public interface TalkRenderer {
    void setTalkTimeout(float timeout);
    float getTalkTimeout();
    void addTalkToQueue(String message, Runnable onShow, Runnable onExpire);

    void render(float delta);
}
