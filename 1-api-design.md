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

## Generate Initial Server Stub using Swagger tools

1. Create a json file and name it options.json

2. Add Json snippet below as contents of option.json

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
        "artifactId": "storelocator",
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

## Migrate the generated SpringBoot project to Spring 2.0

Change the java version to 1.8 by updating the java.version property in pom.xml as shown below

```
<properties>
    <java.version>1.8</java.version>
    <maven.compiler.source>${java.version}</maven.compiler.source>
    <maven.compiler.target>${java.version}</maven.compiler.target>
    <springfox-version>2.7.0</springfox-version>
</properties>
```

Update springboot version to 2.0.3.RELEASE as shown below

```
 <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.3.RELEASE</version>
</parent>

```

Replace the build element with below snippet

```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
