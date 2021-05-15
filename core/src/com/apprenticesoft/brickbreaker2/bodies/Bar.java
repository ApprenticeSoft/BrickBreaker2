package com.apprenticesoft.brickbreaker2.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.apprenticesoft.brickbreaker2.utils.GameConstants;
import com.badlogic.gdx.utils.ShortArray;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;

public class Bar extends PolygonShape{

    public float opacite, centre, ecart;
    public boolean visible;
    public int durete;
    public Body body, body2;
    public BodyDef bodyDef;
    public FixtureDef fixtureDef;
    public float width, height;
    World world;
    Camera camera;

    // Test dessin
    private PolygonSprite polySprite;

    public Bar(World world, Camera camera){
        super();
        this.world = world;
        this.camera = camera;

        width = 1.01f*camera.viewportWidth/15;
        height = camera.viewportHeight/100;
        centre = camera.viewportWidth/2;
        ecart = width * GameConstants.barreNormale;
        this.setAsBox(width , height);

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.KinematicBody;

        fixtureDef = new FixtureDef();
        fixtureDef.shape = this;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1f;

        bodyDef.position.set(new Vector2(centre - ecart, 1.2f*camera.viewportHeight/9));
        body = world.createBody(bodyDef);
        body.setUserData("Bar");
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        bodyDef.position.set(new Vector2(centre + ecart, 1.2f*camera.viewportHeight/9));
        body2 = world.createBody(bodyDef);
        body2.setUserData("Bar");
        body2.createFixture(fixtureDef);
        body2.setFixedRotation(true);

        /******************* TEST DESSIN ********************/
        Vector2 vertex = new Vector2();

        float coordPoly[] = new float [this.getVertexCount()*2];
        for(int i = 0; i < this.getVertexCount(); i++){
            this.getVertex(i, vertex);
            coordPoly[2*i] = vertex.x;
            coordPoly[2*i + 1] = vertex.y;
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
        //polySprite.setColor(223/256f,73/256f,73/256f, 1f);
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void draw(SpriteBatch batch, TextureRegion textureRegion){
        batch.setColor(0,0,0,0.2f);
        batch.draw(textureRegion,
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().x - this.getWidth()) + Gdx.graphics.getWidth()/80,
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().y - this.getHeight()) - Gdx.graphics.getWidth()/68,
                GameConstants.BOX_TO_WORLD * (2 * this.getWidth() + this.body2.getPosition().x - this.body.getPosition().x),
                GameConstants.BOX_TO_WORLD * 2 * this.getHeight());
        batch.setColor(1,1,1,1);
        batch.draw(textureRegion,
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().x - this.getWidth()),
                GameConstants.BOX_TO_WORLD * (this.body.getPosition().y - this.getHeight()),
                GameConstants.BOX_TO_WORLD * (2 * this.getWidth() + this.body2.getPosition().x - this.body.getPosition().x),
                GameConstants.BOX_TO_WORLD * 2 * this.getHeight());
    }

    public void draw(PolygonSpriteBatch polyBatch){
        polySprite.setX(body.getPosition().x);
        polySprite.setY(body.getPosition().y);
        polySprite.draw(polyBatch);
        polySprite.setX(body2.getPosition().x);
        polySprite.setY(body2.getPosition().y);
        polySprite.draw(polyBatch);
    }

    public void deplacement(){
        //D�placement de la barre
        if (Gdx.input.isTouched()) {
            centre = GameConstants.WORLD_TO_BOX * Gdx.input.getX();

            //La barre ne sort pas de l'�cran
            if(centre < width + ecart)
                centre = width + ecart;
            if(centre > camera.viewportWidth - width - ecart)
                centre = camera.viewportWidth - width - ecart;
        }
        else {
            this.body.setLinearVelocity(0,0);
        }

        //Modification de la taille de la barre
        setEcart(GameConstants.ecart);

        //Les deux parties de la barre suivent la position X
        if(this.body.getPosition().x != centre - ecart)
            this.body.setLinearVelocity(-(this.body.getPosition().x - (centre - ecart)) * 45,0);
        else this.body.setLinearVelocity(0,0);

        if(this.body2.getPosition().x != centre + ecart)
            this.body2.setLinearVelocity(-(this.body2.getPosition().x - (centre + ecart)) * 45,0);
        else this.body2.setLinearVelocity(0,0);
    }

    public void setEcart(float nEcart){
        this.ecart = width * nEcart;
    }

    public float getPosition(){
        return centre;
    }

    public float getLargeur(){
        return width + ecart;
    }


}
