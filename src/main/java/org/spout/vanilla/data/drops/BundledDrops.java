/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, VanillaDev <http://www.spout.org/>
 * Vanilla is licensed under the SpoutDev License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.vanilla.data.drops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.vanilla.data.drops.type.FixedDrop;
import org.spout.vanilla.data.drops.type.RandomDrop;
import org.spout.vanilla.data.drops.type.RandomRangeDrop;

public class BundledDrops implements Drops {
	private List<Drop> drops = new ArrayList<Drop>();

	@Override
	public List<ItemStack> getDrops(Random random) {
		List<ItemStack> drops = new ArrayList<ItemStack>(this.drops.size());
		if (!this.drops.isEmpty()) {
			for (Drop drop : this.drops) {
				drops.addAll(drop.getDrops(random));
			}
		}
		return drops;
	}

	@Override
	public boolean containsDrop(Material material) {
		for (Drop drop : drops) {
			if (drop.containsDrop(material)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BundledDrops remove(Material dropMaterial) {
		Iterator<Drop> iter = this.drops.iterator();
		while (iter.hasNext()) {
			Drop next = iter.next();
			if (next instanceof Drops) {
				((Drops) next).remove(dropMaterial);
			} else if (next.containsDrop(dropMaterial)) {
				iter.remove();
			}
		}
		return this;
	}

	@Override
	public BundledDrops add(Drop drop) {
		this.drops.add(drop);
		return this;
	}

	@Override
	public BundledDrops clear() {
		this.drops.clear();
		return this;
	}

	/**
	 * Gets the all the Drops contained
	 * 
	 * @return unsafe List of drops
	 */
	public List<Drop> getAll() {
		return this.drops;
	}

	public BundledDrops add(Material dropMaterial, int... amount) {
		return add(new RandomDrop(dropMaterial, amount));
	}

	public BundledDrops add(Material dropMaterial) {
		return add(new FixedDrop(dropMaterial, 1));
	}

	public BundledDrops addRange(Material dropMaterial, int max) {
		return add(new RandomRangeDrop(dropMaterial, 0, max));
	}

	public BundledDrops addRange(Material dropMaterial, int min, int max) {
		return add(new RandomRangeDrop(dropMaterial, min, max));
	}
}
