package me.Brian.NoLock.API;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R1.INamableTileEntity;
import net.minecraft.server.v1_8_R1.TileEntity;
import net.minecraft.server.v1_8_R1.TileEntityBrewingStand;
import net.minecraft.server.v1_8_R1.TileEntityChest;
import net.minecraft.server.v1_8_R1.TileEntityDispenser;
import net.minecraft.server.v1_8_R1.TileEntityDropper;
import net.minecraft.server.v1_8_R1.TileEntityEnchantTable;
import net.minecraft.server.v1_8_R1.TileEntityFurnace;
import net.minecraft.server.v1_8_R1.TileEntityHopper;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

public class NoLock {
	Block block;
	String rawdata;
	String owner;
	List<String> users = new ArrayList<String>();
	String name;
	String type;
	String extradata;

	public NoLock(Block block) {
		this.block = block;
		this.rawdata = getRawData(block);
		JSONObject jsonobj = new JSONObject(this.rawdata);
		this.owner = jsonobj.getString("Owner");
		if (!jsonobj.isNull("Users")) {
			JSONArray jsonarray = new JSONArray(jsonobj.get("Users").toString());
			for (int i = 0; i < jsonarray.length(); i++) {
				this.users.add(jsonarray.get(i).toString());
			}
		} else {
			this.users = null;
		}

		if (!jsonobj.isNull("Name")) {
			this.name = jsonobj.getString("Name");
		} else {
			this.name = null;
		}

		if (!jsonobj.isNull("ExtraData")) {
			this.extradata = jsonobj.getString("ExtraData");
		} else {
			this.extradata = null;
		}
	}

	// getBlock method
	public Block getBlock() {
		return this.block;
	}

	// getRawdata methods
	public String getRawData() {
		return this.rawdata;
	}

