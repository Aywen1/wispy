package xyz.someboringnerd.superwispy.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import net.npcinteractive.TranscendanceEngine.Managers.FileManager;
import net.npcinteractive.TranscendanceEngine.Util.MiscUtil;
import org.reflections.Reflections;
import xyz.someboringnerd.superwispy.content.Chunk;
import xyz.someboringnerd.superwispy.content.blocks.Block;
import xyz.someboringnerd.superwispy.content.blocks.simple.AirBlock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BlockManager
{

    public static List<Texture> registeredBlocks = new ArrayList<>();

    private static final Map<Integer, Class<? extends Block>> blockMap = new HashMap<>();

    public BlockManager()
    {
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/air"));
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/grass"));
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/dirt"));
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/stone"));
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/log"));
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/leaves"));
        registeredBlocks.add(FileManager.getTexture("tiles/blocks/bedrock"));

        registeredBlocks.sort(Comparator.comparing(Texture::toString));


        /*
            Le code suivant permet de référencer automatiquement les blocks du jeu de manière simple et efficace.

            Paniquez pas, c'est juste l'orienté object et non un problème fondamental de design du langage /s
        */

        Reflections reflections = new Reflections("xyz.someboringnerd.superwispy.content.blocks");
        Set<Class<? extends Block>> blockClasses = reflections.getSubTypesOf(Block.class);

        List<Class<? extends Block>> sortedBlockClasses = new ArrayList<>(blockClasses);
        sortedBlockClasses.sort(Comparator.comparing(Class::getSimpleName));

        int i = 0;
        for (Class<? extends Block> blockClass : sortedBlockClasses)
        {
            try
            {
                Constructor<? extends Block> constructor = blockClass.getConstructor(Vector2.class, Chunk.class);
                blockMap.put(i, blockClass);
                i++;
            }
            catch (NoSuchMethodException e)
            {
                throw new RuntimeException("Block class " + blockClass.getSimpleName() + " is missing the required constructor.");
            }
        }
    }

    // ------------------------------------------------------------------------------
    //              À RETIRER QUAND LE SYSTÈME D'INVENTAIRE SERA FINI !!!!!!!
    // ------------------------------------------------------------------------------
    public static int selected = 2;
    public static void Update()
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            if(selected < registeredBlocks.size() - 1)
            {
                selected++;
            }else{
                selected = 1;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            if(selected > 1)
            {
                selected--;
            }else{
                selected = registeredBlocks.size() - 1;
            }
        }
    }

    // ------------------------------------------------------------------------------

    /**
     * Obtient l'id d'un block par référence a une classe héritant de block de manière future-proof
     * @param block : Classe du block (exemple : AirBlock.class)
     * @return son id
     */
    public static int getIDFromBlock(Class<? extends Block> block)
    {
        int i = 0;
        for(Class<? extends Block> block_tmp : blockMap.values())
        {
            if(block_tmp.getSimpleName().equalsIgnoreCase(block.getSimpleName()))
            {
                return i;
            }else{
                i++;
            }
        }

        return 0;
    }

    /**
     * Obtient l'id d'un block par référence a une instance de block de manière future-proof
     * @param block : instance du block
     * @return son id
     */
    public static int getIDFromBlock(Block block)
    {
        int i = 0;
        for(Class<? extends Block> block_tmp : blockMap.values())
        {
            if(block_tmp.getSimpleName().equalsIgnoreCase(block.getClass().getSimpleName()))
            {
                return i;
            }else{
                i++;
            }
        }

        return 0;
    }

    /**
     * Obtient un block a partir de son nom de classe.
     * @param position position du nouveau block dans le chunk
     * @param name name du block (exemple : AirBlock.getSimpleName())
     * @param chunk instance du chunk qui va contenir le nouveau block
     * @return nouvelle instance du block
     */
    public static Block getBlockFromName(Vector2 position, String name, Chunk chunk)
    {
        Class<? extends Block> blockClass = null;

        name = MiscUtil.addIfAbsent(name, "Block");

        for(Class<? extends Block> block_tmp : blockMap.values())
        {
            if(block_tmp.getSimpleName().equalsIgnoreCase(name))
            {
                blockClass = block_tmp;
            }
        }

        if (blockClass != null) {
            try {
                return blockClass.getConstructor(Vector2.class, Chunk.class)
                        .newInstance(position, chunk);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return new AirBlock(position, chunk);
    }

    /**
     * Obtient un block a partir de son id.<br>
     * Il est cependant FORTEMENT DÉCOURAGÉ de l'utiliser pour autre chose que le texture binding, vu que l'id d'un block peut changer quand un nouveau block est ajouté au jeu.
     * <br><br>
     * merci d'utiliser getBlockFromName pour un autre usage, a moins d'utiliser la fonction getIDFromBlock qui garanti que ce problème n'arrivera pas
     * @param position position du nouveau block dans le chunk
     * @param id id du block (exemple : 0)
     * @param chunk instance du chunk qui va contenir le nouveau block
     * @return nouvelle instance du block
     */
    public static Block getBlockByID(Vector2 position, int id, Chunk chunk)
    {
        Class<? extends Block> blockClass = blockMap.get(id);

        if (blockClass != null) {
            try {
                return blockClass.getConstructor(Vector2.class, Chunk.class)
                        .newInstance(position, chunk);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return new AirBlock(position, chunk);
    }
}
