package com.github.macylion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.texturebank.TextureBank;
import com.github.macylion.thirdparty.GifDecoder;

public class Player extends Entity{
	
	private Animation<TextureRegion> idleAnimation;
	private Animation<TextureRegion> runAnimation;
	private Animation<TextureRegion> jumpAnimation;
	float stateTime;
	
	public Player(Vector2 position, World world, TextureBank bank) {
		super(position, world, 19, 30);

		/*TextureRegion[][] tmp = TextureRegion.split(bank.getTexture("e-player"), 
				bank.getTexture("e-player").getWidth() / 8,
				bank.getTexture("e-player").getHeight() / 16);*/
		
		/*TextureRegion[] idleFrames = new TextureRegion[3];
		idleFrames[0] = bank.getTextureRegion("e-player-idle-0");
		idleFrames[1] = bank.getTextureRegion("e-player-idle-1");
		idleFrames[2] = bank.getTextureRegion("e-player-idle-2");
		this.idleAnimation = new Animation<TextureRegion>(0.25f, idleFrames);*/
		
		this.idleAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/idle.gif").read());
		this.jumpAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/jump.gif").read());
		this.runAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/run.gif").read());
		
		this.stateTime = 0;
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion currentFrame = idleAnimation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, this.x, this.y);
	}
	
	public void update() {
		this.fixedUpdate();
		this.stateTime += Gdx.graphics.getDeltaTime();
	}
}
