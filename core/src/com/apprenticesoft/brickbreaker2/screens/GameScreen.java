package com.apprenticesoft.brickbreaker2.screens;

import com.apprenticesoft.brickbreaker2.BrickBreaker2;
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
    Stage stage;

    private Bar bar;

    //Box2D debug
    Box2DDebugRenderer debug;

    private Array<Ball> balls;

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

        balls = new Array<Ball>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.3f, 0.7f, 1);
        debug.render(world, camera.combined);

        // Debug box2d
        world.step(GameConstants.BOX_STEP, GameConstants.BOX_VELOCITY_ITERATIONS, GameConstants.BOX_POSITION_ITERATIONS);
        //System.out.println("Nombre de body: " + world.getBodyCount());
        //System.out.println("balls.size: " + balls.size);

        if (Gdx.input.justTouched()) {
            spawnBall(  Gdx.input.getX()* GameConstants.WORLD_TO_BOX,
                        camera.viewportHeight - Gdx.input.getY()* GameConstants.WORLD_TO_BOX,
                        new Vector2(MathUtils.random(-0.5f,0.5f),5f));
        }

        bar.deplacement();

        // Activité des balles
        for (int i = balls.size-1; i>-1; i--){
            balls.get(i).Active();
            if (balls.get(i).destroy){
                game.pools.free(balls.get(i));
                balls.removeIndex(i);
            }
        }
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
