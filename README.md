---

# AntiLavaGrief Plugin  

### Descripción  
AntiLavaGrief ayuda a prevenir el griefing con lava, pedernal y acero, y ahora también con lava casting. Notifica a los jugadores cuando se detectan acciones sospechosas, como colocar lava, incendiar tablones de madera o intentar crear grandes estructuras de adoquines con agua y lava. Esto garantiza una mejor protección contra tácticas de griefing como la quema de estructuras o la modificación masiva del terreno.  

### Características principales  
✅ **Alertas cuando la lava toca tablones de madera.**  
✅ **Notificaciones cuando un jugador usa pedernal y acero o coloca lava sobre tablones de madera.**  
✅ **Cooldown para evitar abuso al usar lava o pedernal y acero en bloques de madera.**  
✅ **Nuevo:** Detecta y previene **lava casting** rastreando la formación de adoquines y piedra.  
✅ **Fácil de usar.**  

---

### 📜 Comandos  
| Comando | Descripción |
|---------|------------|
| `/al enable` | Activa alertas para lava y objetos inflamables cerca de tablones de madera. |
| `/al disable` | Desactiva las alertas y detiene las notificaciones. |
| `/al help` | Muestra el menú de ayuda. |
| `/al author` | Muestra el autor del plugin. |
| `/al version` | Muestra la versión del plugin. |
| `/al reload` | Recarga la configuración del plugin. |

---

### 🔒 Permisos  
| Permiso | Descripción |
|---------|------------|
| `antilavagrief.commands.maincommand` | Permite el acceso a los comandos principales del plugin. |
| `antilavagrief.evadeGrief` | Permite al jugador ignorar las alertas y restricciones de griefing. |
| `antilavagrief.bypassCooldown` | Permite al jugador omitir el cooldown de lava y pedernal/acero. |
| `antilavagrief.commands.reload` | Permite recargar la configuración del plugin. |
| `antilavagrief.bypass_dispenser` | Permite a los jugadores evitar restricciones al colocar objetos prohibidos en dispensadores y tolvas. |

---

### ⚙️ Configuración (`config.yml`)  
```yaml
# AntiLavaGrief Configuration
messages:
  anti_grief_enable_text: "&aLava protection has been activated."
  cooldown_text: "&cYou need to wait &f%timeLeft%&c more seconds before using that again!"
  grief_text: "&4%player% has burned a %block% with a %item%"
  lava_place_text: "&4%placer% burned %block%"
  ban_lava_place: "&cYou can't place lava or use the flint and steel here."

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
```

---

### 📂 Archivos y directorios  
- `config.yml`  
- `grief_logs.txt`  

---

### 🌐 Contacto  
Puedes contactarme en mis redes sociales a través de mi sitio web:  
🔗 [https://duszafir.netlify.app/](https://duszafir.netlify.app/)  

---
(English version on spigot, https://www.spigotmc.org/resources/antilavagrieff.121853/)
