package me.Brian.NoLock;

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

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

public class Container {
	String rawdata;
	String owner;
	List<String> users = new ArrayList<String>();

	public Container(Block block) {
		this.rawdata = getRawData(block);
		JSONObject jsonobj = new JSONObject(this.rawdata);
		this.owner = jsonobj.getString("Owner");
		JSONArray jsonarray = new JSONArray(jsonobj.get("Users").toString());
		for (int i = 0; i < jsonarray.length(); i++) {
			this.users.add(jsonarray.get(i).toString());
		}
	}

	public Container(String rawdata) {
		this.rawdata = rawdata;
		JSONObject jsonobj = new JSONObject(this.rawdata);
		this.owner = jsonobj.getString("Owner");
		JSONArray jsonarray = new JSONArray(jsonobj.get("Users").toString());
		for (int i = 0; i < jsonarray.length(); i++) {
			this.users.add(jsonarray.get(i).toString());
		}
	}

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

	public String getOwner() {
		return this.owner;
	}

	public static String getOwner(String rawdata) {
		JSONObject jsonobj = new JSONObject(rawdata);
		return jsonobj.getString("Owner");
	}

	public List<String> getUsers() {
		return this.users;
	}

	public static List<String> getUsers(String rawdata) {
		JSONObject jsonobj = new JSONObject(rawdata);
		if (jsonobj.has("Users")) {
			JSONArray jsonarray = new JSONArray(jsonobj.get("Users").toString());
			List<String> users = new ArrayList<String>();
			for (int i = 0; i < jsonarray.length(); i++) {
				users.add(jsonarray.get(i).toString());
			}
			return users;
		}
		return null;
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

	public static boolean setRawData(Block block, String owner, List<String> users, Object extradata) {
		if (block != null && owner != null) {
			JSONStringer stringer = new JSONStringer();
			stringer.object().key("Owner").value(owner);
			if (users != null) {
				stringer.key("Users").value(users);
			}
			if (extradata != null) {
				stringer.object().key("Extradata").value(extradata);
			}
			setRawData(block, stringer.endObject().toString());
			return true;
			// System.out.println(stringer.endObject().toString());
		}
		return false;
	}

	public static boolean addUsers(Block block, String rawdata, List<String> users) {
		try {
			JSONObject jsonobj = new JSONObject(rawdata);
			if (getUsers(rawdata) != null) {
				jsonobj.put("Users", users.add(jsonobj.get("Users").toString()));
			} else {
				jsonobj.put("Users", users);
			}
			return setRawData(block, jsonobj.toString());
			// System.out.println(jsonobj.toString());
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
}
