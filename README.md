# Shared Library

This project works with public REST API - `https://rdb.altlinux.org/api/ `

Its essence is to take the names of two branches as input and output 3 JSON packages in accordance with the technical specifications:

1. All packages present in the 1st branch but not in the 2nd.
2. All packages present in the 2nd branch but not in the 1st. 
3. All packages whose version-release is greater in the 1st branch than in the 2nd.


## Directory structure


- **src**: Project source codes.
- **target**: Directory for compiled files and collected artifacts.
- **pom**: project dependencies
- **gitignore**: gitignor for github

## Installation and launch

1. Clone repository:
```bash
git clone https://github.com/swerg110/shared-libraries.git
```
2. Go to project directory:

```bash
cd shared-libraries
```
3. Get the project using Maven:
```bash
mvn clean install
```
4. Launch the application:
```bash
java -cp target/classes:target/dependency/* PackageComparatorCLI arg1 arg2
```
