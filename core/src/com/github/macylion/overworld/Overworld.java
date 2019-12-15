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
import com.github.macylion.entities.Player;
import com.github.macylion.texturebank.TextureBank;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Overworld {
	
	int screenWidth;
	int screenHeight;
	World world;
	RayHandler ray;
	public ArrayList<Block> blocks;
	Rectangle renderRect;
	PointLight sun;
	PointLight sunR;
	PointLight sunL;
	//debug
	Box2DDebugRenderer debugRenderer;
	boolean isDebug = false;
	
	public Overworld(int width, int height) {
		Box2D.init();
		this.screenWidth = width;
		this.screenHeight = height;
		this.world = new World(new Vector2(0, -100), true); 
		this.ray = new RayHandler(this.world);
		this.ray.setShadows(true);
		
		this.sun = new PointLight(this.ray, 32);
		this.sun.setPosition(0, 768);
		this.sun.setColor(0f, 0f, 0f, 1);
		this.sun.setDistance(768*2);
		this.sun.setSoftnessLength(128);
		
		this.sunL = this.sun;
		this.sunR = this.sun;
		
		this.debugRenderer = new Box2DDebugRenderer();
		this.blocks = new ArrayList<Block>();
		this.renderRect = new Rectangle(0, 0, this.screenWidth, this.screenHeight);
		generateWorld();
	}
	
	public void generateWorld() {
		System.out.println("[WORLD] Generating world...");
		ArrayList<Vector2> points = new ArrayList<Vector2>();
		Vector2 last = new Vector2(-512, 14);
		for(int i = -512; i <= 512; i++) {
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
	
	public int highSpawn() {
		int high = 0;
		for(Block b : this.blocks)
			if(b.x == 0 && b.y > high)
				high = (int) b.y+32;
		return high;
	}

	public void draw(TextureBank bank, SpriteBatch batch, OrthographicCamera camera) {
		for(Block b : this.blocks)
			b.draw(batch, bank, this.renderRect);
		
		//debug
		if(this.isDebug)
			this.debugRenderer.render(world, camera.combined);
		if(Gdx.input.isKeyJustPressed(Keys.NUMPAD_0))
			this.isDebug = !this.isDebug;

		this.ray.updateAndRender();
	}
	
	public void update(OrthographicCamera cam) {
		this.world.step(1/60f, 6, 2);
		this.renderRect.setPosition(cam.position.x - (this.screenWidth/2), cam.position.y - (this.screenHeight/2));
		this.ray.setCombinedMatrix(cam);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void dispose() {
		for(Block b : this.blocks)
			b.dispose();
		this.ray.dispose();
	}
	
	public void setSunPosition(Player player) {
		int sunY = 768;
		if(player.y >= 400) sunY = (int) (player.y + 400);
		this.sun.setPosition(player.x, sunY);
		this.sunR.setPosition(this.sun.getPosition().x, this.sun.getPosition().y+512);
		this.sunL.setPosition(this.sun.getPosition().x, this.sun.getPosition().y-512);
	}

}
