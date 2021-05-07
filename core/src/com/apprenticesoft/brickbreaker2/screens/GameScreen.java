package com.apprenticesoft.brickbreaker2.screens;

import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.apprenticesoft.brickbreaker2.bodies.Brick;
import com.apprenticesoft.brickbreaker2.utils.BrickEnum;
import com.apprenticesoft.brickbreaker2.utils.GameConstants;
import com.apprenticesoft.brickbreaker2.bodies.Ball;
import com.apprenticesoft.brickbreaker2.bodies.Bar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen extends InputAdapter implements Screen {

    final BrickBreaker2 game;
    protected OrthographicCamera camera;
    protected World world;
    static int initY, son, spawnObjet;
    private PolygonShape boxBordure;
    private BodyDef bordureBodyDef;
    private Body bodyHaut, bodyGauche, bodyDroite;
    private Skin skin;
    private TextureAtlas textureAtlas;
    boolean spawn;
    float spawnX, spawnY;
    Stage stage;

    private Bar bar;
    private Ball ball;

    //Box2D debug
    Box2DDebugRenderer debug;

    private Array<Ball> balls;
    private Array<Brick> bricks;

    public GameScreen (final BrickBreaker2 game){
        this.game = game;

        System.out.println("Game screen");

        GameConstants.vitesseBalle = GameConstants.vitesseBalleNormale;
        GameConstants.ecart = GameConstants.barreNormale;
        GameConstants.bouclierActif = false;
        GameConstants.laserActif = false;
        GameConstants.tirs = 0;
        GameConstants.viesPerdues = 0;
        GameConstants.BOX_STEP = 1/60f;
        GameConstants.pause = false;
        GameConstants.niveauFini = false;

        world = new World(new Vector2(0, 0), true);
        World.setVelocityThreshold(0);

        GameConstants.ecart = GameConstants.barreNormale;
        spawn = false;

        camera = new OrthographicCamera();
        camera.viewportHeight = Gdx.graphics.getHeight() * GameConstants.WORLD_TO_BOX;
        camera.viewportWidth = Gdx.graphics.getWidth() * GameConstants.WORLD_TO_BOX;
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0f);
        camera.update();

        //Bordure de l'écran de jeu
        bordureBodyDef = new BodyDef();
        boxBordure = new PolygonShape();
        boxBordure.setAsBox(camera.viewportWidth/2, camera.viewportHeight/100);
        bordureBodyDef.position.set(new Vector2(camera.viewportWidth/2, 101*camera.viewportHeight/100));
        bodyHaut = world.createBody(bordureBodyDef);
        bodyHaut.setUserData("Haut");
        bodyHaut.createFixture(boxBordure, 0.0f);

        bordureBodyDef.position.set(new Vector2(-camera.viewportWidth/100, camera.viewportHeight/2));
        boxBordure.setAsBox(camera.viewportWidth/100, camera.viewportHeight/2);
        bodyGauche = world.createBody(bordureBodyDef);
        bodyGauche.setUserData("Rebord");
        bodyGauche.createFixture(boxBordure, 0.0f);

        bordureBodyDef.position.set(new Vector2(101*camera.viewportWidth/100, camera.viewportHeight/2));
        bodyDroite = world.createBody(bordureBodyDef);
        bodyDroite.setUserData("Rebord");
        bodyDroite.createFixture(boxBordure, 0.0f);

        debug = new Box2DDebugRenderer();


        //Pad
        bar = new Bar(world, camera);
        Ball ball = (Ball)game.pools.obtain(Ball.class);
        ball.init(world, camera,
                bar.getPosition(),
                bar.body.getPosition().y + bar.getHeight());

        balls = new Array<Ball>();
        balls.add(ball);

        bricks = new Array<Brick>();
        Brick brick1 = new Brick(world, camera, camera.viewportWidth/2, camera.viewportWidth/2, BrickEnum.rectangleH);
        brick1.setPosition(camera.viewportWidth/2, camera.viewportWidth/2);
        bricks.add(brick1);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.3f, 0.7f, 1);
        debug.render(world, camera.combined);

        // Debug box2d
        world.step(GameConstants.BOX_STEP, GameConstants.BOX_VELOCITY_ITERATIONS, GameConstants.BOX_POSITION_ITERATIONS);
        //System.out.println("Nombre de body: " + world.getBodyCount());
        //System.out.println("balls.size: " + balls.size);

        /*
        if (Gdx.input.justTouched()) {
            spawnBall(  Gdx.input.getX()* GameConstants.WORLD_TO_BOX,
                        camera.viewportHeight - Gdx.input.getY()* GameConstants.WORLD_TO_BOX,
                        new Vector2(MathUtils.random(-0.5f,0.5f),5f));
        }
         */

        bar.deplacement();

        // Activité des balles
        for (int i = balls.size-1; i>-1; i--){
            balls.get(i).Active();
            /*
            if (balls.get(i).destroy){
                game.pools.free(balls.get(i));
                balls.removeIndex(i);
            }
             */
        }
        Ball.Destroy(balls);
    }

    @Override
    public void show() {
        //Important pour pouvoir utiliser la touche BACK
        Gdx.input.setCatchBackKey(true);
        //Permet d'interagir avec les Actors du Stage
        Gdx.input.setInputProcessor(stage);

        world.setContactListener(new ContactListener(){
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if(a.getUserData() != null && b.getUserData() != null){
                    if(a.getUserData().equals("Brick") && b.getUserData().equals("Ball")){
                        //couleurEdit = MathUtils.random(couleurMin,couleurMax);
                        System.out.println("Contact avec une brique!!!");
                        for(Ball ball : balls){
                            if(ball.body == b)
                                ball.rebond = 0;
                        }

                        for(Brick brick : bricks){
                            if(brick.body == a){
                                brick.Collision();
                                /*
                                if(Donnees.getSon()){
                                    son = MathUtils.random(0,15);
                                    sons.getSons().get(son).play();
                                }
                                 */

                                //Apparition des objets
                                spawnObjet = MathUtils.random(1,100);
                                if(spawnObjet > 38 && spawnObjet < 65 && brick.opacite == 0 && GameConstants.bricksDetruites > 2){
                                    GameConstants.bricksDetruites = 0;
                                    spawn = true;
                                    spawnX = a.getPosition().x;
                                    spawnY = a.getPosition().y;
                                }
                            }
                        }
                    }
                    else if(a.getUserData().equals("Bar") && (b.getUserData().equals("Ball") || b.getUserData().equals("BallLaser"))){
                        if(b.getPosition().x > (bar.getPosition() - bar.getLargeur()) && b.getPosition().x < (bar.getPosition() + bar.getLargeur())){
                            System.out.println("Contact avec la barre!!!!");
                            for(Ball ball : balls){
                                if(ball.body == b)
                                    ball.rebond = 0;
                            }

                            b.setLinearVelocity(b.getLinearVelocity().rotate((30*(b.getPosition().x - bar.getPosition())/(bar.getLargeur()))));
                        }
                    }
                    else if(a.getUserData().equals("Rebord") && (b.getUserData().equals("Ball") || b.getUserData().equals("BallLaser"))){
                        System.out.println("Contact avec un rebord");
                        for(Ball ball : balls){
                            if(ball.body == b){
                                ball.rebond++;
                                if(ball.rebond > 3 && b.getLinearVelocity().y >= 0 ){
                                    b.applyLinearImpulse(0, 0.1f, b.getPosition().x, b.getPosition().y, true);
                                }
                                else if(ball.rebond > 3){
                                    b.applyLinearImpulse(0, -0.1f, b.getPosition().x, b.getPosition().y, true);
                                }
                            }
                        }
                    }
                    else if((a.getUserData().equals("Bouclier") && b.getUserData().equals("Ball")) ||
                            (a.getUserData().equals("Ball") && b.getUserData().equals("Bouclier"))){
                        GameConstants.bouclierActif = false;
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                // TODO Auto-generated method stub

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                //Ignore les collisions entre la ball et les objets
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if(a.getUserData() != null && b.getUserData() != null){
                    if(a.getUserData().equals("Brick") && b.getUserData().equals("BallLaser")){
                        //couleurEdit = MathUtils.random(couleurMin,couleurMax);			//Choix de la couleur pour le mode Epileptique
                        GameConstants.bricksDetruitesAuLaser++;
                        System.out.println(" Variables.bricksDetruitesAuLaser = " +  GameConstants.bricksDetruitesAuLaser);
                        contact.setEnabled(false);

                        for(Ball ball : balls){
                            if(ball.body == b){
                                ball.rebond = 0;

                                if(!ball.ballLaserContact){
                                    ball.ballLaserContact = true;
                                    //ball.ballLaserTime = TimeUtils.millis();
                                }
                            }
                        }

                        for(Brick brick : bricks){
                            if(brick.body == a){
                                brick.Destruction();
                                /*
                                if(Donnees.getSon()){
                                    son = MathUtils.random(0,15);
                                    sons.getSons().get(son).play();
                                }
                                 */

                                //Apparition des objets
                                spawnObjet = MathUtils.random(1,100);
                                if(spawnObjet > 42 && spawnObjet < 60 && GameConstants.bricksDetruites > 2){
                                    GameConstants.bricksDetruites = 0;
                                    spawn = true;
                                    spawnX = a.getPosition().x;
                                    spawnY = a.getPosition().y;
                                }
                            }
                        }
                    }
                    else if((a.getUserData().equals("Objet") && b.getUserData() != "Bar") ||
                            (b.getUserData().equals("Objet") && a.getUserData() != "Bar")){
                        contact.setEnabled(false);
                    }
                    else if((a.getUserData().equals("Laser") && b.getUserData() != "Brick") ||
                            (b.getUserData().equals("Laser") && a.getUserData() != "Brick")){
                        contact.setEnabled(false);
                    }
                    else if((a.getUserData().equals("Ball") || a.getUserData().equals("BallLaser")) &&
                            (b.getUserData().equals("Ball") || b.getUserData().equals("BallLaser"))){
                        contact.setEnabled(false);
                    }
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                /*
                //Permet d'activer la destruction de l'objet touché par la bar, dans le renderer, sans faire planter l'osti de programme
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if(a.getUserData() != null && b.getUserData() != null){
                    if(a.getUserData().equals("Objet") && b.getUserData().equals("Bar") || a.getUserData().equals("Bar") && b.getUserData().equals("Objet")){
                        for(int i = 0; i < objets.size; i++){
                            if(objets.get(i).body == a || objets.get(i).body == b){
                                objets.get(i).body.setAwake(false);
                            }
                        }
                    }
                    //Destruction du laser et des bricks par le laser
                    else if(a.getUserData().equals("Laser") && b.getUserData().equals("Brick") || a.getUserData().equals("Brick") && b.getUserData().equals("Laser")){
                        couleurEdit = MathUtils.random(couleurMin,couleurMax);

                        for(int i = 0; i < lasers.size; i++){
                            if(lasers.get(i).body == a || lasers.get(i).body == b){
                                lasers.get(i).visible = false;
                            }
                        }
                        for(Brick brick : bricks){
                            if(brick.body == a || brick.body == b){
                                brick.Collision();
                                if(Donnees.getSon()){
                                    son = MathUtils.random(0,15);
                                    sons.getSons().get(son).play();
                                }

                                //Apparition des objets
                                spawnObjet = MathUtils.random(1,100);
                                if(spawnObjet > 45 && spawnObjet < 70 && brick.opacite == 0 && GameConstants.bricksDetruites > 2){
                                    GameConstants.bricksDetruites = 0;
                                    spawn = true;
                                    spawnX = brick.body.getPosition().x;
                                    spawnY = brick.body.getPosition().y;
                                }
                            }
                        }
                    }
                }
                */
            }
        });

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void spawnBall(float x, float y, Vector2 speed){
        //Ball ball = new Ball(world, camera, x, y);

        Ball ball = (Ball)game.pools.obtain(Ball.class);

        System.out.println(world.getClass());
        System.out.println(camera.getClass());
        System.out.println(ball.getClass());

        ball.init(world, camera, x, y);
        ball.ballActive = true;
        ball.startImpulse = true;
        balls.add(ball);
        ball.body.applyLinearImpulse(speed, new Vector2(ball.body.getPosition().x, ball.body.getPosition().y), true);
        //lastDropTime = TimeUtils.nanoTime();
    }
}
