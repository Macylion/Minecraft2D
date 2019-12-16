package com.github.macylion;

import java.awt.Color;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.macylion.entities.Player;
import com.github.macylion.overworld.Overworld;
import com.github.macylion.texturebank.TextureBank;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Minecraft2D extends ApplicationAdapter {
	final int WIDTH = 1024;
	final int HEIGHT = 768;
	SpriteBatch batch;
	SpriteBatch GUIbatch;
	TextureBank txtBank;
	OrthographicCamera cam;
	Viewport viewport;
	Overworld overworld;
	Player player;
	//debug
	BitmapFont debugFont;
	
	@Override
	public void create () {
		System.out.println("[SYSTEM] Game starting...");
		
		batch = new SpriteBatch();
		GUIbatch = new SpriteBatch();
		txtBank = new TextureBank();
		loadTextures();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		viewport = new FillViewport(1024, 768, cam);
		overworld = new Overworld(WIDTH, HEIGHT);
		player = new Player(new Vector2(0, overworld.highSpawn()+8), overworld.getWorld(), txtBank);
		overworld.setSunFilter(player.getFilter());
		//debug
		debugFont = new BitmapFont();

		System.out.println("[SYSTEM] Game started.");
	}
	
	public void loadTextures() {
		System.out.println("[SYSTEM] Loading textures...");
		
		txtBank.addTexture("none.png", "none");
		
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
		
		System.out.println("[SYSTEM] Textures loaded.");
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0.53f, 0.81f, 0.95f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		player.draw(batch);
		overworld.draw(txtBank, batch, cam);
		batch.end();
		
		GUIbatch.begin();
		//debug
		debugFont.draw(GUIbatch, "FrameID: " + Gdx.graphics.getFrameId(), 12, 760);
		debugFont.draw(GUIbatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 12, 740);
		debugFont.draw(GUIbatch, "Player X: " + player.getX(), 12, 720);
		debugFont.draw(GUIbatch, "Player Y: " + player.getY(), 12, 700);
		debugFont.draw(GUIbatch, "Player Velocity X: " + player.getBody().getLinearVelocity().x, 12, 680);
		debugFont.draw(GUIbatch, "Player Velocity Y: " + player.getBody().getLinearVelocity().y, 12, 660);
		debugFont.draw(GUIbatch, "Jump: " + player.isJump(), 12, 640);
		debugFont.draw(GUIbatch, "Animation: " + player.getCurrentAnim(), 12, 620);
		debugFont.draw(GUIbatch, "deltaTime: " + Gdx.graphics.getDeltaTime(), 12, 600);
		debugFont.draw(GUIbatch, "zoom: " + cam.zoom, 12, 580);
		//end debug
		GUIbatch.end();
	}
	
	private void update() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		overworld.update(cam);
		player.update(this.overworld.blocks);
		cam.position.set(this.player.getPosition(), 0);
		overworld.setSunPosition(player);
		if(Gdx.input.isKeyJustPressed(Keys.PLUS) && cam.zoom < 1)
			cam.zoom += 0.1f;
		if(Gdx.input.isKeyJustPressed(Keys.MINUS) && cam.zoom > 0.2f)
			cam.zoom -= 0.1f;
		//debug
		if(Gdx.input.isButtonJustPressed(Buttons.LEFT))
			System.out.println("[DEBUG] CLICK POS: " + Gdx.input.getX() + " : " + Gdx.input.getY());
	}
	
	@Override
	public void resize(int width, int height) {
        viewport.update(width, height);
    }

	@Override
	public void dispose () {
		batch.dispose();
		txtBank.dispose();
		overworld.dispose();
		GUIbatch.dispose();
	}
}
