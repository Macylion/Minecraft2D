package com.github.macylion.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Entity extends Rectangle{

	private PolygonShape groundBox;
	
	public Entity(Vector2 position, World world) {
		this.x = position.x;
		this.y = position.y;
		
		
		
		BodyDef groundBodyDef = new BodyDef();  
		groundBodyDef.position.set(new Vector2(this.x+(this.width/2), this.y+(this.height/2)));  
		Body groundBody = world.createBody(groundBodyDef);  
		
		groundBox = new PolygonShape();
		groundBox.setAsBox(this.width/2, this.height/2);
		groundBody.createFixture(groundBox, 0.0f); 
	}

}
