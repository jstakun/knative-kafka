#!/bin/bash

podman build -f src/main/docker/Dockerfile.native -t dev.local/jstakun/myknativesink:0.1 .
