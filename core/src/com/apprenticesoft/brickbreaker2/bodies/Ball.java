package com.apprenticesoft.brickbreaker2.bodies;

import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.apprenticesoft.brickbreaker2.utils.GameConstants;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.*;

public class Ball extends CircleShape {

    public Body body;
    public BodyDef bodyDef;
    public float posX, posY, rayon;
    public static World world;
    Camera camera;
    public boolean ballActive, startImpulse, ballLaserContact, destroy;
    private Vector2 vectorSpeed;
    public float maxSpeed, vitesseJeu;
    public int initY, rebond;
    public long balleLaserTime;

    /*
    public Ball (World world, Camera camera, float posX, float posY){
        super();
        this.world = world;
        this.camera = camera;
        this.posX = posX;
        this.posY = posY;
        rebond = 0;
        ballActive = false;
        startImpulse = false;
        destroy = false;
        rayon = camera.viewportWidth/50;


        maxSpeed = GameConstants.vitesseBalle * vitesseJeu * camera.viewportHeight;

        bodyDef = new BodyDef();
        this.setRadius(rayon);

        bodyDef.position.set(new Vector2(posX, posY));
        bodyDef.type = BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1;
        body.createFixture(fixtureDef);
        body.setUserData("Balle");

        System.out.println("Balle: " + body.getPosition());
    }
     */

    public Ball(BrickBreaker2 game) {
        System.out.println("Balle venant du Pool!!!");
    }

    public void init(World world, Camera camera, float posX, float posY){
        this.world = world;
        this.camera = camera;
        this.posX = posX;
        this.posY = posY;
        rebond = 0;
        ballActive = false;
        startImpulse = false;
        destroy = false;
        rayon = camera.viewportWidth/50;

        maxSpeed = GameConstants.vitesseBalle * vitesseJeu * camera.viewportHeight;

        bodyDef = new BodyDef();
        this.setRadius(rayon);

        bodyDef.position.set(new Vector2(posX, posY));
        bodyDef.type = BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = this;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1;
        body.createFixture(fixtureDef);
        body.setUserData("Balle");

        System.out.println("Balle initiée");
    }

    public void Active(){

        // Destruction de la balle
        if(body.getPosition().y < -2*getRadius()){
            body.setActive(false);
            world.destroyBody(body);
            destroy = true;
            System.out.println("Destroy");
        }
    }
}
