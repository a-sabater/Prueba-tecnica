## Introduction

Asset manager that offers an asset upload feature and allows the user to search the uploaded assets (against the persisted information in this system).

## Quickstart

### Dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.17.0</version>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-api</artifactId>
    <version>2.8.3</version>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.8.3</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>
<dependency>
    <groupId>io.r2dbc</groupId>
    <artifactId>r2dbc-h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.openapitools</groupId>
    <artifactId>jackson-databind-nullable</artifactId>
    <version>0.2.6</version>
</dependency>
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-test</artifactId>
    <scope>test</scope>
</dependency>
```
### Installation

1. Clone the repository:
```bash
 git clone https://github.com/a-sabater/Prueba-tecnica.git
```
### Ejecution

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.inditex.asset.AssetApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

```shell
mvn spring-boot:run
```

A Postman collection is available in the folder asset.doc

### API Quickstart

#### uploadAssetFile
Performs the upload of the asset.

| Http Method     | POST                                                                                                                                                                                                                                                |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Path            | /api/mgmt/1/assets/actions/upload                                                                                                                                                                                                                   |
| Path parameters | N/A                                                                                                                                                                                                                                                 |
| Query params    | N/A                                                                                                                                                                                                                                                 |
| Request body    | AssetFileUploadRequest object:<br/><br/>  **filenafilename (String)**: The filename associated to the asset <br/>**encodedFile (String)**: The actual file<br/> **contentType (String)**: The filetype according to the MIME type (IANA) definition |


## Responses

### Sucess (202 ACCEPTED)

The api will return back the id of the asset uploaded:

```json
{
"id": "bd13ed73-1703-4a0b-9cd6-dd255fe0a416"
}
```
| Field | Type   | Description                                                   |
|-------|--------|---------------------------------------------------------------|
| id    | String | The id (from a uuid created internally) of the asset uploaded |

## Notes

#### getAssetsByFilter endpoint

Performs the searching (& filtering) of all the uploaded/registered assets. Available filters: filter uploadDate that is between uploadDateStart and uploadDateEnd.

| Http Method     | GET                                                                                                                                                                                                                                                                                                                                                                                                             |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Path            | /api/mgmt/1/assets/                                                                                                                                                                                                                                                                                                                                                                                             |
| Path parameters | **uploadDateStart (String)**: The start date for the filter (format yyyy-MM-dd'T'HH:mm:ss) <br/>uploadDateEnd (String)**: The end date for the filter (format yyyy-MM-dd'T'HH:mm:ss) <br/>**filename (String)**: The filename<br/> **filetype (String)**: The filetype according to the MIME type (IANA) definition <br/>**sortDirection (String)**: The direction of the ordering. Accepted values: ASC / DESC |                |
| Query params    | N/A                                                                                                                                                                                                                                                                                                                                                                                                             |
| Request body    | N/A                                                                                                                                                                                                                                                                                                                                                                                                             |

## Responses

### Sucess (200 OK)

The api will return back the list of assets: 

```json
[
   {
      "id":"bd13ed73-1703-4a0b-9cd6-dd255fe0a416",
      "filename":"ferret.jpg",
      "contentType":"jpg",
      "url":"https://container.inditex.org/file/bd13ed73-1703-4a0b-9cd6-dd255fe0a416",
      "uploadDate":"2025-01-25T10:42:27",
      "size":12
   },
   {
      "id":"9f42df79-a4cb-415d-b421-6c92c3d79d70",
      "filename":"bird.png",
      "contentType":"png",
      "url":" https://container.inditex.org/file/2",
      "uploadDate":"2025-01-05T08:00:00",
      "size":25
   },
   {
      "id":"bcb70f66-6c35-446a-8b7d-7e15707eb612",
      "filename":"flower.jpg",
      "contentType":"jpg",
      "url":" https://container.inditex.org/file/1",
      "uploadDate":"2024-12-05T11:00:00",
      "size":5
   }
]
```

| Field | Type   | Description                                                   |
|-------|--------|---------------------------------------------------------------|
| id    | String | The id (from a uuid created internally) of the asset uploaded |
| filename    | String | Name of the asset                                             |
| contentType    | String | The file type                                                 |
| size    | int    | The actual file size                                          |
| uploadDate    | String | The date & time the file was uploaded with format yyyy-MM-dd'T'HH:mm:ss            |

### Error (400)

Description: Malformed request.

### Error (500)

Description: An unexpected error occurred.

## Tecnology

- Java 21
- Type of the project:
  - Web service
- Spring Boot Framework 3.4.1

## Technical Design

### Architecture Design

The project uses a package based hexagonal design with 3 layers DOMAIN, APPLICATION, INFRASTRUCTURE (in onion mode). The Api-Rest is published using Webflux.

### Project Structure

This project only contains a module called asset package based hexagonal design.

### Integration with other systems

This project is integrated with r2dbc-H2 as database.