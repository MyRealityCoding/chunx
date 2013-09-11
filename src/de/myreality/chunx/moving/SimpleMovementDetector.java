/*
 * chunx is a Java 2D chunk engine to generate "infinite" worlds.
 * Copyright (C) 2013  Miguel Gonzalez
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package de.myreality.chunx.moving;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.myreality.chunx.ChunkConfiguration;
import de.myreality.chunx.ChunkTarget;

/**
 * Simple implementation of {@link MovementDetector}
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class SimpleMovementDetector implements MovementDetector {

	// ===========================================================
	// Constants
	// ===========================================================	

	// ===========================================================
	// Fields
	// ===========================================================

	private List<MovementListener> listeners;
	
	private ChunkTarget target;
	
	private ChunkConfiguration configuration;
	
	private float lastX, lastY;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public SimpleMovementDetector(ChunkTarget target, ChunkConfiguration configuration) {
		this.target = target;
		this.configuration = configuration;
		listeners = new ArrayList<MovementListener>();
		updatePosition();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public void update() {
		update(0.0f);
	}

	@Override
	public void update(float delta) {
		
		if (lastX != target.getX() || lastY != target.getY()) {
			for (MovementListener listener : listeners) {
				MoveEvent event = createEvent(lastX, lastY, target.getX(), target.getY());
				listener.onMove(event);
			}
		}
		
		updatePosition();
	}

	@Override
	public void addListener(MovementListener listener) {
		
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(MovementListener listener) {
		listeners.remove(listener);
	}

	@Override
	public boolean contains(MovementListener listener) {
		return listeners.contains(listener);
	}

	public Collection<MovementListener> getListeners() {
		return listeners;
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	
	private MoveEvent createEvent(float lastX, float lastY, float newX, float newY) {
		return new SimpleMoveEvent(target, configuration, lastX, lastY, newX, newY);
	}
	
	private void updatePosition() {
		if (target != null) {
			lastX = target.getX();
			lastY = target.getY();
		}
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
