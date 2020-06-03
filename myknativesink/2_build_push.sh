VERSION=0.2

podman login quay.io

podman build -f src/main/docker/Dockerfile.native -t dev.local/jstakun/myknativesink:$VERSION .

podman tag dev.local/jstakun/myknativesink:$VERSION quay.io/jstakun/myknativesink:$VERSION
podman push quay.io/jstakun/myknativesink:$VERSION
