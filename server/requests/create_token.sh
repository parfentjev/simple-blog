#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <username> <password>"
    exit 1
fi

curl http://localhost:8080/user/token --json "{\"username\":\"${1}\",\"password\":\"${2}\"}" -w "\n"
