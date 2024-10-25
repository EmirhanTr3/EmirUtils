package xyz.emirdev.emirutils.guis;

import org.bukkit.Material;
import xyz.emirdev.emirutils.Utils;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class ForwardItem extends PageItem {

    public ForwardItem() {
        super(true);
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        ItemBuilder builder = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE);
        builder.setDisplayName(Utils.convertComponentToLegacyString(Utils.format("<#00ddee>Next page")))
                .addLoreLines(Utils.convertComponentToLegacyString(Utils.format(
                        gui.hasNextPage()
                        ? "<#00bbbb>Go to page <#00ddee>" + (gui.getCurrentPage() + 2) + "<#00bbbb>/<#00ddee>" + gui.getPageAmount()
                        : "<#00bbbb>There are no more pages")
                ));

        return builder;
    }

}