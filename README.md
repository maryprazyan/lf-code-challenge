## Task 

Add a new endpoint to the API to allow users to *update the greetings they created*.

## Solution

1. I build the app and fixed failing test, which return message type should have been "Bad Request"
2. Updated the HelloController with new PUT method
3. Wrote tests keeping the same coding style
4. Updated Greeting DTO using lomobok

## User Interface

For simple UI I used Swagger, which I'm using in my everyday work.

## Testing

I did testing both running unit tests and using Swagger

## Time Spent

I worked two days on this task, each day around 4-5 hours. So together 8-10 hours of work

## Better To have

1. It would be better to have more comments and descriptions
2. For better testing we can have "delete all" functionality, which would have delete all the data and testing of "update" could be done in this flow: deleteAll -> createData -> updateData
3. It would also be better to have HelloClient, so HelloService would call HelloClient's methods, but as they're very simple, I didn't add additional layer.

