package com.github.rsvoboda.sse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.Date;
import java.util.Random;

@Path("/server-sent-events-inject") public class SseInjectionResource {

    @Context
    private Sse sse;

    @GET @Path("sse/{id}")
    @Produces("text/event-stream")
    public void greenHouseStatus(@PathParam("id") final String id, @Context SseEventSink sink) {

        // customize delay of events based on id (expecting number)
        int tmp_delay = 1000;
        try { tmp_delay = tmp_delay + (Integer.parseInt(id) - 1) * 1000 / 4; } catch (NumberFormatException e) { /* ignore, keep default multiply */ }
        final int delay = tmp_delay;

        new Thread(() -> {
            try {
                for (int i = 1; i <= 15; i++) {
                    sink.onNext(createStatsEvent(sse.newEventBuilder().comment("greenhouse"), i));
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