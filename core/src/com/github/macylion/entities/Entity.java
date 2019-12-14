package com.github.macylion.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Entity extends Rectangle{

	protected PolygonShape groundBox;
	protected BodyDef groundBodyDef;
	protected Body groundBody;
	
	public Entity(Vector2 position, World world) {
		this.x = position.x;
		this.y = position.y;
		this.width = 30;
		this.height = 48;
		
		groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.DynamicBody;
		groundBodyDef.position.set(new Vector2(this.x+(this.width/2), this.y+(this.height/2)));  
		groundBody = world.createBody(groundBodyDef);  
		groundBox = new PolygonShape();
		groundBox.setAsBox(this.width/2, this.height/2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundBox;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 1;
		
		groundBody.createFixture(fixtureDef); 
	}

}
