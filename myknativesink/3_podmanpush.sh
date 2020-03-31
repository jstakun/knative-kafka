#!/bin/bash

# use docker images | grep quarked to get the image ID for $1

podman login quay.io

podman tag $1 quay.io/jstakun/myknativesink:0.1

podman push quay.io/jstakun/myknativesink:0.1
