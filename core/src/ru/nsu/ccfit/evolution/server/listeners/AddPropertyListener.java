package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.server.CreatureModel;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.server.TableModel;
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
                CreatureModel creature1 = table.getCreatures().get(gameWorldState.getTargetedCreatureID1());
                CreatureModel creature2 = table.getCreatures().get(gameWorldState.getTargetedCreatureID2());
                String ability = gameWorldState.getPlayedAbility();

                if (gameWorldState.getTargetedCreatureID1() != gameWorldState.getTargetedCreatureID2()) {
                    creature1.addAbility(ability, gameWorldState.getTargetedCreatureID1(), gameWorldState.getTargetedCreatureID2());
                    creature2.addAbility(ability, gameWorldState.getTargetedCreatureID2(), gameWorldState.getTargetedCreatureID1());
                } else {
                    creature1.addAbility(ability, gameWorldState.getTargetedCreatureID1(), gameWorldState.getTargetedCreatureID1());
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
