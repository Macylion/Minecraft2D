package com.github.macylion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.macylion.texturebank.TextureBank;

public class Minecraft2D extends ApplicationAdapter {
	final int WIDTH = 1024;
	final int HEIGHT = 768;
	SpriteBatch batch;
	TextureBank txtBank;
	OrthographicCamera cam;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		txtBank = new TextureBank();
		txtBank.addTexture("badlogic.jpg", "badlogic");
		cam = new OrthographicCamera(WIDTH, HEIGHT);
	}

	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(txtBank.getTexture("badlogic"), 0, 1);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		txtBank.dispose();
	}
}
