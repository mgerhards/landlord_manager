#!/bin/bash
set -euxo pipefail
docker compose -f docker-compose.dev.yml up --build