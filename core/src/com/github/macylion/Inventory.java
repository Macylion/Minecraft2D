package com.github.macylion;

import java.util.ArrayList;

public abstract class Inventory {
	
	private static ArrayList<ItemSlot> slots = new ArrayList<ItemSlot>();
	
	public static void addItem(String name, int quantity) {
		boolean isNew = true;
		for(ItemSlot is : slots)
			if(is.getItemName().equals(name)) {
				is.add(quantity);
				isNew = false;
				break;
			}
		if(isNew)
			slots.add(new ItemSlot(name, quantity));
	}
	
	public static int getQuantity(String name) {
		for(ItemSlot is : slots)
			if(is.getItemName().equals(name))
				return is.getQuantity();
		return -1;
	}
	
	public static void removeItem(String name, int quantity) {
		boolean isNew = true;
		for(ItemSlot is : slots)
			if(is.getItemName().equals(name)) {
				is.remove(quantity);
				isNew = false;
				break;
			}
		if(isNew)
			slots.add(new ItemSlot(name, quantity));
	}
	
	public static ArrayList<ItemSlot> getItems(){
		return slots;
	}
	
	public static class ItemSlot {
		
		private String itemName;
		private int quantity;
		
		public ItemSlot(String name, int qua) {
			this.itemName = name;
			this.setQuantity(qua);
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
		public void add(int qua) {
			this.quantity += qua;
		}
		
		public void remove(int qua) {
			this.quantity -= qua;
		}

		public String getItemName() {
			return itemName;
		}
	}
	
}
