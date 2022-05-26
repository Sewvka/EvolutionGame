package ru.nsu.ccfit.evolution.server.listeners;

import com.badlogic.gdx.utils.JsonValue;
import ru.nsu.ccfit.evolution.common.Abilities;
import ru.nsu.ccfit.evolution.server.AbilityModel;
import ru.nsu.ccfit.evolution.server.CreatureModel;
import ru.nsu.ccfit.evolution.server.GameStage;
import ru.nsu.ccfit.evolution.server.GameWorldState;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.util.Locale;

public class CheckChangesListener extends AbstractListener {
    public CheckChangesListener(GameWorldState gameWorldState, EvolutionGame evolutionGame) {
        super(gameWorldState, evolutionGame);
    }

    @Override
    public void handle(JsonValue httpResponse) {
        JsonValue response = httpResponse.get("response");
        if (response.has("creatures")) {
            JsonValue jsonCreatures = response.get("creatures");

            for (JsonValue jsonCreature = jsonCreatures.child; jsonCreature != null; jsonCreature = jsonCreature.next) {
                if (!jsonCreature.getBoolean("dead")) {
                    int creatureID = jsonCreature.getInt("id");
                    int playerID = jsonCreature.get("player").getInt("id");
                    CreatureModel c = new CreatureModel(creatureID);
                    c.setFatMax(jsonCreature.getInt("fat_max"));
                    c.setFatStored(jsonCreature.getInt("fat_reserve"));
                    c.setFood(jsonCreature.getInt("cur_food"));

                    if (jsonCreature.has("properties")) {
                        JsonValue jsonAbilities = jsonCreature.get("properties");
                        for (JsonValue jsonAbility = jsonAbilities.child; jsonAbility != null; jsonAbility = jsonAbility.next) {
                            int abilityID = jsonAbility.getInt("property");
                            int creatureID1 = jsonAbility.getInt("appointed");
                            int creatureID2 = jsonAbility.getInt("target");
                            AbilityModel a = new AbilityModel(Abilities.getAbilityName(abilityID), creatureID1, creatureID2);
                            c.getAbilities().add(a);
                            logger.info("Ability successfully added. Ability name: " + Abilities.getAbilityName(abilityID)
                                    + ", appointed creature ID: " + creatureID1 + ", target creature ID: " + creatureID2);
                        }
                    }

                    gameWorldState.getTables().get(playerID).getCreatures().put(creatureID, c);
                    logger.info("Creature successfully updated. CreatureID: " + creatureID);
                } else {
                    int playerID = jsonCreature.get("player").getInt("id");
                    int creatureID = jsonCreature.getInt("id");
                    gameWorldState.getTables().get(playerID).getCreatures().remove(creatureID);
                    logger.info("Creature successfully removed. CreatureID: " + creatureID);
                }
            }
        }
        if (response.has("game_stage")) {
            String gameStage = response.getString("game_stage").toLowerCase(Locale.ROOT);
            switch (gameStage) {
                case "development":
                    gameWorldState.setGameStage(GameStage.DEVELOPMENT);
                    break;
                case "feeding":
                    gameWorldState.setGameStage(GameStage.FEEDING);
                    break;
                case "extinction":
                    gameWorldState.setGameStage(GameStage.EXTINCTION);
                    break;
            }
        }
        if (response.has("turn")) {
            int activePlayerID = response.get("turn").getInt("id");
            gameWorldState.setActivePlayerID(activePlayerID);
        }

        if (response.has("food_available")) {
            gameWorldState.setFoodAvailable(response.getInt("food_available"));
        }
    }
}
