# Armor Visibility
[![](http://cf.way2muchnoise.eu/full_387962_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/armor-visibility)
[![](https://img.shields.io/modrinth/dt/armor-visibility?logo=modrinth&style=flat)](https://www.modrinth.com/mod/armor-visibility)
[![](http://cf.way2muchnoise.eu/versions/387962.svg)](https://www.curseforge.com/minecraft/mc-mods/armor-visibility)

View yours and others' skins through armor.

When toggled (with `v` by default) your armor will stop rendering.

If you shift and press the toggle key (`V` by default) all player armor will stop rendering.

This mod is only client-side and won't affect the rendering of armor on other clients.

[YouTube Demonstration](https://youtu.be/y9PXSRNULdw)

## Configuration
The configuration file is located at `.minecraft/config/armor_visibility.json`. It can also be edited through in-game UI (using [Mod Menu](https://modrinth.com/mod/modmenu) on Fabric and Quilt).

### Default Config File
```json
{
  "version": 3,
  "keep_elytra_visible": false,
  "keep_cape_visible": true,
  "players_only": true
}
```

### Config Option Descriptions
- `keep_elytra_visible`: When true, elytra will remain visible.
- `keep_cape_visible`: When true, capes will remain visible.
- `players_only`: When true, armor rendering on other entities (zombies, armor stands, etc.) won't be toggle-able.

---

Report any issues on [GitHub](https://github.com/Trikzon/armor-visibility/issues). Chat on [Discord](https://discord.gg/aUwZKagWh2) in the `#mc-mods` channel.

Support the development of my mods on [Ko-fi](https://ko-fi.com/X7X8D56YI).

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/X7X8D56YI)
