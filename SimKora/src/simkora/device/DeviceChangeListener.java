package simkora.device;

import org.ugr.bluerose.events.Event;
import org.ugr.bluerose.events.Value;

public class DeviceChangeListener extends org.ugr.bluerose.events.EventListener
{
    public DeviceChangeListener()
    {
        super(DeviceChangeEvent.TOPIC_EVENT_CHANGE);
    }

    public void performAction(Event event)
    {
        String name = event.getMemberValue("name").getString();
        Value val = event.getMemberValue("value");
        Object valobj=null;
        switch(val.type)
        {
        case Value.BOOLEAN_TYPE:
            valobj = val.getBoolean();
            break;
        case Value.INTEGER_TYPE:
            valobj = val.getInteger();
            break;
        case Value.FLOAT_TYPE:
            valobj = val.getFloat();
            break;
        }

        System.out.println("Query - " + name + " - " + valobj);
    }
}
