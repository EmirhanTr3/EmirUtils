package xyz.emirdev.emirutils.guis;

import org.bukkit.Material;
import xyz.emirdev.emirutils.Utils;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class BackItem extends PageItem {

    public BackItem() {
        super(false);
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        ItemBuilder builder = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE);
        builder.setDisplayName(Utils.convertComponentToLegacyString(Utils.format("<#00ddee>Previous page")))
                .addLoreLines(Utils.convertComponentToLegacyString(Utils.format(
                        gui.hasPreviousPage()
                        ? "<#00bbbb>Go to page <#00ddee>" + gui.getCurrentPage() + "<#00bbbb>/<#00ddee>" + gui.getPageAmount()
                        : "<#00bbbb>You can't go further back")
                ));

        return builder;
    }

}