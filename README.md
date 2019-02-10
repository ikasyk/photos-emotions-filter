# Photos Emotions Filter

The application retrieves photos from Flickr album and provides a filter by people emotions.

### Running
1. Build front-end scripts using following command:
```
mvn install
```
2. Build project and run the main `io.powersurfers.BootApplication` class.

### Environment variables

Provide values of variables for correct application run.

| Name                  | Description              |
|-----------------------|--------------------------|
|spring.data.mongodb.uri|URI for MongoDB connection started with `mongodb://`|

Database is used for photos URLs cache, so the first running retrieves all photos and saves to database. After that, page will be load without delays.