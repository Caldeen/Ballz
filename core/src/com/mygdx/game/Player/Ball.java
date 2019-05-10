package com.mygdx.game.Player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Ball {
    private World world;
    private BodyDef bodyDef = new BodyDef();
    private Batch batch;
    private Body body;
    private float x, y, radius, gravityScale = 1;
    private Sprite sprite;
    public Ball(World world, Batch batch, float x, float y, float radius) {
        this.world = world;
        this.batch = batch;
        this.x = x;
        this.y = y;
        this.radius = radius;
//        this.sprite = sprite;
        onCreate();
    }

    private void onCreate() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        Shape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        circle.dispose();
        body.setAwake(true);
        body.setGravityScale(gravityScale);
    }

    public void update() {

    }

    public Body getBody() {
        return body;
    }

}
