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
		generateWorld();
	}
	
	private void generateWorld() {
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
		Vector2 last = new Vector2(0, 14);
		for(int i = 0; i <= 4; i++) {
			points.add(new Vector2(last.x, last.y));
			last.x += 1;
			double ran = Math.random();
			if(ran >= 0.7f)
				last.y += 1;
			else if(ran >= 0.4f)
				last.y -= 1;
		}
			
		
		for(Vector2 p : points)
			this.blocks.add(new Block((int)p.x*32, (int)p.y*32, "b-rock", this.world));
		
	}
	
	/*
	 	function points(step, maxSur, minSur, color="#000000"){
		var points = []
		for(var i = 1024; i > 0; i -= step){
			var ran = Math.floor(Math.random()*(maxSur - minSur) + minSur);
			points.push({x: i, y: ran})
		}
		for (var i = 0; i <= points.length-2; i++)
			line(points[i], points[i+1], color)
		return points;
	}
	var p = points(100, 300, 480);
	function repoints(){
		var end = p.length-2;
		for (var i = 1; i <= end; i++){
			p[i] = {x: (p[i-1].x + p[i+1].x)/2, y: (p[i-1].y + p[i+1].y)/2}
			p.push({x: (p[i].x + p[i+1].x)/2, y: (p[i].y + p[i+1].y)/2})
		}
		p.sort((a, b) => (a.x > b.x)? -1:1)
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		for (var i = 0; i <= p.length-2; i++)
			line(p[i], p[i+1])
	}
	 */

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
