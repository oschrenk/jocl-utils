/*
 * JOCL Utilities
 *
 * Copyright (c) 2011-2012 Marco Hutter - http://www.jocl.org
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.jocl.utils;

import static org.jocl.CL.clReleaseEvent;
import static org.jocl.CL.clWaitForEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jocl.cl_event;

/**
 * Utility methods related to events.
 */
public class Events
{
    /**
     * Compute the execution time for the given event, in milliseconds.
     * This may only be called if the event is associated with a command
     * queue for which profiling is enabled.
     *
     * @param event The event
     * @return The execution time, in milliseconds
     */
    public static double computeExecutionTimeMs(cl_event event)
    {
        long startTime = EventProfilingInfos.getCommandStart(event);
        long endTime = EventProfilingInfos.getCommandEnd(event);
        return (endTime-startTime) * 1e-6;
    }


    /**
     * Wait for the given events if they are not <code>null</code>.
     *
     * @param events The events to wait for
     */
    public static void waitFor(cl_event ... events)
    {
        if (events != null)
        {
            waitFor(Arrays.asList(events));
        }
    }

    /**
     * Wait for the given events if they are not <code>null</code>.
     *
     * @param events The events to wait for
     */
    public static void waitFor(Iterable<cl_event> events)
    {
        if (events != null)
        {
            List<cl_event> eventList = new ArrayList<cl_event>();
            for (cl_event event : events)
            {
                if (event != null)
                {
                    eventList.add(event);
                }
            }
            cl_event array[] = eventList.toArray(
                new cl_event[eventList.size()]);
            clWaitForEvents(array.length, array);
        }
    }


    /**
     * Release each of the given events if it is not <code>null</code>.
     *
     * @param events The events to release
     */
    public static void release(cl_event ... events)
    {
        if (events != null)
        {
            release(Arrays.asList(events));
        }
    }

    /**
     * Release each of the given events if it is not <code>null</code>.
     *
     * @param events The events to release
     */
    public static void release(Iterable<cl_event> events)
    {
        if (events != null)
        {
            for (cl_event event : events)
            {
                if (event != null)
                {
                    clReleaseEvent(event);
                }
            }
        }
    }


    /**
     * Private constructor to prevent instantiation
     */
    private Events()
    {

    }

}
