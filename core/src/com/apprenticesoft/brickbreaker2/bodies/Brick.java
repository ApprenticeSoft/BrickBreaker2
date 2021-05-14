package com.apprenticesoft.brickbreaker2.bodies;


import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.apprenticesoft.brickbreaker2.utils.GameConstants;
import com.apprenticesoft.brickbreaker2.utils.BrickEnum;
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

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.ShortArray;

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
    private PolygonSprite polySprite;

    public Brick(BrickBreaker2 game) {    }

    public void init(World world, Camera camera, float posX, float posY, float angle, BrickEnum brickEnum){
        this.world = world;
        this.camera = camera;
        this.posX = posX;
        this.posY = posY;
        this.brickEnum = brickEnum;

        opacite = 1;
        visible = true;
        durete = 1;

        switch(brickEnum){
            case rectangle :
                width = camera.viewportWidth/24;
                height = width/2;
                this.setAsBox(width, height);
                break;
            case carre :
                height = camera.viewportWidth/26;
                width = height;
                this.setAsBox(width, height);
                break;
            case triangle :
                width = camera.viewportWidth/24;
                height = (float)Math.sqrt(3*width*width/4);

                Vector2 coordinates [] = new Vector2 [3];
                coordinates[0] = new Vector2(-width,-2*height/3);
                coordinates[1] = new Vector2(width,-2*height/3);
                coordinates[2] = new Vector2(0,2*2*height/3);
                this.set(coordinates);
                break;
            default :
                width = camera.viewportWidth/24;
                height = width/2;
                break;
        }

        bodyDef = new BodyDef();
        //this.setAsBox(width, height);
        bodyDef.position.set(new Vector2(posX, posY));
        bodyDef.angle = MathUtils.degreesToRadians * angle;

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

        /******************* TEST DESSIN ********************/
        System.out.println("Vertex count = " + this.getVertexCount());
        Vector2 vertex = new Vector2();
        this.getVertex(0, vertex);
        System.out.println("Vertex 0 = " + vertex);

        float coordPoly[] = new float [this.getVertexCount()*2];
        for(int i = 0; i < this.getVertexCount(); i++){
            this.getVertex(i, vertex);
            System.out.println("Vertex = " + vertex);
            coordPoly[2*i] = vertex.x;
            coordPoly[2*i + 1] = vertex.y;

            System.out.println("Coord = " + coordPoly[2*i] + " || " + coordPoly[2*i +1]);
        }

        // Creating the color filling (but textures would work the same way)
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(1f, 1f, 1f, 1);
        pix.fill();
        Texture textureSolid = new Texture(pix);
        TextureRegion textureRegion = new TextureRegion(textureSolid);

        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        ShortArray triangleIndices = triangulator.computeTriangles(coordPoly);

        PolygonRegion polyReg = new PolygonRegion(textureRegion, coordPoly, triangleIndices.toArray());

        polySprite = new PolygonSprite(polyReg);
        polySprite.setOrigin(body.getLocalCenter().x, body.getLocalCenter().y);
        polySprite.setColor(223/256f,73/256f,73/256f, 1f);
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
    public void draw(PolygonSpriteBatch polyBatch){
        polySprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        polySprite.setX(body.getPosition().x);
        polySprite.setY(body.getPosition().y);
        polySprite.draw(polyBatch);
    }

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

