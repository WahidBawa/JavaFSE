package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class Click implements InputProcessor {

    public boolean keyPressed;
    Body body;
    BodyDef bodyDef = new BodyDef();
    FixtureDef fdef = new FixtureDef();
    Vector3 clickPos;

    public void leftClickEnemy(float x, float y) {
        for (Enemy enemy : Main.wc.getEnemies()) {
            bodyDef.position.set(x,y);
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.KinematicBody;
            body = Main.world.createBody(bodyDef);
            //body.setUserData();
            PolygonShape shape = new PolygonShape();
            fdef.shape = shape;
            shape.setAsBox(1, 1);
            body.createFixture(fdef);
            body.getFixtureList().get(0).setUserData("CLICK");
            System.out.println("thicc");
            MassData massOfCLick = new MassData();
            massOfCLick.mass = (float) (1);//took the avrarage mass of a male and female goose to to discriminate against a certain sex
            body.setMassData(massOfCLick);

            //Main.world.destroyBody(body);
        }
    }
    //
    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode)
        {
            case Input.Keys.SPACE:
                System.out.println("boi");
                break;

        }
        return true;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button)
        {
            case Input.Buttons.LEFT:
                System.out.println("x:" + Main.camera.unproject(new Vector3(screenX, screenY, 0) ) );
                clickPos = Main.camera.unproject(new Vector3(screenX, screenY, 0));
                leftClickEnemy(clickPos.x, clickPos.y);
                break;

        }
        return true;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
