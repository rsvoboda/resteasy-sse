package com.github.rsvoboda.sse;

import org.jboss.resteasy.plugins.providers.sse.SseEventOutputImpl;
import org.jboss.resteasy.plugins.providers.sse.SseEventProvider;
import org.jboss.resteasy.plugins.providers.sse.SseImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.Date;
import java.util.Random;

@Path("/server-sent-events") public class SseResource {

    final Sse sse = new SseImpl(); //TODO inject this

    @GET @Path("sse/{id}")
    @Produces("text/event-stream")
    public void greenHouseStatus(@PathParam("id") final String id) {
        final SseEventSink eventSink = new SseEventOutputImpl(new SseEventProvider()); //TODO replace this with @Context injected value

        // customize delay of events based on id (expecting number)
        int tmp_delay = 1000;
        try { tmp_delay = tmp_delay + (Integer.parseInt(id) - 1) * 1000 / 4; } catch (NumberFormatException e) { /* ignore, keep default multiply */ }
        final int delay = tmp_delay;

        new Thread(() -> {
            try {
                for (int i = 1; i <= 15; i++) {
                    eventSink.send(createStatsEvent(sse.newEventBuilder().comment("greenhouse"), i));
                    Thread.sleep(delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private OutboundSseEvent createStatsEvent(final OutboundSseEvent.Builder builder, final int eventId) {
        return builder.id("" + eventId).data(GreenHouse.class,
                new GreenHouse(new Date().getTime(), 20 + new Random().nextInt(10), 30 + 20 + new Random().nextInt(10)))
                .mediaType(MediaType.APPLICATION_JSON_TYPE).build();
    }

}