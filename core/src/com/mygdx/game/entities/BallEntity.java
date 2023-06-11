package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BallEntity extends Actor{

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public Body getBody() {
        return body;
    }
    public BallEntity(Texture texture, World world, Vector2 position){
        this.texture=texture;
        this.world=world;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        setSize(texture.getWidth(),texture.getHeight());
        //setScale(0.25f);
        body=world.createBody(def);
        fixture= createBolaFixture(body);
        fixture.setUserData("ball");
        setSize(90*0.5f,90*0.5f);
    }
    private Fixture createBolaFixture(Body bolaBody){
        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);
        Fixture fix=bolaBody.createFixture(shape, 1);
        shape.dispose();
        return(fix);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(90*(body.getPosition().x-0.5f),90*(body.getPosition().y-0.5f));
        //batch.draw(texture, getX(), getY(), getWidth()*getScaleX(), getHeight()*getScaleY());
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
