#!/bin/sh

docker rmi ibelan_virtu_test_mongo_seed
docker rmi ibelan_virtu_test_backend
docker rmi ibelan_virtu_test_frontend
# docker rmi ibelan_virtu_test_backend_build
# docker rmi ibelan_virtu_test_frontend_build

docker volume rm ibelan_virtu_test_frontend_build_volume
docker volume rm ibelan_virtu_test_backend_build_volume
docker volume rm ibelan_virtu_test_postgres_volume
docker volume rm ibelan_virtu_test_mongo_volume

# docker volume rm $(docker volume ls -q)