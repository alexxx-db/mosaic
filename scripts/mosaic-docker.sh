docker run --name mosaic-dev --rm -p 5005:5005 -v $PWD:/root/mosaic -e JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" -it mosaic-dev:jdk8-gdal3.4-spark3.2 /bin/bash