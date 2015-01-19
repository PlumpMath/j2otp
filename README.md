j2otp
===========
Interop with [tlor](https://github.com/junjiemars/tlor) an otp erlang node.

## Requirements
1. JDK1.6+
2. [gradle 2.1+](https://github.com/gradle/gradle.git)

## How to use
2 ways to use it: one via CLI, another just as general java library.
As use as java lib, the way is your favor, so I don't say anymore.

### via raw gnu-opt style:
```shell
java -jar j2otp-<version>.jar \
--nodes=tlor@abc:tlor@def \
--cookie=abc \
--user=newspub@abc.im \
--password=Welcome \
--ins=publish \
--args="/tmp/news:XxX:YyY" 
```
### via configuration file:
`java -jar j2otp-<version>.jar --conf=<your-json-file>`

the json configurations like the following:
```json
{
  "_nodes": [
    "tlor@abc",
    "tlor@def"
  ],
  "_cookie": "abc",
  "_client": "j2otp@abc",
  "_retry": 3,
  "_timeout": 500,
  "_user": "newspub@abc.im",
  "_password": "Welcome",
  "_ins": "publish",
  "_arguments": [
    "/tmp/news",
    "XxX",
    "YyY"
  ]
}
```
