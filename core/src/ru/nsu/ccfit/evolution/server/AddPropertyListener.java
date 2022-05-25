package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.common.Abilities;
import ru.nsu.ccfit.evolution.server.listeners.AbstractListener;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class AddPropertyListener extends AbstractListener {
    public AddPropertyListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        try {
            if (httpResponse.getBoolean("response")) {
                logger.info("Ability successfully placed.");
                TableModel table = gameWorldState.getTables().get(gameWorldState.getSelfID());
                CreatureModel creature1 = table.get(gameWorldState.getTargetedCreatureID1());
                CreatureModel creature2 = table.get(gameWorldState.getTargetedCreatureID2());
                String ability = gameWorldState.getPlayedAbility();

                if (Abilities.isCooperative(ability)) {
                    creature1.addCoopAbility(ability, creature2);
                    creature2.addCoopAbility(ability, creature1);
                } else {
                    creature2.addAbility(ability);
                }
                gameWorldState.getHand().remove(gameWorldState.getPlacedCardIndex());
            } else {
                logger.info("Ability couldn't be placed.");
            }
            gameWorldState.setTargetedCreatureID2(-1);
            gameWorldState.setTargetedCreatureID2(-1);
            gameWorldState.setPlacedCardIndex(-1);
            gameWorldState.setPlayedAbility(null);
        } catch (IllegalArgumentException ex) {
            logger.severe(httpResponse.getString("response"));
            gameWorldState.setGameID(-1);
        }
    }
}
