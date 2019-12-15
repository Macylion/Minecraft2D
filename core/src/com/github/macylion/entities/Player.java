package com.github.macylion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.github.macylion.texturebank.TextureBank;
import com.github.macylion.thirdparty.GifDecoder;

public class Player extends Entity{
	
	private Animation<TextureRegion>[] anims;
	private int currAnim = 0;
	private float stateTime;
	
	public Player(Vector2 position, World world, TextureBank bank) {
		super(position, world, 19, 30);
		
		this.anims = new Animation[3];
		
		this.anims[0] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/run.gif").read());
		this.anims[1] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/jump.gif").read());
		this.anims[2] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Entities/player/idle.gif").read());
		
		this.stateTime = 0;
		this.groundBody.setGravityScale(10);
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion currentFrame = this.anims[this.currAnim].getKeyFrame(stateTime, true);
		batch.draw(currentFrame, this.x, this.y);
	}
	
	public void update() {
		this.fixedUpdate();
		this.movement();
		this.stateTime += Gdx.graphics.getDeltaTime();
	}
	
	private void movement() {
		
		boolean isUp = (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.SPACE));
		boolean isRight = (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT));
		boolean isLeft = (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT));
		boolean isSprinting = (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT));
		
		float speed = 1024;
		float sprintMultiplier = 1;
		Vector2 newPos = new Vector2(0, 0);
		
		if(isSprinting)
			sprintMultiplier = 2f;
		if(isUp) {
			newPos.y += 10240;
			this.currAnim = 1;
		}
		if(isRight) {
			newPos.x += speed;
			this.currAnim = 0;
		}
		else if(isLeft) {
			newPos.x -= speed;
			this.currAnim = 0;
		}
		else {
			this.groundBody.setLinearVelocity(0, this.groundBody.getLinearVelocity().y);
			this.currAnim = 2; //TODO: if in air do jump anim
		}
		
		newPos.x *= sprintMultiplier;
		this.groundBody.applyLinearImpulse(newPos, this.groundBody.getPosition(), true);
	}
}
