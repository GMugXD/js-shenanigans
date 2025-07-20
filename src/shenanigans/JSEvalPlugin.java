package myplugin;

import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JSEvalPlugin extends Plugin {

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

    @Override
    public void init() {
        Log.info("JSEvalModified Plugin loaded.");
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("js", "<code...>", "Evaluates JavaScript code.", (args, player) -> {
            try {
                Object result = engine.eval(args[0]);
                player.sendMessage("[green]Result: " + result);
            } catch(Exception e){
                player.sendMessage("[scarlet]JS Error: " + e.getMessage());
            }
        });

        handler.<Player>register("runmagic", "", "Runs a custom JS script.", (args, player) -> {
            String[] scriptLines = new String[] {
                "Vars.state.rules.waveSpacing = 60;",
                "Vars.state.rules.env = 3;",
                "Groups.unit.each(u => u.kill());",
                "print('Magic script done.');"
            };

            for(String line : scriptLines){
                try {
                    engine.eval(line);
                } catch(Exception e){
                    player.sendMessage("[scarlet]JS Error: " + e.getMessage());
                    return;
                }
            }

            player.sendMessage("[green]Magic script completed!");
        });
    }
}
