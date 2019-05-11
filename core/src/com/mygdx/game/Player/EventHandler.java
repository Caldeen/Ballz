package com.mygdx.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.MyGdxGame;

public class EventHandler implements InputProcessor {
    private Ball ball;
    private OrthographicCamera cam;
    public static boolean mouseDown;
    public static float prevX;
    public static float prevY;
    public final float max_val = 3.0f;

    public EventHandler(Ball ball, OrthographicCamera cam) {
        this.ball = ball;
        this.cam = cam;
        mouseDown = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            mouseDown = true;
            prevX = screenX;
            prevY = MyGdxGame.screenSize.y - screenY;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            float currentX = Gdx.input.getX();
            float currentY = MyGdxGame.screenSize.y - Gdx.input.getY();
            float vecX = -(currentX-EventHandler.prevX) / 100f;
            float vecY = -(currentY-EventHandler.prevY) / 56.25f;

            if (Math.abs(vecX) > max_val) {
                if (vecX > 0) vecX = max_val;
                else vecX = -max_val;
            }

            if (Math.abs(vecY) > max_val) {
                if (vecY > 0) vecY = max_val;
                else vecY = -max_val;
            }

            ball.move(vecX, vecY);

            mouseDown = false;
            prevX = screenX;
            prevY = MyGdxGame.screenSize.y - screenY;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
