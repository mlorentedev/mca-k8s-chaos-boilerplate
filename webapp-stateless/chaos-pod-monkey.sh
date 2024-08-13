#!/bin/bash
# Randomly delete pods in a Kubernetes namespace.
set -ex

: ${DELAY:=10}
: ${NAMESPACE:=default}

while true; do
  kubectl \
    --namespace "${NAMESPACE}" \
    -o 'jsonpath={.items[*].metadata.name}' \
    get pods | \
      tr " " "\n" | \
      grep -v db | \
      shuf | \
      head -n 1 |
      xargs -t --no-run-if-empty \
        kubectl --namespace "${NAMESPACE}" delete pod
  sleep "${DELAY}"
done