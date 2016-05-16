# SpringBoot to EventStore

A simple SpringBoot application that demonstrates retrieving data from an external service, performing a transform and publishing an event to EventStore.

## EventStore

This works with a [stock EventStore installation](http://docs.geteventstore.com/introduction/).

## Interesting implementation details

### Idempotent, asynchronous and safe

EventStore's idempotency is based on the event ID header used when events are posted. This app generates a content hash based on the event body and uses this as the event ID, such that it is impossible to post a duplicate event.

Fire and forget. We don't care if we succeed or fail, really. Duplicate publications are harmless and failed publications can always be attempted again on the next poll.

### Stubbing external services with EventStore

EventStore is being used to stub an endpoint. I'm really happy with this pattern, as I can post any data I want in virtually any format to EventStore using Postman and then get a free RESTful endpoint. It makes it super easy to setup stubs of external services.

### Purely ETL, no models

There are no models, so our codebase isn't tied to any particular source or destination format. Obviously changes in the APIs of external systems may precipitate a change in our transforms, but this is easier to manage. 
