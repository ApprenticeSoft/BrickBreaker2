package com.apprenticesoft.brickbreaker2.utils;

import com.badlogic.gdx.utils.Pool;
import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.apprenticesoft.brickbreaker2.bodies.Ball;
import com.apprenticesoft.brickbreaker2.bodies.Brick;

public class ObjectPools {

    final BrickBreaker2 game;

    public Pool<Ball> ballPool = new Pool<Ball>(){
        @Override
        protected Ball newObject() {
            return new Ball(game);
        }
    };

    public Pool<Brick> brickPool = new Pool<Brick>(){
        @Override
        protected Brick newObject() {
            return new Brick(game);
        }
    };

    public ObjectPools(BrickBreaker2 game) {
        this.game = game;
    }

    public Object obtain(Class object) {
        System.out.println("Object = " + object.toString());
        if (object.toString().equals("class com.apprenticesoft.brickbreaker2.bodies.Ball")) {
            System.out.println("Nouvelle balle créée");
            return this.ballPool.obtain();
        }
        else if (object.toString().equals("class com.apprenticesoft.brickbreaker2.bodies.Brick")) {
            System.out.println("Nouvelle brique créée");
            return this.brickPool.obtain();
        }
        else {
            System.out.println("Pas d'objet créé");
            return null;
        }
    }

    public void free(Object object) {
        if (object.getClass().toString().equals("class com.apprenticesoft.brickbreaker2.bodies.Ball")) {
            System.out.println("Balle mise dans le pool");
            ballPool.free((Ball) object);
        }
        else if (object.getClass().toString().equals("class com.apprenticesoft.brickbreaker2.bodies.Brick")) {
            System.out.println("Brique mise dans le pool");
            brickPool.free((Brick) object);
        }
    }

}
