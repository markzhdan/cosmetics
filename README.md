# Cosmetics
>One of my big projects...

Minecraft Spigot plugin for Morven SMP that added player cosmetic rewards.

# Purpose
Added cosmetic rewards to players that can be redeemed by server points (items) from events. Attracted playerbase and retained player activity.


# Features
1. Connects with MongoDB to create, store and update player data.
    - Stored data regarding:
        - Cosmetic types
        - Cosmetic variations
        - Equipped cosmetic
        - Player currency
        - Unlocked cosmetics
        - Cosmetic prices
        

2. Dynamic and responsive custom GUI.
      - Displayed a different GUI based on a normal player and an admin.
      - Purchase, equip and remove cosmetics.
      - Multiple pages and elements.

3. 20+ particle trails and effects around bow and elytra actions.
      - Arrow particle effects.
      - Flying trails .

4. Player QOL
      - Stored player data on last equipped and unlocked cosmetics.
      - Easy to use GUI with a simple layout.

5. Admin control.
      - Allowed admins to change cosmetic price and availability through a custom GUI.
      - Sped up admin productivity and management.


# Commands
**/cosmetics**
- Opens up cosmetic GUI to purchase, equip and remove cosmetics (effecting all players)

**/cosmetics admin**
- Limited to server admin
- Opens up admin GUI to change prices and availability of certain cosmetics

**/setcurrency**
- Limited to server admin
- Sets the cosmetic currency to the item the player is holding in their hand. Supports custom blocks and player heads

