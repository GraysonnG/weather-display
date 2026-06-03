#!/bin/bash

ARCH_IP="192.168.1.168"
PORT=9876

sleep 5

curl -s -X POST http://$ARCH_IP:$PORT/online --max-time 10 || true