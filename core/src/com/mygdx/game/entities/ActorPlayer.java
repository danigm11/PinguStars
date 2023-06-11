package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorPlayer extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public boolean isPinguVivo() {
        return pinguVivo;
    }

    public void setPinguVivo(boolean pinguVivo) {
        this.pinguVivo = pinguVivo;
    }
    public Body getBody() {
        return body;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    private boolean pinguVivo = true;
    public ActorPlayer(Texture texture, World world, Vector2 position){
        this.texture=texture;
        this.world=world;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        setSize(texture.getWidth(),texture.getHeight());
        setScale(0.25f);
        body=world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.25f,0.5f);
        fixture= body.createFixture(shape, 1);
        fixture.setUserData("pingu");
        shape.dispose();
        setSize(90*3,90*4.5f);
    }
    @Override
    public void act(float delta) {

        //touch contros
        if((Gdx.input.isTouched()&&pinguVivo)){
            //
            //if (Gdx.input.getX()>body.getPosition().x*90) {
            if (Gdx.input.getX()>1125) {
                andarDerecha();
            }else {
                andarIzquierda();
            }
        }
        //Keyboard controls
        if(pinguVivo&&(Gdx.input.isKeyPressed(Input.Keys.D)||Gdx.input.isKeyPressed(Input.Keys.A))){
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                //if (Gdx.input.getX()>300) {
                andarDerecha();
            }else {
                andarIzquierda();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(90*(body.getPosition().x-0.5f),90*(body.getPosition().y-0.5f));
        batch.draw(texture, getX(), getY(), getWidth()*getScaleX(), getHeight()*getScaleY());
        //batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
        //world.dispose();
    }
    private void andarDerecha(){
        body.applyForceToCenter(8,0,true);
    }
    private void andarIzquierda(){
        body.applyForceToCenter(-8,0,true);
    }
}
