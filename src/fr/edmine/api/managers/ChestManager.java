package fr.edmine.api.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class ChestManager
{

	/**
	 * Permet de faire spawn des coffres aléatoirements dans un radius autours d'une
	 * location. ATTENTION CETTE METHODE EST GOURMANDE
	 * 
	 * @param center    Une location ou ce basé pour le radius de spawn de coffres.
	 * @param minRadius Le radius minimum ou faire spawn les coffres.
	 * @param maxRadius Le radius maximum ou faire spawn les coffres.
	 * @param numChests Le nombre de coffres a faire spawn.
	 */
	public void placeChestsAroundLocation(Location center, int minRadius, int maxRadius, int numChests)
	{
		List<Location> possibleLocations = generatePossibleChestLocations(center, minRadius, maxRadius);

		// Mélanger la liste des emplacements possibles
		Collections.shuffle(possibleLocations);

		// Sélectionner aléatoirement un nombre spécifié d'emplacements parmi les
		// emplacements possibles
		List<Location> selectedLocations = possibleLocations.subList(0, Math.min(numChests, possibleLocations.size()));

		// Placer des coffres aux emplacements sélectionnés
		for (Location location : selectedLocations)
		{
			location.getBlock().setType(Material.CHEST);
		}
	}

	/**
	 * Permet de remplir tout les coffres dans tous les mondes
	 * 
	 * @param commonItems       Une liste d'itemStack communs avec les paramètres de
	 *                          votre choix.
	 * @param rareItems         Une liste d'itemStack rares avec les paramètres de
	 *                          votre choix.
	 * @param amountCommonItems Un nombre d'items communs générables dans un coffre.
	 * @param amountRareItems   Un nombre d'items rares générables dans un coffre.
	 */
	public void fillAllChestsRandomlyInAllWorlds(List<ItemStack> commonItems, List<ItemStack> rareItems, int amountCommonItems, int amountRareItems)
	{
		Bukkit.getWorlds().forEach(world -> this.fillAllChestsRandomlyInAWorld(world, commonItems, rareItems, amountCommonItems, amountRareItems));
	}

	/**
	 * Permet de remplir tout les coffres d'un monde
	 * 
	 * @param world             Le monde ou remplir tout les coffres
	 * @param commonItems       Une liste d'itemStack communs avec les paramètres de
	 *                          votre choix.
	 * @param rareItems         Une liste d'itemStack rares avec les paramètres de
	 *                          votre choix.
	 * @param amountCommonItems Un nombre d'items communs générables dans un coffre.
	 * @param amountRareItems   Un nombre d'items rares générables dans un coffre.
	 */
	public void fillAllChestsRandomlyInAWorld(World world, List<ItemStack> commonItems, List<ItemStack> rareItems, int amountCommonItems, int amountRareItems)
	{
		// Parcourir tous les chunks chargés dans le monde
		for (Chunk chunk : world.getLoadedChunks())
		{
			// Parcourir tous les blocs de chaque chunk
			for (int x = 0; x < 16; x++)
			{
				for (int z = 0; z < 16; z++)
				{
					for (int y = 0; y < world.getMaxHeight(); y++)
					{
						Block block = chunk.getBlock(x, y, z);
						if (block.getState() instanceof Chest)
						{
							Chest chest = (Chest) block.getState();
							fillChest(chest, commonItems, rareItems, amountCommonItems, amountRareItems); // Remplir le coffre
						}
					}
				}
			}
		}
	}

	/**
	 * Permet de remplir un coffre avec le contenu de votre choix, avec items
	 * communs et items rares. (chiffres aléatoire, et disposition aléatoire)
	 * 
	 * @param chest             Le coffre que vous voulez remplir
	 * @param commonItems       Une liste d'itemStack communs avec les paramètres de
	 *                          votre choix.
	 * @param rareItems         Une liste d'itemStack rares avec les paramètres de
	 *                          votre choix.
	 * @param amountCommonItems Un nombre d'items communs générables dans un coffre.
	 * @param amountRareItems   Un nombre d'items rares générables dans un coffre.
	 */
	public void fillChest(Chest chest, List<ItemStack> commonItems, List<ItemStack> rareItems, int amountCommonItems, int amountRareItems)
	{
		Random random = new Random();
		int amountCommon = random.nextInt(amountCommonItems) + 1;
		int amountRare = random.nextInt(amountRareItems) + 1;

		// Placer les items communs
		for (int i = 0; i < amountCommon; i++)
		{
			int slot = random.nextInt(26) + 1;
			int randomIndex = random.nextInt(commonItems.size());
			chest.getBlockInventory().setItem(slot, commonItems.get(randomIndex));
		}

		// Placer les items rares
		for (int i = 0; i < amountRare; i++)
		{
			int slot = random.nextInt(26) + 1;
			int randomIndex = random.nextInt(rareItems.size());
			chest.getBlockInventory().setItem(slot, rareItems.get(randomIndex));
		}
	}

	private List<Location> generatePossibleChestLocations(Location center, int minRadius, int maxRadius)
	{
		List<Location> possibleLocations = new ArrayList<>();

		// Parcourir tous les blocs autour de la location centrale avec un rayon maximum
		for (int x = center.getBlockX() - maxRadius; x <= center.getBlockX() + maxRadius; x++)
		{
			for (int y = center.getBlockY() - maxRadius; y <= center.getBlockY() + maxRadius; y++)
			{
				for (int z = center.getBlockZ() - maxRadius; z <= center.getBlockZ() + maxRadius; z++)
				{
					Location location = new Location(center.getWorld(), x, y, z);
					double distance = location.distance(center);
					// Vérifier si la distance est comprise entre le rayon minimum et maximum
					if (distance >= minRadius && distance <= maxRadius && isValidChestLocation(location) && isSafeDistance(location, possibleLocations))
					{
						Location newloc = new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ());
						possibleLocations.add(newloc);
					}
				}
			}
		}
		return possibleLocations;
	}

	/**
	 * @param location - Initial
	 * @param placedLocations - 20 blocs de distance à la position initial
	 * @return false - Si la distance est inférieure à 20 blocs, l'emplacement n'est pas sûr
	 * @return true  - Si la distance est supérieure ou égale à 20 blocs pour tous les coffres déjà placés, l'emplacement est sûr
	 */
	private boolean isSafeDistance(Location location, List<Location> placedLocations)
	{
		for (Location placedLoc : placedLocations)
		{
			// 20 blocs de distance au carré
			return location.distanceSquared(placedLoc) < 600 ? false : true;
		}
		return true;
	}

	/**
	 * @param location - Si un emplacement est valide pour placer un coffre
	 * @return
	 * 
	 * (?) Là il vérifie surtout si le block correspond à se qui contient dans la list -> acceptSpawnBlocks()
	 */
	private boolean isValidChestLocation(Location location)
	{
		Block block = location.getBlock();
		// Si le block est de l'herbe ou de la terre
		if (acceptSpawnBlocks().contains(block.getType()))
		{
			// Si le bloc au-dessus est de l'air
			return location.clone().add(0, 1, 0).getBlock().getType().equals(Material.AIR) ? true : false;
		}
		return false;
	}

	private List<Material> acceptSpawnBlocks()
	{
		List<Material> list = new ArrayList<>();

		list.add(Material.GRASS);
		list.add(Material.DIRT);
		list.add(Material.STONE);
		list.add(Material.COBBLESTONE);
		list.add(Material.LOG);
		list.add(Material.LOG_2);
		list.add(Material.SAND);
		list.add(Material.GRAVEL);
		list.add(Material.SANDSTONE);
		list.add(Material.RED_SANDSTONE);
		list.add(Material.STAINED_CLAY);
		list.add(Material.QUARTZ_BLOCK);
		list.add(Material.ENDER_STONE);
		list.add(Material.NETHERRACK);
		list.add(Material.OBSIDIAN);
		list.add(Material.SOUL_SAND);
		list.add(Material.STAINED_CLAY);
		list.add(Material.SOUL_SAND);
		list.add(Material.SMOOTH_BRICK);
		list.add(Material.DOUBLE_STONE_SLAB2);

		return list;
	}
}
