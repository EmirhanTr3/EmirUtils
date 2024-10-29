package xyz.emirdev.emirutils.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.Utils;
import xyz.emirdev.emirutils.guis.BackItem;
import xyz.emirdev.emirutils.guis.ForwardItem;
import xyz.emirdev.emirutils.punishutils.HistoryEntry;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryCommand {

    @Command("history")
    @CommandPermission("emirutils.history")
    public void history(Player sender, OfflinePlayer target) {
        List<HistoryEntry> history = EmirUtils.get().getData().getHistory(target.getUniqueId());

        if (history.isEmpty()) {
            Utils.sendError(sender, "%s does not have any punishment history.", target.getName());
            return;
        }

        ItemStack borderItem = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        borderItem.editMeta(meta -> meta
                .setHideTooltip(true)
        );

        Item border = new SimpleItem(borderItem);

        List<Item> items = history.reversed().stream()
                .map(entry -> {
                    ItemStack item;
                    if (entry.getPlayerModerator() != null) {
                        item = new ItemStack(Material.PLAYER_HEAD);
                        item.editMeta(SkullMeta.class, meta -> meta
                                .setOwningPlayer(entry.getPlayerModerator())
                        );
                    } else {
                        item = new ItemStack(Material.BEDROCK);
                    }
                    item.editMeta(meta -> meta
                            .displayName(Utils.format("<red>ID: <gray>#%s", entry.getId()).decoration(TextDecoration.ITALIC, false))
                    );

                    List<Component> lore = new ArrayList<>();
                    lore.add(Utils.format("<red>Type: <gray>%s", entry.getType().name()));
                    lore.add(Utils.format("<red>Moderator: <gray>%s", entry.getPlayerModerator() != null ? entry.getPlayerModerator().getName() : "CONSOLE"));
                    if (entry.getReason() != null) lore.add(Utils.format("<red>Reason: <gray>%s", entry.getReason()));
                    if (entry.getDuration() != null) lore.add(Utils.format("<red>Duration: <gray>%s", Utils.parseDurationToString(entry.getDuration())));
                    lore.add(Utils.format("<red>Issued at: <gray>%s", Utils.formatDate(Date.from(Instant.ofEpochMilli(entry.getIssuedAt())))));

                    item.lore(
                            lore.stream()
                                    .map(line -> line.decoration(TextDecoration.ITALIC, false))
                                    .collect(Collectors.toList())
                    );

                    return new SimpleItem(item);
                })
                .collect(Collectors.toList());


        Gui gui = PagedGui.items()
                .setStructure(
                        "# # # # # # # # #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# # # < # > # # #")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('<', new BackItem())
                .addIngredient('>', new ForwardItem())
                .setContent(items)
                .build();

        Window window = Window.single()
                .setViewer(sender)
                .setTitle(Utils.convertComponentToLegacyString(Utils.format("<#00ddee>History of <#00bbbb>%s", target.getName())))
                .setGui(gui)
                .build();

        window.open();
    }
}
