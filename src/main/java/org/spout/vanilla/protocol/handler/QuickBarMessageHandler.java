/*
 * This file is part of Vanilla (http://www.spout.org/).
 *
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
 * the MIT license and the SpoutDev license version 1 along with this program.  
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license, 
 * including the MIT license.
 */
package org.spout.vanilla.protocol.handler;

import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.material.MaterialData;
import org.spout.api.player.Player;
import org.spout.api.protocol.MessageHandler;
import org.spout.api.protocol.Session;
import org.spout.vanilla.Block;
import org.spout.vanilla.protocol.msg.QuickBarMessage;

public class QuickBarMessageHandler extends MessageHandler<QuickBarMessage> {
	@Override
	public void handle(Session session, Player player, QuickBarMessage message) {
		/*if (player.getData("gamemode") != GameMode.CREATIVE) { //TODO: Gamemode is currently not changeable
			player.kick("Now now, don't try that here. Won't work.");
			return;
		}*/
		int slot = message.getSlot();
		if (slot < 0 || slot >= player.getEntity().getInventorySize()) {
			return;
		}
		ItemStack newItem = null;
		if(checkValidId(message.getId())) {
			newItem = new ItemStack(MaterialData.getMaterial(message.getId()), message.getAmount(), message.getDamage());
		} else if(message.getId() != -1) {
			player.kick("Unknown item ID: " + message.getId());
			return;
		}
		player.getEntity().getInventory().setItem(newItem, slot);
		/*if (currentItem != null) {
			player.setItemOnCursor(currentItem);
		} else {
			player.setItemOnCursor(null);
		}*/
	}

	public boolean checkValidId(short id) {
		return MaterialData.getMaterial(id) != null;
	}
}