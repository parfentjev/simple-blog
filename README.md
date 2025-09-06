# Simple Blog

This repository contains the code that powers my blog: [Fake Plastic Trees](https://fakeplastictrees.ee/).

## Running Locally

To run the project locally, you’ll need to set up a database and configure environment variables.

1. The application requires a MariaDB server. Create a database and apply the schema located at `spec/schema.sql`.
2. Set up environment variables based on `example.env`:
    * Set `USER_REGISTRATION_DISABLED` to `false` to enable account creation
      via [http://localhost:8080/register](http://localhost:8080/register).
    * Provide your database credentials.
    * Fill in the remaining values - all are required.
3. Configure your IDE to use Java 21 and run `ee.fakeplastictrees.blog.Application`.

Other important URLs:

* [http://localhost:8080/login](http://localhost:8080/login)
* [http://localhost:8080/admin](http://localhost:8080/admin)
