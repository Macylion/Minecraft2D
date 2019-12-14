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
		loadTextures();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		//debug
		debugPos = new Vector2(0, 0);
	}
	
	public void loadTextures() {
		txtBank.addTexture("blocks/brick.png", "b-brick");
		txtBank.addTexture("blocks/copper_ore.png", "b-copper-ore");
		txtBank.addTexture("blocks/dirt_grass.png", "b-dirt-grass");
		txtBank.addTexture("blocks/dirt.png", "b-dirt");
		txtBank.addTexture("blocks/glass.png", "b-glass");
		txtBank.addTexture("blocks/iron_ore.png", "b-iron-ore");
		txtBank.addTexture("blocks/rock.png", "b-rock");
		txtBank.addTexture("blocks/sand.png", "b-sand");
		txtBank.addTexture("blocks/tin_ore.png", "b-tin-ore");
		txtBank.addTexture("blocks/void.png", "b-void");
		txtBank.addTexture("blocks/wood_leaf.png", "b-wood-leaf");
		txtBank.addTexture("blocks/wood_log.png", "b-wood-log");
		txtBank.addTexture("blocks/wood.png", "b-wood");
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(txtBank.getTexture("b-brick"), 0, 0);
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
