# json2txt

Json2Txt is a utility that can extract fields from JSON using XPath like paths. This utility has been released as open source by [Air New Zealand](http://airnz.co.nz) in the hopes that others find it useful. 

## Basic usage ##
For example, with the following JSON in a file named _store.json_:
```
{ "store": { "book": [ { "category": "reference", "author": "Nigel Rees", "title": "Sayings of the Century", "price": 8.95 }, { "category": "fiction", "author": "Evelyn Waugh", "title": "Sword of Honour", "price": 12.99 }, { "category": "fiction", "author": "Herman Melville", "title": "Moby Dick", "isbn": "0-553-21311-3", "price": 8.99 }, { "category": "fiction", "author": "J. R. R. Tolkien", "title": "The Lord of the Rings", "isbn": "0-395-19395-8", "price": 22.99 } ], "bicycle": { "color": "red", "price": 19.95 }, "misc/other": "none!" }}
```

The command:
```
java -jar json2txt.jar -i store.json "/store/book[0]/title"  "/store/book[0]/author"
```
Would output:
```
Sayings of the Century  Nigel Rees
```
With the fields separated by tabs.

The command:
```
java -jar json2txt.jar -i store.json -o authors.tsv "/store/book[*]/author"
```
Would generate a file named _authors.tsv_ containing:
```
Nigel Rees	Evelyn Waugh	Herman Melville	J. R. R. Tolkien
```

## Multi-line input ##
Json2txt treats each line of input as a unique JSON object. So given _people.json_:
```
{"firstName":"Bob","surname":"Jones","age":34,"address":{"line1":"123 SomeStreet","line2":"SomePlace","country":"SomeCountry"}}
{"firstName":"Mary","surname":"Smith","age":31,"address":{"line1":"12 Street","line2":"PlaceSome","country":"SomeCountry"}}
{"firstName":"Kilroy","surname":"Knows","age":29,"address":{"line1":"21 Jump Street","line2":"PlaceSome","country":"SomeCountry"}}
```
The output of
```
java -jar json2txt.jar -i people.json  "/firstName" "/surname" "/age" "/address/country"
```
Will be:
```
Bob     Jones   34      SomeCountry
Mary    Smith   31      SomeCountry
Kilroy  Knows   29      SomeCountry
```

## More Path examples ##
Given
```
{ "store": { "book": [ { "category": "reference", "author": "Nigel Rees", "title": "Sayings of the Century", "price": 8.95 }, { "category": "fiction", "author": "Evelyn Waugh", "title": "Sword of Honour", "price": 12.99 }, { "category": "fiction", "author": "Herman Melville", "title": "Moby Dick", "isbn": "0-553-21311-3", "price": 8.99 }, { "category": "fiction", "author": "J. R. R. Tolkien", "title": "The Lord of the Rings", "isbn": "0-395-19395-8", "price": 22.99 } ], "bicycle": { "color": "red", "price": 19.95 }, "misc/other": "none!" }}
```


|**Path** 	|**Description** 	|
|:---------|:----------------|
|`/store/book` 	|The Book JSON Array 	|
|`/store/book/author` 	|All of the authors of books 	|
|`/store/book[*]/author` 	|All of the authors of books 	|
|`/store/book[3]` 	|The 4th book in the Store 	|
|`/store/book[last()]` 	|The last book in the store 	|
|`/store/book[$-2]` 	|The second last book in the store |
|`/store/book[-3]` 	|The third last book in the store |
|`/store/book[0,1]` 	|The first two books in the store |
|`/store/book[0,1,-1]` 	|The first two and last books in the store |
|`/store/book[?(@.isbn)]` 	|All books that have an ISBN 	|
|`/store/book[?(@.author = "J. R. R. Tolkien")]` 	|Books written by Tolkien 	|
|`/store/misc\/other` 	|Escape character for Path 	|

## Credits ##
This utility uses the excellent [JCommander](http://jcommander.org/) and [JSONij](http://jsonij.plural.cc/projects/jsonij) libraries.
