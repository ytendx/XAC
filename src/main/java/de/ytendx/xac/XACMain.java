package de.ytendx.xac;

import de.ytendx.xac.checks.*;
import de.ytendx.xac.checks.combat.*;
import de.ytendx.xac.checks.movement.*;
import de.ytendx.xac.checks.player.*;
import de.ytendx.xac.commands.XACCommand;
import de.ytendx.xac.config.XACConfig;
import de.ytendx.xac.helper.FalseDetectionAssistant;
import de.ytendx.xac.notify.AuthorNotifyListener;
import de.ytendx.xac.utils.ServerWatchUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

public class XACMain extends JavaPlugin {


    private static ServerWatchUtil serverWatchUtil;
    private static XACMain instance;
    public static final String PREFIX = "§c§lXAC §8| §7";
    private XACConfig config;
    @Getter
    private FalseDetectionAssistant detectionAssistant;

    public boolean isSilent() {
        return config.isSilent();
    }

    public XACConfig getXACConfig() {
        return config;
    }

    public static XACMain getInstance() {
        return instance;
    }

    public static ServerWatchUtil getServerWatchUtil() {
        return serverWatchUtil;
    }

    @SneakyThrows
    @Override
    public void onEnable() {

        System.out.println("[XAC] Loading XAntiCheat...");

        instance = this;

        serverWatchUtil = new ServerWatchUtil(this);
        detectionAssistant = new FalseDetectionAssistant();

        if(!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        final File dataFile = new File(this.getDataFolder(), "config.json");

        if (!dataFile.exists()) {
            dataFile.createNewFile();

            final FileWriter writer = new FileWriter(dataFile);

            writer.write(XACConfig.getDefault().toString());
            writer.flush();
            writer.close();

            this.config = XACConfig.getDefault();
        } else {
            this.config = XACConfig.fromString(new String(Files.readAllBytes(dataFile.toPath())));
        }


        new RangeCheck(this);
        new IrregularMovementCheck(this);
        new TickCheck(this);
        new FlyCheck(this);
        new FastBreakPlaceCheck(this);
        new VelocityCheck(this);
        new AutoClickerCheck(this);
        new NoWebCheck(this);
        new IrregularBuildingCheck(this);
        new RegenCheck(this);
        new SpeedCheck(this);
        new KillAuraCheck(this);
        new InventoryMoveCheck(this);
        new ChestStealerCheck(this);
        new JesusCheck(this);
        new NoFallCheck(this);
        new MotionCheck(this);
        new AimCheck(this);
        new AuthorNotifyListener();

        System.out.println("[XAC] Registered all cheat checks.");
        this.getCommand("xac").setExecutor(new XACCommand());

        System.out.println("[XAC] Finished loading. XAC by ytendx is now ready to use.");
        System.out.println("[XAC] WARNING! This plugin is not recommended to use for production.");
    }

    @Override
    public void onDisable() {

    }
}
