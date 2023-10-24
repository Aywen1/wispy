package net.npcinteractive.TranscendanceEngine.Managers;

import lombok.AccessLevel;
import lombok.Getter;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.ICancellable;
import meteordevelopment.orbit.IEventBus;
import meteordevelopment.orbit.listeners.LambdaListener;
import net.npcinteractive.TranscendanceEngine.Misc.EVENT_SIDE;
import net.npcinteractive.TranscendanceEngine.TheGame;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventManager
{
    public static final IEventBus GAME_EVENTS = new EventBus();
    public static final IEventBus EVENT_BUS = new EventBus();

    @Getter(AccessLevel.PUBLIC)
    private static EventManager instance;

    public EventManager()
    {
        instance = this;
        EventManager.EVENT_BUS.registerLambdaFactory(TheGame.class.getPackage().getName(), new LambdaListener.Factory()
        {
            @Override
            public MethodHandles.Lookup create(Method lookupInMethod, Class<?> klass) throws InvocationTargetException, IllegalAccessException {
                return (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup());
            }
        });
        EventManager.EVENT_BUS.subscribe(this);

        EventManager.GAME_EVENTS.registerLambdaFactory(TheGame.getInstance().mainApp.getClass().getPackage().getName() , new LambdaListener.Factory()
        {
            @Override
            public MethodHandles.Lookup create(Method lookupInMethod, Class<?> klass) throws InvocationTargetException, IllegalAccessException {
                return (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup());
            }
        });
        EventManager.GAME_EVENTS.subscribe(this);
    }

    public <T extends ICancellable> void FireEvent(T event, EVENT_SIDE side)
    {
        if(side == EVENT_SIDE.GAME || side == EVENT_SIDE.BOTH)
            GAME_EVENTS.post(event);
        if(side == EVENT_SIDE.ENGINE || side == EVENT_SIDE.BOTH)
            EVENT_BUS.post(event);
    }
}
