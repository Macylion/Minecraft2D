package com.github.macylion.overworld;

import java.nio.Buffer;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.entities.Player;
import com.github.macylion.texturebank.TextureBank;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Overworld {
	
	int screenWidth;
	int screenHeight;
	World world;
	public ArrayList<Block> blocks;
	Rectangle renderRect;
	//debug
	Box2DDebugRenderer debugRenderer;
	boolean isDebug = false;
	
	public Overworld(int width, int height) {
		Box2D.init();
		this.screenWidth = width;
		this.screenHeight = height;
		this.world = new World(new Vector2(0, -100), true); 
		
		this.debugRenderer = new Box2DDebugRenderer();
		this.blocks = new ArrayList<Block>();
		this.renderRect = new Rectangle(0, 0, this.screenWidth+256, this.screenHeight+128);
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
		
		int treeSpace = 0;
		
		for(Vector2 p : points) {
			int layer = 0;
			for(int i = (int)p.y; i > -32; i--) {
				String txt = "b-rock";
				if(layer == 0) {
					txt = "b-dirt-grass";
					if(Math.random() >= 0.8f && treeSpace > 7) {
						treeSpace = 0;
						//log
						this.blocks.add(new Block((int)p.x*32, (int)((p.y+1)*32), "b-wood-log", this.world, false));
						this.blocks.add(new Block((int)p.x*32, (int)((p.y+2)*32), "b-wood-log", this.world, false));
						this.blocks.add(new Block((int)p.x*32, (int)((p.y+3)*32), "b-wood-log", this.world, false));
						this.blocks.add(new Block((int)p.x*32, (int)((p.y+4)*32), "b-wood-log", this.world, false));
						//leaves
						this.blocks.add(new Block((int)((p.x+1)*32), (int)((p.y+4)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x+2)*32), (int)((p.y+4)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x+3)*32), (int)((p.y+4)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-1)*32), (int)((p.y+4)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-2)*32), (int)((p.y+4)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-3)*32), (int)((p.y+4)*32), "b-wood-leaf", this.world, false));
						
						this.blocks.add(new Block((int)((p.x+1)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x+2)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-1)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-2)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						
						this.blocks.add(new Block((int)((p.x+1)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-1)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						
						this.blocks.add(new Block((int)((p.x)*32), (int)((p.y+7)*32), "b-wood-leaf", this.world, false));
					}
					treeSpace++;
				}
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
			b.draw(batch, bank, this.renderRect, camera);
		
		//debug
		if(this.isDebug)
			this.debugRenderer.render(world, camera.combined);
		if(Gdx.input.isKeyJustPressed(Keys.NUMPAD_0))
			this.isDebug = !this.isDebug;
	}
	
	public void update(OrthographicCamera cam) {
		this.world.step(1/60f, 6, 2);
		this.renderRect.setPosition(cam.position.x - (this.renderRect.width/2), cam.position.y - (this.renderRect.height/2));
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void dispose() {
		for(Block b : this.blocks)
			b.dispose();
	}

}
