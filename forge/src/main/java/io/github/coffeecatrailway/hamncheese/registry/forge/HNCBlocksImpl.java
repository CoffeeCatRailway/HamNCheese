package io.github.coffeecatrailway.hamncheese.registry.forge;

import io.github.coffeecatrailway.hamncheese.HamNCheese;

/**
 * @author CoffeeCatRailway
 * Created: 30/03/2022
 */
public class HNCBlocksImpl
{
    public static String getWoodTypeId()
    {
        return HamNCheese.getLocation("maple").toString();
    }
}
