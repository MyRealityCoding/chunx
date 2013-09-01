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
package de.myreality.chunx.caching;

import de.myreality.chunx.AbstractChunkSystem;
import de.myreality.chunx.ChunkConfiguration;
import de.myreality.chunx.ChunkTarget;
import de.myreality.chunx.io.ChunkLoader;
import de.myreality.chunx.io.ChunkSaver;
import de.myreality.chunx.io.InputStreamProvider;
import de.myreality.chunx.io.OutputStreamProvider;
import de.myreality.chunx.io.SimpleChunkLoader;
import de.myreality.chunx.io.SimpleChunkSaver;
import de.myreality.chunx.io.SimpleInputStreamProvider;
import de.myreality.chunx.io.SimpleOutputStreamProvider;

/**
 * 
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since
 * @version
 */
public class SimpleCachedChunkSystem extends AbstractChunkSystem implements
		CachedChunkSystem {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private CachedChunkConfiguration configuration;
	
	private Cache cache, preCache;

	// ===========================================================
	// Constructors
	// ===========================================================

	public SimpleCachedChunkSystem(CachedChunkConfiguration configuration) {
		super(configuration);
		
		OutputStreamProvider outProvider = new SimpleOutputStreamProvider();
		InputStreamProvider inProvider = new SimpleInputStreamProvider();
		
		ChunkSaver saver = new SimpleChunkSaver(outProvider);
		ChunkLoader loader = new SimpleChunkLoader(inProvider);
		setHandler(new CachedChunkHandler(configuration, loader, saver));
		
		initializeCache();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public void update(float delta) {
		if (isRunning() && cachingRequested()) {
			alignCache();
			getHandler().handleChunks(chunks, this);
		}
	}

	@Override
	public Cache getCache() {
		return cache;
	}

	@Override
	public Cache getPreCache() {
		return preCache;
	}

	@Override
	public double getProgress() {
		return (double)getCurrentChunkCount() / (double)getTotalChunkCount();
	}

	@Override
	public int getTotalChunkCount() {
		return configuration.getTotalChunkCount();
	}

	@Override
	public CachedChunkConfiguration getCachedConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(ChunkConfiguration configuration) {
		if (configuration instanceof CachedChunkConfiguration) {
			this.configuration = (CachedChunkConfiguration)configuration;
			super.setConfiguration(configuration);
			initializeCache();
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	private void initializeCache() {
		cache = new SimpleCache(configuration);
		CachedChunkConfiguration preConfig = new SimpleCachedChunkConfiguration(configuration);
		preConfig.setOffset(1);
		preCache = new SimpleCache(preConfig);
		alignCache();
	}
	
	private void alignCache() {
		
		ChunkTarget focused = configuration.getFocused();
		
		if (focused != null) {
			
			final int INDEX_X = positionInterpreter.translateX(focused.getX());
			final int INDEX_Y = positionInterpreter.translateY(focused.getY());
			
			cache.align(INDEX_X, INDEX_Y);
			preCache.align(INDEX_X, INDEX_Y);
		}
	}
	
	private boolean cachingRequested() {
		
		ChunkTarget focused = configuration.getFocused();
		
		if (focused != null) {
			final int INDEX_X = positionInterpreter.translateX(focused.getX());
			final int INDEX_Y = positionInterpreter.translateY(focused.getY());
			
			return !cache.containsIndex(INDEX_X, INDEX_Y);
		} else {
			return false;
		}
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
