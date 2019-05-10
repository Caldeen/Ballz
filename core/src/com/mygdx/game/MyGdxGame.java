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

public class MyGdxGame extends ApplicationAdapter {
	public static int METER_TO_PIX=16;
	private World world;
	private SpriteBatch batch;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera cam;

	Ball ball;

	@Override
	public void create () {
		cam=new OrthographicCamera();
		debugRenderer=new Box2DDebugRenderer();
		world=new World(new Vector2(0,-10),true);
		cam.setToOrtho(false,200,120);
		batch = new SpriteBatch();
		tiledMap = new TmxMapLoader().load("core/assets/testMapa.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		MapParser.parseMapLayers(world, tiledMap);

		ball = new Ball(world, batch, 100.0f, 100.0f, 50.0f);
		EventHandler eventHandler = new EventHandler(ball);
		Gdx.input.setInputProcessor(eventHandler);
	}

	@Override
	public void render () {
		tiledMapRenderer.setView(cam);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		tiledMapRenderer.setView(cam);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world,cam.combined);

		world.step(1/60f, 6, 2);


		tiledMapRenderer.render();

		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
	}
}
