KINAI: Overview
===============

KUNAI aims to support extracting and storing java class files
from/to some archives.  This product is utility library, therefore,
using only the product do nothing.
This product makes sense under used in other products.

Features
--------

Only reading and writing from/to the following archives.

* jar files,
* war files (read only),
* class files,
* directories, and
* local maven repository (read only).

Requirements
------------

 This tool requires the following libraries.  Put them into the
directory which contains kunai-${project.version}.jar.

### Development

* Runtime/Development Environment
    * Java SE 8
* Project Management
    * [Maven 3.x](http://maven.apache.org/)
* Dependencies for Compilation
    * [ASM 5.0.4](http://asm.objectweb.org/)
* Test Environment
    * [JUnit 4.12](http://www.junit.org/)

Install
-------

 Simply run ```mvn package```, then maven compiles the project, runs
unit test, and build project archive in ```target``` directory.  In
first time for running maven, it requires much times to download
dependent libraries from Internet.

Contact
-------

* Author
    * Associate Professor, Haruaki Tamada (Ph.D)
* Affiliation
    * Faculty of Computer Science and Engineering, Kyoto Sangyo University.
* E-mail
    * tamada@users.noreply.github.com
* Web Page
    * http://github.com/tamada/kunai

License
-------

Apache License Version 2.0

    Copyright 2013- Haruaki Tamada

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
