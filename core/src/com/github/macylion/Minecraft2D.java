package com.github.macylion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.github.macylion.overworld.Overworld;
import com.github.macylion.texturebank.TextureBank;

public class Minecraft2D extends ApplicationAdapter {
	final int WIDTH = 1024;
	final int HEIGHT = 768;
	SpriteBatch batch;
	TextureBank txtBank;
	OrthographicCamera cam;
	Overworld overworld;
	//debug
	Vector2 debugPos;
	float debugZoom = 0.2f;
	float debugSpeed = 250.0f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		txtBank = new TextureBank();
		loadTextures();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		overworld = new Overworld(WIDTH, HEIGHT);
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
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		overworld.draw(txtBank, batch, cam);
		batch.end();
	}
	
	private void update() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		overworld.update();
		//debug
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_6))
			debugPos.x += debugSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_4))
			debugPos.x -= debugSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_8))
			debugPos.y += debugSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.NUMPAD_2))
			debugPos.y -= debugSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyJustPressed(Keys.PLUS))
			debugZoom -= 0.2f;
		if(Gdx.input.isKeyJustPressed(Keys.MINUS))
			debugZoom += 0.2f;
		if(Gdx.input.isKeyJustPressed(Keys.NUMPAD_9)) {
			debugSpeed += 50;
			System.out.println("[DEBUG] CAM SPEED: " + debugSpeed);
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUMPAD_7)) {
			debugSpeed -= 50;
			System.out.println("[DEBUG] CAM SPEED: " + debugSpeed);
		}
		cam.zoom = debugZoom;
		cam.position.set(debugPos, 0);
	}

	@Override
	public void dispose () {
		batch.dispose();
		txtBank.dispose();
		overworld.dispose();
	}
}
