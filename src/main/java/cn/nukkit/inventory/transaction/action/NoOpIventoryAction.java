package cn.nukkit.inventory.transaction.action;

import cn.nukkit.Player;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.item.Item;

/**
 * @author joserobjr
 * @since 2021-12-11
 */
@PowerNukkitOnly
@Since("1.6.0.0-PN")
public abstract class NoOpIventoryAction extends InventoryAction {
    @PowerNukkitOnly
    @Since("1.6.0.0-PN")
    protected NoOpIventoryAction(Item sourceItem, Item targetItem) {
        super(sourceItem, targetItem);
    }

    @Override
    public boolean execute(Player source) {
        return true;
    }

    @Override
    public void onExecuteSuccess(Player source) {
        // Does nothing
    }

    @Override
    public void onExecuteFail(Player source) {
        // Does nothing
    }
}
