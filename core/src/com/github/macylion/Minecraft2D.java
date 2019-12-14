package com.github.macylion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.macylion.texturebank.TextureBank;

public class Minecraft2D extends ApplicationAdapter {
	final int WIDTH = 1024;
	final int HEIGHT = 768;
	SpriteBatch batch;
	TextureBank txtBank;
	OrthographicCamera cam;
	//debug
	Vector2 debugPos;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		txtBank = new TextureBank();
		txtBank.addTexture("badlogic.jpg", "badlogic");
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		//debug
		debugPos = new Vector2(0, 0);
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(txtBank.getTexture("badlogic"), 0, 1);
		batch.end();
	}
	
	private void update() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		//debug
		float debugSpeed = 250;
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_6))
			debugPos.x += debugSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_4))
			debugPos.x -= debugSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_8))
			debugPos.y += debugSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_2))
			debugPos.y -= debugSpeed * Gdx.graphics.getDeltaTime();
		cam.position.set(debugPos, 0);
	}

	@Override
	public void dispose () {
		batch.dispose();
		txtBank.dispose();
	}
}
