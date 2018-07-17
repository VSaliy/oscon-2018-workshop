# Store Locator API design
In this lab we will use Swagger/OpenAPI to design APIs required to build the Store Locator APP


## Login to Swaggerhub and create storelocator API

1. Navigate to [swaggerhub](https://swagger.io/tools/swaggerhub/) and click on signin option at the top 

2. From Product selection page, select SwaggerHub

![select product](/images/prodselection.png)

3. Select Log In with GitHub

![Login](/images/loginwithgh.png)

4. Click on "CREATE API" button under "MY hub" to create a new API

![Create API](/images/create-api.png)

5. Author Store locator API

### Setup github sync
In this section we will configure github sync to automatically update store locator API specification document stored in github repository. Follow instructions [here](https://app.swaggerhub.com/help/integrations/github-sync?_ga=2.56492203.1240020663.1531258195-243865128.1525394160) to configure github sync

7. Share the storelocator API with another person next to you and get some feedback

8. Generate Initial Server Stub using Swagger tools

    a. Create a json file and name it options.json

    b. Add Json snippet below as contents of option.json

    ```
    {
        "basePackage": "com.oscon2018.tutorials",
        "invokerPackage": "com.oscon2018.tutorials",
        "configPackage": "com.oscon2018.tutorials.configuration", 
        "modelPackage": "com.oscon2018.tutorials.models",
        "apiPackage": "com.oscon2018.tutorials.api",
        "serializableModel": true,
        "dateLibrary": "joda",
        "groupId": "com.oscon2018.tutorials",
        "artifactId": "store-locator",
        "artifactVersion": "1.0-SNAPSHOT",
        "java8": true,
        "async": true,
        "delegatePattern": true,
        "hideGenerationTimestamp": true
    }

    ``` 
if you want to see list of configuration options supported in json for springboot, run command below

```
swagger-codegen config-help -l spring
```

```
swagger-codegen generate -i storelocator.yaml -l spring -c options.json --skip-overwrite

```