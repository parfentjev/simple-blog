#!/bin/bash
cd src/test/resources
rm -rf dump || exit 1
mongodump --db blog_integration --username root --password password --authenticationDatabase admin