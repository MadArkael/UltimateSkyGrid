package com.gmail.labuff.shane.UltimateSkyGrid;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UltimateSkyGridPopulator
  extends BlockPopulator
{
  int[] spawnEgg = {
  
    61, 
    56, 
    62, 
    50, 
    58, 
    54, 
    51, 
    52, 
    57, 
    55, 
    90, 
    91, 
    93, 
    92, 
    96, 
    95, 
    
    120, 
    59, 
    
    98, 
    
    66 };
  
  public void populate(World world, Random random, Chunk chunk)
  {
    int wH = world.getMaxHeight();
    for (int x = 0; x < 16; x += 4) {
      for (int y = 0; y < wH; y += 4) {
        for (int z = 0; z < 16; z += 4)
        {
          Block blk = chunk.getBlock(x, y, z);
          if (blk.getType() == Material.CHEST)
          {
            Chest chest = (Chest)blk.getState();
            populateChest(world, random, UltimateSkyGrid.allBlocksOneWorld, chest);
          }
          else if (blk.getType() == Material.GRASS)
          {
            blk.getRelative(BlockFace.UP).setType(getGrassPop(), false);
            if (blk.getRelative(BlockFace.UP).getType() == Material.RED_ROSE)
            {
              Random r = new Random();
              blk.getRelative(BlockFace.UP).setData((byte)r.nextInt(9));
            }
          }
          else if (blk.getType() == Material.DIRT)
          {
            Random r = new Random();
            int data = r.nextInt(3);
            blk.setData((byte)data);
            if (data == 0)
            {
              blk.getRelative(BlockFace.UP).setType(getDirtPop());
              if (blk.getRelative(BlockFace.UP).getType() == Material.SAPLING) {
                blk.getRelative(BlockFace.UP).setData((byte)r.nextInt(6));
              }
            }
          }
          else if (blk.getType() == Material.WOOL)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(16));
          }
          else if (blk.getType() == Material.PRISMARINE)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(3));
          }
          else if (blk.getType() == Material.RED_SANDSTONE)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(3));
          }
          else if (blk.getType() == Material.SMOOTH_BRICK)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(4));
          }
          else if (blk.getType() == Material.STONE)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(7));
          }
          else if (blk.getType() == Material.LOG)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(4));
          }
          else if (blk.getType() == Material.LOG_2)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(2));
          }
          else if (blk.getType() == Material.WOOD)
          {
            Random rnd = new Random();
            blk.setData((byte)rnd.nextInt(6));
          }
          else if (blk.getType() == Material.SAND)
          {
            Random r = new Random();
            int data = r.nextInt(2);
            blk.setData((byte)data);
            if ((data == 0) && 
              (r.nextInt(10) < 1)) {
              blk.getRelative(BlockFace.UP).setTypeId(Material.CACTUS.getId(), false);
            }
          }
          else if (blk.getType() == Material.SOUL_SAND)
          {
            blk.getRelative(BlockFace.UP).setType(getSoulPop());
          }
          else if (blk.getType() == Material.MOB_SPAWNER)
          {
            if ((world.getEnvironment() == World.Environment.NETHER) || (world.getEnvironment() == World.Environment.THE_END))
            {
              if (world.getEnvironment() == World.Environment.NETHER)
              {
                CreatureSpawner spawner = (CreatureSpawner)blk.getState();
                spawner.setSpawnedType(getNetherEntity());
              }
              else
              {
                CreatureSpawner spawner = (CreatureSpawner)blk.getState();
                spawner.setSpawnedType(EntityType.ENDERMAN);
              }
            }
            else if (!UltimateSkyGrid.allBlocksOneWorld)
            {
              CreatureSpawner spawner = (CreatureSpawner)blk.getState();
              spawner.setSpawnedType(getNormEntity());
            }
            else
            {
              CreatureSpawner spawner = (CreatureSpawner)blk.getState();
              spawner.setSpawnedType(getEntityType());
            }
          }
        }
      }
    }
  }
  
  public static EntityType getEntityType()
  {
    EntityType[] mobHosNorm = {
      EntityType.ZOMBIE, 
      EntityType.SKELETON, 
      EntityType.SPIDER, 
      EntityType.PIG_ZOMBIE, 
      EntityType.SLIME };
    
    EntityType[] mobHosRare = {
      EntityType.BLAZE, 
      EntityType.GHAST, 
      EntityType.MAGMA_CUBE, 
      EntityType.CREEPER, 
      EntityType.ENDERMAN };
    
    EntityType[] mobNorm = {
      EntityType.PIG, 
      EntityType.SHEEP, 
      EntityType.CHICKEN };
    
    EntityType[] mobRare = {
      EntityType.COW, 
      EntityType.MUSHROOM_COW };
    
    Random random = new Random();
    int c = random.nextInt(100);
    EntityType entRet;
    EntityType entRet;
    if (c < 2)
    {
      entRet = mobHosRare[random.nextInt(mobHosRare.length)];
    }
    else
    {
      EntityType entRet;
      if (c < 5)
      {
        entRet = mobRare[random.nextInt(mobRare.length)];
      }
      else
      {
        EntityType entRet;
        if (c < 14) {
          entRet = mobHosNorm[random.nextInt(mobHosNorm.length)];
        } else {
          entRet = mobNorm[random.nextInt(mobNorm.length)];
        }
      }
    }
    return entRet;
  }
  
  public EntityType getNormEntity()
  {
    EntityType[] mobHosNorm = {
      EntityType.ZOMBIE, 
      EntityType.SKELETON, 
      EntityType.SPIDER, 
      EntityType.SLIME };
    
    EntityType[] mobHosRare = {
      EntityType.CREEPER, 
      EntityType.ENDERMAN };
    
    EntityType[] mobNorm = {
      EntityType.PIG, 
      EntityType.SHEEP, 
      EntityType.CHICKEN };
    
    EntityType[] mobRare = {
      EntityType.COW, 
      EntityType.MUSHROOM_COW };
    
    Random random = new Random();
    int c = random.nextInt(100);
    EntityType ent;
    EntityType ent;
    if (c < 2)
    {
      ent = mobHosRare[random.nextInt(mobHosRare.length)];
    }
    else
    {
      EntityType ent;
      if (c < 5)
      {
        ent = mobRare[random.nextInt(mobRare.length)];
      }
      else
      {
        EntityType ent;
        if (c < 14) {
          ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
        } else {
          ent = mobNorm[random.nextInt(mobNorm.length)];
        }
      }
    }
    return ent;
  }
  
  public EntityType getNetherEntity()
  {
    EntityType[] mobHosNorm = {
      EntityType.PIG_ZOMBIE, 
      EntityType.SKELETON };
    
    EntityType[] mobHosRare = {
      EntityType.BLAZE, 
      EntityType.GHAST, 
      EntityType.MAGMA_CUBE };
    
    Random random = new Random();
    int c = random.nextInt(100);
    EntityType ent;
    EntityType ent;
    if (c < 2) {
      ent = mobHosRare[random.nextInt(mobHosRare.length)];
    } else {
      ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
    }
    return ent;
  }
  
  public static Material getGrassPop()
  {
    Random rand = new Random();
    int p = rand.nextInt(100);
    if (p < 5)
    {
      Material popMat = Material.RED_MUSHROOM;
      return popMat;
    }
    if (p < 10)
    {
      Material popMat = Material.BROWN_MUSHROOM;
      return popMat;
    }
    if (p < 18)
    {
      Material popMat = Material.RED_ROSE;
      return popMat;
    }
    if (p < 20)
    {
      Material popMat = Material.YELLOW_FLOWER;
      return popMat;
    }
    if (p < 25)
    {
      Material popMat = Material.SUGAR_CANE_BLOCK;
      return popMat;
    }
    return Material.AIR;
  }
  
  public static Material getSoulPop()
  {
    Random random = new Random();
    int a = random.nextInt(10);
    if (a < 2) {
      return Material.NETHER_WARTS;
    }
    return Material.AIR;
  }
  
  public static Material getDirtPop()
  {
    Random r = new Random();
    int p = r.nextInt(10);
    if (p < 1) {
      return Material.SAPLING;
    }
    return Material.AIR;
  }
  
  public void populateChest(World world, Random random, boolean allBlocks, Chest chest)
  {
    World.Environment env = world.getEnvironment();
    
    int[] itemMythicID = new int[1];
    int[] itemMythicAmount = new int[1];
    
    int mythChance = 0;
    int maxI;
    int rareChance;
    int[] itemRareID;
    int[] itemRareAmount;
    int[] itemID;
    int[] itemAmount;
    int maxI;
    if ((env == World.Environment.NETHER) || (env == World.Environment.THE_END))
    {
      int maxI;
      if (env == World.Environment.NETHER)
      {
        int rareChance = UltimateSkyGrid.cNethChRare;
        int[] itemRareID = UltimateSkyGrid.iNethChRare;
        int[] itemRareAmount = UltimateSkyGrid.iNethChRareAmount;
        int[] itemID = UltimateSkyGrid.iNethChNorm;
        int[] itemAmount = UltimateSkyGrid.iNethChNormAmount;
        int a = random.nextInt(10);
        int preMax1 = random.nextInt(2);
        int preMax2 = random.nextInt(5);
        int maxI;
        if (a < 2) {
          maxI = 1 + preMax2;
        } else {
          maxI = 1 + preMax1;
        }
      }
      else
      {
        int rareChance = UltimateSkyGrid.cEndChRare;
        int[] itemRareID = UltimateSkyGrid.iEndChRare;
        int[] itemRareAmount = UltimateSkyGrid.iEndChRareAmount;
        int[] itemID = UltimateSkyGrid.iEndChNorm;
        int[] itemAmount = UltimateSkyGrid.iEndChNormAmount;
        int a = random.nextInt(10);
        int preMax1 = random.nextInt(2);
        int preMax2 = random.nextInt(5);
        int maxI;
        if (a < 2) {
          maxI = 1 + preMax2;
        } else {
          maxI = 1 + preMax1;
        }
      }
    }
    else
    {
      int maxI;
      if (allBlocks)
      {
        mythChance = UltimateSkyGrid.cChMythic;
        int rareChance = UltimateSkyGrid.cChRare;
        itemMythicID = UltimateSkyGrid.iChMythic;
        itemMythicAmount = UltimateSkyGrid.iChMythicAmount;
        int[] itemRareID = UltimateSkyGrid.iChRare;
        int[] itemRareAmount = UltimateSkyGrid.iChRareAmount;
        int[] itemID = UltimateSkyGrid.iChNormal;
        int[] itemAmount = UltimateSkyGrid.iChNormalAmount;
        int a = random.nextInt(10);
        int preMax1 = random.nextInt(4);
        int preMax2 = random.nextInt(10);
        int maxI;
        if (a < 2) {
          maxI = 1 + preMax2;
        } else {
          maxI = 1 + preMax1;
        }
      }
      else
      {
        mythChance = UltimateSkyGrid.cChMythic;
        rareChance = UltimateSkyGrid.cChRare;
        itemMythicID = UltimateSkyGrid.iNormChMythic;
        itemMythicAmount = UltimateSkyGrid.iNormChMythicAmount;
        itemRareID = UltimateSkyGrid.iNormChRare;
        itemRareAmount = UltimateSkyGrid.iNormChRareAmount;
        itemID = UltimateSkyGrid.iNormChNormal;
        itemAmount = UltimateSkyGrid.iNormChNormalAmount;
        int a = random.nextInt(10);
        int preMax1 = random.nextInt(4);
        int preMax2 = random.nextInt(10);
        int maxI;
        if (a < 2) {
          maxI = 1 + preMax2;
        } else {
          maxI = 1 + preMax1;
        }
      }
    }
    for (int i = 0; i < maxI; i++)
    {
      Inventory inv = chest.getInventory();
      int quality = random.nextInt(100);
      if (quality < rareChance)
      {
        if ((quality < mythChance) && (env == World.Environment.NORMAL))
        {
          int aPos = random.nextInt(itemMythicID.length);
          int ID = itemMythicID[aPos];
          int maxAmount = itemMythicAmount[aPos];
          int amount;
          int amount;
          if (maxAmount == 1) {
            amount = 1;
          } else {
            amount = random.nextInt(maxAmount) + 1;
          }
          ItemStack itm = new ItemStack(ID, amount, (short)0);
          if (itm.getType() == Material.MONSTER_EGG)
          {
            Random rdm = new Random();
            itm = new ItemStack(Material.MONSTER_EGG, 1, (short)this.spawnEgg[rdm.nextInt(this.spawnEgg.length)]);
            inv.addItem(new ItemStack[] { itm });
          }
          else
          {
            inv.addItem(new ItemStack[] { itm });
          }
        }
        else
        {
          int aPos = random.nextInt(itemRareID.length);
          int ID = itemRareID[aPos];
          int maxAmount = itemRareAmount[aPos];
          int amount;
          int amount;
          if (maxAmount == 1) {
            amount = 1;
          } else {
            amount = random.nextInt(maxAmount) + 1;
          }
          ItemStack itm = new ItemStack(ID, amount, (short)0);
          if ((itm.getType() == Material.MONSTER_EGG) || (itm.getType() == Material.LOG) || (itm.getType() == Material.LOG_2))
          {
            if (itm.getType() == Material.MONSTER_EGG)
            {
              itm = new ItemStack(Material.MONSTER_EGG, amount, (short)this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
              inv.addItem(new ItemStack[] { itm });
            }
            else if (itm.getType() == Material.LOG)
            {
              itm = new ItemStack(Material.LOG, amount, (short)random.nextInt(4));
              inv.addItem(new ItemStack[] { itm });
            }
            else if (itm.getType() == Material.LOG_2)
            {
              itm = new ItemStack(Material.LOG_2, amount, (short)random.nextInt(2));
              inv.addItem(new ItemStack[] { itm });
            }
          }
          else {
            inv.addItem(new ItemStack[] { itm });
          }
        }
      }
      else
      {
        int aPos = random.nextInt(itemID.length);
        int ID = itemID[aPos];
        int maxAmount = itemAmount[aPos];
        int amount;
        int amount;
        if (maxAmount == 1) {
          amount = 1;
        } else {
          amount = random.nextInt(maxAmount) + 1;
        }
        ItemStack itm = new ItemStack(ID, amount, (short)0);
        if ((itm.getType() == Material.MONSTER_EGG) || (itm.getType() == Material.LOG))
        {
          if (itm.getType() == Material.MONSTER_EGG)
          {
            itm = new ItemStack(Material.MONSTER_EGG, amount, (short)this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
            inv.addItem(new ItemStack[] { itm });
          }
          else if (itm.getType() == Material.LOG)
          {
            itm = new ItemStack(Material.LOG, amount, (short)random.nextInt(4));
            inv.addItem(new ItemStack[] { itm });
          }
          else if (itm.getType() == Material.LOG_2)
          {
            itm = new ItemStack(Material.LOG_2, amount, (short)random.nextInt(2));
            inv.addItem(new ItemStack[] { itm });
          }
        }
        else {
          inv.addItem(new ItemStack[] { itm });
        }
      }
    }
  }
}
