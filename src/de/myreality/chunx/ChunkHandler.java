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
package de.myreality.chunx;

import de.myreality.chunx.moving.MovementListener;
import de.myreality.chunx.util.MatrixList;


/**
 * Handles chunks by calculating them for a chunk system
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface ChunkHandler extends MovementListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Handles existing chunks
	 * 
	 * @param chunks
	 * @param system
	 */
	void handleChunks(MatrixList<Chunk> chunks);
	
	/**
	 * Saves all chunks in that list
	 * 
	 * @param chunks
	 */
	void saveChunks(MatrixList<Chunk> chunks);
	
	/**
	 * Saves this chunk
	 * 
	 * @param chunk
	 */
	void saveChunk(Chunk chunk);
	
	
}
