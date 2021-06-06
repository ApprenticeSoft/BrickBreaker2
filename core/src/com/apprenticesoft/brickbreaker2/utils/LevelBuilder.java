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
    float posX, posY;

    public LevelBuilder(final BrickBreaker2 game, World world, Camera camera, Array<Brick> bricks){
        this.game = game;
        this.bricks = bricks;
        this.world = world;
        this.camera = camera;
    }

    public void Build(int niveau, int ligne, int colonne, float margin, BrickEnum briqueEnum){
        for(int i = 0; i < (ligne); i++){
            for(int j = 0; j < colonne; j++){
                Brick brick = (Brick)game.pools.obtain(Brick.class);
                brick.init(world, camera, 0, 0, 0, briqueEnum);
                bricks.add(brick);
                brick.body.setUserData("Brick");

                switch (niveau){
                    case 1:
                        posX = (j + 1)*((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1)) + (1 + j*2)*brick.width;
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));
                        break;
                    case 2:
                        if(i%2 == 1)
                        {
                            posX = (j + 1)*((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1)) + (1 + j*2)*brick.width;
                            posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));
                        }
                        else
                        {
                            posX = (j + 1)*((camera.viewportWidth - (colonne - 1)*2*brick.width)/(colonne)) + (1 + j*2)*brick.width;
                            posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));
                        }
                        break;
                    case 3:
                        if(i%2 == 1)
                        {
                            posX = (j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth();
                            posY = camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1));
                        }
                        else
                        {
                            posX = (j + 1.5f)*((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1)) + (j + 1) * 2 * brick.width;
                            posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));
                        }
                        break;
                    case 4:
                        if(j%2 == 1)
                        {
                            posX = (j + 1)*((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1)) + (1 + j*2)*brick.getWidth();
                            posY = camera.viewportHeight - (2*i + 1) * brick.getHeight() - i *((camera.viewportWidth - colonne*2*brick.getWidth())/(colonne + 1));
                        }
                        else
                        {
                            posX = (j + 1)*((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1)) + (1 + j*2)*brick.width;
                            posY = camera.viewportHeight - 2*(i + 1) * brick.height - (i + 0.5f) *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));
                        }
                        break;
                    case 5:
                        posX = (j + 1)*((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1)) + (1 + j*2)*brick.width;
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));
                        brick.body.setTransform(posX, posY, 45*MathUtils.degreesToRadians); //Rotation !
                        break;
                    case 6: // Briques triangles
                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        if((i+j)%2 == 0)
                        {
                            posY = posY + 2*brick.height/3;
                            brick.body.setTransform(posX, posY, 180*MathUtils.degreesToRadians);
                        }
                        break;
                    case 7:
                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        if((i+j)%2 == 0)
                        {
                            posY = posY + 2*brick.height/3;
                            brick.body.setTransform(posX, posY, 180*MathUtils.degreesToRadians);
                        }
                        break;
                    case 8:
                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        if((i+j)%2 == 0)
                        {
                            posY = posY + 2*brick.height/3;
                            brick.body.setTransform(posX, posY, 180*MathUtils.degreesToRadians);
                        }
                        break;
                    case 9:
                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        if(j%2 == 0)
                            brick.body.setTransform(posX, posY, 270*MathUtils.degreesToRadians);
                        else
                            brick.body.setTransform(posX, posY, 90*MathUtils.degreesToRadians);
                        break;
                    case 10:
                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        if(j%2 == 0)
                            brick.body.setTransform(posX, posY, 90*MathUtils.degreesToRadians);
                        else
                            brick.body.setTransform(posX, posY, -90*MathUtils.degreesToRadians);
                        break;
                    case 11:
                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        if(j%2 == 0)
                            brick.body.setTransform(posX, posY, 270*MathUtils.degreesToRadians);
                        else
                            brick.body.setTransform(posX, posY, 90*MathUtils.degreesToRadians);

                        break;
                    case 12:
                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        if(i%2 == 0){
                            posX -= brick.width/3;
                            brick.body.setTransform(posX, posY, 270*MathUtils.degreesToRadians);
                        }
                        else
                            brick.body.setTransform(posX, posY, 90*MathUtils.degreesToRadians);

                        break;
                    case 13:
                        float angle = -3;

                        posX = brick.width + margin + j*(camera.viewportWidth - 2*(brick.width + margin))/(colonne-1);
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));

                        brick.body.setTransform(posX, posY, j*angle*MathUtils.degreesToRadians);

                        break;
                    default:
                        posX = (j + 1)*((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1)) + (1 + j*2)*brick.width;
                        posY = camera.viewportHeight - (2*i + 1) * brick.height - i *((camera.viewportWidth - colonne*2*brick.width)/(colonne + 1));
                        break;
                }

                brick.setPosition(posX, posY);
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

}
