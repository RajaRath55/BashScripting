#!/bin/bash

if [ $((1%2)) == 0 ]; then
  echo "Number is Even"
else
  echo "Number is Odd"
fi
