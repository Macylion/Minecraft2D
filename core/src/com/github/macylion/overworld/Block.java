package com.github.macylion.overworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.texturebank.TextureBank;

public class Block extends Rectangle{

	private String txtKey;
	private PolygonShape groundBox;
	private Body groundBody;
	
	public Block(int x, int y, String textureKey, World world) {
		super();
		this.x = x;
		this.y = y;
		this.width = 32;
		this.height = 32;
		this.txtKey = textureKey;
		
		BodyDef groundBodyDef = new BodyDef();  
		groundBodyDef.position.set(new Vector2(this.x+(this.width/2), this.y+(this.height/2)));  
		groundBody = world.createBody(groundBodyDef);  
		
		groundBox = new PolygonShape();
		groundBox.setAsBox(this.width/2, this.height/2);
		groundBody.createFixture(groundBox, 0.0f); 
	}
	
	public void draw(SpriteBatch batch, TextureBank bank, Rectangle renderRect) {
		if(renderRect.overlaps(this))
			batch.draw(bank.getTexture(this.txtKey), this.x, this.y);
	}
	
	public void dispose() {
		groundBox.dispose();
	}

}
