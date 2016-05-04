MobShowcase
===========

これはMinecraftのModです。追加されたBlockを右クリックし、Itemを入れることでMobを表示することができます。

## Description

追加されるBlockにItemのEggかSpawn Eggを中に入れるとそれに対応したMobが表示されます。  
追加されるBlockは以下の2種類。見た目が異なるだけで機能は同じです。

- Stone Showcase
- Glass Showcase

## Recipe
  
Stone Showcaseを作る場合は石(Stone)、Glass Showcaseを作る場合はガラス(Glass)を用いる。

```    
XXX
XZX

X = 石(Stone) or ガラス(Glass)
Z = ラピスラズリ(Lapislazuli)
```

## Addon

このModのアドオンとして他にもMobを表示させるItemを追加したいのであれば、以下のようにして登録して追加することができます。

```java
// Itemの卵をいれると鶏が表示されるようになります。
MobShowcaseRegistry.registerModContainer(new IMobContainer() {
	@Override
	public boolean accept(ItemStack is) {
		return is.getItem() == Items.egg;
	}
	
	@Override
	public Entity getEntity(ItemStack is, World world) {
		return new EntityChicken(world);
	}
});
```

## Requirement

- MinecraftForge
