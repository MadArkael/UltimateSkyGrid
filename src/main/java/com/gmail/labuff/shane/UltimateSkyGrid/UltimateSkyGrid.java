package com.gmail.labuff.shane.UltimateSkyGrid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimateSkyGrid
  extends JavaPlugin
{
  public FileConfiguration configMain;
  public File dirPlayers;
  public File conFileMain;
  public File dataFolder;
  public static int cdelay;
  public static int cHeight;
  public static int cNetherHeight;
  public static int cEndHeight;
  public static int cMythic;
  public static int cUnique;
  public static int cRare;
  public static int cUncommon;
  public static int cMax;
  public static int cMin;
  public static int cChMythic;
  public static int cChRare;
  public static int cNethRare;
  public static int cNethChRare;
  public static int cEndChRare;
  public static int cEndRare;
  public static boolean genGlass;
  public static boolean allBlocksOneWorld;
  public static String cName;
  public static String cNetherName;
  public static String cEndName;
  public static int[] iMythic;
  public static int[] iUnique;
  public static int[] iRare;
  public static int[] iUncommon;
  public static int[] iAbundant;
  public static int[] iNormMythic;
  public static int[] iNormUnique;
  public static int[] iNormRare;
  public static int[] iNormUncommon;
  public static int[] iNormAbundant;
  public static int[] iChMythic;
  public static int[] iChMythicAmount;
  public static int[] iNormChMythic;
  public static int[] iNormChMythicAmount;
  public static int[] iChRare;
  public static int[] iChRareAmount;
  public static int[] iNormChRare;
  public static int[] iNormChRareAmount;
  public static int[] iChNormal;
  public static int[] iChNormalAmount;
  public static int[] iNormChNormal;
  public static int[] iNormChNormalAmount;
  public static int[] iNethBlkRare;
  public static int[] iNethBlkNorm;
  public static int[] iEndBlkRare;
  public static int[] iEndBlkNorm;
  public static int[] iNethChRare;
  public static int[] iNethChRareAmount;
  public static int[] iNethChNorm;
  public static int[] iNethChNormAmount;
  public static int[] iEndChRare;
  public static int[] iEndChRareAmount;
  public static int[] iEndChNorm;
  public static int[] iEndChNormAmount;
  
  public void onEnable()
  {
    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
    File dFolder = getDataFolder();
    File drPlyrs = new File(getDataFolder(), "Players");
    File cnFlMn = new File(getDataFolder(), "config.yml");
    
    this.dirPlayers = drPlyrs;
    this.conFileMain = cnFlMn;
    this.configMain = getConfig();
    this.dataFolder = dFolder;
    if (!this.dirPlayers.exists()) {
      this.dirPlayers.mkdir();
    }
    if (this.conFileMain.exists()) {
      this.configMain = YamlConfiguration.loadConfiguration(this.conFileMain);
    } else {
      copyConfig(this.conFileMain, getClass());
    }
    initConfig();
    
    getLogger().info("UltimateSkyGrid Version v0.2.3 Enabled/Reloaded");
  }
  
  public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
  {
    return new UltimateSkyGridGenerator();
  }
  
  private static void copyConfig(File config, Class<? extends UltimateSkyGrid> cl)
  {
    try
    {
      InputStream in = cl.getResourceAsStream("/" + config.getName());
      FileOutputStream out = new FileOutputStream(config);
      byte[] buffer = new byte['?'];
      int i;
      while ((i = in.read(buffer)) != -1)
      {
        int i;
        out.write(buffer, 0, i);
      }
      out.close();
    }
    catch (FileNotFoundException e)
    {
      Bukkit.getLogger().log(Level.SEVERE, "Plugin jar does not appear to have the required config file for writing", e);
    }
    catch (IOException e)
    {
      Bukkit.getLogger().log(Level.SEVERE, null, e);
    }
  }
  
  public void initConfig()
  {
    FileConfiguration config = this.configMain;
    
    cdelay = config.getInt("Delay", 0);
    cHeight = config.getInt("World_Height", 128);
    cMythic = config.getInt("Mythic_Block_Probability", 4);
    cNetherHeight = config.getInt("NetherConfig.Nether_World_Height", 128);
    cEndHeight = config.getInt("EndConfig.End_World_Height", 128);
    cUnique = config.getInt("Unique_Block_Probability", 181);
    cRare = config.getInt("Rare_Block_Probability", 1801);
    cUncommon = config.getInt("Uncommon_Block_Probability", 4001);
    cMax = config.getInt("Spawn_Max", 500);
    cMin = config.getInt("Spawn_Min", 0);
    cName = config.getString("World_Name", "Skygrid");
    cNetherName = config.getString("NetherConfig.Nether_World_Name", "NetherSkygrid");
    cEndName = config.getString("EndConfig.End_World_Name", "EndSkygrid");
    cChMythic = config.getInt("Chest_Prob_Mythic", 2);
    cChRare = config.getInt("Chest_Prob_Rare", 6);
    genGlass = config.getBoolean("ReplaceAirWithGlass", false);
    allBlocksOneWorld = config.getBoolean("AllBlocksOneWorld", true);
    cNethRare = config.getInt("NetherConfig.NetherBlocks.RareProb", 1);
    cNethChRare = config.getInt("NetherConfig.NetherChestItems.RareProb", 2);
    cEndRare = config.getInt("EndConfig.EndBlocks.RareProb", 1);
    cEndChRare = config.getInt("EndConfig.EndChestItems.RareProb", 1);
    
    String[] sMythic = config.getString("BlockGroups.Mythic").split(" ");
    String[] sUnique = config.getString("BlockGroups.Unique").split(" ");
    String[] sRare = config.getString("BlockGroups.Rare").split(" ");
    String[] sUncommon = config.getString("BlockGroups.Uncommon").split(" ");
    String[] sAbundant = config.getString("BlockGroups.Abundant").split(" ");
    String[] sNethBlkRare = config.getString("NetherConfig.NetherBlocks.Rare").split(" ");
    String[] sNethBlkNorm = config.getString("NetherConfig.NetherBlocks.Normal").split(" ");
    String[] sEndBlkRare = config.getString("EndConfig.EndBlocks.Rare").split(" ");
    String[] sEndBlkNorm = config.getString("EndConfig.EndBlocks.Normal").split(" ");
    String[] sChMythic = config.getString("ChestItems.Mythic").split(" ");
    String[] sChRare = config.getString("ChestItems.Rare").split(" ");
    String[] sChNormal = config.getString("ChestItems.Normal").split(" ");
    String[] sNethChRare = config.getString("NetherConfig.NetherChestItems.Rare").split(" ");
    String[] sNethChNorm = config.getString("NetherConfig.NetherChestItems.Normal").split(" ");
    String[] sEndChRare = config.getString("EndConfig.EndChestItems.Rare").split(" ");
    String[] sEndChNorm = config.getString("EndConfig.EndChestItems.Normal").split(" ");
    String[] sNormMythic = config.getString("NormalConfig.BlockGroups.Mythic").split(" ");
    String[] sNormUnique = config.getString("NormalConfig.BlockGroups.Unique").split(" ");
    String[] sNormRare = config.getString("NormalConfig.BlockGroups.Rare").split(" ");
    String[] sNormUncommon = config.getString("NormalConfig.BlockGroups.Uncommon").split(" ");
    String[] sNormAbundant = config.getString("NormalConfig.BlockGroups.Abundant").split(" ");
    String[] sNormChMythic = config.getString("NormalConfig.ChestItems.Mythic").split(" ");
    String[] sNormChRare = config.getString("NormalConfig.ChestItems.Rare").split(" ");
    String[] sNormChNormal = config.getString("NormalConfig.ChestItems.Normal").split(" ");
    
    iMythic = stringArrayToIntArray(sMythic);
    iUnique = stringArrayToIntArray(sUnique);
    iRare = stringArrayToIntArray(sRare);
    iUncommon = stringArrayToIntArray(sUncommon);
    iAbundant = stringArrayToIntArray(sAbundant);
    iNethBlkRare = stringArrayToIntArray(sNethBlkRare);
    iNethBlkNorm = stringArrayToIntArray(sNethBlkNorm);
    iEndBlkRare = stringArrayToIntArray(sEndBlkRare);
    iEndBlkNorm = stringArrayToIntArray(sEndBlkNorm);
    iNormMythic = stringArrayToIntArray(sNormMythic);
    iNormUnique = stringArrayToIntArray(sNormUnique);
    iNormRare = stringArrayToIntArray(sNormRare);
    iNormUncommon = stringArrayToIntArray(sNormUncommon);
    iNormAbundant = stringArrayToIntArray(sNormAbundant);
    
    iChMythic = positionalStringArrayToIntArray(sChMythic, 0);
    iChMythicAmount = positionalStringArrayToIntArray(sChMythic, 1);
    iChRare = positionalStringArrayToIntArray(sChRare, 0);
    iChRareAmount = positionalStringArrayToIntArray(sChRare, 1);
    iChNormal = positionalStringArrayToIntArray(sChNormal, 0);
    iChNormalAmount = positionalStringArrayToIntArray(sChNormal, 1);
    iNethChRare = positionalStringArrayToIntArray(sNethChRare, 0);
    iNethChRareAmount = positionalStringArrayToIntArray(sNethChRare, 1);
    iNethChNorm = positionalStringArrayToIntArray(sNethChNorm, 0);
    iNethChNormAmount = positionalStringArrayToIntArray(sNethChNorm, 1);
    iEndChRare = positionalStringArrayToIntArray(sEndChRare, 0);
    iEndChRareAmount = positionalStringArrayToIntArray(sEndChRare, 1);
    iEndChNorm = positionalStringArrayToIntArray(sEndChNorm, 0);
    iEndChNormAmount = positionalStringArrayToIntArray(sEndChNorm, 1);
    iNormChMythic = positionalStringArrayToIntArray(sNormChMythic, 0);
    iNormChMythicAmount = positionalStringArrayToIntArray(sNormChMythic, 1);
    iNormChRare = positionalStringArrayToIntArray(sNormChRare, 0);
    iNormChRareAmount = positionalStringArrayToIntArray(sNormChRare, 1);
    iNormChNormal = positionalStringArrayToIntArray(sNormChNormal, 0);
    iNormChNormalAmount = positionalStringArrayToIntArray(sNormChNormal, 1);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    String cmdName = cmd.getName();
    if ((!(sender instanceof Player)) && (cmdName.equalsIgnoreCase("usg")))
    {
      sender.sendMessage("Only a player can execute this command.");
      return true;
    }
    Player player = (Player)sender;
    if (!cmdName.equalsIgnoreCase("usg")) {
      return false;
    }
    initPlayerConfigFile(player, getClass());
    if (args.length == 0)
    {
      player.sendMessage(ChatColor.RED + "Correct usage of this command is /usg sethome or /usg home (/usg reload for admin config reloading)");
      return true;
    }
    if (args.length >= 2)
    {
      player.sendMessage(ChatColor.RED + "Too many arguments. Correct usage of this command is /usg sethome or /usg home (/usg reload for admin config reloading)");
      return true;
    }
    if (args[0].equalsIgnoreCase("reload"))
    {
      if (!player.hasPermission("UltimateSkyGrid.reload"))
      {
        player.sendMessage(ChatColor.RED + "You do not have permission for this command");
        return true;
      }
      player.sendMessage(ChatColor.BLUE + "Reloading configuration file...");
      onEnable();
      player.sendMessage(ChatColor.BLUE + "Configuration reloaded.");
      return true;
    }
    if (args[0].equalsIgnoreCase("sethome"))
    {
      if (!player.hasPermission("UltimateSkyGrid.sethome"))
      {
        player.sendMessage(ChatColor.RED + "You do not have permission for this command");
        return true;
      }
      setHome(player);
      return true;
    }
    if (args[0].equalsIgnoreCase("home"))
    {
      if (!player.hasPermission("UltimateSkyGrid.home"))
      {
        player.sendMessage(ChatColor.RED + "You do not have permission for this command");
        return true;
      }
      File pFile = new File(this.dirPlayers, player.getName() + ".yml");
      FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
      String wName = cName;
      String nWName = cNetherName;
      String eWName = cEndName;
      World skygrid = getServer().getWorld(wName);
      World nSkygrid = getServer().getWorld(nWName);
      World eSkygrid = getServer().getWorld(eWName);
      World curWorld = player.getWorld();
      World selWorld = getServer().getWorld(wName);
      String selName = cName;
      String cWorldName = "World_Name";
      int w = cdelay;
      int x = 0;
      int y = 0;
      int z = 0;
      if ((curWorld != nSkygrid) || (curWorld != eSkygrid))
      {
        selName = wName;
        selWorld = skygrid;
        x = pConfig.getInt("homex");
        y = pConfig.getInt("homey");
        z = pConfig.getInt("homez");
      }
      if (curWorld == nSkygrid)
      {
        cWorldName = "Nether_World_Name";
        selName = nWName;
        selWorld = nSkygrid;
        x = pConfig.getInt("netherhomex");
        y = pConfig.getInt("netherhomey");
        z = pConfig.getInt("netherhomez");
      }
      if (curWorld == eSkygrid)
      {
        cWorldName = "End_World_Name";
        selName = eWName;
        selWorld = eSkygrid;
        x = pConfig.getInt("endhomex");
        y = pConfig.getInt("endhomey");
        z = pConfig.getInt("endhomez");
      }
      if (selWorld == null)
      {
        getLogger().severe("Config value: " + cWorldName + ": = null");
        getLogger().info(cWorldName + ": " + selName + " in config doesn't exist. Make sure the config name matches the actual world name, case sensitive.");
        player.sendMessage(ChatColor.RED + "Error: UltimateSkyGrid world names are not set up correctly. Tell an Admin");
        return true;
      }
      Block home = selWorld.getBlockAt(x, y, z);
      
      Location homeLoc = home.getLocation().add(0.5D, 0.0D, 0.5D);
      if (w > 0)
      {
        player.sendMessage(ChatColor.GREEN + "Waiting " + ChatColor.BLUE + w + ChatColor.GREEN + " seconds before you port...");
        new TeleportDelay(player, homeLoc, selName).runTaskLater(this, w * 20);
        return true;
      }
      player.sendMessage(ChatColor.GREEN + "Teleporting you to your home in " + selName);
      player.teleport(homeLoc);
      return true;
    }
    player.sendMessage(ChatColor.RED + "Correct usage of this command is /usg sethome or /usg home");
    return false;
  }
  
  public void initPlayerConfigFile(Player player, Class<? extends UltimateSkyGrid> cl)
  {
    File mFile = new File(getDataFolder(), "config.yml");
    
    FileConfiguration mConfig = YamlConfiguration.loadConfiguration(mFile);
    File pFile = new File(this.dirPlayers, player.getName() + ".yml");
    
    int wH = mConfig.getInt("World_Height");
    try
    {
      if (pFile.exists())
      {
        FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
        pConfig.save(pFile);
      }
      else
      {
        InputStream in = cl.getResourceAsStream("/defaultplayer.yml");
        FileOutputStream out = new FileOutputStream(pFile);
        byte[] buffer = new byte['?'];
        int i;
        while ((i = in.read(buffer)) != -1)
        {
          int i;
          out.write(buffer, 0, i);
        }
        out.close();
        
        FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
        
        pConfig.addDefault("homex", Integer.valueOf(0));
        pConfig.addDefault("homey", Integer.valueOf(wH - 3));
        pConfig.addDefault("homez", Integer.valueOf(0));
        pConfig.addDefault("netherhomex", Integer.valueOf(0));
        pConfig.addDefault("netherhomey", Integer.valueOf(wH - 3));
        pConfig.addDefault("netherhomez", Integer.valueOf(0));
        pConfig.addDefault("endhomex", Integer.valueOf(0));
        pConfig.addDefault("endhomey", Integer.valueOf(wH - 3));
        pConfig.addDefault("endhomez", Integer.valueOf(0));
        
        pConfig.set("homex", Integer.valueOf(randCoord()));
        pConfig.set("homey", Integer.valueOf(wH - 3));
        pConfig.set("homez", Integer.valueOf(randCoord()));
        pConfig.set("netherhomex", Integer.valueOf(randCoord()));
        pConfig.set("netherhomey", Integer.valueOf(wH - 3));
        pConfig.set("netherhomez", Integer.valueOf(randCoord()));
        pConfig.set("endhomex", Integer.valueOf(randCoord()));
        pConfig.set("endhomey", Integer.valueOf(wH - 3));
        pConfig.set("endhomez", Integer.valueOf(randCoord()));
        pConfig.save(pFile);
      }
    }
    catch (FileNotFoundException e)
    {
      Bukkit.getLogger().log(Level.SEVERE, "defaultplayer.yml is missing from the jar file", e);
    }
    catch (IOException e)
    {
      Bukkit.getLogger().log(Level.SEVERE, null, e);
    }
  }
  
  public void setHome(Player player)
  {
    int x = player.getLocation().getBlockX();
    int y = player.getLocation().getBlockY();
    int z = player.getLocation().getBlockZ();
    World curWorld = player.getWorld();
    if ((curWorld != getServer().getWorld(cName)) && (curWorld != getServer().getWorld(cNetherName)) && (curWorld != getServer().getWorld(cEndName)))
    {
      player.sendMessage(ChatColor.RED + "You cant set your skygrid home in this world.");
      return;
    }
    try
    {
      File pConFile = new File(this.dirPlayers, player.getName() + ".yml");
      if (pConFile.exists())
      {
        FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pConFile);
        if (curWorld == getServer().getWorld(cName))
        {
          pConfig.set("homex", Integer.valueOf(x));
          pConfig.set("homey", Integer.valueOf(y));
          pConfig.set("homez", Integer.valueOf(z));
          pConfig.save(pConFile);
          player.sendMessage(ChatColor.BLUE + "Your Skygrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
          return;
        }
        if (curWorld == getServer().getWorld(cNetherName))
        {
          pConfig.set("netherhomex", Integer.valueOf(x));
          pConfig.set("netherhomey", Integer.valueOf(y));
          pConfig.set("netherhomez", Integer.valueOf(z));
          pConfig.save(pConFile);
          player.sendMessage(ChatColor.BLUE + "Your NetherSkyGrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
          return;
        }
        pConfig.set("endhomex", Integer.valueOf(x));
        pConfig.set("endhomey", Integer.valueOf(y));
        pConfig.set("endhomez", Integer.valueOf(z));
        pConfig.save(pConFile);
        player.sendMessage(ChatColor.BLUE + "Your EndSkyGrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
        return;
      }
      player.sendMessage(ChatColor.RED + "Your player config file must not have initialized properly. Talk to an admin");
    }
    catch (FileNotFoundException e)
    {
      Bukkit.getLogger().log(Level.SEVERE, null, e);
    }
    catch (IOException e)
    {
      Bukkit.getLogger().log(Level.SEVERE, null, e);
    }
  }
  
  public File getFolder()
  {
    return this.dataFolder;
  }
  
  public File getDefConfigFile()
  {
    return this.conFileMain;
  }
  
  public int randCoord()
  {
    Random r = new Random();
    Random r2 = new Random();
    
    int b = r2.nextInt(2);
    int a = r.nextInt(cMax - cMin + 1) + cMin;
    while (a % 4 != 0) {
      a++;
    }
    if (b == 0)
    {
      a = -a;
      return a;
    }
    return a;
  }
  
  public int[] stringArrayToIntArray(String[] stringArray)
  {
    int[] newArray = new int[stringArray.length];
    for (int i = 0; i < stringArray.length; i++) {
      try
      {
        newArray[i] = Integer.parseInt(stringArray[i]);
      }
      catch (NumberFormatException e)
      {
        Bukkit.getLogger().log(Level.SEVERE, "Invalid integer in string array!", e);
      }
    }
    return newArray;
  }
  
  public int[] positionalStringArrayToIntArray(String[] stringArray, int pos)
  {
    int[] newArray = new int[stringArray.length];
    for (int i = 0; i < stringArray.length; i++)
    {
      String sP = stringArray[i];
      String[] sPA = sP.split(":");
      try
      {
        newArray[i] = Integer.parseInt(sPA[pos]);
      }
      catch (NumberFormatException e)
      {
        Bukkit.getLogger().log(Level.SEVERE, "Invalid integer in string array, or your format is wrong! <ID>:<AMOUNT>", e);
      }
    }
    return newArray;
  }
}
