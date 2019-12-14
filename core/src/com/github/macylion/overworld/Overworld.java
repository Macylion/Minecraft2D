package com.github.macylion.overworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.texturebank.TextureBank;

public class Overworld {
	
	private int screenWidth;
	private int screenHeight;
	private World world;
	ArrayList<Block> blocks;
	Rectangle renderRect;
	//debug
	Box2DDebugRenderer debugRenderer;
	boolean isDebug = false;
	
	public Overworld(int width, int height) {
		Box2D.init();
		this.screenWidth = width;
		this.screenHeight = height;
		this.world = new World(new Vector2(0, -10), true); 
		this.debugRenderer = new Box2DDebugRenderer();
		this.blocks = new ArrayList<Block>();
		this.renderRect = new Rectangle(0, 0, this.screenWidth, this.screenHeight);
		generateWorld();
	}
	
	private void generateWorld() {
		System.out.println("[WORLD] Generating world...");
		int worldLenX = 1024;
		int worldLenY = 768;
		ArrayList<Vector2> points = new ArrayList<Vector2>();
		/*float step = 100;
		float maxSur = 32;
		float minSur = 10;
		for(int i = 1024; i > 0; i -= step) {
			int ran = (int)(Math.random()*(maxSur - minSur) + minSur);
			points.add(new Vector2(i, ran));
		}*/
		Vector2 last = new Vector2(-1024, 14);
		for(int i = -1024; i <= 1024; i++) {
			points.add(new Vector2(last.x, last.y));
			last.x += 1;
			double ran = Math.random();
			if(ran >= 0.8f && last.y < 22)
				last.y += 1;
			else if(ran >= 0.6f && last.y > 12)
				last.y -= 1;
		}
			
		for(Vector2 p : points) {
			int layer = 0;
			for(int i = (int)p.y; i > -32; i--) {
				String txt = "b-rock";
				if(layer == 0) txt = "b-dirt-grass";
				else if(layer < 5) txt = "b-dirt";
				if(i == -31) txt = "b-void";
				this.blocks.add(new Block((int)p.x*32, i*32, txt, this.world));
				layer++;
			}
		}
		System.out.println("[WORLD] World generated successfully!");
	}

	public void draw(TextureBank bank, SpriteBatch batch, OrthographicCamera camera) {
		for(Block b : this.blocks)
			b.draw(batch, bank, this.renderRect);
		
		//debug
		if(this.isDebug)
			this.debugRenderer.render(world, camera.combined);
		if(Gdx.input.isKeyJustPressed(Keys.NUMPAD_0))
			this.isDebug = !this.isDebug;
	}
	
	public void update(OrthographicCamera cam) {
		this.world.step(1/60f, 6, 2);
		this.renderRect.setPosition(cam.position.x - (this.screenWidth/2), cam.position.y - (this.screenHeight/2));
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void dispose() {
		for(Block b : this.blocks)
			b.dispose();
	}

}
