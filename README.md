# SpringBoot to EventStore

A simple SpringBoot application that demonstrates the posting of events to EventStore.

## EventStore

This works with a [stock EventStore installation](http://docs.geteventstore.com/introduction/).

## Interesting Implementation Details

### Idempotency

EventStore's idempotency is based on the event ID header used when events are posted. This app generates a content hash based on the event body and uses this as the event ID, such that it is impossible to post a duplicate event.

### Groovy

We're using it.
