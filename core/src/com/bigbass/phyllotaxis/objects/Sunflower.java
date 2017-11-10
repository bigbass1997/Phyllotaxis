package com.bigbass.phyllotaxis.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Sunflower {
	
	private final int c = 4;
	private final float theta = 137.5f;
	
	public ArrayList<Particle> particles;
	
	private float deltaCount = 0;
	
	public Sunflower(){
		particles = new ArrayList<Particle>();
	}
	
	public void render(ShapeRenderer sr){
		sr.begin(ShapeType.Filled);
		for(Particle part : particles){
			part.render(sr);
		}
		sr.end();
	}
	
	public void update(float delta){
		// slowing generate new particles
		deltaCount += delta;
		if(deltaCount >= 0.01f){
			deltaCount = 0;
			for(int i = 0; i < 5; i++){
				int n = particles.size();
				particles.add(new Particle(n * theta, (float) (c * Math.sqrt(n)), Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f, n));
			}
		}
		
		changeTheta(0.001f);
	}
	
	public void changeTheta(float delta){
		for(Particle part : particles){
			part.changeTheta(delta);
		}
	}
}
