package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Player.Ball;
import com.mygdx.game.Player.EventHandler;
import com.sun.org.apache.xpath.internal.operations.Or;

public class MyGdxGame extends ApplicationAdapter {
	public static int METER_TO_PIX=16;
	public static Vector2 screenSize;
	private World world;
	private SpriteBatch batch;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera cam;
	private OrthographicCamera viewCam;

	Ball ball;

	@Override
	public void create () {
		screenSize=new Vector2(1600,900);
		viewCam=new OrthographicCamera();
		viewCam.setToOrtho(false,100,60);
		cam=new OrthographicCamera();
		debugRenderer=new Box2DDebugRenderer();
		world=new World(new Vector2(0,-10),true);
		cam.setToOrtho(false,200,120);
		Gdx.graphics.setWindowedMode((int)screenSize.x,(int)screenSize.y);
		batch = new SpriteBatch();
		tiledMap = new TmxMapLoader().load("core/assets/testMapa.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		MapParser.parseMapLayers(world, tiledMap);

		ball = new Ball(world, batch, 100.0f, 100.0f, 1.0f);
		EventHandler eventHandler = new EventHandler(ball,cam);
		Gdx.input.setInputProcessor(eventHandler);
	}

	@Override
	public void render () {
		tiledMapRenderer.setView(cam);
		cam.update();
		cam.position.set(ball.getBody().getWorldCenter().x,ball.getBody().getWorldCenter().y,0);
		viewCam.update();
		batch.setProjectionMatrix(viewCam.combined);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world,cam.combined.scl(1.2f));

		world.step(1/60f, 6, 2);


		tiledMapRenderer.render();

		batch.begin();
		moveBall();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
	}

	public void moveBall() {
		if (EventHandler.mouseDown) {
			float currentX = Gdx.input.getX();
			float currentY = MyGdxGame.screenSize.y - Gdx.input.getY();
			float vecX = -Math.abs(currentX-EventHandler.prevX);
			float vecY = -Math.abs(currentY-EventHandler.prevY);
			ball.move(vecX/100, vecY/100);
		}
	}

}
