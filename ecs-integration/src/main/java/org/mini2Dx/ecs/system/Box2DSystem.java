/**
 * Copyright (c) 2015, mini2Dx Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the mini2Dx nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mini2Dx.ecs.system;

import java.util.SortedSet;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.ecs.entity.Entity;
import org.mini2Dx.ecs.physics.PositionTracker;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Wrap Box2D into an implementation of {@link System}
 */
public abstract class Box2DSystem<T extends Entity> extends GameSystem<T> {
	private World world;
	private int velocityIterations, positionIterations;

	/**
	 * Constructor
	 * 
	 * @param world
	 *            The Box2D {@link World} instance
	 * @param velocityIterations
	 * @param positionIterations
	 */
	public Box2DSystem(World world, int velocityIterations,
			int positionIterations) {
		this.world = world;
		this.velocityIterations = velocityIterations;
		this.positionIterations = positionIterations;
	}

	@Override
	public void update(GameContainer gc, float delta) {
		world.step(delta, velocityIterations, positionIterations);
		for(Entity entity : entities.values()) {
			update(entity, gc, delta);
		}
	}
	
	@Override
	public void interpolate(GameContainer gc, float alpha) {
		for(Entity entity : entities.values()) {
			interpolate(entity, gc, alpha);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		for(Entity entity : entities.values()) {
			render(entity, gc, g);
		}
	}

	public void update(Entity entity, GameContainer gc, float delta) {
		SortedSet<PositionTracker> positionTrackers = entity
				.getComponents(PositionTracker.class);
		for (PositionTracker tracker : positionTrackers) {
			tracker.update();
		}
	}

	public void interpolate(Entity entity, GameContainer gc, float alpha) {
		SortedSet<PositionTracker> positionTrackers = entity
				.getComponents(PositionTracker.class);
		for (PositionTracker tracker : positionTrackers) {
			tracker.interpolate(alpha);
		}
	}

	public void render(Entity entity, GameContainer gc, Graphics g) {
		SortedSet<PositionTracker> positionTrackers = entity
				.getComponents(PositionTracker.class);
		for (PositionTracker tracker : positionTrackers) {
			render(entity, tracker.getBody(), tracker.getRenderX(),
					tracker.getRenderY(), gc, g);
		}
	}

	/**
	 * Render a Box2D {@link Body} associated with an {@link Entity}
	 * 
	 * @param entity
	 *            The {@link Entity} the {@link Body} belongs to
	 * @param body
	 *            The {@link Body} to be rendered
	 * @param renderX
	 *            The x coordinate to render at
	 * @param renderY
	 *            The y coordinate to render at
	 * @param gc
	 *            The {@link GameContainer} calling render
	 * @param g
	 *            The current {@link Graphics} context
	 */
	public abstract void render(Entity entity, Body body, float renderX,
			float renderY, GameContainer gc, Graphics g);

	@Override
	public void removeEntity(T entity) {
		super.removeEntity(entity);
		SortedSet<Body> bodies = entity.getComponents(Body.class);
		SortedSet<Joint> joints = entity.getComponents(Joint.class);
		for (Body body : bodies) {
			world.destroyBody(body);
		}
		for (Joint joint : joints) {
			world.destroyJoint(joint);
		}
	}

	public World getWorld() {
		return world;
	}
}
