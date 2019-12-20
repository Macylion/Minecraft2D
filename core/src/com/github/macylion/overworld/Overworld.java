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
	
	final boolean GENERATE_CAVES = true; //CHANGE THIS
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
						this.blocks.add(new Block((int)p.x*32, (int)((p.y+5)*32), "b-wood-log", this.world, false));
						//leaves
						this.blocks.add(new Block((int)((p.x+1)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x+2)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x+3)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-1)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-2)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-3)*32), (int)((p.y+5)*32), "b-wood-leaf", this.world, false));
						
						this.blocks.add(new Block((int)((p.x+1)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x+2)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-1)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-2)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x)*32), (int)((p.y+6)*32), "b-wood-leaf", this.world, false));
						
						this.blocks.add(new Block((int)((p.x+1)*32), (int)((p.y+7)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x-1)*32), (int)((p.y+7)*32), "b-wood-leaf", this.world, false));
						this.blocks.add(new Block((int)((p.x)*32), (int)((p.y+7)*32), "b-wood-leaf", this.world, false));
						
						this.blocks.add(new Block((int)((p.x)*32), (int)((p.y+8)*32), "b-wood-leaf", this.world, false));
					}
					else if(Math.random() >= 0.8f && treeSpace > 2) {
						this.blocks.add(new Block((int)p.x*32, (int)((p.y+1)*32), "b-flower", this.world, false));
					}
					treeSpace++;
				}
				else if(layer < 5) txt = "b-dirt";
				
				if(i == -24) if(Math.random() > 0.9f) txt = "b-sand";
				if(i == -25) if(Math.random() > 0.8f) txt = "b-sand";
				if(i == -26) if(Math.random() > 0.7f) txt = "b-sand";
				if(i == -27) if(Math.random() > 0.6f) txt = "b-sand";
				if(i == -28) if(Math.random() > 0.5f) txt = "b-sand";
				if(i == -29) if(Math.random() > 0.4f) txt = "b-sand";
				if(i == -30) if(Math.random() > 0.3f) txt = "b-sand";
				if(i == -31) txt = "b-void";
				this.blocks.add(new Block((int)p.x*32, i*32, txt, this.world));
				layer++;
			}
		}
		
		if(this.GENERATE_CAVES) {
			ArrayList<Vector2> caveOrigin = new ArrayList<Vector2>();
			for(Block b : this.blocks) 
				if(b.getType().equals("b-rock") && Math.random() > 0.99f)
					caveOrigin.add(new Vector2(b.getX(), b.getY()));
	
			for(int i = 0; i <= 4; i++) {
				ArrayList<Vector2> caveBlocks = new ArrayList<Vector2>();
				for(Vector2 v : caveOrigin) {
					if(Math.random() > 0.4f)
						caveBlocks.add(new Vector2(v.x+32, v.y));
					if(Math.random() > 0.4f)
						caveBlocks.add(new Vector2(v.x-32, v.y));
					if(Math.random() > 0.4f)
						caveBlocks.add(new Vector2(v.x, v.y+32));
					if(Math.random() > 0.4f)
						caveBlocks.add(new Vector2(v.x, v.y-32));
				}
				caveOrigin.addAll(caveBlocks);
				
			}
			
			class BlockFrame {
				public int x;
				public int y;
				public String txt;
				public boolean active;
				BlockFrame(int x, int y, String txt, boolean isActive){
					this.x = x;
					this.y = y;
					this.txt = txt;
					this.active = isActive;
				}
			}
			
			ArrayList<BlockFrame> ores = new ArrayList<BlockFrame>();
			for(Block b : this.blocks) {
				boolean died = false;
				if(b.getType().equals("b-rock") || b.getType().equals("b-dirt")) {
					for(Vector2 v2 : caveOrigin) 
						if(v2.x == b.x && v2.y == b.y) {
							ores.add(new BlockFrame((int)b.getX(), (int)b.getY(), "b-glass", false));
							died = true;
							b.die();
							break;
						}
					if(!died && b.getType().equals("b-rock") && Math.random() > 0.9f) {
						String texture = "b-copper-ore";
						if(Math.random() > 0.8f) texture = "b-tin-ore";
						else if(Math.random() > 0.8f) texture = "b-iron-ore";
						ores.add(new BlockFrame((int)b.getX(), (int)b.getY(), texture, true));
						b.die();
					}
				}
			}
			
			for(BlockFrame bf : ores)
				this.blocks.add(new Block(bf.x, bf.y, bf.txt, this.world, bf.active));
			
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

	public void draw(TextureBank bank, SpriteBatch batch, OrthographicCamera camera, Rectangle distanceRect) {
		for(Block b : this.blocks)
			b.draw(batch, bank, this.renderRect, camera, distanceRect);
		
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
