package com.bigbass.phyllotaxis.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.bigbass.phyllotaxis.SimOptions;

public class Sunflower {
	
	private int c;
	private float theta;
	
	public ArrayList<Particle> particles;
	
	private float deltaCount = 0;
	
	public Sunflower(){
		SimOptions opt = SimOptions.getInstance();
		
		c = opt.expandConstant;
		theta = opt.rotateTheta;
		
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
		SimOptions opt = SimOptions.getInstance();
		
		// slowing generate new particles
		deltaCount += delta;
		if(deltaCount >= opt.genRate){
			deltaCount = 0;
			for(int i = 0; i < opt.particlesPerGen; i++){
				int n = particles.size();
				particles.add(new Particle(n * theta, (float) (c * Math.sqrt(n)), Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f, n));
			}
		}
		
		changeTheta(opt.rotateRate);
	}
	
	public void changeTheta(float delta){
		for(Particle part : particles){
			part.changeTheta(delta);
		}
	}
}
