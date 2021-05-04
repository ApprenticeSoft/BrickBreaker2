package com.apprenticesoft.brickbreaker2.screens;

import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.apprenticesoft.brickbreaker2.BrickBreaker2;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadingScreen implements Screen {

    final BrickBreaker2 game;
    OrthographicCamera camera;
    private Texture textureLogo;
    private Image imageLogo;
    private Stage stage;

    public LoadingScreen(final BrickBreaker2 game){
        this.game = game;

        System.out.println("TEST");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1, 1);
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
}
