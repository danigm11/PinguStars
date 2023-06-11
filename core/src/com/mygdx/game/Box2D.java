package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2D extends BaseScreen{

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body pinguBody, sueloBody, starBody, bolaBody;
    private Fixture pinguFixture, sueloFixture, starFixture, bolaFixture;
    private boolean contactoEstrella, contactoBola, pinguVivo = true;
    public Box2D(MainJuego juego) {
        super(juego);
    }

    private BodyDef createDynamicBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0,0);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }
    private BodyDef createStarBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(1,0);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }
    private BodyDef createBolaBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(2,0);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }
    private BodyDef createStaticBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0,-1);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }
    private Fixture createStarFixture(Body starBody){
        float width = 0.5f; // Aspect ratio del pentágono
        float[] verts = new float[10];
        for (int i = 0; i < 5; i++) {
            float angle = i * 2 * MathUtils.PI / 5; // Ángulo correspondiente a cada vértice
            verts[i * 2] = width / 2 * MathUtils.cos(angle); // Coordenada x del vértice
            verts[i * 2 + 1] = (width*1.618f) / 2 * MathUtils.sin(angle);
        }
        PolygonShape shape = new PolygonShape();
        shape.set(verts);
        Fixture fix=starBody.createFixture(shape, 1);
        shape.dispose();
        return(fix);
    }
    private Fixture createBolaFixture(Body bolaBody){
        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);
        Fixture fix=bolaBody.createFixture(shape, 1);
        shape.dispose();
        return(fix);
    }
    @Override
    public void show() {
        world= new World(new Vector2(0,-8),true);
        renderer=new Box2DDebugRenderer();
        camera= new OrthographicCamera(7.11f,4);
        camera.translate(2.5f,1.5f);


        pinguBody= world.createBody(createDynamicBodyDef());
        sueloBody= world.createBody(createStaticBodyDef());
        starBody= world.createBody(createStarBodyDef());
        bolaBody= world.createBody(createBolaBodyDef());


        PolygonShape pinguShape = new PolygonShape();
        pinguShape.setAsBox(0.25f,0.35f);
        pinguFixture= pinguBody.createFixture(pinguShape, 1);
        pinguShape.dispose();

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(1000,1);
        sueloFixture= sueloBody.createFixture(sueloShape, 1);
        sueloShape.dispose();

        starFixture= createStarFixture(starBody);
        bolaFixture= createBolaFixture(bolaBody);

        world.setContactListener(new ContactListener(){
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
                if ((((fixtureA==starFixture)&&(fixtureB==pinguFixture))||((fixtureB==starFixture)&&(fixtureA==pinguFixture)))||(((fixtureA==starFixture)&&(fixtureB==sueloFixture))||((fixtureB==starFixture)&&(fixtureA==sueloFixture)))){
                    contactoEstrella=true;
                }
                if (((fixtureA==bolaFixture)&&(fixtureB==pinguFixture))||((fixtureB==bolaFixture)&&(fixtureA==pinguFixture))){
                    pinguVivo=false;
                }
                if (((fixtureA==bolaFixture)&&(fixtureB==sueloFixture))||((fixtureB==bolaFixture)&&(fixtureA==sueloFixture))){
                    contactoBola=true;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void dispose() {
        world.destroyBody(pinguBody);
        world.destroyBody(sueloBody);
        world.destroyBody(starBody);
        world.destroyBody(bolaBody);
        world.dispose();
        renderer.dispose();
        pinguBody.destroyFixture(pinguFixture);
        sueloBody.destroyFixture(sueloFixture);
        starBody.destroyFixture(starFixture);
        bolaBody.destroyFixture(bolaFixture);
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isTouched()&&pinguVivo){
            //if (Gdx.input.getX()>pinguBody.getPosition().x*1000) {
            if (Gdx.input.getX()>300) {
                andarDerecha();
            }else {
                andarIzquierda();
            }
        }
        if(contactoEstrella){
            starBody.setTransform(((int)(Math.random()*7.11)),4, 0);
            starBody.setLinearVelocity(0,0);
            starBody.setAngularVelocity(0);
            contactoEstrella=false;
        }
        if(contactoBola){
            bolaBody.setTransform(((int)(Math.random()*7.11)),4, 0);
            bolaBody.setLinearVelocity(0,0);
            bolaBody.setAngularVelocity(0);
            contactoBola=false;
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1/60f, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);
    }
    private void andarDerecha(){
        pinguBody.applyForceToCenter(8,0,true);
    }
    private void andarIzquierda(){
        pinguBody.applyForceToCenter(-8,0,true);
    }
}
