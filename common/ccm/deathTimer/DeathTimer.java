package ccm.deathTimer;
import ccm.deathTimer.commands.Timer;
import ccm.deathTimer.network.PacketHandler;
import ccm.deathTimer.proxy.CommonProxy;
import ccm.deathTimer.server.EventTracker;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.utils.lib.Archive;
import ccm.deathTimer.utils.lib.Locations;
import ccm.nucleum_omnium.IMod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * Main mod class.
 * 
 * Main author: Dries007
 * Idea and concept: Claycorp
 * 
 * @author Dries007
 *
 */
@SuppressWarnings({"Dries007SpellingErrors"})
@Mod(modid = Archive.MOD_ID,
    name = Archive.MOD_NAME,
    useMetadata = true,
    dependencies = Archive.MOD_DEPENDANCIES)
@NetworkMod(clientSideRequired = false,
       serverSideRequired = false,
       channels = {Archive.MOD_CHANNEL_TIMERS},
       packetHandler = PacketHandler.class)
public class DeathTimer
{
    @Instance(Archive.MOD_ID)
    public static DeathTimer instance;
    
    @SidedProxy(serverSide = Locations.SERVER_PROXY,
            clientSide = Locations.CLIENT_PROXY)
    public static CommonProxy   proxy;
    
    @PreInit
    public void preInit(final FMLPreInitializationEvent event)
    {
        Config.runConfig(event.getSuggestedConfigurationFile());
    }
    
    @Init
    public void init(final FMLInitializationEvent event)
    {
        proxy.init();
        new EventTracker();
    }
    
    @ServerStarting
    public void serverStart(final FMLServerStartingEvent event)
    {
        event.registerServerCommand(new Timer());
        
        new ServerTimer();
    }
}