	public static String getRawData(Block block) {
		final CraftWorld world = (CraftWorld) block.getWorld();
		final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());
		if (nmsTileEntity instanceof INamableTileEntity) {
			return ((INamableTileEntity) nmsTileEntity).getName();
		}
		return null;
	}

	// getOwner methods
	public String getOwner() {
		return this.owner;
	}

	public static String getOwner(String rawdata) {
		JSONObject jsonobj = new JSONObject(rawdata);
		return jsonobj.getString("Owner");
	}

	// geUsers methods
	public List<String> getUsers() {
		return this.users;
	}

	public static List<String> getUsers(String rawdata) {
		JSONObject jsonobj = new JSONObject(rawdata);
		if (!jsonobj.isNull("Users")) {
			JSONArray jsonarray = new JSONArray(jsonobj.get("Users").toString());
			List<String> users = new ArrayList<String>();
			for (int i = 0; i < jsonarray.length(); i++) {
				users.add(jsonarray.get(i).toString());
			}
			return users;
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public static String getName(String rawdata) {
		JSONObject jsonobj = new JSONObject(rawdata);
		if (!jsonobj.isNull("Name")) {
			return jsonobj.getString("Name");
		} else {
			return null;
		}
	}

	// getExtraData methods
	public String getExtraData() {
		return this.extradata;
	}

	public static String getExtraData(String rawdata) {
		JSONObject jsonobj = new JSONObject(rawdata);
		if (!jsonobj.isNull("ExtraData")) {
			return jsonobj.getString("ExtraData");
		} else {
			return null;
		}
	}

	// setRawData methods
	public boolean setRawData(String rawdata) {
		return setRawData(this.block, rawdata);
	}

	public static boolean setRawData(Block block, String rawdata) {
		final CraftWorld world = (CraftWorld) block.getWorld();
		final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());

		if (nmsTileEntity instanceof INamableTileEntity) {
			if (nmsTileEntity instanceof TileEntityChest) {
				((TileEntityChest) nmsTileEntity).a(rawdata);
			} else if (nmsTileEntity instanceof TileEntityFurnace) {
				((TileEntityFurnace) nmsTileEntity).a(rawdata);
			} else if (nmsTileEntity instanceof TileEntityDispenser) {
				((TileEntityDispenser) nmsTileEntity).a(rawdata);
			} else if (nmsTileEntity instanceof TileEntityDropper) {
				((TileEntityDropper) nmsTileEntity).a(rawdata);
			} else if (nmsTileEntity instanceof TileEntityHopper) {
				((TileEntityHopper) nmsTileEntity).a(rawdata);
			} else if (nmsTileEntity instanceof TileEntityBrewingStand) {
				((TileEntityBrewingStand) nmsTileEntity).a(rawdata);
			} else if (nmsTileEntity instanceof TileEntityEnchantTable) {
				((TileEntityEnchantTable) nmsTileEntity).a(rawdata);
			}
			nmsTileEntity.update();
			return true;
		}
		return false;
	}

	public static boolean setRawData(Block block, String owner, List<String> users, String name, Object extradata) {
		if (block != null && owner != null) {
			JSONStringer stringer = new JSONStringer();
			stringer.object().key("Owner").value(owner);

			stringer.key("Users").value(users);

			stringer.key("Name").value(name);

			stringer.key("Extradata").value(extradata);

			setRawData(block, stringer.endObject().toString());
			return true;
		}
		return false;
	}

	// addUsers methods
	public boolean addUsers(List<String> users) {
		return addUsers(this.block, users);
	}

	public static boolean addUsers(Block block, String rawdata, List<String> users) {
		try {
			JSONObject jsonobj = new JSONObject(rawdata);
			if (getUsers(rawdata) != null) {
				ArrayList<String> userslist = new ArrayList<String>(getUsers(rawdata));
				userslist.addAll(users);
				jsonobj.put("Users", userslist);
			} else {
				jsonobj.put("Users", users);
			}
			return setRawData(block, jsonobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addUsers(Block block, List<String> users) {
		if (getRawData(block) != null) {
			return addUsers(block, getRawData(block), users);
		}
		return false;
	}

	public boolean removeUsers(List<String> users) {
		return removeUsers(this.block, users);
	}

	public static boolean removeUsers(Block block, String rawdata, List<String> users) {
		try {
			JSONObject jsonobj = new JSONObject(rawdata);
			if (getUsers(rawdata) != null) {
				ArrayList<String> userslist = new ArrayList<String>(getUsers(rawdata));
				userslist.removeAll(users);
				jsonobj.put("Users", userslist);
			} else {
				return false;
			}
			return setRawData(block, jsonobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeUsers(Block block, List<String> users) {
		if (getRawData(block) != null) {
			return removeUsers(block, getRawData(block), users);
		}
		return false;
	}

	// isContainer method
	public static boolean isContainer(Block block) {
		try {
			JSONObject jsonobj = new JSONObject(getRawData(block));
			if (jsonobj.has("Owner")) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isContainer(String rawdata) {
		try {
			JSONObject jsonobj = new JSONObject(rawdata);
			if (jsonobj.has("Owner") && jsonobj.has("Users") && jsonobj.has("Name") && jsonobj.has("Extradata")) {
				if (jsonobj.get("Owner") != null) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	// isOwner method
	public boolean isOwner(Player player) {
		return isOwner(this.block, player);
	}

	public static boolean isOwner(Block block, Player player) {
		return getOwner(getRawData(block)).equalsIgnoreCase(player.getUniqueId().toString());
	}

	// isUser method
	public boolean isUser(Player player) {
		return isUser(this.block, player);
	}

	public static boolean isUser(Block block, Player player) {
		if (getUsers(getRawData(block)) == null) {
			return false;
		} else {
			return getUsers(getRawData(block)).contains(player.getUniqueId().toString());
		}
	}

	public Material getType() {
		return this.block.getType();
	}

	public static Material getType(NoLock container) {
		return container.getBlock().getType();
	}

	public static boolean isNamableTileEntity(Block block) {
		final CraftWorld world = (CraftWorld) block.getWorld();
		final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());

		if (nmsTileEntity instanceof INamableTileEntity) {
			return true;
		}
		return false;
	}

}
