#!/usr/bin/env bash

x=1

f() {
  (( x++ ))
  printf "%d\n" $x
}

g() {
  local x=10
  f
}

g
printf "%d\n" $x
