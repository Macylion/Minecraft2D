package com.github.macylion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.texturebank.TextureBank;

public class Player extends Entity{
	
	private Animation<TextureRegion> idleAnimation;
	float stateTime;
	
	public Player(Vector2 position, World world, TextureBank bank) {
		super(position, world);

		/*TextureRegion[][] tmp = TextureRegion.split(bank.getTexture("e-player"), 
				bank.getTexture("e-player").getWidth() / 8,
				bank.getTexture("e-player").getHeight() / 16);*/
		
		TextureRegion[] idleFrames = new TextureRegion[3];
		idleFrames[0] = bank.getTextureRegion("e-player-idle-1");
		idleFrames[1] = bank.getTextureRegion("e-player-idle-2");
		idleFrames[2] = bank.getTextureRegion("e-player-idle-3");
		this.idleAnimation = new Animation<TextureRegion>(0.025f, idleFrames);
		
		this.stateTime = 0;
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion currentFrame = idleAnimation.getKeyFrame(stateTime, true);
		//batch.draw(currentFrame, this.x, this.y);
	}
	
	public void update() {
		this.x = this.groundBody.getPosition().x;
		this.y = this.groundBody.getPosition().y;
		this.stateTime += Gdx.graphics.getDeltaTime();
	}
}
