package com.github.macylion.overworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.texturebank.TextureBank;

public class Overworld {
	
	private int screenWidth;
	private int screenHeight;
	private World world;
	Box2DDebugRenderer debugRenderer;
	ArrayList<Block> blocks;
	//debug
	boolean isDebug = false;
	
	public Overworld(int width, int height) {
		Box2D.init();
		this.screenWidth = width;
		this.screenHeight = height;
		this.world = new World(new Vector2(0, -10), true); 
		this.debugRenderer = new Box2DDebugRenderer();
		this.blocks = new ArrayList<Block>();
		this.blocks.add(new Block(0, 0, "b-brick", this.world));
	}
	
	public void draw(TextureBank bank, SpriteBatch batch, OrthographicCamera camera) {
		for(Block b : this.blocks)
			b.draw(batch, bank);
		
		//debug
		if(this.isDebug)
			this.debugRenderer.render(world, camera.combined);
		if(Gdx.input.isKeyJustPressed(Keys.NUMPAD_0))
			this.isDebug = !this.isDebug;
	}
	
	public void update() {
		this.world.step(1/60f, 6, 2);
	}
	
	public void dispose() {
		for(Block b : this.blocks)
			b.dispose();
	}

}
