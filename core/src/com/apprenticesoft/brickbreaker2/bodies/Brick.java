package com.apprenticesoft.brickbreaker2.bodies;


import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.apprenticesoft.brickbreaker2.utils.GameConstants;
import com.apprenticesoft.brickbreaker2.utils.BrickEnum;
//import com.minimal.brick.breaker.free.Couleurs;

public class Brick extends PolygonShape{

    public float opacite;
    public boolean visible;
    public int durete;
    public Body body;
    public BodyDef bodyDef;
    public float posX, posY, width, height;
    static World world;
    BrickEnum brickEnum;
    Camera camera;
    //Couleurs couleurs = new Couleurs(GameConstants.groupeSelectione);

    public Brick(BrickBreaker2 game) {    }

    public void init(World world, Camera camera, float posX, float posY, BrickEnum brickEnum){
        this.world = world;
        this.camera = camera;
        this.posX = posX;
        this.posY = posY;
        this.brickEnum = brickEnum;

        opacite = 1;
        visible = true;
        durete = 1;

        switch(brickEnum){
            case rectangleH :
                width = camera.viewportWidth/24;
                height = width/2;
                break;
            case rectangleV :
                height = camera.viewportWidth/24;
                width = height/2;
                break;
            case carre :
                height = camera.viewportWidth/26;
                width = height;
                break;
            default :
                width = camera.viewportWidth/24;
                height = width/2;
                break;
        }

        bodyDef = new BodyDef();
        this.setAsBox(width, height);
        bodyDef.position.set(new Vector2(posX, posY));

        if(GameConstants.microgravite){
            bodyDef.type = BodyType.DynamicBody;
            body = world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = this;
            fixtureDef.density = 160.0f;
            fixtureDef.friction = 100.0f;
            fixtureDef.restitution = 0.3f;
            body.createFixture(fixtureDef);
        }
        else{
            body = world.createBody(bodyDef);
            body.createFixture(this, 0.0f);
        }

        body.setUserData("Brick");
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public float getX(){
        return posX;
    }

    public float getY(){
        return posY;
    }

    public void setX( float X){
        posX = X;
    }

    public void setY( float Y){
        posY = Y;
    }

    public void setPosition(float X, float Y){
        posX = X;
        posY = Y;

        this.body.setTransform(new Vector2(posX, posY), this.body.getAngle());
    }

    public void Collision(){
        if(durete > 1)
            durete--;
        else {
            durete--;
            opacite = 0;
            GameConstants.bricksDetruites++;
            visible = false;
        }

        System.out.println("Collision!! Dureté: " + durete);
    }

    public void Destruction(){
        durete = 0;
        opacite = 0;
        GameConstants.bricksDetruites++;
        visible = false;

        System.out.println("Destruction!!");
    }

    public void Edition(){
        if(durete > 1){
            durete--;
            opacite = 1;
        }
        else if(durete == 1){
            durete = 0;
            opacite = 0;
        }
        else {
            durete = 4;
            opacite = 1;
        }
    }

    public void drawOmbre(SpriteBatch batch, TextureRegion textureRegion){
        batch.setColor(0,0,0,0.2f);

        batch.draw(textureRegion,
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().x - this.getWidth()) + Gdx.graphics.getWidth()/80,
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().y - this.getHeight()) - Gdx.graphics.getWidth()/68,
                GameConstants.BOX_TO_WORLD * this.getWidth() /*+ Gdx.graphics.getWidth()/80*/,
                GameConstants.BOX_TO_WORLD * this.getHeight() /*- Gdx.graphics.getWidth()/68*/,
                GameConstants.BOX_TO_WORLD * 2 * this.getWidth(),
                GameConstants.BOX_TO_WORLD * 2 * this.getHeight(),
                1,
                1,
                body.getAngle()*MathUtils.radiansToDegrees);
    }

    /*
    public void draw(SpriteBatch batch, TextureRegion textureRegion){
        if(durete == 0)
            batch.setColor(couleurs.getCouleur0());
        else if(durete == 1)
            batch.setColor(couleurs.getCouleur1());
        else if(durete == 2)
            batch.setColor(couleurs.getCouleur2());
        else if(durete == 3)
            batch.setColor(couleurs.getCouleur3());
        else if(durete == 4)
            batch.setColor(couleurs.getCouleur4());

        batch.draw(textureRegion,
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().x - this.getWidth()),
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().y - this.getHeight()),
                GameConstants.BOX_TO_WORLD * this.getWidth(),
                GameConstants.BOX_TO_WORLD * this.getHeight(),
                GameConstants.BOX_TO_WORLD * 2 * this.getWidth(),
                GameConstants.BOX_TO_WORLD * 2 * this.getHeight(),
                1,
                1,
                body.getAngle()*MathUtils.radiansToDegrees);
    }
     */

    public static void Destroy(final BrickBreaker2 game, Array<Brick> array){
        for(int i = 0; i < array.size; i++){
            Brick brick = array.get(i);

            if(!brick.visible){
                brick.body.setActive(false);
                world.destroyBody(brick.body);
                array.removeIndex(i);
                System.out.println("Brique détruite!!");

                game.pools.free(brick);
            }
        }
    }

    /*
    public void setCouleur(int i){
        couleurs.setGroupe(i);
    }
     */
}

