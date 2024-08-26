package io.github.creeper12356.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RectangleActor extends Actor {
    private ShapeRenderer shapeRenderer;

    public RectangleActor() {
        shapeRenderer = new ShapeRenderer();
    }
    @Override
    public void draw(Batch batch ,float parentAlpha) {
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        int n = 54; // TODO: 修改这个值以调整矩形的大小
        shapeRenderer.rect(0, 0, Resource.totalWidth, Resource.halfHeight - n);
        shapeRenderer.rect(0, Resource.halfHeight + n, Resource.totalWidth, Resource.halfHeight - n);

        shapeRenderer.end();

        batch.begin();
    }

    public void resize(int width, int height) {
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
