to get running: mvn spring-boot:run

Swagger UI available at "http://localhost:8080/swagger-ui.html"

simple URL redirect generation (using curl):

curl -sG --data-urlencode "url=$target_url" "http://localhost:8080/admin/create"

(replace "$target_url" with url for which abbreviation is generated.  Response will be the generated
redirect URL, presented in plaintext.


Keith's Wall of Text
=========================

Intro
-------------

This app demonstrates a couple of spring techniques.  Its primary function is to take a URL and provide a redirection
URL, as well as perform said redirection.  For example, you provide a url like "http://www.google.com", and it will
generate "http://localhost:8080/go/xDjr".  Calling the latter URL will then redirect to google.

It stores generated URLs in an in-memory database.  However, just by changing some config values (and adding
a dependency on whatever db driver), it can be made to use mysql.

Some clumsiness in the REST endpoints have to do with URL construction.  The redirect URL's domain and port number
are naturally context-depended, whereas only the path is stored in the database.  It would be a mistake to pollute the 
model (UrlAbbreviation) with the context-depended elements.  Yet it seems appropriate to provide an endpoint that furnishes the
fully assembled URL.  I'm generally disinclined to have REST endpoints produce models that are detached from the underlying
datamodel (and I'm loathe to have some DTO class for this purpose, but maybe that's the least-bad strategy).

URL Hashing...wut?
-------------

I see why some thought would be given to hashing a URL for this problem.  But there are two reasons I think time
would be better spent on other approaches:

* We're going to have to check for uniqueness anyway.  That is going to be the operation that would require optimization eventually.

* Query Strings.  I'm assuming query strings would be potentially included in the target URL.  Parameter ordering have no
   meaning in URLs ("?color=red&shape=square" is equal to "?shape=square&color=red".  So, to be complete, a true hash would
   have to account for that.

Of course, I'm all for encouraging developer indulgences for exploratory code, so have at it.  I'm just not convinced
unique-path generation has all that much bearing on the problem.


Data Model/Database
-------------

The UrlAbbreviation is the sole domain, persisted object.  I wouldn't even point it out, except I always thought syntricity
didn't seem to have such a core concept.  The annotations you see are JPA-related (except for the @ApiModel).  All fields
 are persisted.  If you're wondering where the SQL is that defines the schema, you won't find it.  Its just auto-created.
 
Hibernate is used, but you won't find any direct code references to it.
 
Spring Repository
-------------

The UrlAbbreviationRepository is what stores and retrieves data.  What's that?  Don't see an implementation of that 
interface?  Due to the magic of Spring's CrudRepository, it figures out how to retrieve data just by the method name,
no implementation needed.  For example, I could add a method "findById" and like magic, spring will figure it out.
 
This is pretty neat and something I haven't used yet in production.  

REST
-------------

There's a single class providing my rest endpoints, find in the rest package.  You'll not find a single call to 
serialize/de-serialize JSON.  Your use of GSON makes me wonder if there isn't some mismatch of domain/rest models.  
Not saying there's never a case for manual JSON control, but these days I'm more accustomed to have that stuff done
 by the framework, with occassional annotations for various tweaks.
 
Redirect Servlet
-------------

Doing an HTTP Redirect just ain't a duty of any REST endpoint.  Here we drop down to lower-level servlet code.  Note
at this level its up to us to grab the spring context ourselves (fortunately spring makes this pretty easy).  

You were right, the redirect method is indeed on the Response object.

I map this servlet to receive any requests that begin with "/go/".  If I tried to do just "/*", I'd be colliding with
 Spring's REST framework.  There are ways to handle this, but nothing I wanted to get into here, but nevertheless 
 acknowledge always generating URLs that include "/go/" is less than desired.
 
Swagger
-------------
A project named springfox provides swagger UI generation.  Its configuration is the Docket creation in UrlAbbreviatorApplication.
 
