# NoLock
Chest locks have never been this simple and clean!

A mordern spigot chestlock plugin, requires no external database storage or extra
signs around to record owner's name. 

The idea of how data is stored is to set 
TileEntityName for containers (chests/furnaces..etc) as json data with owner and
users' names and UUIDs.

Information of a container is  stored like this:  
```
{
  "Owner": "UUID",
  "Users": [
    "UUID",
    "UUID"
  ],
  "Name": "Custom name",
  "Extradata": "Any data"
}
```

**Owner** section stored the UUID of a player, this player own the container and have permission to add/remove user(s), or unlock the container

**Users** section stored the UUIDs of several players in a list, this players can open, add/remove item from the container. But they have no permission to unlock/destroy the container or add/remove user(s).

**Name** section stored the custom name of the chest. When ProtocolLib overide enabled in config file, this name will override the normal chest name.  

**Extradata** section is basicly for other devolopers. It allow devolopers to add any json format information in it and store in the container.

**Users**, **Name** and **Extradata** sections can be null. But **Owner** section can't be null or plugin won't detect it as a locked container.


一个全新的箱子锁插件，融合了Lockette的轻量与LWC的干净整洁。

使用TileEntityName来储存信息，不需要任何额外数据库，安全方便高效

References:  
http://wiki.vg/Protocol#Open_Window  
https://bukkit.org/threads/custom-gui-names-non-chest.255395/
