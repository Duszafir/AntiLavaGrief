This plugin helps prevent griefing with lava and flint-and-steel by notifying server players via chat whenever a player places lava or sets fire to any type of wood plank. If a player attempts to use lava or a flint-and-steel on wooden planks, the plugin will broadcast a message to alert the server about the suspicious activity. It’s designed to help protect your server from players who may be attempting to burn down structures or grief the environment.

Key features:

Alerts when lava touches wood planks.
Sends a notification when a player uses a flint-and-steel or right-clicks with a lava bucket on wooden planks.
Cooldown when using lava or flint and steel on wooden blocks, preventing abuse.
Very easy-to-use.
Commands:

/al enable to activate alerts for when lava or flammable items are placed near wooden planks.
/al disable to turn off these alerts and stop notifications from appearing.
/al help to open Help menu.
/al author to se the author of the Plugin
/al version to se the version of the Plugin
/al reload

Permissions:

antilavagrief.commands.maincommand: Grants access to the main plugin commands.

antilavagrief.evadeGrief: Allows the player to bypass the lava grief alerts and actions.

antilavagrief.bypassCooldown: Allows the player to bypass the lava and flint-steel cooldown.

antilavagrief.commands.reload: Allows the player to reload plugin configuration


Directories:

config.yml
grief_logs.txt
IMPORTANT

This plugin does not prevent players from burning wood, instead, every time they do, it sends a notification to the chat and applies a cooldown. Additionally, it logs each attempt in a file for record-keeping.
