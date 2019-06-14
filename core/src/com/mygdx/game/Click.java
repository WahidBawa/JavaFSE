package com.mygdx.game;
//this class using inputprocessor touchdown, a method that is called by libgdx when a button is clicked down
//this is used to click on the Enemys on the world witch are seen on screen, using collision to do damage to the Enemys
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;



public class Click implements InputProcessor {

    //class fields
    Body body;//the body that will represent the mouse click and collid with the enemys.
    BodyDef bodyDef = new BodyDef();
    FixtureDef fdef = new FixtureDef();
    Vector3 clickPos;//the position of the mouse;

    public void leftClickEnemy(float x, float y) {//this method takes in the x and y of the mouse and creates body at the that point

        bodyDef.position.set(x,y);
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;//the body should move and so it is made to be a kinematicBody
        body = Main.world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        fdef.shape = shape;
        shape.setAsBox(1, 1);//small box
        body.createFixture(fdef);
        body.getFixtureList().get(0).setUserData("CLICK");//collision data i "CLICK" so we identify this body as a mouse click
        MassData massOfCLick = new MassData();
        massOfCLick.mass = (float) (1);
        body.setMassData(massOfCLick);


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
        switch (button)//goes trough different cases of the button parameters
        {
            case Input.Buttons.LEFT://in the case that the button variable is the left mouse button
                clickPos = Main.camera.unproject(new Vector3(screenX, screenY, 0));//get the pos of the mouse in a vector3
                leftClickEnemy(clickPos.x, clickPos.y);//creates body at the x and y of that vector3
                break;//end of task

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
