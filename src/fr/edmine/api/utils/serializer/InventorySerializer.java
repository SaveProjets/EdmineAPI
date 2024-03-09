package fr.edmine.api.utils.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class InventorySerializer
{

	public static String toBase64(Inventory inventory)
	{
		try
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(inventory.getSize());

			// Save all elements in the inventory
			for (int slot = 0; slot < inventory.getSize(); slot++) dataOutput.writeObject(inventory.getItem(slot));

			// Serialize
			dataOutput.close();
			return Base64.getEncoder().encodeToString(outputStream.toByteArray());
		}
		catch (IOException exeption)
		{
			throw new IllegalStateException("Erreur lors de la sauvegarde de l'inventaire.", exeption);
		}
	}

	public static Inventory fromBase64(String base64)
	{
		try
		{
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

			// Read the size of the inventory
			int size = dataInput.readInt();

			// Create the inventory
			Inventory inventory = Bukkit.createInventory(null, size);

			// Read the elements in the inventory
			for (int slot = 0; slot < size; slot++)
			{
				inventory.setItem(slot, (ItemStack) dataInput.readObject());
			}

			// Deserialize
			dataInput.close();
			return inventory;
		}
		catch (IOException | ClassNotFoundException exeption)
		{
			throw new IllegalStateException("Erreur lors du chargement de l'inventaire.", exeption);
		}
	}

}
