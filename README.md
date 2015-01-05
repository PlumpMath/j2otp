j2otp
===========

java interacts with otp erlang: tlor which is a small facade of ejabberd.

2 Ways to use it
===========

ONE: as a CLI command:
1. java -jar j2otp-<version>.jar [-h]
2. java -jar j2otp-<version>.jar
--nodes=tlor@k27:tlor@as0
--cookie=abc
--user=newspub@xwtec.im
--password=Welc0me
--ins=publish
--args="/home/xwtec.im/newspub/news:XxX:YyY"
3. java -jar j2otp-<version>.jar --config=j2otp-options.json
json format like this:
{
  "_nodes": [
    "tlor@k27",
    "tlor@as0"
  ],
  "_cookie": "abc",
  "_client": "j2otp@K27",
  "_retry": 3,
  "_timeout": 500,
  "_user": "newspub@xwtec.im",
  "_password": "Welc0me",
  "_ins": "publish",
  "_arguments": [
    "/home/xwtec.im/newspub/news",
    "XxX",
    "YyY"
  ]
}

TWO: as a  java Library:

