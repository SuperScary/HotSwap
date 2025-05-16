![CurseForge Game Versions](https://img.shields.io/curseforge/game-versions/404217?logo=curseforge&label=CurseForge&style=for-the-badge)
![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/DzrFkrG2?logo=modrinth&style=for-the-badge)
![GitHub branch check runs](https://img.shields.io/github/check-runs/SuperScary/HotSwap/main?style=for-the-badge)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/SuperScary/HotSwap/build.yml?style=for-the-badge)

![Demo GIF](https://i.ibb.co/q9y4SFq/ezgif-6-65308f07ed.gif)

# HotSwap

HotSwap is a lightweight, client-side Minecraft mod that automatically selects the optimal tool or weapon in your hotbar based on what you’re doing—be it mining, 
chopping, fighting, or digging. By leveraging Minecraft’s built-in item tags, 
HotSwap ensures you always have the right tool in hand without manual inventory juggling.

---

## 🚀 Features

- **Context-Aware Swapping**  
  Automatically switches to the “best” tool in your hotbar for the block you’re breaking or mob you’re hitting, based on enchantments and material tiers.
- **Customizable Priority**  
  Define your own tag priorities and blacklists in `hotswap.json5` to fine-tune which tools should be preferred or skipped.
- **On-the-Fly Enable/Disable**  
  Toggle HotSwap with a single keybind (default: `;`). When disabled, your hotbar remains unchanged.
- **Lightweight & Non-Invasive**  
  No extra GUIs or big configuration menus—just an easy-to-edit JSON5 file and a small, performant mod jar.

---

## 🎯 Installation

1. **Install Fabric** (or Forge, depending on build) for Minecraft 1.20+  
2. Download the latest `HotSwap-x.y.z.jar` from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/hotswap) or [Modrinth](https://modrinth.com/mod/hotswap).  
3. Copy the jar into your Minecraft `mods/` folder.  
4. Launch Minecraft with the Fabric (or Forge) profile.

---

## 🎮 Usage

1. **Toggle** — press your configured toggle key (default `H`) to enable or disable HotSwap.
2. **Mine or Chop** — break a block; HotSwap will swap in your best pickaxe/axe/shovel according to the tags in your hotbar.
3. **Fight** — left-click a mob; HotSwap will switch to the highest-damage sword or tool.
4. **Return** — once the action ends (block broken or mob dead), your previous hotbar slot is restored automatically.

---

## 🛠️ Keybinds & Commands

| Action         | Default Key    | Description                      |
|----------------|----------------|----------------------------------|
| Toggle HotSwap | ```;```        | Enable or disable the auto-swap  |
| Hold Off       | ```Left Alt``` | Temporarily disable auto-swaping |

> **Tip:** You can change or assign these in Minecraft’s **Options → Controls** menu under “HotSwap.”

---

## 📜 Changelog

See [the changelog](https://superscary.net/minecraft/hotswap/changelog) for a full history of changes and releases.

---

## 📄 License

HotSwap is released under the [MIT License](LICENSE).
