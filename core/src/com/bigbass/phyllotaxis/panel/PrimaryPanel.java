package com.bigbass.phyllotaxis.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass.phyllotaxis.Main;
import com.bigbass.phyllotaxis.objects.Sunflower;
import com.bigbass.phyllotaxis.skins.SkinManager;

public class PrimaryPanel extends Panel {
	
	private Camera cam;
	private Stage stage;
	private ShapeRenderer sr;
	
	private Label infoLabel;
	
	private Sunflower sun;
	
	public PrimaryPanel() {
		super();
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		
		stage = new Stage();
		Main.inputMultiplexer.addProcessor(stage);
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		stage.addActor(infoLabel);
		
		sr = new ShapeRenderer(50000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		sun = new Sunflower();
	}
	
	public void render() {
		sr.begin(ShapeType.Filled);
		sr.setColor(0, 0, 0, 1);
		sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sr.end();
		
		sun.render(sr);
		
		panelGroup.render();
		
		stage.draw();
		
		/*sr.begin(ShapeType.Filled);
		sr.setColor(Color.FIREBRICK);
		renderDebug(sr);
		sr.end();*/
	}
	
	public void update(float delta) {
		sun.update(delta);
		
		stage.act(delta);
		
		panelGroup.update(delta);
		
		String info = String.format("Data:%n  FPS: %s%n  Particles: %s",
				Gdx.graphics.getFramesPerSecond(),
				sun.particles.size()
			);
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		infoLabel.setText("");
	}
	
	public boolean isActive() {
		return true; // Always active
	}
	
	public void dispose(){
		stage.dispose();
		sr.dispose();
		panelGroup.dispose();
	}
}