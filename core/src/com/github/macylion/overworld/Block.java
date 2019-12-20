package com.github.macylion.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.Inventory;
import com.github.macylion.texturebank.TextureBank;

public class Block extends Rectangle{

	private String txtKey;
	private PolygonShape groundBox;
	private Body groundBody;
	private boolean isOnScreen;
	private float durability;
	private float maxDurability;
	private boolean isAlive;
	
	public Block(int x, int y, String textureKey, World world) {
		super();
		this.isAlive = true;
		this.x = x;
		this.y = y;
		this.width = 32;
		this.height = 32;
		this.txtKey = textureKey;
		this.maxDurability = 1;
		if(this.txtKey.equals("b-rock")) this.maxDurability = 1.4f;
		if(this.txtKey.equals("b-wood-leaf")) this.maxDurability = 0.1f;
		this.durability = this.maxDurability;
		
		BodyDef groundBodyDef = new BodyDef();  
		groundBodyDef.position.set(new Vector2(this.x+(this.width/2), this.y+(this.height/2)));  
		groundBody = world.createBody(groundBodyDef);  
		
		groundBox = new PolygonShape();
		groundBox.setAsBox(this.width/2, this.height/2);
		groundBody.createFixture(groundBox, 0.0f); 
	}
	
	public Block(int x, int y, String textureKey, World world, boolean active) {
		this(x, y, textureKey, world);
		this.groundBody.setActive(active);
	}
	
	public void draw(SpriteBatch batch, TextureBank bank, Rectangle renderRect, OrthographicCamera cam, Rectangle distanceRect) {
		if(renderRect.overlaps(this) && this.isAlive) {
			batch.draw(bank.getTexture(this.txtKey), this.groundBody.getPosition().x-16, this.groundBody.getPosition().y-16);
			this.isOnScreen = true;
			if(this.overlaps(distanceRect) 
					&& this.contains((Gdx.input.getX() + (cam.position.x-1024/2)), 
					((768-Gdx.input.getY()) + (cam.position.y-768/2)))
					&& Gdx.input.isButtonPressed(Buttons.LEFT)) {
				
				this.durability -= Gdx.graphics.getDeltaTime();
				
				if(this.durability/this.maxDurability > 0.66f)
					batch.draw(bank.getTexture("alert1"), this.x, this.y);
				else if(this.durability/this.maxDurability > 0.33f)
					batch.draw(bank.getTexture("alert2"), this.x, this.y);
				else 
					batch.draw(bank.getTexture("alert3"), this.x, this.y);
				
				if(this.durability < 0) {
					Inventory.addItem(txtKey, 1);
					this.die();
				}
				
			}
			else if(this.durability < this.maxDurability)
				this.durability = this.maxDurability;
		}
		else 
			this.isOnScreen = false;
	}
	
	private void die() {
		this.x = -20000;
		this.y = -2000;
		this.groundBody.setActive(false);
		this.isAlive = false;
	}

	public void dispose() {
		groundBox.dispose();
	}

	public boolean isOnScreen() {
		return isOnScreen;
	}
	
	public boolean canGoThrough() {
		return this.groundBody.isActive();
	}

}
