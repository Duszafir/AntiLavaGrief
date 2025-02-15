AntiLavaGrief Plugin

This plugin helps prevent griefing with lava, flint-and-steel, and now lava casting. It notifies players when suspicious actions are detected, such as placing lava, setting fire to wooden planks, or attempting to create large cobblestone structures using water and lava. This ensures better protection against griefing tactics like burning structures or mass terrain modification.

Key Features:
Alerts when lava touches wood planks.
Sends a notification when a player uses a flint-and-steel or right-clicks with a lava bucket on wooden planks.
Cooldown when using lava or flint and steel on wooden blocks, preventing abuse.
New! Detects and prevents lava casting by tracking cobblestone and stone formation.
Very easy-to-use.

Commands:
/al enable - Activates alerts for lava or flammable items placed near wooden planks.
/al disable - Turns off alerts and stops notifications from appearing.
/al help - Opens the Help menu.
/al author - Shows the author of the Plugin.
/al version - Displays the plugin version.
/al reload - Reloads the plugin configuration.

Permissions:
antilavagrief.commands.maincommand: Grants access to the main plugin commands.
antilavagrief.evadeGrief: Allows the player to bypass lava grief alerts and actions.
antilavagrief.bypassCooldown: Allows the player to bypass the lava and flint-steel cooldown.
antilavagrief.commands.reload: Allows the player to reload plugin configuration.
antilavagrief.bypass_dispenser: Allows players to bypass restrictions on placing prohibited items in dispensers and hoppers.
Config.yml

[code=YAML]
# AntiLavaGrieff Configuration
messages:
  anti_grief_enable_text: "&aLava protection has been activated."
  cooldown_text: "&cYou need to wait &f%timeLeft%&c more seconds before using that again!"
  grief_text: "&4%player% has burned a %block% with a %item%"
  lava_place_text: "&4%placer% burned %block%"
  ban_lava_place: "&cYou cant place lava or use the flint and steel here"

ban_blocks:
  - "LAVA_BUCKET"
  - "FLINT_AND_STEEL"
  - "FIRE_CHARGE"

config:

  block_lava_place:
    enabled: false
    allowed_blocks:
      - "OBSIDIAN"
      - "FIRE"
  cooldown:
    enabled: true
    cooldown-time: 9

  anti_dispenser_and_hopper:
 
    enabled: true

  anti_lavacast:
    enabled: true
    blocks: 20

  worlds:
    - "world"
    - "world_nether"
    - "world_the_end"
[/code]

Directories:

config.yml
grief_logs.txt
Stay one step ahead of potential griefers with AntiLavaGrief!

You can contact me on my social pages found on my website:

https://duszafir.netlify.app/
