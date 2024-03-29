package com.github.macylion.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Entity extends Rectangle{

	protected PolygonShape groundBox;
	protected BodyDef groundBodyDef;
	protected Body groundBody;
	protected FixtureDef fixtureDef;
	
	public Entity(Vector2 position, World world, int width, int height) {
		this.x = position.x;
		this.y = position.y;
		this.width = width;
		this.height = height;
		
		groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.DynamicBody;
		groundBodyDef.position.set(new Vector2(this.x+(this.width/2), this.y+(this.height/2)));  
		groundBody = world.createBody(groundBodyDef);  
		groundBody.setFixedRotation(true);
		groundBox = new PolygonShape();
		groundBox.setAsBox(this.width/2, this.height/2);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = groundBox;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0;
		
		fixtureDef.filter.groupIndex = 1;
		fixtureDef.filter.categoryBits = 0x0001;
		fixtureDef.filter.maskBits = 1;
		
		groundBody.createFixture(fixtureDef); 
	}
	
	protected void fixedUpdate() {
		this.x = this.groundBody.getPosition().x - (this.width/2);
		this.y = this.groundBody.getPosition().y - (this.height/2);
	}
	
	public Vector2 getPosition() {
		return this.groundBody.getPosition();
	}
	
	public Body getBody() {
		return this.groundBody;
	}
	
	public Filter getFilter() {
		return this.fixtureDef.filter;
	}
	
}
