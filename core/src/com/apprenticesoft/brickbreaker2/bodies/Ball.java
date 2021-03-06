package com.apprenticesoft.brickbreaker2.bodies;

import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.apprenticesoft.brickbreaker2.utils.GameConstants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.*;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class Ball extends CircleShape {

    public Body body;
    public BodyDef bodyDef;
    public float posX, posY, rayon;
    public static World world;
    Camera camera;
    public boolean ballActive, startImpulse, ballLaserContact;
    private Vector2 vectorSpeed;
    public float maxSpeed;
    public int initY, rebond;
    public long balleLaserTime;

    private ShapeRenderer shapeRenderer;

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
        rayon = camera.viewportWidth/50;

        maxSpeed = GameConstants.vitesseBalle * camera.viewportHeight;

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
        body.setUserData("Ball");

        System.out.println("Balle initi??e");

        /******************* TEST DESSIN ********************/
        shapeRenderer = new ShapeRenderer();
        //shapeRenderer.setProjectionMatrix(camera.combined);

        //shapeRenderer.scale(GameConstants.BOX_TO_WORLD,GameConstants.BOX_TO_WORLD,1);

    }

    public void Active(){
        //Limitation de la vitesse de la balle
        maxSpeed = GameConstants.vitesseBalle * camera.viewportHeight;
        vectorSpeed = body.getLinearVelocity();
        body.setLinearVelocity(vectorSpeed.clamp(maxSpeed, maxSpeed));

        //D??placement de la balle
        if(startImpulse && !ballActive && !Gdx.input.isTouched()){
            ballActive = true;
            initY = MathUtils.random(-3,3);
            body.applyLinearImpulse(initY, 10, body.getPosition().x, body.getPosition().y, true);
        }

        //La balle ne sort pas de l'??cran
        if(body.getPosition().x - rayon < 0)
            body.setTransform(rayon, body.getPosition().y, 0);
        if(body.getPosition().x + rayon > camera.viewportWidth)
            body.setTransform(camera.viewportWidth - rayon, body.getPosition().y, 0);
        if(body.getPosition().y + rayon > camera.viewportHeight)
            body.setTransform(body.getPosition().x, camera.viewportHeight - rayon, 0);

        if (Gdx.input.isTouched()) {
            startImpulse = true;

            if(!ballActive){
                if(body.getPosition().x != GameConstants.WORLD_TO_BOX * Gdx.input.getX())
                    body.setLinearVelocity(-(body.getPosition().x - GameConstants.WORLD_TO_BOX * Gdx.input.getX()) * 50,0);
                else body.setLinearVelocity(0,0);
            }
        }
        else {
            if(!ballActive)
                body.setLinearVelocity(0,0);
        }

        //La balle laser ne dure que 3,6 secondes
        if (ballLaserContact && TimeUtils.millis() - balleLaserTime > 3600)
            this.body.setUserData("Ball");

        // Destruction de la balle
        /*
        if(body.getPosition().y < -2*getRadius()){
            body.setActive(false);
            world.destroyBody(body);
            destroy = true;
            System.out.println("Destroy");
        }
         */
    }

    public static void Destroy(final BrickBreaker2 game, Array<Ball> array){
        for(int i = array.size-1; i>-1; i--){
            Ball ball = array.get(i);

            if(ball.body.getPosition().y < -2*ball.getRadius()){
                ball.body.setActive(false);
                world.destroyBody(ball.body);
                array.removeIndex(i);

                System.out.println("Destroy");
                game.pools.free(ball);
            }
        }
    }

    public void draw(){
        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.circle(   body.getPosition().x * GameConstants.BOX_TO_WORLD,
                                body.getPosition().y*GameConstants.BOX_TO_WORLD,
                            getRadius()*GameConstants.BOX_TO_WORLD);
        shapeRenderer.end();
    }
}
