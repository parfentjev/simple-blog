#!/bin/bash
cd static/
minify index.css > index.min.css
minify index.js > index.min.js