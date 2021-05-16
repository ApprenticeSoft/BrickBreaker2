package com.apprenticesoft.brickbreaker2.utils;

import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.apprenticesoft.brickbreaker2.bodies.Brick;
import com.apprenticesoft.brickbreaker2.utils.BrickEnum;

public class LevelBuilder {

    final BrickBreaker2 game;
    private Array<Brick> bricks;
    private World world;
    private Camera camera;

    public LevelBuilder(final BrickBreaker2 game, World world, Camera camera, Array<Brick> bricks){
        this.game = game;
        this.bricks = bricks;
        this.world = world;
        this.camera = camera;
    }

    public void Build(int niveau, int ligne, int colonne, float margin, BrickEnum briqueEnum){
        if(niveau == 1){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);
                    brick.setPosition(  (j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth(),
                                        camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));
                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }

        else if(niveau == 2){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);
                    if(i%2 == 1)
                        brick.setPosition((j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth(),
                                camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));
                    else
                        brick.setPosition((j + 1)*((camera.viewportWidth - (colonne - 1)*2*brick.getWidth())/(colonne)) + (1 + j*2)*brick.getWidth(),
                                camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));

                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }

            for(int i = 0; i < bricks.size; i++){
                if(bricks.get(i).body.getPosition().x + bricks.get(i).getWidth() > camera.viewportWidth){
                    bricks.get(i).body.setActive(false);
                    world.destroyBody(bricks.get(i).body);
                    bricks.removeIndex(i);
                }
            }
        }

        else if(niveau == 3){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);
                    if(i%2 == 1)
                        brick.setPosition((j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth(),
                                camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));
                    else
                        brick.setPosition((j + 1.5f)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (j + 1) * 2 * brick.getWidth(),
                                camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));

                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }

            for(int i = 0; i < bricks.size; i++){
                if(bricks.get(i).body.getPosition().x + bricks.get(i).getWidth() > camera.viewportWidth){
                    bricks.get(i).body.setActive(false);
                    world.destroyBody(bricks.get(i).body);
                    bricks.removeIndex(i);
                }
            }
        }

        else if(niveau == 4){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);
                    if(j%2 == 1)
                        brick.setPosition((j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth(),
                                camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));
                    else
                        brick.setPosition((j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth(),
                                camera.viewportHeight - 2*(i + 1) * brick.getHeight() - (i + 0.5f) *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));

                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }

        else if(niveau == 5){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);
                    brick.setPosition((j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth(),
                            camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)));
                    bricks.add(brick);
                    brick.body.setTransform(brick.body.getPosition().x, brick.body.getPosition().y, 45*MathUtils.degreesToRadians); //Rotation !
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }
        // Niveaux pour briques triangles
        else if(niveau == 6){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);

                    float posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                    float posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                    brick.setPosition(posX, posY);
                    if((i+j)%2 == 0)
                        brick.body.setTransform(posX, posY + 2*brick.height/3, 180*MathUtils.degreesToRadians);
                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }
        else if(niveau == 7){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);

                    float posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                    float posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                    brick.setPosition(posX, posY);
                    if(i%2 == 0)
                        brick.body.setTransform(posX, posY + 2*brick.height/3, 180*MathUtils.degreesToRadians);
                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }
        else if(niveau == 8){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 0, briqueEnum);

                    float posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                    float posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                    brick.setPosition(posX, posY);
                    if(j%2 == 0)
                        brick.body.setTransform(posX, posY + 2*brick.height/3, 180*MathUtils.degreesToRadians);
                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }
        else if(niveau == 9){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, 90, briqueEnum);

                    float posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                    float posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                    brick.setPosition(posX, posY);
                    if(j%2 == 0)
                        brick.body.setTransform(posX, posY, 270*MathUtils.degreesToRadians);
                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }
        else if(niveau == 10){
            for(int i = 0; i < (ligne); i++){
                for(int j = 0; j < colonne; j++){
                    Brick brick = (Brick)game.pools.obtain(Brick.class);
                    brick.init(world, camera, 0, 0, -90, briqueEnum);

                    float posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                    float posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                    brick.setPosition(posX, posY);
                    if(j%2 == 0)
                        brick.body.setTransform(posX, posY, 90*MathUtils.degreesToRadians);
                    bricks.add(brick);
                    brick.body.setUserData("Brick");
                    brick.dispose();
                }
            }
        }
    }

}
