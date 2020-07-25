# mongo-db-lego-store

### banner.txt
```
${AnsiColor.BRIGHT_RED}                          _                           ___   _
${AnsiColor.BRIGHT_RED}                          | |     ___   __ _   ___    / __| | |_   ___   _ _   ___
${AnsiColor.BRIGHT_RED}                          | |__  / -_) / _` | / _ \   \__ \ |  _| / _ \ | '_| / -_)
${AnsiColor.BRIGHT_RED}                          |____| \___| \__, | \___/   |___/  \__| \___/ |_|   \___|
${AnsiColor.BRIGHT_RED}                                       |___/
```

### link to Atlas Mongo
https://www.opencodez.com/java/use-mongodb-atlas-with-spring-boot.htm
> If your password is @bc123, you must escape the @ character when specifying the password in the connection string; e.g. %40bc123.
>
>MongoRepository<Document,Id> is an interface designed to offer functionality over a single class(document). 
>The type of the class is passed as a generic type parameter. 
>MongoTemplate is less abstract and can be used for queries against the entire database.
>
>
>https://www.baeldung.com/queries-in-spring-data-mongodb
    > https://www.baeldung.com/queries-in-spring-data-mongodb#querydsl-queries
>

### Aggregate on mongo compass etc.
```
db.legosets.aggregate([{
         $project : {
                 legoSetName: "$name",
                 avgRating : {$avg : "$reviews.rating"}
             }
         }])
```