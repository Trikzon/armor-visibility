# Armor-Visibility
[![](http://cf.way2muchnoise.eu/full_387962_Downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/armor-visibility) [![](http://cf.way2muchnoise.eu/versions/387962.svg)](https://www.curseforge.com/minecraft/mc-mods/armor-visibility)

[YouTube Video Demonstration](https://youtu.be/y9PXSRNULdw)

## What does this mod do?

When toggled (with `v` by default) your armor will stop rendering (including elytra).

If you shift and press the toggle key (`V` by default) all player armor will stop rendering (including elytra).

This mod works on vanilla servers too! This mod is only required by and only works on the client.

**Disclaimer:** Most servers have rules against the use of mods. I am not liable for any rule breakers.

## Configuration

The configuration file is located at `.minecraft/config/armor-visibility.json`.

The default config file:
```json
{
    "version": 1,
    "my_armor_visibility_toggle": true,
    "all_armor_visibility_toggle": true,
    "show_join_message": true,
    "keep_elytra_visible": false
}
```

The only options a player should touch are:

`show_join_message`: Whether to print to chat when joining a world if armor visibility has been toggled.
`keep_elytra_visible`: Whether elytra should stay visible when armor visibility is toggled.

## Supported Versions

![](https://raw.githubusercontent.com/Trikzon/lts-schedule/master/out/armor-visibility.png)

## License

All assets found in the following directories are licensed under All Rights Reserved.
- ./common/src/main/resources/assets/
- ./fabric/src/main/resources/assets/
- ./forge/src/main/resources/assets/

Everything else is licensed under MIT.

Want clarification or explicit permission? Contact me on [Discord](https://discord.gg/aqXkvbJ).

## Other Information

- Report issues here: https://github.com/Trikzon/armor-visibility/issues
- Join my discord: https://discord.gg/aqXkvbJ
- You can include this mod in your modpack as long as you don't claim the mod as your own.
